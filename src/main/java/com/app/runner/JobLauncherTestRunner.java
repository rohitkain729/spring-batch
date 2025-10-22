package com.app.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherTestRunner implements CommandLineRunner{

	
	@Autowired
	private static JobLauncher launcher;
	
	@Autowired
	private static Job job;
	
	
	@Override
	public void run(String... args) throws Exception {
	
		try {
		JobParameters params = new JobParametersBuilder().addDate("startDate", new Date()).toJobParameters();
			
		JobExecution execution = launcher.run(job, params);
			
		System.out.println("Job Exec LauncherTestRunner.run()"+execution.getExitStatus());
		
		
			
		} catch (Exception e) {
		
		
		}
		
	}

}
