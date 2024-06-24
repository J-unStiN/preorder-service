package com.junstin.preorder.domain.member.controller;


import com.junstin.preorder.common.CommonResponse;
import com.junstin.preorder.domain.member.dto.req.MemberEmailAuthReqDto;
import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import com.junstin.preorder.domain.member.dto.res.MemberSignUpResDto;
import com.junstin.preorder.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class MemberController {
    private final MemberService memberService;

    /* 회원 가입 */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberSignUpReqDto memberSign) {
        MemberSignUpResDto dto = memberService.SignUp(memberSign);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .status(HttpStatus.CREATED)
                        .message("회원가입이 완료되었습니다.")
                        .data(dto));
    }

    /* 이메일 검증 */
    @PostMapping("/email/valid/{email}")
    public ResponseEntity<?> email(@PathVariable("email") String email) {
        boolean isCheck = memberService.SignUpEmailValid(email);
        String message = "이메일 없음";
        if (!isCheck) {
            message = "가입되어있는 이메일 입니다.";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .status(HttpStatus.OK)
                        .message(message));
    }

    /* 이메일 인증번호 발송 */
    @PostMapping("/email/auth/{email}")
    public ResponseEntity<?> emailAuthSend(@PathVariable("email") String email) {
        String randomString = memberService.emailAuthSend(email);


        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .status(HttpStatus.OK)
                        .message("인증번호 이메일로 발송")
                        .data(randomString));
    }

    /* 이메일 인증번호 검증 */
    @PostMapping("/email/auth")
    public ResponseEntity<?> emailAuth(@RequestBody MemberEmailAuthReqDto emailAuthReqDto) {


        return null;
    }


}
