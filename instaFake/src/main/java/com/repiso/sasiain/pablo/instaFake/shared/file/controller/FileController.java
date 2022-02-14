package com.repiso.sasiain.pablo.instaFake.shared.file.controller;

import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.shared.media_type.MediaTypeUrlResource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/resource/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException, FileNotFoundException {

        MediaTypeUrlResource resource = (MediaTypeUrlResource) fileService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .header("content-type", resource.getType())
                .body(resource);
    }
}
