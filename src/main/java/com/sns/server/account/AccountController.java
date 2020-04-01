package com.sns.server.account;

import com.sns.server.common.ApiClientResponse;
import com.sns.server.common.TaggareException;
import com.sns.server.enums.ErrorCode;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid final AccountDto.Create accountDto, Errors errors) {
        // errors.rejectValue("closeEnrollmentDateTime", "wrong.value", "closeEnrollmentDateTime is wrong");
        if (errors.hasErrors()) {
            System.out.println("ERROR CHECK");
            return ResponseEntity.badRequest().body(new TaggareException(ErrorCode.NOT_FOUND_USER, errors.getObjectName()));
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
        accountService.delete(id);
        ApiClientResponse response = ApiClientResponse.builder()
                .message("회원탈퇴가 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
