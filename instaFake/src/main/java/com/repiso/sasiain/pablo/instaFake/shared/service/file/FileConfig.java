package com.repiso.sasiain.pablo.instaFake.shared.service.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
@Getter @Setter
public class FileConfig {
    private String rutaGuardado;
}
