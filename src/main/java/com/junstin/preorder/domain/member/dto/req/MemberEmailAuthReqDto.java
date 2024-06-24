package com.junstin.preorder.domain.member.dto.req;

public record MemberEmailAuthReqDto(
        String email,
        String authNumber
) {
}
