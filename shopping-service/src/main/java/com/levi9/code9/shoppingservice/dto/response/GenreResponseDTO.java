package com.levi9.code9.shoppingservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponseDTO {
	private Long _id;
	private String _name;
}
