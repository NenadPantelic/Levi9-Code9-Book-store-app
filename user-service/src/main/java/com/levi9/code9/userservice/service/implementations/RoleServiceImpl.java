package com.levi9.code9.userservice.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.userservice.dto.response.RoleResponseDTO;
import com.levi9.code9.userservice.mapper.RoleMapper;
import com.levi9.code9.userservice.repository.RoleRepository;
import com.levi9.code9.userservice.service.RoleService;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository _roleRepository;
	
	@Autowired
	private RoleMapper _roleMapper;
	
	@Override
	public List<RoleResponseDTO> getRoles() {
		return getRoleMapper().mapRoleListToRoleResponseDTOList(getRoleRepository().findAll());
	}

}
