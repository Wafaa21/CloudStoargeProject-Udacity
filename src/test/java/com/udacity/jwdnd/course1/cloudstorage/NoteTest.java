package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTest extends CloudStorageApplicationTests {

    private final String noteTitle = "First Note";
    private final String noteDescription = "My First Note.";
    private final String newNoteTitle = "New Note";
    private final String newNoteDescription = "New Note Desc.";


    private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
        homePage.navigateNotesTab();

        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNote();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateNotesTab();
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }


    @Test
    public void testCreate() {
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);

        homePage = new HomePage(driver);
        Note note = homePage.getTopNote();

        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());

        deleteNote(homePage);
        homePage.logout();
    }


    @Test
    public void testEdit() {
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);

        homePage = new HomePage(driver);
        homePage.editNote();
        homePage.changeNote(newNoteTitle,newNoteDescription);
        homePage.saveNote();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navigateNotesTab();
        Note newNote = homePage.getTopNote();

        Assertions.assertEquals(newNoteTitle, newNote.getNoteTitle());
        Assertions.assertEquals(newNoteDescription, newNote.getNoteDescription());

        deleteNote(homePage);
        homePage.logout();
    }

    @Test
    public void testDelete() {
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);

        homePage = new HomePage(driver);
        Assertions.assertFalse(!homePage.noNotes(driver));

        deleteNote(homePage);
        Assertions.assertTrue(homePage.noNotes(driver));

        homePage.logout();
    }
}