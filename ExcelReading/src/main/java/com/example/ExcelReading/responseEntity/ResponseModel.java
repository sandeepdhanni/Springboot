package com.example.ExcelReading.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {

    private String status;

    private Integer statusCode;

    private List<T> result;

    private String error;





}