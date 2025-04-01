package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class PopularServerDTO
 * @author Mustafa
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class PopularServerDTO {
    private String endpoint;
    private String name;
    private double averageMatchesPerDay;
}
