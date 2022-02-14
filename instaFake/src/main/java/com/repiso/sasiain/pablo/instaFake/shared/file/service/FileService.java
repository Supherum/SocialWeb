package com.repiso.sasiain.pablo.instaFake.shared.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    void init() throws IOException;

    String saveFile(MultipartFile file) throws IOException;

    String saveFileWithCopy(MultipartFile file,int size) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename) throws MalformedURLException, FileNotFoundException;

    void deleteFile(String filename);

    void deleteAll();

    String rescaleAndSaveImagen(MultipartFile file,int size) throws IOException;
}
