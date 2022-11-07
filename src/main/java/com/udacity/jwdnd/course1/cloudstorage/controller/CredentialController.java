package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("credential")
public class CredentialController {
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication,
            @ModelAttribute("credentialForm") CredentialForm newCredential,
            Model model) {
        Integer userId = 0;
        if(userId!=null)
        {userId= userService.getUserByName(authentication.getName()).getUserId();}
        model.addAttribute("credentials", this.credentialService.getCredentialByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping()
    public String newCredential(Authentication authentication, @ModelAttribute("credentialForm") CredentialForm newCredential, Model model) {
        String userName = authentication.getName();
        Integer userId= userService.getUserByName(authentication.getName()).getUserId();

        String url = newCredential.getUrl();
        Integer credentialId = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        List<Credential> credentials = credentialService.getCredentialByUser(userId);

                if (credentialId == null) {
                    credentialService.createCredential(url, userName, newCredential.getUsername(), encodedKey, encryptedPassword);
                } else {
                    Credential existingCredential = getCredential(credentialId);
                    credentialService.updateCredential(existingCredential.getCredentialId(), url,newCredential.getUsername(), encodedKey, encryptedPassword);
                }
                model.addAttribute("result", "success");

        model.addAttribute("credentials", credentialService.getCredentialByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "result";
    }

    @GetMapping("/getCredential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        Integer userId = 0;
        if(userId!=null)
        {userId= userService.getUserByName(authentication.getName()).getUserId();}
        model.addAttribute("credentials", credentialService.getCredentialByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        credentialService.deleteCredential(credentialId);
        model.addAttribute("result", "success");
        return "result";
    }
}
