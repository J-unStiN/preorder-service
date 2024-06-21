package com.junstin.preorder.domain.member.dto.res;

import lombok.Builder;

@Builder
public record MemberSignUpResDto(
        String name,
        String email
) {
}
