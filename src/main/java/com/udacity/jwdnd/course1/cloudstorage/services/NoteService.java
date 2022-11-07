package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private UserMapper userMapper;
    private NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }
    public Integer createNote(String noteTitle, String noteDescription, Integer userId){
      //  Integer userId = userMapper.getUserByName(username).getUserId();
        return noteMapper.insertNote(new Note(null,noteTitle,noteDescription,userId));
    }

    public List<Note>  getNoteByUser(Integer userId){
        return noteMapper.getNoteByUser(userId);
    }
    public Note getNote(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription) {
        noteMapper.updateNote(noteId, noteTitle, noteDescription);
    }

}
