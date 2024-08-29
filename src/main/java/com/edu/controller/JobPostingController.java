package com.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.JobPosting;
import com.edu.exception.GlobalException;
import com.edu.service.interfaces.JobPostingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Job Posting API", description = "Operations related to job postings")
@RestController
@RequestMapping("/api/job-postings")
public class JobPostingController {
	
	@Autowired
	private JobPostingService jobPostingService;
	
	@Operation(summary = "Create a new job posting", description = "Creates a new job posting and returns the created entity")
    @PostMapping
    public ResponseEntity<JobPosting> createJobPosting(
    		@Parameter(description = "JobPosting object to be created", required = true)
    		@RequestBody JobPosting jobPosting) {
        JobPosting jobposting = jobPostingService.createJobPosting(jobPosting);
    	return new ResponseEntity<JobPosting>(jobposting,HttpStatus.CREATED);
        		
    }
    
	 @Operation(summary = "Get all job postings", description = "Returns a list of all job postings")
    @GetMapping
    public List<JobPosting> getAllJobPostings(){
        return jobPostingService.getAllJobPostings();
    }
    
	
	 @Operation(summary = "Get job posting by ID", description = "Fetches a job posting by its ID")
    @GetMapping("/{id}")
    public JobPosting getJobPostingById(
    		@Parameter(description = "ID of the job posting to be fetched", required = true)
    		@PathVariable Long id) throws GlobalException{
        return jobPostingService.getJobPostingById(id);
    }
    
	 @Operation(summary = "Update job posting", description = "Updates an existing job posting by its ID")
    @PutMapping("/{id}")
    public JobPosting updateJobPosting(
    		@Parameter(description = "ID of the job posting to be updated", required = true)
    		@PathVariable Long id, 
    		@Parameter(description = "Updated job posting object", required = true)
    		@RequestBody JobPosting jobPosting) throws GlobalException{
    	return jobPostingService.updateJobPosting(id,jobPosting);
    }
    
	 
	 @Operation(summary = "Delete job posting", description = "Deletes a job posting by its ID")
    @DeleteMapping("/{id}")
    public String deleteJobPosting(
    		@Parameter(description = "ID of the job posting to be deleted", required = true)
    		@PathVariable Long id) throws GlobalException{
        jobPostingService.deleteJobPosting(id);
        return "Job Posting deleted";
    }
    
    
	 @Operation(summary = "Search job postings", description = "Searches for job postings based on the given keyword")
    @GetMapping("/search")
    public List<JobPosting> searchJobPostings(
    		 @Parameter(description = "Keyword for searching job postings", required = true)
    		@RequestParam String keyword) {
        return jobPostingService.searchJobPostings(keyword, keyword, keyword);
    }


}
