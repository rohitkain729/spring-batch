package com.app.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.app.listener.JobMonitoringListener;
import com.app.model.Employee;
import com.app.processor.EmployeeItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private JobMonitoringListener listener;
	@Autowired
	private EmployeeItemProcessor processor;
	@Autowired
	private DataSource ds;

	@Bean(name = "reader")
	public FlatFileItemReader<Employee> createReader() {

		return new FlatFileItemReaderBuilder<Employee>().name("file-reader")
				.resource(new ClassPathResource("Employee_info.csv")).delimited()
				.names("empno", "empname", "empaddrs", "salary").targetType(Employee.class).build();
	}
	// bidefault it take delimited as ,

	@Bean(name = "writer")
	public JdbcBatchItemWriter<Employee> createWriter() {

		return new JdbcBatchItemWriterBuilder<Employee>().dataSource(ds)
				.sql("INSERT INTO BATCH_EMPLOYEE VALUES (:empno,:empname,:empaddrs,:salary,:grossSalary,:netSalary)")
				.beanMapped().build();
	}

	// create the step object
	@Bean(name = "step1")
	public Step createStep1() {
		return stepFactory.get("step1").<Employee, Employee>chunk(3).reader(createReader()).writer(createWriter())
				.processor(processor).build();
	}

	@Bean(name = "job1")
	public Job createJob1() {
		return jobFactory.get("job1").incrementer(new RunIdIncrementer()).listener(listener).start(createStep1())
				.build();
	}
}
