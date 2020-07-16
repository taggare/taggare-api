package com.sns.server.job.postcreate;

import com.sns.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBatchTest()
@SpringBootTest(classes = Application.class)
@TestPropertySource(properties = "job.name=postCreateJob")
public class PostCreateJobTest {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    @Test
    public void runBatch() throws Exception {
        JobParameters jobParameter = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
        jobLauncherTestUtils.launchJob(jobParameter);
    }
}