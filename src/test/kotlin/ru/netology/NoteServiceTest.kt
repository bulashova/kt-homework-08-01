package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
        CommentToNoteService.clear()
        NoteService.clearCommentsByNoteId()
        CommentToNoteService.clearCommentsByNoteId()
    }

    @Test
    fun addNote() {
        val result = NoteService.add(Note(ownerId = 11))
        assertEquals(1, result)
    }

    @Test
    fun getNotesByOwnerId() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 33))

        val result = NoteService.get(11).size
        assertEquals(2, result)
    }

    @Test(expected = ElementNotFoundException::class)
    fun getNotesByOwnerIdNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 33))

        NoteService.get(44)
    }

    @Test
    fun editNoteExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        val result = NoteService.edit(Note(id = 2, ownerId = 22, title = "New title"))
        assertTrue(result)
    }

    @Test(expected = ElementNotFoundException::class)
    fun editNoteNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        NoteService.edit(Note(id = 4, ownerId = 22, title = "New title"))
    }

    @Test
    fun deleteNote() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        val result = NoteService.delete(Note(id = 2, ownerId = 22))
        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        NoteService.delete(Note(id = 3, ownerId = 33))
    }

    @Test
    fun findNoteById() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 33))

        val result = (NoteService.findById(4)).id
        assertEquals(4, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun findNoteByIdNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 33))

        NoteService.findById(6)
    }

    @Test
    fun addCommentExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        val result = CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))
        assertEquals(1, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun shouldThrowNoteNotFound() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 3, ownerId = 22))
    }

    @Test
    fun getCommentsByNoteId() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        val result = CommentToNoteService.getComments(2).size
        assertEquals(2, result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getCommentsByNoteIdNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 33))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 3, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 4, ownerId = 33))

        CommentToNoteService.getComments(2)
    }

    @Test
    fun editComment() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        val result = CommentToNoteService.edit(CommentToNote(id = 1, noteId = 1, ownerId = 11, message = "New message"))
        assertTrue(result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        CommentToNoteService.edit(CommentToNote(id = 3, noteId = 1, ownerId = 11, message = "New message"))
    }

    @Test(expected = NoteNotFoundException::class)
    fun editCommentToNoteNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        CommentToNoteService.edit(CommentToNote(id = 1, noteId = 4, ownerId = 11, message = "New message"))
    }

    @Test
    fun deleteComment() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        val result = CommentToNoteService.delete(CommentToNote(id = 1, noteId = 1, ownerId = 11))
        assertTrue(result)
    }

    @Test(expected = ElementNotFoundException::class)
    fun deleteCommentNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        CommentToNoteService.delete(CommentToNote(id = 1, noteId = 2, ownerId = 11))
    }

    @Test
    fun restoreCommentExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        CommentToNoteService.delete(CommentToNote(id = 1, noteId = 1, ownerId = 11))
        val result = CommentToNoteService.restoreComment(CommentToNote(id = 1, noteId = 1, ownerId = 11))
        assertTrue(result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreCommentNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        CommentToNoteService.delete(CommentToNote(id = 1, noteId = 1, ownerId = 11))
        CommentToNoteService.restoreComment(CommentToNote(id = 2, noteId = 1, ownerId = 11))
    }

    @Test(expected = NoteNotFoundException::class)
    fun restoreCommentToNoteNoExisting() {
        NoteService.add(Note(ownerId = 11))
        NoteService.add(Note(ownerId = 22))

        CommentToNoteService.add(CommentToNote(noteId = 1, ownerId = 11))
        CommentToNoteService.add(CommentToNote(noteId = 2, ownerId = 22))

        NoteService.delete(Note(id = 1, ownerId = 11))
        CommentToNoteService.delete(CommentToNote(id = 2, noteId = 2, ownerId = 22))
        CommentToNoteService.restoreComment(CommentToNote(id = 1, noteId = 1, ownerId = 11))
    }
}