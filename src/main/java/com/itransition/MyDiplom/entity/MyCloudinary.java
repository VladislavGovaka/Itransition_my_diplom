package com.itransition.MyDiplom.entity;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


@Service
public class MyCloudinary {

    @Value("${cloud_name}")
    private String cloud_name;

    @Value("${api_key}")
    private String api_key;

    @Value("${api_secret}")
    private String api_secret;


    private Cloudinary cloudinary ;



    public String upload(MultipartFile file, String nameUser) {

        if (file == null)
            return null;

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", api_secret));

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();


        String pathName = nameUser +  "/" + uuidAsString + "_" + file.getOriginalFilename();

        try {
            File tempFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(tempFile , ObjectUtils.asMap(  "public_id", pathName));
            tempFile.delete();
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            System.out.println("CloudinaryService: (upload) Error! \n" + ex.getMessage());
            return null;
        }

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());

        fos.close();
        return convFile;
    }

}