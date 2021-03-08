package com.show.showticketingservice.tool.encryptor;

public interface PasswordEncryptor {

    String encrypt(String rawPassword);

    Boolean isMatched(String passwordRequest, String encryptedPassword);

}
