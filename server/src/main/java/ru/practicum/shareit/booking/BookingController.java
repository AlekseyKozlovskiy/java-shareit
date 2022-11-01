package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    ResponseEntity<BookingDto> addItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                              @RequestBody BookingDto bookingDto) {
        log.info("SERVER: Creating booking {}, userId={}", bookingDto, userId);
        return ResponseEntity.ok(bookingService.add(userId, bookingDto));
    }

    @PatchMapping("/{bookingId}")
    ResponseEntity<BookingDto> upgradeNewItem(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                              @RequestParam(required = false) Boolean approved,
                                              @PathVariable("bookingId") Long bookingId) {
        log.info("SERVER: Updating booking {}", bookingId);
        return ResponseEntity.ok(bookingService.upgrade(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    ResponseEntity<BookingDto> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                   @PathVariable("bookingId") Long bookingId,
                                   @RequestParam(required = false) Long from,
                                   @RequestParam(required = false) Long size) {
        log.info("SERVER: Get booking {} userId={}", bookingId, userId);
        return ResponseEntity.ok(bookingService.get(userId, bookingId, from, size));
    }

    @GetMapping()
    ResponseEntity<List<BookingDto>> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                            Long userId,
                                            @RequestParam(required = false, defaultValue = "ALL") String state,
                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("SERVER: Get all bookings, userId={}", userId);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getAll(userId, state, pageRequest));
    }

    @GetMapping("/owner")
    ResponseEntity<List<BookingDto>> getAllOfOwner(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                                   Long userId,
                                                   @RequestParam(required = false, defaultValue = "ALL") String state,
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("SERVER: Get all bookings by owner, userId={}", userId);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getAllOfOwner(userId, state, pageRequest));
    }

}
