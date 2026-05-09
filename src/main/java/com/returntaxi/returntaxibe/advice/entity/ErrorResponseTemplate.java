package com.returntaxi.returntaxibe.advice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseTemplate<T> {
    private Integer status;
    private T message;
    private String exception;
    private String path;
    private Boolean isExpired;
}
