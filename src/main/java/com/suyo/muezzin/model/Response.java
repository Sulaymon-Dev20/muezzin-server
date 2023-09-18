package com.suyo.muezzin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private List<ErrorField> errorList;
    @Deprecated
    private String mainMessage;
}
