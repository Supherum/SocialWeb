package com.repiso.sasiain.pablo.instaFake.error.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GenericError {

    private String message;
    private String rute;
    private HttpStatus status;
    private int code;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy HH:mm:ss")
    private LocalDateTime date= LocalDateTime.now();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubError> listSubError;

    public GenericError(String message, String rute, HttpStatus status, int code, LocalDateTime date) {
        this.message = message;
        this.rute = rute;
        this.status = status;
        this.code = code;
        this.date = date;
    }
}
