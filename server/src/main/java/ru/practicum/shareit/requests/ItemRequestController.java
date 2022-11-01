package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    ResponseEntity<ItemRequestDto> addItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                                  @RequestBody ItemRequestDto itemRequestDto) {
        log.info("SERVER: Create new Item Request={}, userId={}", itemRequestDto, userId);
        return ResponseEntity.ok(itemRequestService.add(userId, itemRequestDto));
    }

    @GetMapping
    ResponseEntity<List<ItemRequestDto>> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        log.info("SERVER: Get Item Request, userId={}", userId);
        return ResponseEntity.ok(itemRequestService.get(userId));
    }

    @GetMapping("/all")
    ResponseEntity<List<ItemRequestDto>> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                                @RequestParam(required = false, defaultValue = "0") Long from,
                                                @RequestParam(required = false, defaultValue = "10") Long size) {
        log.info("SERVER: Get All Item Requests userId={}", userId);
        return ResponseEntity.ok(itemRequestService.getAll(userId, from, size));
    }

    @GetMapping("/{itemRequestId}")
    ResponseEntity<ItemRequestDto> getById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                           @PathVariable("itemRequestId") Long itemRequestId) {
        log.info("SERVER: Get Item Request={}, userId={}", itemRequestId, userId);
        return ResponseEntity.ok(itemRequestService.getById(userId, itemRequestId));
    }

}
