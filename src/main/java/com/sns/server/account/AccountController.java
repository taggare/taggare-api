package com.sns.server.account;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid final AccountDto.Create accountDto) {
//        ApiClientResponse response = ApiClientResponse.builder()
//                .data(accountService.createUser(accountDto))
//                .status(HttpStatus.OK)
//                .build();

        // return ResponseEntity.status(response.getStatus()).body(response);
        accountService.createUser(accountDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원정보 요청")
    public ResponseEntity get(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "회원정보 수정")
    public ResponseEntity<AccountDto.Update> update(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
