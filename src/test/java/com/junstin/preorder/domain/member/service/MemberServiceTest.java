package com.junstin.preorder.domain.member.service;

import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import com.junstin.preorder.domain.member.dto.res.MemberSignUpResDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberServiceTest {


    @Autowired private MemberService memberService;


    @DisplayName("회원가입 테스트 (인증없이)")
    @Test
    public void 인증없이회원가입테스트() {
        MemberSignUpReqDto dto = MemberSignUpReqDto.builder()
                .name("손흥민")
                .password("손흥민의비밀번호486")
                .email("sonheungmin@gmail.com")
                .phoneNumber("010-1111-2222")
                .build();

        MemberSignUpResDto save = memberService.SignUp(dto);
        Assertions.assertThat(save.email()).isEqualTo(dto.email());
    }
}