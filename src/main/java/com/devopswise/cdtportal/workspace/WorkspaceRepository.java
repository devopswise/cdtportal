package com.devopswise.cdtportal.workspace;

import com.devopswise.cdtportal.model.Workspace;

import org.springframework.data.repository.CrudRepository;

public interface WorkspaceRepository extends CrudRepository<Workspace, Long> {

}