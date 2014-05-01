package com.github.emalock3.spring.example;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration2 {
  
  @Autowired
  private JobBuilderFactory jobs;
  @Autowired
  private StepBuilderFactory steps;
  
  private Step step1() {
    return steps.get("batch2.step1")
            .<String, String> chunk(10)
            .reader(new ListItemReader<>(IntStream.range(0, 1000)
                    .mapToObj(i -> "batch2-hogehoge-" + (i + 1))
                    .collect(Collectors.toList()))
            )
            .writer(new EmptyItemWriter<>())
            .build();
  }
  
  private Step step2() {
    return steps.get("batch2.step2")
            .<String, String> chunk(10)
            .reader(new ListItemReader<>(IntStream.range(0, 1000)
                    .mapToObj(i -> "batch2-foobar-" + (i + 1))
                    .collect(Collectors.toList()))
            )
            .writer(new EmptyItemWriter<>())
            .build();
  }
  
  @Bean(name = "batch2.job1")
  public Job job1() {
    return jobs.get("batch2.job1")
            .start(step1())
            .build();
  }
  
  @Bean(name = "batch2.job2")
  public Job job2() {
    return jobs.get("batch2.job2")
            .start(step2())
            .build();
  }
  
}
