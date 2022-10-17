package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Validated
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
    ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAll(pageRequest));
    }

}
