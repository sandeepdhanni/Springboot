package com.example.Actuators.myhelper;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;


@Controller
public class MyDbHealthService implements HealthIndicator {


    private static final String DB_SERVICE="Database service";

    public boolean isHealthGood(){
        //custom logic
        return true;
    }

    @Override
    public Health health() {
        if(isHealthGood()){
            return Health.up().withDetail(DB_SERVICE,"database is running up").build();
        }
        return Health.down().withDetail(DB_SERVICE,"database is running down").build();
    }
}
