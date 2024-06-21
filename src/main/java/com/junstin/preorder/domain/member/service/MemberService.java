package com.junstin.preorder.domain.member.service;


import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import com.junstin.preorder.domain.member.dto.res.MemberSignUpResDto;
import com.junstin.preorder.domain.member.entity.Member;
import com.junstin.preorder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberSignUpResDto SignUp(MemberSignUpReqDto memberSignUpReqDto) {
        Member signMember = Member.builder()
                .name(memberSignUpReqDto.name())
                .email(memberSignUpReqDto.email())
                .password(memberSignUpReqDto.password())
                .phoneNumber(memberSignUpReqDto.phoneNumber())
                .address(memberSignUpReqDto.address())
                .build();

        Member saveMember = memberRepository.save(signMember);

        return MemberSignUpResDto.builder()
                .name(saveMember.getName())
                .email(saveMember.getEmail())
                .build();
    }


}
