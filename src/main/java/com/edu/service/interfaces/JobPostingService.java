package com.edu.service.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.edu.entity.JobPosting;
import com.edu.exception.GlobalException;

public interface JobPostingService {
	
	JobPosting createJobPosting(@RequestBody JobPosting jobPosting);

	List<JobPosting> getAllJobPostings();

	JobPosting getJobPostingById(Long id) throws GlobalException;

	JobPosting updateJobPosting(Long id, JobPosting jobPosting) throws GlobalException;

	void deleteJobPosting(Long id) throws GlobalException;

	List<JobPosting> searchJobPostings(String keyword, String keyword2, String keyword3);
	
	
	

}
