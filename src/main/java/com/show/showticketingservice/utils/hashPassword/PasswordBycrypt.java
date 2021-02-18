package com.show.showticketingservice.utils.hashPassword;

public interface PasswordBycrypt {

    String encode(String password);

    boolean isMatch(String password, String hashed);
}
