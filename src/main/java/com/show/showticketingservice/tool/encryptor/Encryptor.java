package com.show.showticketingservice.tool.encryptor;

public interface Encryptor {

    String encrypt(String rawPassword);

    Boolean isMatched(String passwordRequest, String encryptedPassword);

}
