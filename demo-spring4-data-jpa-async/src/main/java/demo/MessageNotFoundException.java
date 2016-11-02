package demo;

public class MessageNotFoundException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public MessageNotFoundException(long id) {
        super("Message not found by id: " + id);
    }
}
