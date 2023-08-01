package ru.netology

class CommentNotFoundException(message: String) : RuntimeException(message)

object CommentToNoteService : CRUD<CommentToNote>() {

    private var nextCid: Int = 0
    private var commentsByNoteId = mutableListOf<CommentToNote>()

    override fun add(element: CommentToNote): Int {
        for (note in NoteService.elements) {
            if (note.id == element.noteId) {
                element.id = ++nextCid
                elements.add(element)
                return element.id
            }
        }
        throw NoteNotFoundException("Note with id ${element.noteId} not found")
    }

    fun getComments(noteId: Int): MutableList<CommentToNote> {
        for (comment in elements) {
            if (comment.noteId == noteId) {
                commentsByNoteId.add(comment)
            }
        }
        if (commentsByNoteId.isNotEmpty()) {
            printAll(commentsByNoteId)
            return commentsByNoteId
        }
        throw CommentNotFoundException("Comments to Note with id $noteId not found")
    }

    override fun edit(element: CommentToNote): Boolean {
        for (note in NoteService.elements) {
            if (note.id == element.noteId) {
                for ((index, item) in elements.withIndex()) {
                    if (item.id == element.id && item.ownerId == element.ownerId) {
                        elements[index] = element
                        return true
                    }
                }
                throw CommentNotFoundException("Comment with id ${element.id}, ownerId ${element.ownerId} not found")
            }
        }
        throw NoteNotFoundException("Note with id ${element.noteId} not found")
    }

    fun restoreComment(element: CommentToNote): Boolean {
        for (note in NoteService.elements) {
            if (note.id == element.noteId) {
                for (removedElement in removedElements) {
                    if (removedElement.id == element.id && removedElement.ownerId == element.ownerId) {
                        removedElements.remove(element)
                        elements.add(element)
                        return true
                    }
                }
                throw CommentNotFoundException("Comment with id ${element.id}, ownerId ${element.ownerId} not found")
            }
        }
        throw NoteNotFoundException("Note with id ${element.noteId} not found")
    }

    fun clearCommentsByNoteId() {
        commentsByNoteId.clear()
        nextCid = 0
    }
}