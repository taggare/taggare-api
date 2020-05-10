package com.sns.server.account;

import com.sns.server.common.ApiResponse;
import com.sns.server.security.tokens.PostAuthorizationToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final AccountDtoValidator accountDtoValidator;

    public ResponseEntity<?> sendErrorResponse(Errors errors) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(errors.getAllErrors().get(0).getDefaultMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @CrossOrigin
    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<?> create(@RequestBody @Valid final AccountDto.Create accountDto,
                                    @ApiIgnore Errors errors, BindingResult result) {
        accountDtoValidator.validate(result, errors);
        if (errors.hasErrors()) {
            return sendErrorResponse(errors);
        }

        accountService.create(accountDto);
        ApiResponse response = ApiResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin
    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원정보 요청")
    @ApiResponses({@io.swagger.annotations.ApiResponse(code = 400, message = "클라이언트에서 잘못된 요청함."),
            @io.swagger.annotations.ApiResponse(code = 401, message = "비인증된 클라이언트에서 요청함."),
            @io.swagger.annotations.ApiResponse(code = 403, message = "요청한 클라이언트는 서버에 접근할 권한이 없음."),
            @io.swagger.annotations.ApiResponse(code = 404, message = "클라이언트에서 요청했으나 찾으려는 사용가 존재하지 않음.")})
    public ResponseEntity get(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .data(AccountDto.Read.from(accountService.get(id)))
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin
    @PutMapping("/users/{id}")
    @ApiOperation(value = "회원정보 수정")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid final AccountDto.Update accountDto,
                                    @ApiIgnore Errors errors, BindingResult result) {
        accountDtoValidator.validate(result, errors);
        if (errors.hasErrors()) {
            return sendErrorResponse(errors);
        }

        accountService.update(id, accountDto);
        ApiResponse response = ApiResponse.builder()
                .message("회원정보 수정이 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin
    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity delete(@PathVariable Long id) {
        accountService.delete(id);
        ApiResponse response = ApiResponse.builder()
                .message("회원탈퇴가 완료되었습니다.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @CrossOrigin
//    @GetMapping("/users/hello")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity getHello(Authentication authentication) {
//        PostAuthorizationToken authorizationToken = (PostAuthorizationToken) authentication;
//        ApiResponse response = ApiResponse.builder()
//                .data(authorizationToken.getAccountContext().getUsername())
//                .status(HttpStatus.OK)
//                .build();
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }
  
    @CrossOrigin
    @GetMapping("/users/hello")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getHello(Authentication authentication) {
        PostAuthorizationToken authorizationToken = (PostAuthorizationToken) authentication;
        ApiResponse response = ApiResponse.builder()
                .data(authorizationToken.getAccountContext().getUsername())
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
