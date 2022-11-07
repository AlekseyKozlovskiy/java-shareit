package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping
    ResponseEntity<UserDto> add(@RequestBody UserDto userDto) {
        log.info("SERVER: Add new user {}", userDto);
        return ResponseEntity.ok(userService.add(userDto));
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserDto> upgrade(@PathVariable("id") Long id,
                                    @RequestBody UserDto userDto) {
        log.info("SERVER: Upgrade new user {}, id={}", userDto, id);
        return ResponseEntity.ok(userService.upgrade(id, userDto));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getById(@PathVariable("id") Long userId) {
        log.info("SERVER: Get user, id={}", userId);
        return ResponseEntity.ok(userService.get(userId));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        log.info("SERVER: Delete user, id={}", id);
        userService.delete(id);
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAll(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("SERVER: Get All  users");
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAll(pageRequest));
    }

}
