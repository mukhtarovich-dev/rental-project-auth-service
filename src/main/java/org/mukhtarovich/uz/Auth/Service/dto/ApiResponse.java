package org.mukhtarovich.uz.Auth.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private HttpStatus code;
    private Boolean status;
    private T object;

    public ApiResponse( String message,HttpStatus code, Boolean status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
