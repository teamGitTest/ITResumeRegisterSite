package kr.co.itresumeregistersite.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto { // ํ์์กฐํ Dto

    private String name;

    private String email;

    private String birth;

    private String gender;
}

