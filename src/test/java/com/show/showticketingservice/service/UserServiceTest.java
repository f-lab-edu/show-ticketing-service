package com.show.showticketingservice.service;

import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserDTO testMember;

    @Mock
    private UserService userService;

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

    @Test
    @DisplayName("중복된 아이디가 있을 경우 true를 리턴합니다.")
    public void checkDuplicateId() {
        when(userService.isDuplicateUserId(testMember.getId())).thenReturn(true);

        assertEquals(userService.isDuplicateUserId(testMember.getId()), true);
    }

    @Test
    @DisplayName("중복된 이메일이 있을 경우 true를 리턴합니다.")
    public void checkDuplicateEmail() {
        when(userService.isDuplicateUserEmail(testMember.getEmail())).thenReturn(true);

        assertEquals(userService.isDuplicateUserEmail(testMember.getEmail()), true);
    }
}
