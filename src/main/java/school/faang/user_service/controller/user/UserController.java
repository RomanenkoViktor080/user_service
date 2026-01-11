package school.faang.user_service.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
@Tag(name = "Users", description = "Interaction with users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Updating user data",
            description = "Allows you to update user data"
    )
    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable long userId, @RequestBody @Valid UpdateUserDto userDto) {
        return userService.update(userId, userDto);
    }

    @PutMapping("/profile")
    public UserDto update(
            @RequestBody
            UpdateUserDto dto
    ) {
        return userService.updateProfile(dto);
    }

    @Operation(
            summary = "Search user by ID",
            description = "Allows you to get a user by their ID"
    )
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }


    @Operation(
            summary = "Deactivate user by ID",
            description = "Allows you to deactivate user by their ID"
    )
    public UserDto deactivateUserById(long userId) {
        return userService.deactivateUserById(userId);
    }
}
