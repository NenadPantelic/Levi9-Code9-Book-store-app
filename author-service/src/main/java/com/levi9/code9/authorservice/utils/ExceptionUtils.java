package com.levi9.code9.authorservice.utils;

public class ExceptionUtils {
	// helper
	public static Throwable getRootCause(Throwable t) {
		Throwable result = t;
		Throwable cause;

		while (null != (cause = result.getCause()) && (result != cause)) {
			result = cause;
		}
		return result;
	}
}
