# docswant-backend

스마트 회진 서비스 **Docswant**의 백엔드 API 레포지토리입니다.

<br/>

> ## 개요
- 병원에서 발생하고 있는 여러 회진 관련 문제들을 해결하기 위한 서비스
- 세종대학교 캡스톤디자인 프로젝트
- 산학협력 프로젝트로 ATEC IoT와 연계하여 프로젝트 수행   

👉 <a href="https://youtu.be/kAs8bh00rnE" target="_blank">발표 영상</a>
<br/>
<br/>

> ## 🏆 Awards
- 세종대학교 13회 창의설계 경진대회 금상 수상
- 제1회 U&I 페스티벌 캡스톤디자인 경진대회 우수상 수상

<br/>

> ## 개발 인원 및 기간

- 개발 기간: 2022.03 ~ 2022.06
- 프론트엔드 2명 / 백엔드 2명

<br/>

> ## 💻 Tech Stack

- ### Skills

  Spring Boot, Spring Data JPA, Spring Security, Spring RestDocs

- ### CI/CD
  
  Github Actions, AWS CodeDeploy

- ### ETC

  JWT, ESL(전자가격표시기)

<br/>

> ## 💨 구현 기능

- 로그인/회원가입
- 마이페이지
- 의사
  - 환자 정보 CRUD
  - 회진 전 질문 CRUD
  - 회진 일정 CRUD
- 환자
  - 제공 받은 질문 답변/수정/삭제
  - 건의사항 CRUD
- ESL
  - 환자 로그인 시 환자 ESL에 사용자 정보 표기
  - 의사가 회진 일정 시 환자 ESL에 회진 일정 및 회진 순서 표기

<br/>

> ## 서버 아키텍처

<img src="https://user-images.githubusercontent.com/59433441/208224269-ccc8a587-dacc-4a25-ac9d-61d43a3ca6d9.png" width="800" />

> ## ERD

<img src="https://user-images.githubusercontent.com/59433441/208224637-a96ec51d-b877-4f13-bc32-3b1e5ec78378.png" width="800" />
