package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;


    @PostMapping
    ResponseEntity<UserDto> add(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.add(userDto));
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserDto> upgrade(@Valid @PathVariable("id") Long id,
                                    @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.upgrade(id, userDto));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

}
