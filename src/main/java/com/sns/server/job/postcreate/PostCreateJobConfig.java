package com.sns.server.job.postcreate;

import com.sns.server.account.AccountRepository;
import com.sns.server.hashtag.HashTagRepository;
import com.sns.server.image.ImageRepository;
import com.sns.server.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "job.name", havingValue = "postCreateJob")
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class PostCreateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final ImageRepository imageRepository;

    @Bean
    public Job postCreateJob() {
        return jobBuilderFactory.get("postCreateJob").start(postCreateStep()).build();
    }

    @Bean
    public Step postCreateStep() {
        return stepBuilderFactory.get("postCreateStep").tasklet(postCreateTasklet()).build();
    }

    @Bean
    public Tasklet postCreateTasklet() {
        return new PostsCreateTasklet(accountRepository, postRepository, hashTagRepository, imageRepository);
    }

}
