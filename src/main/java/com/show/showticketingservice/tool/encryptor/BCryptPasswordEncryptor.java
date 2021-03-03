package com.show.showticketingservice.tool.encryptor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncryptor implements Encryptor {

    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public Boolean isMatched(String passwordRequest, String encryptedPassword) {
        return BCrypt.checkpw(passwordRequest, encryptedPassword);
    }
}
