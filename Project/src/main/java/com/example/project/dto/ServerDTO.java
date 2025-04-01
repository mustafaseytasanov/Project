package com.example.project.dto;


import lombok.*;

import java.util.List;

/**
 * Class ServerDTO
 * @author Mustafa
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ServerDTO {
    private String endpoint;
    private InfoDTO info;
}
