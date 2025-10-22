package com.app.config;

import java.util.Random;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchTestService {

	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job job;
	
	@Scheduled(cron = "0 */2 * * * *")// every 2min  // every hr 2nd min  * 2 * * * *  , * 20/2 7 * * *  = first task 7:20 repeat every 2min
	public void execute() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		System.out.println("Batch test Runner");
		
		JobParameters params = new JobParametersBuilder().addLong("run.id", new Random().nextLong(1000)).toJobParameters();
	
		JobExecution execution = launcher.run(job, params);
	
		System.out.println("launcher method is called");
		
		System.out.println(" job execution status "+execution.getExitStatus());
		
		
	}

}
