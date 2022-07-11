package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequest;

import javax.validation.Valid;

/**
 * // TODO .
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    ResponseEntity addItemRequest(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                  @RequestBody Booking booking,
                                  @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(bookingService.add(userId, itemId, booking));
    }

}
