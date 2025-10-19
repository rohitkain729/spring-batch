package com.app.config;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.listener.JobMonitoringListener;
import com.app.processor.BookItemProcessor;
import com.app.reader.BookItemReader;
import com.app.writter.BookItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private BookItemReader reader;
	@Autowired
	private BookItemWriter writer;
	@Autowired
	private BookItemProcessor processor;

	@Autowired
	private JobMonitoringListener listener;

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private JobBuilderFactory jobFactory;

	@Bean(name = "step1")
	public Step createStep1() {
		return stepFactory.get("step1").<String, String>chunk(2).reader(reader).writer(writer).processor(processor)
				.build();
	}
	
	@Bean(name = "job1")
	public Job createJob() {
		return jobFactory.get("job1")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(createStep1())
				.build();
	}
	
	

}
