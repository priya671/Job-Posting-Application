package com.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.edu.dao.interfaces.JobPostingRepository;
import com.edu.entity.JobPosting;
import com.edu.exception.GlobalException;
import com.edu.service.impl.JobPostingServiceImpl;
import com.edu.service.interfaces.JobPostingService;

public class JobPostingServiceTest {
	
	 @Mock
	    private JobPostingRepository jobPostingRepository;

	    @InjectMocks
	    private JobPostingServiceImpl jobPostingService;

	    private JobPosting jobPosting;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);

	        jobPosting = new JobPosting();
	        jobPosting.setId(1L);
	        jobPosting.setTitle("Java Developer");
	        jobPosting.setDescription("Develop and maintain Java applications.");
	        jobPosting.setLocation("Hyderabad");
	        jobPosting.setCompany("Tech Solutions Pvt. Ltd.");
	        jobPosting.setSalaryRange("6-8 LPA");
	        jobPosting.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "Hibernate"));
	        jobPosting.setApplicationDeadline(new Date());
	    }

	    @Test
	    void testCreateJobPosting() {
	        when(jobPostingRepository.save(any(JobPosting.class))).thenReturn(jobPosting);

	        JobPosting createdJobPosting = jobPostingService.createJobPosting(jobPosting);

	        assertEquals(jobPosting.getId(), createdJobPosting.getId());
	        verify(jobPostingRepository, times(1)).save(jobPosting);
	    }

	    @Test
	    void testGetAllJobPostings() {
	        List<JobPosting> jobPostings = Arrays.asList(jobPosting);
	        when(jobPostingRepository.findAll()).thenReturn(jobPostings);

	        List<JobPosting> retrievedJobPostings = jobPostingService.getAllJobPostings();

	        assertEquals(1, retrievedJobPostings.size());
	        verify(jobPostingRepository, times(1)).findAll();
	    }

	    @Test
	    void testGetJobPostingById_Success() throws GlobalException {
	        // Arrange
	        Long id = 1L;
	        JobPosting jobPosting = new JobPosting();
	        jobPosting.setId(id);
	        // Set other properties of jobPosting as needed

	        // Mock the repository to return the JobPosting when findById is called
	        when(jobPostingRepository.findById(id)).thenReturn(Optional.of(jobPosting));

	        // Act
	        JobPosting result = jobPostingService.getJobPostingById(id);

	        // Assert
	        assertNotNull(result, "The result should not be null");
	        assertEquals(id, result.getId(), "The ID should match");
	        // Add more assertions as needed to check other properties
	    }
	    
	    @Test
	    void testGetJobPostingById_NotFound() {
	        // Arrange
	        Long id = 1L;

	        // Mock the repository to return an empty Optional when findById is called
	        when(jobPostingRepository.findById(id)).thenReturn(Optional.empty());

	        // Act & Assert
	        Exception exception = assertThrows(GlobalException.class, () -> {
	            jobPostingService.getJobPostingById(id);
	        });

	        assertEquals("JobPosting id= " + id + " does not exist", exception.getMessage(), "The exception message should match");
	    }


	    @Test
	    void testUpdateJobPosting() throws GlobalException {
	        when(jobPostingRepository.findById(1L)).thenReturn(Optional.of(jobPosting));
	        when(jobPostingRepository.save(any(JobPosting.class))).thenReturn(jobPosting);

	        JobPosting updatedDetails = new JobPosting();
	        updatedDetails.setTitle("Senior Java Developer");
	        updatedDetails.setDescription("Develop and maintain Java applications and mentor juniors.");
	        updatedDetails.setLocation("Bangalore");
	        updatedDetails.setCompany("Tech Solutions Pvt. Ltd.");
	        updatedDetails.setSalaryRange("8-10 LPA");
	        updatedDetails.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "Microservices"));
	        updatedDetails.setApplicationDeadline(new Date());

	        JobPosting updatedJobPosting = jobPostingService.updateJobPosting(1L, updatedDetails);

	        assertEquals("Senior Java Developer", updatedJobPosting.getTitle());
	        verify(jobPostingRepository, times(1)).save(any(JobPosting.class));
	    }

	    @Test
	    void testSearchJobPostings() {
	        List<JobPosting> jobPostings = Arrays.asList(jobPosting);
	        when(jobPostingRepository.findByTitleContainingOrLocationContainingOrRequiredSkillsContaining(
	            anyString(), anyString(), anyString())).thenReturn(jobPostings);

	        List<JobPosting> result = jobPostingService.searchJobPostings("Engineer", "Hyderabad", "Java");

	        assertEquals(1, result.size());
	        assertEquals("Java Developer", result.get(0).getTitle());
	        verify(jobPostingRepository, times(1))
	            .findByTitleContainingOrLocationContainingOrRequiredSkillsContaining("Engineer", "Hyderabad", "Java");
	    }
	    
	    @Test
	    void whenJobPostingExists_thenDeleteIt() throws GlobalException {
	        // Arrange
	        Long id = 1L;
	        JobPosting jobPosting = new JobPosting();
	        jobPosting.setId(id);
	        // Mock the repository to return the JobPosting when findById is called
	        when(jobPostingRepository.findById(id)).thenReturn(Optional.of(jobPosting));

	        // Act
	        jobPostingService.deleteJobPosting(id);

	        // Assert
	        verify(jobPostingRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void whenJobPostingDoesNotExist_thenThrowException() {
	        // Arrange
	        Long id = 1L;
	        // Mock the repository to return an empty Optional when findById is called
	        when(jobPostingRepository.findById(id)).thenReturn(Optional.empty());

	        GlobalException exception = assertThrows(GlobalException.class, () -> {
	            jobPostingService.deleteJobPosting(id);
	        });

	        assertEquals("JobPosting id= " + id + " does not exist", exception.getMessage(), "The exception message should match");
	    }


}
