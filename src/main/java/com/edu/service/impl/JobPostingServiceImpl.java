package com.edu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.dao.interfaces.JobPostingRepository;
import com.edu.entity.JobPosting;
import com.edu.exception.GlobalException;
import com.edu.service.interfaces.JobPostingService;

@Service
public class JobPostingServiceImpl implements JobPostingService {
	
	@Autowired
	private JobPostingRepository jobPostingRepository;

	@Override
	public JobPosting createJobPosting(JobPosting jobPosting) {
		
		return jobPostingRepository.save(jobPosting);
	}

	@Override
	public List<JobPosting> getAllJobPostings() {
		
		return jobPostingRepository.findAll();
	}

	@Override
	public JobPosting getJobPostingById(Long id) throws GlobalException{
		Optional<JobPosting> jobposting = jobPostingRepository.findById(id);
		
		if(!jobposting.isPresent()) {
			throw new GlobalException("JobPosting id= " + id + " does not exist");
		}
		
		return jobPostingRepository.findById(id).get();
	}

	@Override
	public JobPosting updateJobPosting(Long id, JobPosting jobPosting) throws GlobalException {
		
		JobPosting jop = null;
		Optional<JobPosting> existingJobPosting = jobPostingRepository.findById(id);
		if(!existingJobPosting.isPresent()) {
			throw new GlobalException("Job Posting id= " + id + " does not exist");
		}
		else {
			jop = jobPostingRepository.findById(id).get();
			jop.setTitle(jobPosting.getTitle());
			jop.setDescription(jobPosting.getDescription());
			jop.setLocation(jobPosting.getLocation());
			jop.setCompany(jobPosting.getCompany());
			jop.setSalaryRange(jobPosting.getSalaryRange());
			jop.setRequiredSkills(jobPosting.getRequiredSkills());
			jop.setApplicationDeadline(jobPosting.getApplicationDeadline());
			
		}
        return jobPostingRepository.save(jop);
	}

	@Override
	public void deleteJobPosting(Long id) throws GlobalException {
		
		Optional<JobPosting> jobposting = jobPostingRepository.findById(id);
		if(!jobposting.isPresent()) {
			throw new GlobalException("JobPosting id= " + id + " does not exist");
		}
		
		jobPostingRepository.deleteById(id);
	}

	@Override
	public List<JobPosting> searchJobPostings(String keyword, String keyword2, String keyword3) {
		
		return jobPostingRepository.findByTitleContainingOrLocationContainingOrRequiredSkillsContaining(keyword, keyword2, keyword3);
	}

}
