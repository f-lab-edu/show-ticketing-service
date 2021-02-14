package com.show.showticketingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class User {

    private final String id;
    private final String password;
    private final String name;
    private final String phoneNum;
    private final String email;
    private final String address;

    public User pwEncryptedUser(String encryptedPw) {
        return builder()
                .id(getId())
                .password(encryptedPw)
                .name(getName())
                .phoneNum(getPhoneNum())
                .email(getEmail())
                .address(getAddress())
                .build();
    }
}
