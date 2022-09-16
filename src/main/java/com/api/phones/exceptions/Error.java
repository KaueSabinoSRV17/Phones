package com.api.phones.exceptions;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Error {

    private Integer status;
    private String title;
    private OffsetDateTime timeStamp;
    private List<Field> fields;

    public Error(Integer status, String message, OffsetDateTime timeStamp) {
        this.status = status;
        this.title = message;
        this.timeStamp = timeStamp;
    }

    @Getter
    @AllArgsConstructor
    public static class Field {

        private String label;
        private String message;

    }
}
