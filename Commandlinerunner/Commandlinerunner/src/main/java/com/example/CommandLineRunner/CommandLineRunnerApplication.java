package com.example.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineRunnerApplication implements CommandLineRunner {

	private static Logger LOG= LoggerFactory.getLogger(CommandLineRunnerApplication.class);

	public static void main(String[] args) {
		LOG.info("\n 1. STARTING: springboot appllication starting");
		SpringApplication.run(CommandLineRunnerApplication.class, args);
		LOG.info("\n 3. STOPPED: springboot application stopped");
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("2. EXECUTING: commandline runner");

		for(int i=1;i<=5;i++){
			LOG.info("Count:"+i);
		}
	}
}
