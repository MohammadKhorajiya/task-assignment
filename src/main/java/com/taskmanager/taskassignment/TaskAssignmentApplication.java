package com.taskmanager.taskassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = "com.taskmanager.taskassignment")
public class TaskAssignmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(TaskAssignmentApplication.class, args);
	}

}
