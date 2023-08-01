package ru.netology

class ElementNotFoundException(message: String) : RuntimeException(message)

open class CRUD<T : Element> {

    var elements = mutableListOf<T>()
    var removedElements = mutableListOf<T>()
    private var elementsByOwnerId = mutableListOf<T>()
    private var nextId = 0

    open fun add(element: T): Int {
        element.id = ++nextId
        elements.add(element)
        return element.id
    }

    fun get(ownerId: Int): MutableList<T> {
        for (element in elements) {
            if (element.ownerId == ownerId) {
                elementsByOwnerId.add(element)
            }
        }
        if (elementsByOwnerId.isNotEmpty()) {
            printAll(elementsByOwnerId)
            return elementsByOwnerId
        }
        throw ElementNotFoundException("${elements.last()::class} with ownerId $ownerId not found")
    }

    open fun edit(element: T): Boolean {
        for ((index, item) in elements.withIndex()) {
            if (item.id == element.id && item.ownerId == element.ownerId) {
                elements[index] = element
                return true
            }
        }
        throw ElementNotFoundException("${element::class} not found")
    }

    open fun delete(element: T): Boolean {
        if (elements.contains(element)) {
            removedElements.add(element)
            elements.remove(element)
            return true
        }
        throw ElementNotFoundException("${element::class} not found")
    }

    fun printAll(elements: MutableList<T>) {
        for (element in elements) {
            println(element)
        }
    }

    fun clear() {
        elements.clear()
        removedElements.clear()
        elementsByOwnerId.clear()
        nextId = 0
    }
}