package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Validated
@Slf4j
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    ResponseEntity<Object> addItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody ItemRequestDto itemRequestDto) {
        log.info("GATEWAY: Create new Item Request={}, userId={}", itemRequestDto, userId);
        return itemRequestClient.add(userId, itemRequestDto);
    }

    @GetMapping
    ResponseEntity<Object> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        log.info("GATEWAY: Get Item Request, userId={}", userId);
        return itemRequestClient.get(userId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                  @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Long from,
                                  @Positive @RequestParam(required = false, defaultValue = "10") Long size) {
        log.info("GATEWAY: Get All Item Requests userId={}", userId);
        return itemRequestClient.getAll(userId, from, size);
    }

    @GetMapping("/{itemRequestId}")
    ResponseEntity<Object> getById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                   @PathVariable("itemRequestId") Long itemRequestId) {
        log.info("GATEWAY: Get Item Request={}, userId={}", itemRequestId, userId);
        return itemRequestClient.getById(userId, itemRequestId);
    }

}
