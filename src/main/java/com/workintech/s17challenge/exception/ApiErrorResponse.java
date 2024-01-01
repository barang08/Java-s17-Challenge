package com.workintech.s17challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private Integer status;
    private String name;
    private Long timestamp;
}
