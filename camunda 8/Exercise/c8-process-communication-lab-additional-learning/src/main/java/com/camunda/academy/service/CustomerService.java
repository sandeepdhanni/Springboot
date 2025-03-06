package com.camunda.academy.service;

public class CustomerService {

    public void notifyTravelConfirmed(String travelRequestId) {

		System.out.println(travelRequestId + " Business Travel confirmed");
    }
    
    public void notifyTravelCancelled(String travelRequestId) {

		System.out.println(travelRequestId + " Bussiness Travel cancelled");
    }
    
    
}