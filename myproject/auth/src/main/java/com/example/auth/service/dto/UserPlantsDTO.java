package com.example.auth.service.dto;

import java.util.ArrayList;
import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserPlantsDTO {

	private @Getter Collection<PlantDTO> plants = new ArrayList<>();
	private @Setter @Getter PlantDTO defaultPlant;
}
