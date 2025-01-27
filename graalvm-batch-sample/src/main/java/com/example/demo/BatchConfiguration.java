package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

	@Value("${csv.file.directory}")
	private String csvDirectoryPath;

	@Bean
	public FlatFileItemReader<Member> itemReader() {
		return new FlatFileItemReaderBuilder<Member>().name("personItemReader")
				.resource(new FileSystemResource(this.csvDirectoryPath + "/member.csv")).linesToSkip(1).delimited()
				.names("id", "firstName", "lastName").fieldSetMapper(fieldSet -> new Member(fieldSet.readString("id"),
						fieldSet.readString("firstName"), fieldSet.readString("lastName")))
				.build();
	}

	@Bean
	public MemberItemProcessor processor() {
		return new MemberItemProcessor();
	}

	@Bean
	public ItemWriter<FullNameMember> writer() {
		return chunk -> {
			for (FullNameMember fullNameMember : chunk) {
				System.out.println(fullNameMember.getId() + ":" + fullNameMember.getFirstName() + ":"
						+ fullNameMember.getLastName() + ":" + fullNameMember.getFullName());
			}
		};
	}

	@Bean
	public Step step(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
			FlatFileItemReader<Member> reader, MemberItemProcessor processor, ItemWriter<FullNameMember> writer) {
		return new StepBuilder("step", jobRepository).<Member, FullNameMember>chunk(3, transactionManager)
				.reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	public Job printDataJob(JobRepository jobRepository, Step step) {
		return new JobBuilder("printDataJob-" + System.currentTimeMillis(), jobRepository).start(step).build();
	}

}
