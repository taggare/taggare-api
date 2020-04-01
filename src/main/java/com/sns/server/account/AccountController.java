package com.sns.server.account;

import com.sns.server.common.ApiClientResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AccountDtoValidator accountDtoValidator;

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<?> create(@RequestBody @Valid final AccountDto.Create accountDto,
                                    BindingResult result,
                                    Errors errors) {
        accountDtoValidator.validate(result, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiClientResponse.builder()
                    .message(errors.getAllErrors().get(0).getDefaultMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }

        accountService.create(accountDto);
        ApiClientResponse response = ApiClientResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원정보 요청")
    public ResponseEntity get(@PathVariable Long id) {
        ApiClientResponse response = ApiClientResponse.builder()
                .data(accountService.isExistAccount(id))
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "회원정보 수정")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid final AccountDto.Update accountDto,
                                    BindingResult result,
                                    Errors errors) {
        accountDtoValidator.validate(result, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiClientResponse.builder()
                    .message(errors.getAllErrors().get(0).getDefaultMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }

        accountService.update(id, accountDto);
        ApiClientResponse response = ApiClientResponse.builder()
                .message("회원정보 수정이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity delete(@PathVariable Long id) {
        accountService.delete(id);
        ApiClientResponse response = ApiClientResponse.builder()
                .message("회원탈퇴가 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
