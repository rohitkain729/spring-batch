package com.app.config;

import com.app.listener.JobMonitoringListener;
import com.app.model.Employee;
import com.app.processor.EmployeeItemProcessor;
import lombok.Builder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import javax.sql.DataSource;

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

	@Bean(name = "reader") // using anonymus sub class + instance block
	public FlatFileItemReader<Employee> createReader() {
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>() {
			{
				// call all non static methods
				setResource(new ClassPathResource("Employee_info.csv"));
				setLineMapper(new DefaultLineMapper<>() {
					{
						setLineTokenizer(new DelimitedLineTokenizer() {
							{
								setDelimiter(",");
								setNames("empno", "empname", "empaddrs", "salary");
							}
						});
						setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
							{
								setTargetType(Employee.class);
							}
						});
					}
				});

			}
		};
		return reader;
	}
	@Bean(name = "writer")
	public JdbcBatchItemWriter<Employee> createWriter() {
		JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<Employee>() {
			{
				setDataSource(ds);
				setSql("INSERT INTO BATCH_EMPLOYEE1 VALUES (:empno,:empname,:empaddrs,:salary,:grossSalary,:netSalary)");
				setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
			}
		};
		return writer;
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
