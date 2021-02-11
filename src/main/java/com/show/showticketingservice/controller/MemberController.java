package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.MemberDTO;
import com.show.showticketingservice.service.MemberService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/membership")
    public ResponseEntity<Void> addMember(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (memberService.checkMemberId(memberDTO.getId()) || memberService.checkMemberEmail(memberDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String hashPassword = BCrypt.hashpw(memberDTO.getPassword(), BCrypt.gensalt());
        memberDTO.setPassword(hashPassword);
        memberService.insertMember(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
