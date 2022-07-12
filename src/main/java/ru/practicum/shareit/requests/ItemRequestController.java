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
    private final ItemRequestService itemRequestService;

    @PostMapping
    ResponseEntity<ItemRequestDto> addItemRequest(@Valid
                                                  @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                                  @RequestBody ItemRequestDto itemRequestDto) {
        return ResponseEntity.ok(itemRequestService.add(userId, itemRequestDto));
    }

    @DeleteMapping("/{requestId}")
    void deleteItemRequest(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                           @PathVariable("requestId") Long requestId) {
        itemRequestService.delete(userId, requestId);
    }
}
