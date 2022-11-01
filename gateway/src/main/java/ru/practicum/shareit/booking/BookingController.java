package ru.practicum.shareit.booking;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
@Slf4j
public class BookingController {
    private final BookingClient bookingClient;
    private final BookingValidation bookingValidation;

    @PostMapping
    ResponseEntity<Object> addItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody BookingDto bookingDto) {
        log.info("GATEWAY: Creating booking {}, userId={}", bookingDto, userId);
        bookingValidation.validate(bookingDto);
        return bookingClient.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    ResponseEntity<Object> upgradeNewItem(@NonNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @NonNull @RequestParam(required = false) Boolean approved,
                                          @PathVariable("bookingId") Long bookingId) {
        log.info("GATEWAY: Updating booking {}", bookingId);
        return bookingClient.upgrade(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    ResponseEntity<Object> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                               @PathVariable("bookingId") Long bookingId,
                               @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Long from,
                               @Positive @RequestParam(required = false, defaultValue = "10") Long size) {
        log.info("GATEWAY: Get booking {} userId={}", bookingId, userId);
        return bookingClient.get(userId, bookingId, from, size);
    }

    @GetMapping()
    ResponseEntity<Object> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                  Long userId,
                                  @RequestParam(required = false, name = "state", defaultValue = "ALL") String state,
                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("GATEWAY: Get all bookings, userId={}", userId);
        bookingValidation.isStateCorrect(state);
        return bookingClient.getAll(userId, state, from, size);
    }

    @GetMapping("/owner")
    ResponseEntity<Object> getAllOfOwner(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                         Long userId,
                                         @RequestParam(required = false, defaultValue = "ALL") String state,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("GATEWAY: Get all bookings by owner, userId={}", userId);
        bookingValidation.isStateCorrect(state);
        int page = from / size;
        return bookingClient.getAllOfOwner(userId, state, page, size);
    }

}
