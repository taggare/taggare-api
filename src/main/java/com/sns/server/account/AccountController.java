package com.sns.server.account;

import com.sns.server.common.ApiClientResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountDtoValidator accountDtoValidator;

    public ResponseEntity<?> sendErrorResponse(Errors errors) {
        return ResponseEntity.badRequest().body(ApiClientResponse.builder()
                .message(errors.getAllErrors().get(0).getDefaultMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<?> create(@RequestBody @Valid final AccountDto.Create accountDto) {
//        accountDtoValidator.validate(result, errors);
//        if (errors.hasErrors()) {
//            return sendErrorResponse(errors);
//        }

        accountService.create(accountDto);
        ApiClientResponse response = ApiClientResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원정보 요청")
    @ApiResponses(@ApiResponse(code = 400, message = "해당 유저가 없는 경우"))
    public ResponseEntity get(@PathVariable Long id) {
        ApiClientResponse response = ApiClientResponse.builder()
                .data(AccountDto.Read.from(accountService.get(id)))
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
            return sendErrorResponse(errors);
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
