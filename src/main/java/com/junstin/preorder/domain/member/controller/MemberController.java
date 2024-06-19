package com.junstin.preorder.domain.member.controller;


import com.junstin.preorder.domain.member.dto.req.MemberSignUpReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1")
@RestController
public class MemberController {

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberSignUpReqDto memberSign) {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body("");

    }


}
