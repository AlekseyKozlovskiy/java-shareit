package ru.practicum.shareit.util;

import ru.practicum.shareit.exceptions.ValidationException;

public class ParamValidation {
    public static void chekParam(Long from, Long size) {
        if (from < 0 || size < 0 || size < from || (from == 0 && size == 0)) {
            throw new ValidationException("Wrong parameter");
        }
    }
}
