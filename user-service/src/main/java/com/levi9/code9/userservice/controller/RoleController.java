package com.levi9.code9.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.userservice.dto.request.RoleRequestDTO;
import com.levi9.code9.userservice.dto.response.RoleResponseDTO;
import com.levi9.code9.userservice.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@RestController
@RequestMapping(value = "/api/v1/roles/")
@Api(tags = "RoleEndpoints")
public class RoleController {

	@Autowired
	private RoleService _roleService;

	@ApiOperation(value = "Add a new role")
	@PostMapping
	public RoleResponseDTO createRole(@RequestBody RoleRequestDTO roleDTO) {
		return getRoleService().createRole(roleDTO);
	}

	@ApiOperation(value = "Get all roles")
	@GetMapping
	public List<RoleResponseDTO> getRoles() {
		return getRoleService().getRoles();
	}

}
