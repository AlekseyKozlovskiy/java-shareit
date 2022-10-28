package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;


    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                      @RequestBody ItemDto itemDto) {
        return itemClient.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> upgrade(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody ItemDto itemDto,
                                          @PathVariable("itemId") Long itemId) {
        return itemClient.upgradeItem(userId, itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                      @PathVariable("itemId") Long itemId) {
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        return itemClient.getAll(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text) {
        return itemClient.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                             @RequestBody CommentDtoNew commentDtoNew,
                                             @PathVariable("itemId") Long itemId) {
        return itemClient.addComment(userId, commentDtoNew, itemId);
    }
}
