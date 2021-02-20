package com.show.showticketingservice.repository;

import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    private UserDTO testMember;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        testMember = UserDTO.builder()
                .id("tlsgkr7416")
                .password("123456!aA")
                .email("tls7gkr@naver.com")
                .name("강신학")
                .phoneNum("010-9905-7416")
                .address("부천시 오정구")
                .build();
    }

    private int insertMember() {
        return userRepository.insertUser(testMember);
    }

    @Test
    @DisplayName("회원가입 성공시 db에서 0이상의 숫자를 반환합니다.")
    public void registerMemberTest() {
        int result = insertMember();

        assertThat(result, greaterThan(0));
    }

    @Test
    @DisplayName("회원가입을 할때 id가 중복될 경우 db에서 중복된 숫자를 반환합니다.")
    public void checkDuplicateIdTest() {

        insertMember();

        int result = userRepository.selectUserId(testMember.getId());

        assertThat(result, greaterThan(0));
    }

}
