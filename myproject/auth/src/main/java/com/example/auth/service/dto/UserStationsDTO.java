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
public class UserStationsDTO {
	
	private @Getter Collection<StationDTO> stations = new ArrayList<>();
	private @Setter @Getter StationDTO defaultStation;
}
