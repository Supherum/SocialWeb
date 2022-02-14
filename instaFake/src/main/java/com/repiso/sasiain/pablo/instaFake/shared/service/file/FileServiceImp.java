package com.repiso.sasiain.pablo.instaFake.shared.service.file;

import com.repiso.sasiain.pablo.instaFake.error.exceptions.FileEmptyExceptionCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileServiceImp implements FileService{

    private final Path ruta;

    @Autowired
    public FileServiceImp (FileConfig config) {
        this.ruta = Paths.get(config.getRutaGuardado());
    }

    @PostConstruct
    @Override
    public void init() throws IOException {
        Files.createDirectories(ruta);
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) throw new FileEmptyExceptionCustom(FileEmptyExceptionCustom.class);
        String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());

        String nombreArchivo=generateName(extension)+extension;

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.ruta.resolve(nombreArchivo),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        return nombreArchivo;
    }

    @Override
    public List<String> saveFileWithCopy(MultipartFile file) throws IOException {

        if(file.isEmpty()) throw new FileEmptyExceptionCustom(FileEmptyExceptionCustom.class);
        String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());

        String nombreArchivo1=generateName(extension);
        String nombreArchivo2=nombreArchivo1+"w500"+extension;
        nombreArchivo1+=extension;

        Files.copy(file.getInputStream(),this.ruta.resolve(nombreArchivo1),StandardCopyOption.REPLACE_EXISTING);
        Files.copy(file.getInputStream(),this.ruta.resolve(nombreArchivo2),StandardCopyOption.REPLACE_EXISTING);

        return List.of(nombreArchivo1,nombreArchivo2);
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteFile(String filename) {

    }

    @Override
    public void deleteAll() {

    }

    private String generateName (String extension){

        Double nuevoNombreDigito = Math.random();
        String nuevoNombre = nuevoNombreDigito.toString().substring(0,20)+extension;
        String nombreFinal= nuevoNombre+extension;

        while(Files.exists(ruta.resolve(nombreFinal))){

            nuevoNombreDigito = Math.random();
            nuevoNombre = nuevoNombreDigito.toString().substring(0,20);
            nombreFinal= nuevoNombre+extension;

        }
        return nuevoNombre;
    }
}
