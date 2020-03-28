package com.sns.server.account;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public ResponseEntity create(@RequestBody @Valid UserDTO.Create user) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원정보 요청")
    public ResponseEntity get(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "회원정보 수정")
    public ResponseEntity update(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
