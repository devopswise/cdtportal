package com.devopswise.cdtportal.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.model.Project;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		Iterable projectIterable = projectRepository.findAll();
		
		for (Iterator<Project> i = projectIterable.iterator(); i.hasNext();) {
		    Project item = i.next();
		    projects.add(item);
		}
		return projects;
	}
	public Project getProject(Long id){
		return (Project) projectRepository.findOne(id);
	}
	
	public Project getProject(String key){
		List<Project> projects = getAllProjects();
		for(Project p:projects){
			if (p.getKey().equalsIgnoreCase(key)){
				return p;
			}
		}
		return null;
	}
	public void addProject(Project project){
		projectRepository.save(project);
	}
	
	public void updateProject(String id, Project project){
		projectRepository.save(project);		
	}
	
	public void deleteProject(Long projectId){
		projectRepository.delete(projectId);
	}
	/*
	public String key;
	public String name;
	public String description;
	public List<String> users;
	public String lead;
	*/
}
