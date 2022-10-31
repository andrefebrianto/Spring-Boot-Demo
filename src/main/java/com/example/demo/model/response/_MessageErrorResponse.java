package com.example.demo.model.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class _MessageErrorResponse {

    private Date timestamp;

    private int status;

    private String error;
}
