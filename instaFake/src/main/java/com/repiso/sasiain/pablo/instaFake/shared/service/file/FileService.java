package com.repiso.sasiain.pablo.instaFake.shared.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    void init() throws IOException;

    String saveFile(MultipartFile file) throws IOException;

    List<String> saveFileWithCopy(MultipartFile file) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);

    void deleteAll();
}
