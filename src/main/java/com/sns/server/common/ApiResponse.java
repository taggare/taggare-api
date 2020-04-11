package com.sns.server.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ApiResponse {

    @ApiModelProperty(notes = "요청에 대햔 결과 데이터(리스트일 경우)")
    private List<?> list;

    @ApiModelProperty(notes = "요청에 대햔 결과 데이터(단일 오브젝트인 경우)")
    private Object data;

    @ApiModelProperty(notes = "응답 HTTP STATUS CODE가 2XX가 아닌 경우 에러에 관한 메세지")
    private String message;

    @ApiModelProperty(notes = "응답 HTTP STATUS CODE")
    private HttpStatus status;
}
