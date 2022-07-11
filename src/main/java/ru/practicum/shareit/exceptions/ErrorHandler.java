package ru.practicum.shareit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIncorrectHeaderException(IncorrectHeaderException e) {
        return new ErrorResponse(
                "Ошибка в заголовке") ;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectUserException(IncorrectUserException e) {
        return new ErrorResponse(
                String.format("Пользователя с ID: \"%s\" не существует .", e.getHeader())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectItemAvailableException(IncorrectItemAvailableException e) {
        return new ErrorResponse("У вещи должен быть статус аренды");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectItemNameException(IncorrectItemNameException e) {
        return new ErrorResponse("У вещи должно быть название");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectItemDescriptionException(IncorrectItemDescriptionException e) {
        return new ErrorResponse("У вещи должно быть описание");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectOwnerException(IncorrectOwnerException e) {
        return new ErrorResponse("У пользователя нет такой вещи");
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectItemIdException(IncorrectItemIdException e) {
        return new ErrorResponse("Нет вещи с таким ID");
    }
}
