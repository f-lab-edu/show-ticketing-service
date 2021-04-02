package com.show.showticketingservice.exception.venue;

public class VenueListPageOutOfRangeException extends RuntimeException {

    public VenueListPageOutOfRangeException() {
        super("공연장 리스트 조회 페이지 범위를 벗어났습니다.");
    }
}
