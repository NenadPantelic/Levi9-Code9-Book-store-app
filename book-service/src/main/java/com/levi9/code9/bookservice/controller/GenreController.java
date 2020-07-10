package com.levi9.code9.bookservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/genres/")
public class GenreController {
	
	@GetMapping(value="")
	public boolean test() {
		return true;
	}

}
