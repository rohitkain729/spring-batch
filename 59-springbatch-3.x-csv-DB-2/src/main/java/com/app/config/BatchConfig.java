package com.app.config;

import javax.sql.DataSource;

import com.app.repo.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.listener.JobMonitoringListener;
import com.app.model.Employee;
import com.app.processor.EmployeeItemProcessor;

@Configuration
//@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    private JobMonitoringListener listener;
    @Autowired
    private EmployeeItemProcessor processor;
    @Autowired
    private DataSource ds;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean(name = "reader")
    public FlatFileItemReader<Employee> createReader() {

        return new FlatFileItemReaderBuilder<Employee>().name("file-reader")
                .resource(new ClassPathResource("Employee_info.csv")).delimited()
                .names("empno", "empname", "empaddrs", "salary").targetType(Employee.class).build();
    }
    // bidefault it take delimited as ,

    @Bean(name = "writer")
    public RepositoryItemWriter<Employee> createWriter() {

        return new RepositoryItemWriterBuilder<Employee>()
                .repository(employeeRepository)
                .methodName("save")
                .build();
    }

    // create the step object
    @Bean(name = "step1")
    public Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {


        return new StepBuilder("step1", jobRepository)
                .<Employee, Employee>chunk(3, transactionManager)
                .reader(createReader())
                .writer(createWriter())
                .processor(processor)
                .build();
    }

    @Bean(name = "job1")
    public Job createJob1(JobRepository jobRepository, Step step1) {
        return new JobBuilder("job1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }




}
