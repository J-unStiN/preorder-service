package com.junstin.preorder.domain.member.dto.req;

import lombok.Builder;

@Builder
public record MemberSignUpReqDto(
        String name,
        String password,
        String email,
        String phoneNumber,
        String address
) {
}
