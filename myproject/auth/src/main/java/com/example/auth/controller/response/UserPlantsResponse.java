package com.example.auth.controller.response;

import java.util.Map;
import java.util.TreeMap;

import com.example.auth.service.dto.PlantDTO;
import com.example.auth.service.dto.UserPlantsDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class UserPlantsResponse {
	
	private Map<String, String> plants;
	private String defaultPlantId;
	
	public UserPlantsResponse(UserPlantsDTO userPlantsDTO) {
		if(userPlantsDTO.getDefaultPlant() != null) {
			defaultPlantId = userPlantsDTO.getDefaultPlant().getId();
		}
		if (!userPlantsDTO.getPlants().isEmpty()) {
			plants = new TreeMap<>();
			for (PlantDTO plantDTO: userPlantsDTO.getPlants()) {
				plants.put(plantDTO.getId(), plantDTO.getDescription());
			}
		}
	}
}
