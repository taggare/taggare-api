package com.sns.server.account;

import com.sns.server.common.ApiClientResponse;
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
        ApiClientResponse response = ApiClientResponse.builder()
                .data(accountService.createUser(accountDto))
                .message("회원가입이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

         return ResponseEntity.status(response.getStatus()).body(response.getMessage());
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
