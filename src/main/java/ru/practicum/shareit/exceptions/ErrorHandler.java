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
        return new ErrorResponse("Ошибка в заголовке");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectUserException(IncorrectUserException e) {
        return new ErrorResponse(String.format("Пользователя с ID: \"%s\" не существует .", e.getHeader()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectItemValidException(IncorrectItemValidException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectOwnerException(IncorrectOwnerException e) {
        return new ErrorResponse("У пользователя нет такой вещи");
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectItemIdException(IncorrectItemIdException e) {
        return new ErrorResponse("Нет вещи с таким ID");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectCoincidenceOfЕTimeException(TryingToBookingSelfItemException e) {
        return new ErrorResponse("ОШИБКА");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectBookingTimeException(IncorrectBookingTimeException e) {
        return new ErrorResponse("Неверное время");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectBookingIdException(IncorrectBookingIdException e) {
        return new ErrorResponse("Неверный ID Booking");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectMatchesBookingIdAndUserIdException(IncorrectMatchesBookingIdAndUserIdException e) {
        return new ErrorResponse("Владелец вещи другой пользователь");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectBookerException(IncorrectBookerException e) {
        return new ErrorResponse("Только владелец может менять статус");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectItemApprovedException(IncorrectItemApprovedException e) {
        return new ErrorResponse("Вещь уже подтверждена для бронирования");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectChangeStatusBookerException(IncorrectChangeStatusBookerException e) {
        return new ErrorResponse("Только владелец может поменять статус1");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectBookerBookingException(IncorrectBookerBookingException e) {
        return new ErrorResponse("Только владелец может поменять статус2");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectOwnerBookingException(IncorrectOwnerBookingException e) {
        return new ErrorResponse("Только владелец может поменять статус3");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectApprovedParameterException(IncorrectApprovedParameterException e) {
        return new ErrorResponse("Не указан параметр approved");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectAddCommentException(IncorrectAddCommentException e) {
        return new ErrorResponse("Вещь не была в аренде");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectStateException(IncorrectStateException e) {
        return new ErrorResponse("Unknown state: UNSUPPORTED_STATUS");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectItemRequestDescription(IncorrectItemRequestDescription e) {
        return new ErrorResponse("Description is empty");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectParametrException(IncorrectParameterException e) {
        return new ErrorResponse("Wrong parameter");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectItemRequestIdException(IncorrectItemRequestIdException e) {
        return new ErrorResponse("Wrong item request id");
    }


}
