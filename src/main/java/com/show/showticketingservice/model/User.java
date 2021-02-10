package com.show.showticketingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private String id;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private String address;

}
