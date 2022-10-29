package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    ResponseEntity<Object> addItemRequest(@Valid
                                          @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.add(userId, itemRequestDto);
    }

    @GetMapping
    ResponseEntity<Object> get(@Valid
                               @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        return itemRequestClient.get(userId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAll(@Valid
                                  @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                  @RequestParam(required = false, defaultValue = "0") Long from,
                                  @RequestParam(required = false, defaultValue = "10") Long size) {
        return itemRequestClient.getAll(userId, from, size);
    }

    @GetMapping("/{itemRequestId}")
    ResponseEntity<Object> getById(@Valid
                                   @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                   @PathVariable("itemRequestId") Long itemRequestId) {
        return itemRequestClient.getById(userId, itemRequestId);
    }

}
