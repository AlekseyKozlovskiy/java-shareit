package ru.practicum.shareit.item;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;


    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                      @Valid @RequestBody ItemDto itemDto) {
        log.info("GATEWAY: Create new Item ={}, userId={}", itemDto, userId);
        return itemClient.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> upgrade(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                          @RequestBody ItemDto itemDto,
                                          @PathVariable("itemId") Long itemId) {
        log.info("GATEWAY: Update Item ={}, userId={}", itemDto, userId);
        return itemClient.upgradeItem(userId, itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                      @NonNull @PathVariable("itemId") Long itemId) {
        log.info("GATEWAY: Get Item, userId={}", userId);
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        log.info("GATEWAY: Get All Items, userId={}", userId);
        return itemClient.getAll(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text) {
        log.info("GATEWAY: Search Items by text, text={}", text);
        return itemClient.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                                             @Valid @RequestBody CommentDtoNew commentDtoNew,
                                             @NotNull @PathVariable("itemId") Long itemId) {
        log.info("GATEWAY: Create new comment, comment={}, userId={}", commentDtoNew, userId);
        return itemClient.addComment(userId, commentDtoNew, itemId);
    }
}
