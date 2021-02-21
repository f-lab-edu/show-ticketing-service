package com.show.showticketingservice.service;

import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.utils.encoder.PasswordEncoder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserDTO testMember;

    @InjectMocks
    private UserService userService;

    @Mock
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

    @Test
    @DisplayName("중복된 아이디가 있을 경우 true를 리턴합니다.")
    public void checkDuplicateId() {
        when(userRepository.selectUserId(testMember.getId())).thenReturn(1);

        assertEquals(userService.isDuplicateUserId(testMember.getId()), true);
    }

}
