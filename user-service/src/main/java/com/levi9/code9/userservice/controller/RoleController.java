package com.levi9.code9.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.userservice.dto.response.RoleResponseDTO;
import com.levi9.code9.userservice.service.RoleService;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@RestController
@RequestMapping(value="/api/v1/roles/")
public class RoleController {
	
	@Autowired
	private RoleService _roleService;
	@GetMapping
	public List<RoleResponseDTO> getRoles(){
		return getRoleService().getRoles();
	}
	
	

}
