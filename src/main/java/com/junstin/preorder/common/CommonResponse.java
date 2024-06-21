package com.junstin.preorder.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse {

    private HttpStatus status;
    private String message;
    private Object data;
}
