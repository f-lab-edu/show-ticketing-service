package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.DuplicateIdException;
import com.show.showticketingservice.exception.IdUnconformityException;
import com.show.showticketingservice.exception.PasswordUnconformityException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.utils.encoder.BCryptPasswordEncoder;
import com.show.showticketingservice.utils.encoder.PasswordEncoder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserDTO testMember;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

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

    @Test
    @DisplayName("중복된 아이디가 있을 경우 DuplicateIdException이 발생합니다.")
    public void checkDuplicateId() {

        when(userMapper.isDuplicateUserId(testMember.getUserId())).thenReturn(true);

        assertThrows(DuplicateIdException.class, () -> {
            userService.duplicateUserId(testMember.getUserId());
        });

        verify(userMapper, times(1)).isDuplicateUserId(testMember.getUserId());
    }

    @Test
    @DisplayName("비밀번호가 암호화한 비밀번호와 일치하지 않을시 PasswordUnconformityException이 발생합니다.")
    public void validatePasswordFail() {

        String notHashPassword = "aaaa123!azvd";

        when(passwordEncoder.isMatch(testMember.getPassword(), notHashPassword)).thenReturn(false);

        assertThrows(PasswordUnconformityException.class, () -> {
            userService.validatePassword(testMember.getPassword(), notHashPassword);
        });

        verify(passwordEncoder, times(1)).isMatch(testMember.getPassword(), notHashPassword);
    }

}
