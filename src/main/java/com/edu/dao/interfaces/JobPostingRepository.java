package com.edu.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.entity.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
	
	List<JobPosting> findByTitleContainingOrLocationContainingOrRequiredSkillsContaining(String title, String location, String skills);

}
