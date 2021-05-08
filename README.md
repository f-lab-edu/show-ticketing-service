# show-ticketing-service
- 인터파크 티켓을 모티브로, 다양한 공연(뮤지컬, 연극, 콘서트) 정보를 제공하며 예매를 할 수 있는 서비스
- 서버(Back-End) 개발을 중점으로 진행하기 위해 Front-End 개발은 생략 & 카카오 오븐을 활용하여 간단한 UI 설계 진행

<br>

## &#128204; 프로젝트 목표
* 대용량 트래픽 상황을 목표로 한 성능 튜닝
* 객체지향 프로그래밍의 개념과 특징을 이해하고 구현
* 왜 이 기술을 사용하는지 명확한 이유 제시하기
* 코드 리뷰를 통한 피드백 제공
* 서비스 안정성을 높이기 위해 테스트 코드 작성

<br>

## &#128204; 프로젝트 사용 기술
- Spring Boot
- Java
- Maven
- MySQL
- Mybatis
- Redis

<br>

## &#128204; UI 설계
카카오 오븐 URL:  
https://ovenapp.io/view/cNr1OqmrrV2axhFd2BSPT7ZWaogTax6s/

<br>

## &#128204; DB ERD
🚀[Wiki - DB ERD 구조](https://github.com/f-lab-edu/show-ticketing-service/wiki/DB-ERD-%EA%B5%AC%EC%A1%B0)

<br>

## &#128204; 기능 정의
### 공통
 회원가입, 로그인(아웃), 회원정보 관리
### 일반 회원
 개요: 공연 조회, 공연 검색, 찜하기, 공연예매, 찜 관리, 예매 관리  
 상세: 🚀[Wiki - 일반 회원 기능 정의](https://github.com/f-lab-edu/show-ticketing-service/wiki/%EC%9D%BC%EB%B0%98-%ED%9A%8C%EC%9B%90-%EA%B8%B0%EB%8A%A5-%EC%A0%95%EC%9D%98)
### 관리자 회원
 개요: 공연 조회, 공연장 정보 관리, 공연 정보 관리  
 상세: 🚀[Wiki - 관리자 회원 기능 정의](https://github.com/f-lab-edu/show-ticketing-service/wiki/%EA%B4%80%EB%A6%AC%EC%9E%90-%ED%9A%8C%EC%9B%90-%EA%B8%B0%EB%8A%A5-%EC%A0%95%EC%9D%98)

<br>

## &#128204; 프로젝트 진행 중 이슈
🚀[Wiki - 프로젝트 관련 이슈](https://github.com/f-lab-edu/show-ticketing-service/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B4%80%EB%A0%A8-%EC%9D%B4%EC%8A%88)

