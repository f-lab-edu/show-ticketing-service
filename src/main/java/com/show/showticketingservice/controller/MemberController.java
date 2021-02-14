package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.MemberDTO;
import com.show.showticketingservice.service.MemberService;
import com.show.showticketingservice.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/memberships")
    public ResponseEntity<Void> addMember(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Responses.BAD_REQUEST;
        }

        if (memberService.checkDuplication(memberDTO)) {
            return Responses.CONFLICT;
        }

        memberService.insertMember(memberDTO);
        return Responses.CREATED;
    }

}
