package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

private FileMapper fileMapper;
    private UserMapper userMapper;


    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<String> getFileByUser(Integer userId) {
        return fileMapper.getFileByUser(userId);
    }

    public void createFile(MultipartFile multipartFile, Integer userId) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = multipartFile.getSize() + "";
        //Integer userId = userMapper.getUserByName(username).getUserId();
        byte[] fileData = multipartFile.getBytes();
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.insertFile(file);
    }


    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
