package ru.netology

open class Element(
    open var id: Int = 0,
    open var ownerId: Int = 0,
)

data class Note(
    override var id: Int = 0,
    override var ownerId: Int,
    val title: String = "title",
    val text: String = "text",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val privacyView: String = "privacyView",
    val privacyComment: String = "privacyComment",
) : Element()

data class CommentToNote(
    override var id: Int = 0,
    override var ownerId: Int,
    val noteId: Int,
    val message: String = "message",
) : Element()