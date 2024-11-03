package ru.clevertec.globalexceptionhandlingstarter.exception.comment;

/**
 * Исключение, которое выбрасывается, когда комментарий с указанным идентификатором не найден.
 */
public class CommentNotFoundException extends RuntimeException {
    private static final String message = "Comment with id : %s not found";

    /**
     * @param id идентификатор комментария, который не найден
     */
    public CommentNotFoundException(Long id) {
        super(String.format(message, id));
    }
}
