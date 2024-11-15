package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class CheckWeatherDelgate implements JavaDelegate{

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Random rand=new Random();

//        execution.setVariable("name","sandeep");
        execution.setVariable("weatherOk",rand.nextBoolean());
        System.out.println("the weather is okk...");
    }
}
