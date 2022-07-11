package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;


    @PostMapping
    ResponseEntity<User> add(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.add(user));
    }

    @PatchMapping("/{id}")
    ResponseEntity<User> update(@Valid @PathVariable("id") Long id,
                                @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @GetMapping("/{id}")
    ResponseEntity<User> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping
    ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

}
