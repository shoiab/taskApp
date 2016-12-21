package com.taskapp.service.data;

import org.springframework.http.HttpStatus;

import com.taskapp.model.TaskModel;

public interface TaskService {

	HttpStatus createTask(TaskModel taskModel);

	TaskModel fetchTask(String taskName);

}
