package com.show.showticketingservice.repository;

import com.show.showticketingservice.mapper.MemberMapper;
import com.show.showticketingservice.model.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @Autowired
    MemberMapper memberMapper;

    public int insertMember(MemberDTO memberDTO) {
        return memberMapper.insertMember(memberDTO);
    }

    public MemberDTO selectMemberId(String id) {
        return memberMapper.selectMemberId(id);
    }

    public MemberDTO selectMemberEmail(String email) {
        return memberMapper.selectMemberEmail(email);
    }

}
