package com.devopswise.cdtportal.workspace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.model.Workspace;
import com.devopswise.cdtportal.workspace.WorkspaceRepository;

@Service
public class WorkspaceService {
	
	@Autowired
	private WorkspaceRepository workspaceRepository;

	public List<Workspace> getAllProjects() {
		List<Workspace> workspaces = new ArrayList<>();
		Iterable workspaceIterable = workspaceRepository.findAll();
		
		for (Iterator<Workspace> i = workspaceIterable.iterator(); i.hasNext();) {
			Workspace item = i.next();
			workspaces.add(item);
		}
		return workspaces;
	}
	
	public Workspace getProject(Long id){
		return (Workspace) workspaceRepository.findOne(id);
	}
	
	public void addProject(Workspace workspace){
		workspaceRepository.save(workspace);
	}
	
	public void updateProject(String id, Workspace workspace){
		workspaceRepository.save(workspace);		
	}
	
	public void deleteProject(Long workspaceId){
		workspaceRepository.delete(workspaceId);
	}
}






