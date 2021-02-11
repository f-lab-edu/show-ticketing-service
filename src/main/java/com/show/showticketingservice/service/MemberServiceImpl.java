package com.show.showticketingservice.service;

import com.show.showticketingservice.model.MemberDTO;
import com.show.showticketingservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberRepository memberRepository;

    @Override
    public int insertMember(MemberDTO memberDTO) {
        return memberRepository.insertMember(memberDTO);
    }

    @Override
    public boolean checkMemberId(String id) {
        MemberDTO result = memberRepository.selectMemberId(id);
        if(result == null) return false;

        return true;
    }

    @Override
    public boolean checkMemberEmail(String email) {
        MemberDTO result = memberRepository.selectMemberEmail(email);
        if(result == null) return false;

        return true;
    }
}
