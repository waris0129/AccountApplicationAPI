package com.account.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

    private Integer code;
    private Boolean success;
    private String message;
    private Object data;

    public ResponseWrapper(String message, Object data) {
        this.code= HttpStatus.OK.value();
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public ResponseWrapper(String message) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.success = false;
        this.message = message;
    }
}
