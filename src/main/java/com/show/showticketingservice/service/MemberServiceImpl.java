package com.show.showticketingservice.service;

import com.show.showticketingservice.model.MemberDTO;
import com.show.showticketingservice.repository.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public int insertMember(MemberDTO memberDTO) {
        String hashPassword = BCrypt.hashpw(memberDTO.getPassword(), BCrypt.gensalt());
        MemberDTO hashMemberDTO = new MemberDTO(
                memberDTO.getId(),
                hashPassword,
                memberDTO.getName(),
                memberDTO.getPhoneNum(),
                memberDTO.getEmail());

        return memberRepository.insertMember(hashMemberDTO);
    }

    @Override
    public boolean checkMemberId(String id) {
        MemberDTO result = memberRepository.selectMemberId(id);

        return result != null;
    }

    @Override
    public boolean checkMemberEmail(String email) {
        MemberDTO result = memberRepository.selectMemberEmail(email);

        return result != null;
    }

    @Override
    public boolean checkDuplication(MemberDTO memberDTO) {
        return checkMemberId(memberDTO.getId()) || checkMemberEmail(memberDTO.getEmail());
    }

}
