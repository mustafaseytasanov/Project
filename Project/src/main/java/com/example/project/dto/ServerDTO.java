package com.example.project.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ServerDTO {
    private String endpoint;
    private InfoDTO info;
}
