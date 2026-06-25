package com.coditas.frontline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse <T>{
    private T data;
    private List<ErrorResponse> errors;

    public ApplicationResponse(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public ApplicationResponse(T data) {
        this.data = data;
    }
}
