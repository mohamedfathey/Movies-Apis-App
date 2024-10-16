package com.moviefilx.movieApi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // Get Name Of The File
        String fileName = file.getOriginalFilename();
        //To Get The File Path
        String filePath =  path + File.separator + fileName ;
        // Create File Object
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }
        //Copy The File Or Upload The Path
        Files.copy(file.getInputStream() , Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName ;

        return new FileInputStream(filePath);
    }
}
