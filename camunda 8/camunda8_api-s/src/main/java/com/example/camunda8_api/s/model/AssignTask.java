package com.example.camunda8_api.s.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignTask {

    public String assignee;
    public Boolean allowOverrideAssignment;

}