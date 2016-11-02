package demo

class MessageNotFoundException(id: Long) : RuntimeException("Message not found by id: " + id) {

    companion object {
        @JvmStatic private val serialVersionUID: Long = 1
    }
}
