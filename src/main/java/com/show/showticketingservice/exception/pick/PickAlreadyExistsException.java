package com.show.showticketingservice.exception.pick;

public class PickAlreadyExistsException extends RuntimeException {

    public PickAlreadyExistsException() {
        super("이미 찜으로 등록된 공연입니다.");
    }

}
