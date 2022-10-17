package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    ResponseEntity<BookingDtoCreate> addItemRequest(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
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
                                   @PathVariable("bookingId") Long bookingId,
                                   @RequestParam(required = false) Long from,
                                   @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(bookingService.get(userId, bookingId, from, size));
    }

    @GetMapping()
    ResponseEntity<List<BookingDto>> getAll(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                            Long userId,
                                            @RequestParam(required = false, defaultValue = "ALL") String state,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getAll(userId, state, pageRequest));
    }

    @GetMapping("/owner")
    ResponseEntity<List<BookingDto>> getAllOfOwner(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false)
                                                   Long userId,
                                                   @RequestParam(required = false, defaultValue = "ALL") String state,
                                                   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getAllOfOwner(userId, state, pageRequest));
    }

}
