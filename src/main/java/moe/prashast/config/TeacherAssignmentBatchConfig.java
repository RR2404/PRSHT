/*
package moe.prashast.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import moe.prashast.entity.TeacherSectionAssignment;
import moe.prashast.repository.TeacherSectionAssignmentRepository;
import moe.prashast.request.pojo.TchSectionAssignRequest;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TeacherAssignmentBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final TeacherSectionAssignmentRepository teacherSecAssignRepo;

    @Bean
    @StepScope
    public ListItemReader<TchSectionAssignRequest.AssignmentData> reader(
            @Value("#{jobParameters['requestData']}") String requestData)
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TchSectionAssignRequest request =
                mapper.readValue(requestData, TchSectionAssignRequest.class);

        return new ListItemReader<>(request.getAssignments());
    }


    @Bean
    @StepScope
    public ItemProcessor<TchSectionAssignRequest.AssignmentData, TeacherSectionAssignment> processor(
                         @Value("#{jobParameters['requestData']}") String requestData)throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TchSectionAssignRequest request =mapper.readValue(requestData, TchSectionAssignRequest.class);

        return data -> {

            Optional<TeacherSectionAssignment> optionalAssignment =teacherSecAssignRepo
                            .findBySchoolIdAndClassIdAndSectionIdAndYearId(
                                    request.getSchoolId(),
                                    data.getClassId(),
                                    data.getSectionId(),
                                    request.getYearId());

            if (optionalAssignment.isPresent()) {

                TeacherSectionAssignment assignment = optionalAssignment.get();

                assignment.setAssignTeacherId(data.getAssignTeacherId());
                assignment.setAssignTeacherName(data.getAssignTeacherName());
                assignment.setAssignEndDate(
                        LocalDate.parse(data.getDeadline()));
                assignment.setAssignStatus((short) 1);
                assignment.setModifiedTime(LocalDateTime.now());

                return assignment;
            }

            return null; // skip if not found
        };
    }

    @Bean
    public JpaItemWriter<TeacherSectionAssignment> writer() {

        return new JpaItemWriter<>(entityManagerFactory);
    }

    @Bean
    public Step teacherAssignmentStep() throws Exception {

        return new StepBuilder("teacherAssignmentStep", jobRepository)
                .<TchSectionAssignRequest.AssignmentData, TeacherSectionAssignment>chunk(50)
                .reader(reader(null))
                .processor(processor(null))
                .writer(writer())
                .transactionManager(transactionManager)
                .build();
    }
    @Bean
    public Job teacherAssignmentJob() throws Exception {

        return new JobBuilder("teacherAssignmentJob", jobRepository)
                .start(teacherAssignmentStep())
                .build();
    }



}
*/
