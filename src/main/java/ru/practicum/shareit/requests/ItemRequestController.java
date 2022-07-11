package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService;

import javax.validation.Valid;

/**
 * // TODO .
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    ResponseEntity addItemRequest(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                  @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemRequestService.add(userId, itemRequest));
    }

    @DeleteMapping("/{requestId}")
    void deleteItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                     @PathVariable("requestId") Long requestId) {
        itemRequestService.delete(userId, requestId);
    }
}
