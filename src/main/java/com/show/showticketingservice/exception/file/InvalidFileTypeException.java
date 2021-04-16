package com.show.showticketingservice.exception.file;

public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException() {
        super("지원되지 않는 파일 형식입니다.");
    }
}
