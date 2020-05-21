package com.sns.server.login;

import com.sns.server.account.AccountDtoValidator;
import com.sns.server.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;
    private final AccountDtoValidator accountDtoValidator;

    public ResponseEntity<?> sendErrorResponse(Errors errors) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(errors.getAllErrors().get(0).getDefaultMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

//    @CrossOrigin
//    @PostMapping("/login")
//    @ApiOperation("로그인 - 인증 성공 시 token 제공")
//    public ResponseEntity<?> create(@RequestBody @Valid FormLoginDto loginDto,
//                                    @ApiIgnore Errors errors, BindingResult result) {
//        accountDtoValidator.validate(result, errors);
//        if (errors.hasErrors()) {
//            return sendErrorResponse(errors);
//        }
//
//        loginService.authenticate((Authentication) loginDto);
//        ApiResponse response = ApiResponse.builder()
//                .status(HttpStatus.OK)
//                .build();
//
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }
}
