package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;


/**
 * // TODO .
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;


    @PostMapping
    public ResponseEntity add(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                              @RequestBody Item item) {
        return ResponseEntity.ok(ItemMapper.toItemDto(itemService.addNewItem(userId, item)));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity upgrade(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                  @RequestBody Item item,
                                  @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.upgradeItem(userId, item, itemId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                              @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.get(userId, itemId));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        return ResponseEntity.ok(itemService.getAll(userId));
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(name = "text") String text) {
        return ResponseEntity.ok(itemService.search(text));
    }
}
