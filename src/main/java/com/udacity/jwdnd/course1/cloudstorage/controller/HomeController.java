package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, EncryptionService encryptionService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication,
                              @ModelAttribute("noteForm") NoteForm noteForm,
                              @ModelAttribute("fileForm") FileForm fileForm,
                              @ModelAttribute("credentialForm") CredentialForm credentialForm,
                              Model model) {

        Integer userId = 0;
        if(userId!=null)
        {
            userId= userService.getUserByName(authentication.getName()).getUserId();}
        model.addAttribute("files", fileService.getFileByUser(userId));
        model.addAttribute("notes", noteService.getNoteByUser(userId));
        model.addAttribute("credentials", credentialService.getCredentialByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }



    @PostMapping
    public String newFile(Authentication authentication,
                          @ModelAttribute("fileForm") FileForm fileForm,
                          Model model) throws IOException {

        String userName = authentication.getName();
        Integer userId;
        if(!userName.equals(null)){
             userId = userService.getUserByName(userName).getUserId();}
        else
             userId=0;
        List<String> files = fileService.getFileByUser(userId);
        MultipartFile multipartFile = fileForm.getFile();
       // String fileName = multipartFile.getOriginalFilename();
        boolean DuplicateFile = false;

        for (String filename:files) {
            if(filename.equals(files))
                DuplicateFile=true;
            break;
        }
        if (!DuplicateFile) {
                fileService.createFile(multipartFile, userId);
                model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
        }
        model.addAttribute("files", fileService.getFileByUser(userId));
        return "result";
    }

    @GetMapping(value = "/viewFile/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] viewFile(@PathVariable String fileName) {
        return fileService.getFileByName(fileName).getFileData();
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(Authentication authentication,
                             @PathVariable String fileName,
                             Model model) {
        fileService.deleteFile(fileName);
        Integer userId = userService.getUserByName(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getFileByUser(userId));
        model.addAttribute("result", "success");

        return "result";
    }



}
