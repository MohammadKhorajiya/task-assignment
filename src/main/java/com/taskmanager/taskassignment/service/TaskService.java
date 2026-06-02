package com.taskmanager.taskassignment.service;

import com.taskmanager.taskassignment.exception.ResourceNotFoundException;
import com.taskmanager.taskassignment.model.Priority;
import com.taskmanager.taskassignment.model.Task;
import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    private boolean isAdmin() {
        logger.debug("Checking if administrator");
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Async
    public void sendEmail(String to, String body)
    {
        logger.debug("Sending email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Task created");
        message.setText(body);
        javaMailSender.send(message);
        logger.info("Email sent successfully to {}", to);
    }

    public String createTasks(Task task) {
        logger.info("creating a task with title:{}", task.getTitle());
        if(task.getTitle() == null || task.getTitle().trim().isEmpty())
        {
            throw new RuntimeException("Validation Error: Title cannot be empty");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        task.setUsername(username);
        task.setCompletedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);

        sendEmail(user.getEmail(),
                "Your task '"+ task.getTitle() +"' was created successfully!");

        user.getTaskEntries().add(savedTask);
        userService.saveUser(user);
        return savedTask.getId();
    }

    public List<Task> getAll() {
        logger.info("getting all tasks");
        if (isAdmin()) {
            return taskRepository.findAll();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        return user.getTaskEntries();
    }

    public Page<Task> searchTasks(Boolean status, Priority priority, int page, int size) {
        logger.info("searching tasks for current user");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Task probe = new Task();
        probe.setUsername(username);
        if (status != null) {
            probe.setCompleted(status);
        }
        if (priority != null) {
            probe.setPriority(priority);
        }
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();
        Example<Task> example = Example.of(probe, matcher);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return taskRepository.findAll(example, pageable);
    }

    public Page<Task> findTasksPaginated(int page, int size) {
        logger.info("finding tasks paginated with page:{}", page);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return taskRepository.findByUsername(username, pageable);
    }

    public Task getTaskById(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!isAdmin() && !task.getUsername().equals(username)) {
            throw new ResourceNotFoundException("Access Denied: Task does not belong to you.");
        }
        return task;
    }

    public Task updateTask(String id, Task updatedTask) {
        logger.info("updating a task with id:{}", id);
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setPriority(updatedTask.getPriority() != null ? updatedTask.getPriority() : existingTask.getPriority());
        existingTask.setCompletedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public void deleteTaskById(String id) {
        logger.info("deleting a task with id:{}", id);
        Task task = getTaskById(id);
        User user = userService.findByUsername(task.getUsername());
        if (user != null) {
            user.getTaskEntries().remove(task);
            userService.saveUser(user);
        }
        taskRepository.delete(task);
    }

    public List<Task> searchTaskByTitle(String keyword) {
        logger.info("searching for a task with title:{}", keyword);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByTitleContainingIgnoreCaseAndUsername(keyword, username);
    }
}