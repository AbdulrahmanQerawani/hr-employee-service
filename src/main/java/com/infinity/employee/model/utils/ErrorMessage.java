package com.infinity.employee.model.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ErrorMessage {
    private String code;
    private String message;
    private String detail;

    public ErrorMessage(String message, String code, String detail) {
        super();
        this.message = message;
        this.code = code;
        this.detail = detail;
    }
}

