package com.sgu.todo.service.impl;

import com.sgu.todo.entity.TaskList;
import com.sgu.todo.repository.TaskListRepository;
import com.sgu.todo.service.TaskListService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class TaskListServiceImpl implements TaskListService {
    final private TaskListRepository taskRepository;

    public TaskListServiceImpl(TaskListRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<TaskList> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskList save(TaskList taskList) {
        taskList.setFlgDelete("0");
        if(taskList.getTaskListId()==null){
            taskList.setCreateDate(new Date());
        }
        return taskRepository.save(taskList);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<TaskList> optionalTaskList = findById(id);
        TaskList taskList= new TaskList();
        taskList=optionalTaskList.get();
        taskList.setFlgDelete("1");
        taskRepository.save(taskList);
    }

    @Override
    public Optional<TaskList> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<TaskList> findByFlagDelete(String flag) {
        return taskRepository.findByFlgDelete(flag);
    }
}
