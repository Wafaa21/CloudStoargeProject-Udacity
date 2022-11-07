package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private UserMapper userMapper;
    private CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }
    public Integer createCredential(String url, String username,String credentialName, String key,String password){
        Integer userId = userMapper.getUserByName(username).getUserId();
        return credentialMapper.insertCredential(new Credential(null,url,credentialName,key, password,userId));
    }

    public List<Credential> getCredentialByUser(Integer userId){
        return credentialMapper.getCredentialsByUser(userId);
    }
    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public void updateCredential(Integer credentialId, String url,String username,  String key, String password) {
        credentialMapper.updateCredential(credentialId,url, username,key,password);
    }

}
