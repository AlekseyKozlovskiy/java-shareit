package ru.practicum.shareit.exceptions;

public class IncorrectRequest extends RuntimeException {
    public IncorrectRequest(String s) {
        super(s);
    }
}
