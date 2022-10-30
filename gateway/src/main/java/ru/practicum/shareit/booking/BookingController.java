package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    ResponseEntity<Object> addItemRequest(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody BookingDto bookingDto) {
        return bookingClient.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    ResponseEntity<Object> upgradeNewItem(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestParam(required = false) Boolean approved,
                                          @PathVariable("bookingId") Long bookingId) {
        return bookingClient.upgrade(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    ResponseEntity<Object> get(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                               @PathVariable("bookingId") Long bookingId,
                               @RequestParam(required = false, defaultValue = "0") Long from,
                               @RequestParam(required = false, defaultValue = "10") Long size) {
        return bookingClient.get(userId, bookingId, from, size);
    }

    @GetMapping()
    ResponseEntity<Object> getAll(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                  Long userId,
                                  @RequestParam(required = false, name = "state", defaultValue = "ALL") String state,
                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return bookingClient.getAll(userId, state, from, size);
    }

    @GetMapping("/owner")
    ResponseEntity<Object> getAllOfOwner(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                         Long userId,
                                         @RequestParam(required = false, defaultValue = "ALL") String state,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        return bookingClient.getAllOfOwner(userId, state, page, size);
    }

}
