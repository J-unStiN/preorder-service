package com.junstin.preorder.domain.member.service;


import com.junstin.preorder.config.redis.RedisService;
import com.junstin.preorder.domain.email.service.EmailService;
import com.junstin.preorder.domain.member.dto.req.MemberEmailAuthReqDto;
import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import com.junstin.preorder.domain.member.dto.res.MemberSignUpResDto;
import com.junstin.preorder.domain.member.entity.Member;
import com.junstin.preorder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final EmailService emailService;


    public MemberSignUpResDto SignUp(MemberSignUpReqDto memberSignUpReqDto) {
        Member signMember = Member.create(memberSignUpReqDto);
        Member saveMember = memberRepository.save(signMember);

        return MemberSignUpResDto.builder()
                .name(saveMember.getName())
                .email(saveMember.getEmail())
                .build();
    }

    /* 이메일 인증번호 레디스에 저장 */
    public String emailAuthSend(String email) {
        String randomString = emailService.generateRandomString();
        redisService.setValues(email, randomString, Duration.ofMinutes(3));
        emailService.emailAuthSend(email, "인증번호 발송", randomString);

        return randomString;
    }

    /* 인증번호 검증 */
    public void emailAuthCheck(MemberEmailAuthReqDto memberEmailAuthReqDto) {
        boolean randomString = emailService.isRandomString(memberEmailAuthReqDto.email(), memberEmailAuthReqDto.authNumber());
        if (!randomString) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
    }


    /* 이메일 검증 - 이메일 없으면 true
    * ENUM 만들어서 에러메시지 통일하기 */
    public boolean SignUpEmailValid(String email) {
        Member byEmail = memberRepository.findByEmail(email);
        if (byEmail != null) {
            return false;
        }

        return true;
    }


}
