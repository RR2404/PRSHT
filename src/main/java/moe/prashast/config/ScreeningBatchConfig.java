package moe.prashast.config;

import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.repository.SnapshotStudentDataPrstRepository;
import moe.prashast.repository.StudentPart1ScreeningRepository;
import moe.prashast.request.pojo.StudentScreeningData;
import moe.prashast.util.CommonUtil;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//@Configuration
//@EnableBatchProcessing
/*public class ScreeningBatchConfig {

    private final SnapshotStudentDataPrstRepository studentDataPrstRepo;
    private final StudentPart1ScreeningRepository screeningRepository;

    public ScreeningBatchConfig(
            SnapshotStudentDataPrstRepository studentDataPrstRepo,
            StudentPart1ScreeningRepository screeningRepository
    ) {
        this.studentDataPrstRepo = studentDataPrstRepo;
        this.screeningRepository = screeningRepository;
    }

    // ---------------- JOB ----------------

    @Bean
    public Job screeningJob(JobRepository jobRepository, Step screeningStep) {
        return new JobBuilder("screeningJob", jobRepository)
                .start(screeningStep)
                .build();
    }

    // ---------------- STEP ----------------

    @Bean
    public Step screeningStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              ItemReader<StudentScreeningData> reader,
                              ItemProcessor<StudentScreeningData, StudentPart1Screening> processor,
                              ItemWriter<StudentPart1Screening> writer) {

        return new StepBuilder("screeningStep", jobRepository)
                .<StudentScreeningData, StudentPart1Screening>chunk(50, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // ---------------- READER ----------------

    @Bean
    @StepScope
    public ItemReader<StudentScreeningData> reader(
            @Value("#{jobParameters['students']}") String studentsJson) {

        if (studentsJson == null || studentsJson.isEmpty()) {
            return new ListItemReader<>(Collections.emptyList());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<StudentScreeningData> students = Arrays.asList(
                    mapper.readValue(studentsJson, StudentScreeningData[].class)
            );
            return new ListItemReader<>(students);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid students JSON", e);
        }
    }

    // ---------------- PROCESSOR ----------------

    @Bean
    @StepScope
    public ItemProcessor<StudentScreeningData, StudentPart1Screening> processor(
            @Value("#{jobParameters['stateId']}") Short stateId,
            @Value("#{jobParameters['schoolId']}") Integer schoolId,
            @Value("#{jobParameters['yearId']}") Integer yearId,
            @Value("#{jobParameters['classId']}") Short classId,
            @Value("#{jobParameters['sectionId']}") Short sectionId
    ) {

        return studentReq -> {

            StudentPart1Screening screeningObj;

            if (studentReq.getScreeningId() != null) {

                screeningObj = screeningRepository
                        .findById(studentReq.getScreeningId())
                        .orElseThrow(() -> new RuntimeException("Screening Not Found"));

                screeningObj.setModifiedTime(LocalDateTime.now());

            } else {

                screeningObj = new StudentPart1Screening();
                screeningObj.setCreatedTime(LocalDateTime.now());
                screeningObj.setStateId(stateId);
                screeningObj.setSchoolId(schoolId);
                screeningObj.setYearId(yearId);
                screeningObj.setClassId(classId);
                screeningObj.setSectionId(sectionId);
                screeningObj.setStudentId(studentReq.getStudentId());

                studentDataPrstRepo.findByIdStudentId(studentReq.getStudentId())
                        .ifPresent(studentData -> {
                            screeningObj.setStudentPen(studentData.getStudentPen());
                            screeningObj.setStudentName(studentData.getStudentName());
                            screeningObj.setMotherName(studentData.getMotherName());
                            screeningObj.setFatherName(studentData.getFatherName());
                            screeningObj.setGuardianName(studentData.getGuardianName());
                            screeningObj.setGender(studentData.getGender());
                            screeningObj.setStudentDob(studentData.getStudentDob());
                        });
            }

            screeningObj.setScreeningDoneYn(studentReq.getScreeningDoneYN());
            screeningObj.setScreeningNotDoneReason(studentReq.getScreeningNotDoneReason());
            screeningObj.setScreeningQuestionCount(studentReq.getScreeningQuestionCount());
            screeningObj.setNoConcernYn(studentReq.getNoConcernYN());
            screeningObj.setEligiblePart2Yn(studentReq.getEligiblePart2YN());

            screeningObj.setScreeningAnswers(
                    CommonUtil.formatTo128Bit(studentReq.getScreeningAnswer())
            );

            screeningObj.setScreeningOn(LocalDateTime.now());

            return screeningObj;
        };
    }

    // ---------------- WRITER ----------------

    @Bean
    public ItemWriter<StudentPart1Screening> writer() {
        return screeningRepository::saveAll;
    }
}*/
