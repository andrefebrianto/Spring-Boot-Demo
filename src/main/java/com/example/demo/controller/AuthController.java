package com.example.demo.controller;

import com.example.demo.common.exception.TokenInvalidException;
import com.example.demo.common.util.JwtUtil;
import com.example.demo.model.entity.RefreshToken;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserDetail;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.AuthRefreshTokenRequest;
import com.example.demo.model.request.AuthSignInRequest;
import com.example.demo.model.request.AuthSignUpRequest;
import com.example.demo.model.response.AuthJwtResponse;
import com.example.demo.model.response.AuthRefreshTokenResponse;
import com.example.demo.model.response.UserResponse;
import com.example.demo.model.response._MessageSuccessResponse;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "1. Authentication")
public class AuthController {

    @Autowired private JwtUtil jwtUtil;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private RefreshTokenService refreshTokenService;

    @Autowired private UserMapper userMapper;

    @Autowired private UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "Sign Up")
    public ResponseEntity<UserResponse> signup(@Validated @RequestBody AuthSignUpRequest request) {
        User user = userMapper.authSignUpRequestToUser(request);
        user = userService.create(user);
        UserResponse response = userMapper.userToUserResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign In")
    public ResponseEntity<AuthJwtResponse> signin(@Validated @RequestBody AuthSignInRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        String jwt = jwtUtil.generateJwtToken(userDetail);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetail.getId());

        return ResponseEntity.ok(new AuthJwtResponse(userDetail.getId(), jwt, refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Regenerate Token")
    public ResponseEntity<AuthRefreshTokenResponse> refreshtoken(
            @Validated @RequestBody AuthRefreshTokenRequest request) {
        String refreshtoken = request.getRefreshToken();

        return refreshTokenService
                .findByToken(refreshtoken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(
                        user -> {
                            String token = jwtUtil.generateTokenFromEmail(user.getEmail());
                            return ResponseEntity.ok(new AuthRefreshTokenResponse(token, refreshtoken));
                        })
                .orElseThrow(() -> new TokenInvalidException("Invalid refresh token"));
    }

    @PostMapping("/sign-out")
    @Operation(summary = "Sign Out")
    public ResponseEntity<_MessageSuccessResponse> signout(
            @Validated @RequestBody AuthRefreshTokenRequest request) {
        String refreshtoken = request.getRefreshToken();

        return refreshTokenService
                .findByToken(refreshtoken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(
                        user -> {
                            refreshTokenService.deleteAllByUserId(user.getId());
                            return ResponseEntity.ok(new _MessageSuccessResponse("Log out successful"));
                        })
                .orElseThrow(() -> new TokenInvalidException("Invalid refresh token"));
    }
}
