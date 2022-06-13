package com.example.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.spring.batch.chunk.listener.CustomChunkListener;
import com.example.spring.batch.item.process.listener.StepItemProcessListener;
import com.example.spring.batch.item.write.listener.StepItemWriteListener;
import com.example.spring.batch.jobexecutionlistener.JobResultListener;
import com.example.spring.batch.model.Employee;
import com.example.spring.batch.processor.EmployeeProcessor;
import com.example.spring.batch.skip.listener.StepSkipListener;
import com.example.spring.batch.step.execution.listener.StepResultListener;

@Configuration
@EnableBatchProcessing
public class EmployeeBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EmployeeProcessor processor;

	@Autowired
	private MongoTemplate template;

	@Bean
	public FlatFileItemReader<Employee> reader() {
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("Sample.csv"));

		reader.setLineMapper(new DefaultLineMapper<Employee>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "name", "age", "address" });
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				});
			}
		});
		return reader;
	}

//	private LineMapper<Customer> lineMapper() {
//		// TODO Auto-generated method stub
//		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
//
//		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//		lineTokenizer.setDelimiter(",");
//		lineTokenizer.setStrict(false);
//		lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
//
//		BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//		fieldSetMapper.setTargetType(Customer.class);
//
//		lineMapper.setLineTokenizer(lineTokenizer);
//		lineMapper.setFieldSetMapper(fieldSetMapper);
//		return lineMapper;
//	}
//	
	@Bean
	public MongoItemWriter<Employee> writer() {
		MongoItemWriter<Employee> writer = new MongoItemWriter<Employee>();
		writer.setTemplate(template);
		writer.setCollection("Employee1");
		return writer;
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step").<Employee, Employee>chunk(2).reader(reader()).processor(processor)
				.writer(writer()).listener(new CustomChunkListener()).listener(new StepResultListener()).listener(new StepItemProcessListener())
				.listener(new StepItemWriteListener())
				.listener(new StepSkipListener())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new JobResultListener()).start(step()).build();
	}

}
