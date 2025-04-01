package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class BestPlayerDTO
 * @author Mustafa
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestPlayerDTO {
    private String name;
    private double killToDeathRatio;
}
