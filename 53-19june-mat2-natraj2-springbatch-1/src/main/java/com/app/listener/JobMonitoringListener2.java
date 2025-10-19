package com.app.listener;
//package com.app.listener;
//
//import java.util.Date;
//
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.stereotype.Component;
//
//@Component("jobListener")
//public class JobMonitoringListener implements JobExecutionListener {
//
//	private long startTime, endTime;
//
//	@Override
//	public void beforeJob(JobExecution jobExecution) {
//
//		System.out.println("JobMonitoringListener.beforeJob()::" + new Date());
//
//		startTime = System.currentTimeMillis();
//
//		System.out.println("job status::" + jobExecution.getStatus());
//	}
//
//	@Override
//	public void afterJob(JobExecution jobExecution) {
//
//		System.out.println("JobMonitoringListener.afterJob()::" + new Date());
//
//		endTime = System.currentTimeMillis();
//
//		System.out.println("job execution time is ::" + (endTime - startTime));
//
//		System.out.println("job status::" + jobExecution.getStatus());
//
//	}
//}
