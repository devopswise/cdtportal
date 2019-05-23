package com.devopswise.cdtportal.workspace;

import org.springframework.data.repository.CrudRepository;

import com.devopswise.cdtportal.model.Workspace;

public interface WorkspaceRepository extends CrudRepository<Workspace,Long> {
	
}
