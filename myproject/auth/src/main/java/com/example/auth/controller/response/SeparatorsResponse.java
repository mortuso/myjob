package com.example.auth.controller.response;

import java.util.Set;

import com.example.auth.service.dto.SeparatorsDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class SeparatorsResponse {
	private Set<String> separators;
	
	public SeparatorsResponse(SeparatorsDTO separatorsDTO) {
		separators = separatorsDTO.getValues();
	}
}
