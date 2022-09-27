package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    ResponseEntity<BookingDto> addItemRequest(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                              @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.add(userId, bookingDto));
    }

    @PatchMapping("/{bookingId}")
    ResponseEntity<BookingDto> upgradeNewItem(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                              @RequestParam(required = false) Boolean approved,
                                              @PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(bookingService.upgrade(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    ResponseEntity<BookingDto> get(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                   @PathVariable("bookingId") Long bookingId) {
        System.out.println("!!!!!!");
        return ResponseEntity.ok(bookingService.get(userId, bookingId));
    }

    @GetMapping()
    ResponseEntity<List<BookingDto>> getAll(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                            Long userId,
                                            @RequestParam(required = false, defaultValue = "ALL") String state) {
        System.out.println(state);
        return ResponseEntity.ok(bookingService.getAll(userId, state));
    }

    @GetMapping("/owner")
    ResponseEntity<List<BookingDto>> getAllOfOwner(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                                   Long userId,
                                                   @RequestParam(required = false, defaultValue = "ALL") String state) {
        return ResponseEntity.ok(bookingService.getAllOfOwner(userId, state));
    }

}
