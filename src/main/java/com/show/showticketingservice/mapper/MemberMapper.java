package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int insertMember(MemberDTO memberDTO);

    MemberDTO selectMemberId(String id);

    MemberDTO selectMemberEmail(String email);
}
