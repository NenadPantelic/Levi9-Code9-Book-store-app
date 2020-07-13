package com.levi9.code9.userservice.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

	public static Map<String, String> parseResponseToString(String responseBody) {
		String[] jsonParts = responseBody.substring(1, responseBody.length() - 1).split(",");
		Map<String, String> map = (HashMap<String, String>) Arrays
				.asList(responseBody.substring(1, responseBody.length() - 1).split(",")).stream().map(s -> s.split(":"))
				.collect(Collectors.toMap(e -> e[0], e -> e[1]));

		return map;
	}

}
