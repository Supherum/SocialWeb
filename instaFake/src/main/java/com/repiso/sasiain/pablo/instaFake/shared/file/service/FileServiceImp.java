package com.repiso.sasiain.pablo.instaFake.shared.file.service;

import com.repiso.sasiain.pablo.instaFake.error.exceptions.FileEmptyExceptionCustom;
import com.repiso.sasiain.pablo.instaFake.shared.file.config.StorageProperties;
import com.repiso.sasiain.pablo.instaFake.shared.media_type.MediaTypeUrlResource;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
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
    public FileServiceImp(StorageProperties properties) {
        this.ruta = Paths.get(properties.getLocation());
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

        String nombreArchivo=generateName(file)+"."+extension;

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.ruta.resolve(nombreArchivo),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        return nombreArchivo;
    }

    @Override
    public List<String> saveFileWithCopy(MultipartFile file, int size) throws IOException {

        if(file.isEmpty()) throw new FileEmptyExceptionCustom(FileEmptyExceptionCustom.class);
        String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());

        String nombreArchivo1=generateName(file);
        nombreArchivo1+="."+extension;

        Files.copy(file.getInputStream(),this.ruta.resolve(nombreArchivo1),StandardCopyOption.REPLACE_EXISTING);
        String imagenEscalada=rescaleAndSaveImagen(file,size);

        return List.of(nombreArchivo1,imagenEscalada);
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return ruta.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws MalformedURLException, FileNotFoundException {
        Path file = load(filename);
        MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else   throw new FileNotFoundException(
                "Could not read file: " + filename);
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Path ruta =Paths.get("archivos/"+filename);
        Files.delete(ruta);
    }

    public void deleteListFile(List<String> listaFileNames){
        listaFileNames.forEach(x-> {
            try {
                deleteFile(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public String rescaleAndSaveImagen(MultipartFile file,int size) throws IOException {
        String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());
        String nombreArchivo=generateName(file);
        nombreArchivo+="."+extension;

        BufferedImage bufferedImage= ImageIO.read(file.getInputStream());
        BufferedImage escalado =Scalr.resize(bufferedImage, size);

        OutputStream out=Files.newOutputStream(Paths.get("archivos/"+nombreArchivo));
        ImageIO.write(escalado,extension,out);
        return nombreArchivo;
    }

    private String generateName (MultipartFile file){
        String extension=StringUtils.getFilenameExtension(file.getOriginalFilename());

        Double nuevoNombreDigito = Math.random();
        String nuevoNombre = nuevoNombreDigito.toString().substring(2,10);
        String nombreFinal= nuevoNombre+"."+extension;

        while(Files.exists(ruta.resolve(nombreFinal))){

            nuevoNombreDigito = Math.random();
            nuevoNombre = nuevoNombreDigito.toString().substring(2,10);
            nombreFinal= nuevoNombre+"."+extension;

        }
        return nuevoNombre;
    }

    public String getUri (String fileName){
       return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resource/")
                .path(fileName)
                .toUriString();
    }

}
