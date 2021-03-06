package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserMapperTest {

    private UserDTO testMember;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        testMember = UserDTO.builder()
                .userId("tlsgkr7416")
                .password("123456!aA")
                .email("tls7gkr@naver.com")
                .name("강신학")
                .phoneNum("010-9905-7416")
                .address("부천시 오정구")
                .build();
    }

    private int insertMember() {
        return userMapper.insertUser(testMember);
    }

    @Test
    @DisplayName("회원가입 성공시 db에서 1 숫자를 반환합니다.")
    public void registerMemberTest() {

        assertEquals(insertMember(), 1);
    }

    @Test
    @DisplayName("회원가입을 할때 id가 중복될 경우 db에서 true를 반환합니다.")
    public void checkDuplicateIdTest() {

        insertMember();

        assertEquals(userMapper.isDuplicateUserId(testMember.getUserId()), true);

    }

    @Test
    @DisplayName("로그인시 id를 통해 비밀번호를 반환합니다.")
    public void getUseByIdTest() {

        insertMember();

        assertNotNull(userMapper.getUserPasswordById(testMember.getUserId()));

    }

    @Test
    @DisplayName("회원 탈퇴 성공시 1을 반환합니다.")
    public void deleteUser() {

        insertMember();

        assertEquals(userMapper.deleteUserByUserId(testMember.getUserId()), 1);
    }

}
