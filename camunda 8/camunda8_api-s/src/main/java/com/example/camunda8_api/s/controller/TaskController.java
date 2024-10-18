package com.example.camunda8_api.s.controller;

import com.example.camunda8_api.s.model.AssignTask;
import com.example.camunda8_api.s.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

        @Autowired
        TaskService taskService;

//        @PostMapping("/completeTask")
//        public String completeTask(@RequestBody CompleteTaskModel completeTask){
//            return auditRequestService.completeTask(completeTask);
//        }

//        @PostMapping("/assignTask")
//        public Object assignTask(@RequestBody AssignTask assignTask){
//            return auditRequestService.getAssignTask(assignTask);
//        }



    // Assign Task
    @PostMapping("/{taskId}/assign")
    public Object assignTask(@PathVariable String taskId, @RequestBody AssignTask assignTask) {
        return taskService.assignTask(taskId, assignTask);
    }

    // Complete Task
    @PostMapping("/{taskId}/complete")
    public Object completeTask(@PathVariable String taskId) {
        return taskService.completeTask(taskId);
    }

    // Unassign Task
    @PatchMapping("/{taskId}/unassign")
    public Object unassignTask(@PathVariable String taskId) {
        return taskService.unassignTask(taskId);
    }

    // Get Task
    @GetMapping("/{taskId}")
    public Object getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }



    }

