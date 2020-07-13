package com.levi9.code9.authorservice.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

	private Date _timestamp;
	private String _message;
	private String _details;
}
