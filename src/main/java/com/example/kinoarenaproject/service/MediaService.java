package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
@Service
public class MediaService extends com.example.kinoarenaproject.service.Service {


    @SneakyThrows
    public UserWithoutPasswordDTO upload(MultipartFile file, int userId){

        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = UUID.randomUUID().toString() + "."+ext;
        File dir = new File("uploads");
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream(), f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        User u = userById(userId);
        u.setProfileImageUrl(url);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);


    }

    public File download(String fileName) {
        File dir = new File("uploads");
        File f = new File(dir, fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }
}

