package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Validated
@Slf4j
public class UserController {
    private final UserClient userClient;


    @PostMapping
    ResponseEntity<Object> add(@Valid @RequestBody UserDto userDto) {
        log.info("GATEWAY: Add new user {}", userDto);
        return userClient.add(userDto);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Object> upgrade(@Valid @PathVariable("id") Long id,
                                   @RequestBody UserDto userDto) {
        return userClient.upgrade(id, userDto);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable("id") Long userId) {
        return userClient.getById(userId);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        userClient.delete(id);
    }

    @GetMapping
    ResponseEntity<Object> getAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        System.out.println("from на контроллере gateway -> " + from);
        return userClient.getAll(from, size);
    }

}
