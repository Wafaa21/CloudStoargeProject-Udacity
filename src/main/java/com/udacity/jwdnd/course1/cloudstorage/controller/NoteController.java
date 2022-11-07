package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getNotePage(
            Authentication authentication,
            @ModelAttribute("noteForm") NoteForm noteForm,
            Model model) {
        Integer userId = 0;
        if(userId!=null)
        {userId= userService.getUserByName(authentication.getName()).getUserId();}
        model.addAttribute("notes", this.noteService.getNoteByUser(userId));
        return "home";
    }

    @PostMapping()
    public String newNote(Authentication authentication,
                          @ModelAttribute("noteForm") NoteForm noteForm,
                          Model model)  {

        String userName = authentication.getName();
        Integer userId = 0;
        if(userId!=null)
            userId = userService.getUserByName(userName).getUserId();
        else userId=0;

        List<Note> notes = noteService.getNoteByUser(userId);
        String newTitle = noteForm.getNoteTitle();
        Integer noteId = noteForm.getNoteId();
        String newDescription = noteForm.getNoteDescription();
        if (noteId==null)
            noteService.createNote(newTitle, newDescription, userId);
                else {
                    Note existingNote = viewNote(noteId);
                    noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
                }
                model.addAttribute("result", "success");

        model.addAttribute("notes", noteService.getNoteByUser(userId));
        return "result";
    }

    @GetMapping(value = "/viewNote/{noteId}")
    public Note viewNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping(value = "/deleteNote/{noteId}")
    public String deleteNote(
            Authentication authentication,
            @PathVariable Integer noteId,
            @ModelAttribute("newNote") NoteForm addNote,
            Model model) {

        noteService.deleteNote(noteId);
        Integer userId = userService.getUserByName(authentication.getName()).getUserId();
        model.addAttribute("notes", noteService.getNoteByUser(userId));
        model.addAttribute("result", "success");
        return "result";
    }
}
