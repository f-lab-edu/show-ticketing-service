package com.show.showticketingservice.tool.Encryptor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor implements Encryptor {

    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }
}
