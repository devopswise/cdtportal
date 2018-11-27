package com.devopswise.cdtportal.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devopswise.cdtportal.model.Project;

public interface ProjectRepository extends CrudRepository<Project,Long> {
	
}
