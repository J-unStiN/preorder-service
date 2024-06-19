package com.junstin.preorder.domain.member.repository;

import com.junstin.preorder.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}