package com.example.auth.controller.response;

import java.util.Map;
import java.util.TreeMap;

import com.example.auth.service.dto.StationDTO;
import com.example.auth.service.dto.UserStationsDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class UserStationsResponse {

	private Map<Integer, String> stations;
	private Integer defaultStation;

	public UserStationsResponse(UserStationsDTO userStationsDTO) {
		if(userStationsDTO.getDefaultStation() != null) {
			defaultStation = userStationsDTO.getDefaultStation().getId();
		}
		if (!userStationsDTO.getStations().isEmpty()) {
			stations = new TreeMap<>();
			for (StationDTO stationDTO: userStationsDTO.getStations()) {
				stations.put(stationDTO.getId(), stationDTO.getDescription());
			}
		}
	}
}
