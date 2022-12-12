package com.example.demo.Configuration;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.example.demo.Entity.Employee;

@Configuration
@EnableBatchProcessing

public class EmployeesConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
//	@Autowired
//	private DataSource dataSource;

	@Value("${file.input}")
	private String fileInput;

	@Bean
	public FlatFileItemReader<Employee> reader() {
		System.out.println("flat file item reader");
		return new FlatFileItemReaderBuilder<Employee>().name("employeeItemReader")
				.resource(new ClassPathResource(fileInput)).delimited()
				.names(new String[] { "firstName", "lastName", "mail" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				}).build();

	}

	@Bean
	public EmployeeItemProcessor processor() {
		return new EmployeeItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
		System.out.println("batch item sql" + dataSource);
		return new JdbcBatchItemWriterBuilder<Employee>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO employee (id, first_name,last_name, mail) VALUES (null,:firstName, :lastName, :mail)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		System.out.println("job");
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Employee> writer) {
		System.out.println("step");
		return stepBuilderFactory.get("step1").<Employee, Employee>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}
}
