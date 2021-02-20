package com.show.showticketingservice.utils.encoder;

/**
 * 암호화 방법의 변경 가능성이 있을 수 있으므로 추상화 시킴
 */
public interface PasswordEncoder {

    String encode(String password);

    boolean isMatch(String password, String hashed);
}
