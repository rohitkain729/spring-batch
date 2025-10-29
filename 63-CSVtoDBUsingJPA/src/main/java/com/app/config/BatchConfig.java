package com.app.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.app.entity.OExamResult;
import com.app.listener.JobMonitoringListener;
import com.app.model.IExamResult;
import com.app.processor.ExamResultProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobFactory;

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private EntityManagerFactory emFactory;

	@Autowired
	private JobMonitoringListener listener;

	@Autowired
	private ExamResultProcessor processor;

	// listener
	@Bean
	public JobExecutionListener createListener() {
		return new JobMonitoringListener();
	}

	// processor
	@Bean
	public ExamResultProcessor createProcessor() {
		return new ExamResultProcessor();
	}

	@Bean(name = "ffiReader")
	public FlatFileItemReader<IExamResult> createReader() {
		return new FlatFileItemReaderBuilder<IExamResult>().name("file-reader")
				.resource(new ClassPathResource("topbrains.csv")).delimited().delimiter(",")
				.names("id", "dob", "percentage", "semester")
				.fieldSetMapper(new BeanWrapperFieldSetMapper<IExamResult>() {
					{
						setTargetType(IExamResult.class);
					}
				}).build();
	}

//	@Bean("miw")
//	public MongoItemWriter<OExamResult> createWriter() {
//		return new MongoItemWriterBuilder<OExamResult>().template(template).collection("SuperBrains").build();
//	}

	@Bean(name = "jpaWriter")
	public JpaItemWriter<OExamResult> createWriter() {
		return new JpaItemWriterBuilder<OExamResult>().entityManagerFactory(emFactory).build();
	}

	@Bean(name = "step1")
	public Step createStep1() {
		return stepFactory.get("step1").<IExamResult, OExamResult>chunk(4).reader(createReader()).writer(createWriter())
				.processor(processor).build();
	}

	@Bean(name = "job1")
	public Job createJob1() {
		return jobFactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();
	}
}
