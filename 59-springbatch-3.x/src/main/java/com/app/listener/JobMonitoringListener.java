package com.app.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("jobListener")
public class JobMonitoringListener implements JobExecutionListener {

	private Long start, end;

	@Override
	public void beforeJob(JobExecution jobExecution) {

		System.out.println("JobMonitoringListener.beforeJob()::" + new Date());

		start = System.currentTimeMillis();
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		System.out.println("JobMonitoringListener.afterJob()::" + new Date());

		end = System.currentTimeMillis();

		System.out.println("job execution time is  ::" + (end - start));

	}
}
