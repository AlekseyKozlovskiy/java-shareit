package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ru.practicum.shareit.item.ItemService itemService;


    @PostMapping
    public ResponseEntity<ItemDto> add(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                       @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.addNewItem(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> upgrade(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                           @RequestBody ItemDto itemDto,
                                           @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.upgradeItem(userId, itemDto, itemId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                       @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.get(userId, itemId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        System.out.println("!!!!!!!!" + userId);
        return ResponseEntity.ok(itemService.getAll(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam(value = "text") String text) {
        return ResponseEntity.ok(itemService.search(text));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDtoNew> addComment(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                                    @RequestBody CommentDtoNew commentDtoNew,
                                                    @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.addComment(userId, commentDtoNew, itemId));
    }
}
