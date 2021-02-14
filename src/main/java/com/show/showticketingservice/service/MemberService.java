package com.show.showticketingservice.service;

import com.show.showticketingservice.model.MemberDTO;

public interface MemberService {

    int insertMember(MemberDTO memberDTO);

    boolean checkMemberId(String id);

    boolean checkMemberEmail(String email);

    boolean checkDuplication(MemberDTO memberDTO);

}
