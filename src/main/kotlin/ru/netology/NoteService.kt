package ru.netology

class NoteNotFoundException(message: String) : RuntimeException(message)

object NoteService : CRUD<Note>() {

    private var commentsByNoteId = mutableListOf<CommentToNote>()

    override fun delete(element: Note): Boolean {
        if (elements.contains(element)) {
            removedElements.add(element)

            for (comment in CommentToNoteService.elements) {
                if (comment.noteId == element.id) {
                    commentsByNoteId.add(comment)
                }
            }
            CommentToNoteService.removedElements.addAll(commentsByNoteId)
            CommentToNoteService.elements.removeAll(commentsByNoteId)

            return elements.remove(element)
        }
        throw NoteNotFoundException("Note with id ${element.id}, ownerId ${element.ownerId} not found")
    }

    fun findById(nid: Int): Note {
        for (note in elements) {
            if (note.id == nid) {
                return note
            }
        }
        throw NoteNotFoundException("Note with id $nid not found")
    }

    fun clearCommentsByNoteId() {
        commentsByNoteId.clear()
    }
}