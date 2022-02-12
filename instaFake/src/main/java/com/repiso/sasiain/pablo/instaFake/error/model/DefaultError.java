package com.repiso.sasiain.pablo.instaFake.error.model;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class DefaultError extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String,Object> atributes=super.getErrorAttributes(webRequest, options);
        Map<String,Object> result= Map.of(
                "status",atributes.get("status"),
                "code", HttpStatus.valueOf((int) atributes.get("status")).name(),
                "date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "rute",atributes.get("path")
        );
        if (atributes.containsKey("message"))
            result.put("message", atributes.get("message"));
        if (atributes.containsKey("errors"))
            result.put("subError", atributes.get("errors"));
        return result;
    }
}
