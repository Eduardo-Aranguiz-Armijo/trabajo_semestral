package com.tiendaropa.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteInfoDTO {

    private String id;
    private String uri;
    private String pathPattern;
    private String status;
}
