package com.junstin.preorder.domain.member.entity;

import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
@Entity
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(unique = true, nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 25)
    private String password;

    @Column(unique = true, nullable = false, length = 13)
    private String phoneNumber;

    @Column(nullable = true, length = 70)
    private String address;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;


    public static Member create(MemberSignUpReqDto memberSignUpReqDto) {
        return Member.builder()
                .name(memberSignUpReqDto.name())
                .email(memberSignUpReqDto.email())
                .password(memberSignUpReqDto.password())
                .phoneNumber(memberSignUpReqDto.phoneNumber())
                .address(memberSignUpReqDto.address())
                .build();
    }
}
