package school.faang.user_service.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.auth.AuthRequest;
import school.faang.user_service.dto.auth.JwtTokens;
import school.faang.user_service.dto.auth.ResponseToken;
import school.faang.user_service.dto.auth.Token;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.service.auth.AuthService;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Authentication",
        description = "User authentication and authorization endpoints"
)
public class AuthController {
    public static final String REFRESH_COOKIE_NAME = "refresh_token";

    private final AuthService authService;

    @Operation(
            summary = "User registration",
            description = "Registers a new user and returns an access token",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully. "
                                    + "Returns an access token and sets an HttpOnly refresh token cookie"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseToken> register(
            @RequestBody @Valid CreateUserDto dto
    ) {
        JwtTokens jwtTokens = authService.register(dto);
        return createAuthenticationResponse(jwtTokens);
    }

    @Operation(
            summary = "User authentication",
            description = "Authenticates a user using login credentials and returns an access token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful. "
                                    + "Returns an access token and sets an HttpOnly refresh token cookie"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Invalid credentials"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ResponseToken> authenticate(
            @RequestBody @Valid AuthRequest dto
    ) {
        JwtTokens jwtTokens = authService.authenticate(dto);
        return createAuthenticationResponse(jwtTokens);
    }

    @Operation(
            summary = "Refresh access token",
            description = "Issues a new access token based on the refresh token stored in an HTTP cookie",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "New access token and its expiration time"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Refresh token not found"
                    )
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseToken> refreshToken(
            @CookieValue(value = REFRESH_COOKIE_NAME) String refreshToken
    ) {
        Token token = authService.refreshToken(refreshToken);
        return ResponseEntity.ok()
                .body(
                        new ResponseToken(
                                token.value(),
                                token.expireAt())
                );
    }

    private ResponseEntity<ResponseToken> createAuthenticationResponse(JwtTokens jwtTokens) {
        ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, jwtTokens.refreshToken().value())
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMillis(jwtTokens.refreshToken().expiration()))
                .sameSite("Strict")
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new ResponseToken(
                        jwtTokens.accessToken().value(),
                        jwtTokens.accessToken().expireAt())
                );
    }
}
