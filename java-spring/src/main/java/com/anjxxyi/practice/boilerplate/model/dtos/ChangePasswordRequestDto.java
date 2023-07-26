package com.anjxxyi.practice.boilerplate.model.dtos;

import lombok.*;

@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 필드의 생성자 자동생성
@NoArgsConstructor  // final이나 @NotNull이 붙은 필드의 생성자 자동생성
public class ChangePasswordRequestDto {
    private String email;
    private String exPassword;
    private String newPassword;
}
