package com.levi9.code9.userservice.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.userservice.dto.request.RoleRequestDTO;
import com.levi9.code9.userservice.dto.response.RoleResponseDTO;
import com.levi9.code9.userservice.mapper.RoleMapper;
import com.levi9.code9.userservice.model.Role;
import com.levi9.code9.userservice.repository.RoleRepository;
import com.levi9.code9.userservice.service.RoleService;

import jdk.internal.jline.internal.Log;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Data
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository _roleRepository;

	@Autowired
	private RoleMapper _roleMapper;

	//TODO: add rest of the CRUD services
	@Override
	public RoleResponseDTO createRole(RoleRequestDTO roleDTO) {
		log.info("Adding a new role...");
		Role role = getRoleMapper().mapToEntity(roleDTO);
		getRoleRepository().save(role);
		log.info("Role successfully added.");
		return getRoleMapper().mapToDTO(role);
	}

	@Override
	public List<RoleResponseDTO> getRoles() {
		log.info("Fetching all roles...");
		return getRoleMapper().mapToDTOList(getRoleRepository().findAll());
	}

}
