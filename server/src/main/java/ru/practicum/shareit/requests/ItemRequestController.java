package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    ResponseEntity<List<ItemRequestDto>> get(@Valid
                                             @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        return ResponseEntity.ok(itemRequestService.get(userId));
    }

    @GetMapping("/all")
    ResponseEntity<List<ItemRequestDto>> getAll(@Valid
                                                @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                                @RequestParam(required = false, defaultValue = "0") Long from,
                                                @RequestParam(required = false, defaultValue = "10") Long size) {
        return ResponseEntity.ok(itemRequestService.getAll(userId, from, size));
    }

    @GetMapping("/{itemRequestId}")
    ResponseEntity<ItemRequestDto> getById(@Valid
                                           @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                           @PathVariable("itemRequestId") Long itemRequestId) {
        return ResponseEntity.ok(itemRequestService.getById(userId, itemRequestId));
    }

}
