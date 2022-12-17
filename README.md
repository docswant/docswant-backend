# docswant-backend

스마트 회진 서비스 **Docswant**의 백엔드 API 서버 레포지토리입니다.

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
  - 문의 사항 CRUD
- ESL
  - 환자 로그인 시 환자 ESL에 사용자 정보 표기
  - 의사가 회진 일정 시 환자 ESL에 회진 일정 및 회진 순서 표기

<br/>

> ## API 명세

<details>
<summary>응답 데이터 정보</summary>

### 응답 데이터

```
💡 요청 성공 시 error 필드, 실패 시 data 필드는 응답 데이터에 포함되지 않음
```

`status` : 응답 상태
- `success` : 요청 성공
- `fail` : 요청 데이터, 조건 오류
- `error` : 요청을 처리하는 과정에서의 오류

`timestamp` : 응답 시간

`data` : 응답 데이터

`error` : 에러 정보
- `code` : 에러 코드
- `message` : 에러 메세지
- `fields` : 에러 필드

### 에러 코드
*Common*
- `C001` : `405` 허용되지 않는 요청 메소드
- `C002` : `400` 요청 데이터가 검증에 실패함
- `C003` : `400` 요청 데이터 타입이 맞지 않음
- `C004` : `400` 요청한 엔티티를 찾을 수 없음
- `C005` : `500` 서버 내부 오류 (*개발자에게 문의*)
- `C006` : `400` 요청한 미디어 타입은 지원하지 않음

*Authentication*
- `A001` : `401` 인증 실패
- `A002` : `403` 실행 권한 없음
- `A003` : `400` 유효하지 않은 토큰

*Question*
- `Q001` : `400` 이미 응답한 질문

</details>

<details>
<summary>회원</summary>

### 로그인

```http
POST /api/v1/login HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 56
Host: docswant.zooneon.dev

{
  "username" : "username",
  "password" : "password"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json;charset=UTF-8
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 540

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:26",
  "data" : {
    "code" : "DOCTOR001",
    "accountType" : "ACCOUNT_DOCTOR",
    "accessToken" : "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NjYsImV4cCI6MTY3MTI1NTU2Nn0.zYL0DQh6K1cqjn8HCOPp2iUBKlt_z1UOEIt4NpMmSJ0",
    "refreshToken" : "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoicmVmcmVzaCB0b2tlbiIsInN1YiI6InVzZXJuYW1lIiwiaWF0IjoxNjcxMjUxOTY2LCJleHAiOjE2NzM4NDM5NjZ9.gsOBeJPOkLtMz15asKZ95MX1Ex7_rpm4334_elWtODU"
  }
}
```
### 사용자명 중복 확인

```http
GET /api/v1/account/exists/username HTTP/1.1
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 83

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:25",
  "data" : false
}
```

### 토큰 재발급

```http
GET /api/v1/account/code?token=refresh-token HTTP/1.1
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 92

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:26",
  "data" : "ACCESS_TOKEN"
}
```

</details>

<details>
<summary>의사</summary>

### 의사 코드 검증

```http
GET /api/v1/doctor/validate/DOCTOR001 HTTP/1.1
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 82

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:28",
  "data" : true
}
```

### 회원가입

```http
POST /api/v1/doctor HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 127
Host: docswant.zooneon.dev

{
  "code" : "DOCTOR002",
  "username" : "doctor",
  "password" : "password",
  "name" : "zooneon",
  "major" : "orthopedics"
}
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 188

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:28",
  "data" : {
    "code" : "DOCTOR002",
    "username" : "doctor",
    "name" : "zooneon",
    "major" : "orthopedics"
  }
}
```

### 의사정보 수정

```http
PATCH /api/v1/doctor/DOCTOR001 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NjgsImV4cCI6MTY3MTI1NTU2OH0.-hDJXrOGXKCIAE7qamxcBKksdmAR0VUIpvedhi67RYY
Accept: application/json
Content-Length: 143
Host: docswant.zooneon.dev

{
  "code" : "DOCTOR001",
  "username" : "update username",
  "password" : "update password",
  "name" : "zooneon",
  "major" : "orthopedics"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 197

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:28",
  "data" : {
    "code" : "DOCTOR001",
    "username" : "update username",
    "name" : "zooneon",
    "major" : "orthopedics"
  }
}
```

</details>

<details>
<summary>환자</summary>

### 환자 등록

```http
POST /api/v1/patient HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTYsImV4cCI6MTY1NDI2NDc1Nn0.eB61ay8DgFaNvLSIrQfDukFRuPLyHRhyf2B1rG4SV6o
Accept: application/json
Content-Length: 173
Host: docswant.zooneon.dev

{
  "code" : "PATIENT001",
  "name" : "zooneon",
  "birthDate" : "1997-08-26",
  "hospitalizationDate" : "2022-05-05",
  "diseaseName" : "COVID-19",
  "hospitalRoom" : 302
}
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 265

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:16",
  "data" : {
    "code" : "PATIENT001",
    "name" : "zooneon",
    "birthDate" : "1997-08-26",
    "hospitalizationDate" : "2022-05-05",
    "diseaseName" : "COVID-19",
    "hospitalRoom" : 302
  }
}
```

### 환자 정보수정

```http
PATCH /api/v1/patient/PATIENT001 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTYsImV4cCI6MTY1NDI2NDc1Nn0.eB61ay8DgFaNvLSIrQfDukFRuPLyHRhyf2B1rG4SV6o
Accept: application/json
Content-Length: 267
Host: docswant.zooneon.dev

{
  "username" : "zooneon",
  "password" : "password",
  "name" : "zooneon",
  "birthDate" : "1997-08-26",
  "hospitalizationDate" : "2022-05-05",
  "surgeryDate" : "2022-05-08",
  "dischargeDate" : "2022-05-12",
  "diseaseName" : "COVID-19",
  "hospitalRoom" : 302
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 335

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:16",
  "data" : {
    "code" : "PATIENT001",
    "name" : "zooneon",
    "birthDate" : "1997-08-26",
    "hospitalizationDate" : "2022-05-05",
    "surgeryDate" : "2022-05-08",
    "dischargeDate" : "2022-05-12",
    "diseaseName" : "COVID-19",
    "hospitalRoom" : 302
  }
}
```

### 환자 삭제

```http
DELETE /api/v1/patient/PATIENT001 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTYsImV4cCI6MTY1NDI2NDc1Nn0.eB61ay8DgFaNvLSIrQfDukFRuPLyHRhyf2B1rG4SV6o
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 65

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:16"
}
```

### 환자 조회

```http
GET /api/v1/patient/PATIENT001 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTYsImV4cCI6MTY1NDI2NDc1Nn0.eB61ay8DgFaNvLSIrQfDukFRuPLyHRhyf2B1rG4SV6o
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 335

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:16",
  "data" : {
    "code" : "PATIENT001",
    "name" : "zooneon",
    "birthDate" : "1997-08-26",
    "hospitalizationDate" : "2022-05-05",
    "surgeryDate" : "2022-05-08",
    "dischargeDate" : "2022-05-12",
    "diseaseName" : "COVID-19",
    "hospitalRoom" : 300
  }
}
```

### 환자 메인페이지 조회

```http
GET /api/v1/patient/PATIENT001/rounding?date=2022-05-17 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY1NDI2MTE1NiwiZXhwIjoxNjU0MjY0NzU2fQ.vNLaSeRUWg_dp3wDvtRU33ppb8IkR9yktEbSXtnj8SI
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 467

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "code" : "PATIENT001",
    "patientName" : "zooneon",
    "birthDate" : "1997-08-26",
    "hospitalizationDate" : "2022-05-05",
    "surgeryDate" : "2022-05-08",
    "dischargeDate" : "2022-05-12",
    "diseaseName" : "COVID-19",
    "hospitalRoom" : 300,
    "doctorName" : "zooneon",
    "doctorMajor" : "orthopedics",
    "roundingTime" : "12:00",
    "roundsWaitingOrder" : 0
  }
}
```

### 환자 메인페이지 조회

```http
GET /api/v1/patient?page=1&size=3 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTYsImV4cCI6MTY1NDI2NDc1Nn0.eB61ay8DgFaNvLSIrQfDukFRuPLyHRhyf2B1rG4SV6o
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 1085

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:16",
  "data" : {
    "page" : 1,
    "hasNext" : false,
    "content" : [ {
      "code" : "PATIENT001",
      "name" : "zooneon",
      "birthDate" : "1997-08-26",
      "hospitalizationDate" : "2022-05-05",
      "surgeryDate" : "2022-05-08",
      "dischargeDate" : "2022-05-12",
      "diseaseName" : "COVID-19",
      "hospitalRoom" : 300,
      "hasUnreadRequirement" : true
    }, {
      "code" : "PATIENT002",
      "name" : "zooneon",
      "birthDate" : "1997-08-26",
      "hospitalizationDate" : "2022-05-05",
      "surgeryDate" : "2022-05-08",
      "dischargeDate" : "2022-05-12",
      "diseaseName" : "COVID-19",
      "hospitalRoom" : 301,
      "hasUnreadRequirement" : false
    }, {
      "code" : "PATIENT003",
      "name" : "zooneon",
      "birthDate" : "1997-08-26",
      "hospitalizationDate" : "2022-05-05",
      "surgeryDate" : "2022-05-08",
      "dischargeDate" : "2022-05-12",
      "diseaseName" : "COVID-19",
      "hospitalRoom" : 302,
      "hasUnreadRequirement" : false
    } ]
  }
}
```

</details>

<details>
<summary>회진 일정</summary>

### 회진 생성

```http
POST /api/v1/doctor/DOCTOR001/rounding HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Accept: application/json
Content-Length: 88
Host: docswant.zooneon.dev

{
  "code" : "PATIENT001",
  "roundingDate" : "2022-05-17",
  "roundingTime" : "12:00"
}
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 223

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 4,
    "roundingDate" : "2022-05-17",
    "patientName" : "zooneon",
    "roundingTime" : "12:00",
    "roundingStatus" : "TODO"
  }
}
```

### 회진 수정

```http
PATCH /api/v1/doctor/DOCTOR001/rounding/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Accept: application/json
Content-Length: 63
Host: docswant.zooneon.dev

{
  "roundingDate" : "2022-05-18",
  "roundingTime" : "12:30"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 223

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 1,
    "roundingDate" : "2022-05-18",
    "patientName" : "zooneon",
    "roundingTime" : "12:30",
    "roundingStatus" : "TODO"
  }
}
```

### 회진 삭제

```http
DELETE /api/v1/doctor/DOCTOR001/rounding/1 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 65

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31"
}
```

### 회진 전체 삭제

```http
DELETE /api/v1/doctor/DOCTOR001/rounding?ids=1,2,3 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 65

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31"
}
```

### 회진 상태 변경

```http
PATCH /api/v1/doctor/DOCTOR001/rounding/1/status HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 223

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 1,
    "roundingDate" : "2022-05-17",
    "patientName" : "zooneon",
    "roundingTime" : "12:00",
    "roundingStatus" : "DONE"
  }
}
```

### 회진 조회

```http
GET /api/v1/doctor/DOCTOR001/rounding/1 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 223

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 1,
    "roundingDate" : "2022-05-17",
    "patientName" : "zooneon",
    "roundingTime" : "12:00",
    "roundingStatus" : "DONE"
  }
}
```

### 날짜별 회진 조회

```http
GET /api/v1/doctor/DOCTOR001/rounding?date=2022-05-17 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev

```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 719

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : [ {
    "hospitalRoom" : 300,
    "roundings" : [ {
      "id" : 1,
      "roundingDate" : "2022-05-17",
      "patientName" : "zooneon",
      "roundingTime" : "12:00",
      "roundingStatus" : "TODO"
    } ]
  }, {
    "hospitalRoom" : 301,
    "roundings" : [ {
      "id" : 2,
      "roundingDate" : "2022-05-17",
      "patientName" : "zooneon",
      "roundingTime" : "12:05",
      "roundingStatus" : "TODO"
    } ]
  }, {
    "hospitalRoom" : 302,
    "roundings" : [ {
      "id" : 3,
      "roundingDate" : "2022-05-17",
      "patientName" : "zooneon",
      "roundingTime" : "12:10",
      "roundingStatus" : "TODO"
    } ]
  } ]
}
```

</details>

<details>
<summary>문의사항</summary>

### 문의사항 등록

```http
POST /api/v1/patient/PATIENT001/requirement HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY3MTI1MTk3MSwiZXhwIjoxNjcxMjU1NTcxfQ.Rn8aPOnmOPcS-KRk8flv9PscIj-nKLikGd4s09Ke_p8
Accept: application/json
Content-Length: 48
Host: docswant.zooneon.dev

{
  "title" : "title",
  "content" : "content"
}
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 171

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 4,
    "title" : "title",
    "content" : "content",
    "status" : "UNREAD"
  }
}
```

### 문의사항 수정

```http
PATCH /api/v1/patient/PATIENT001/requirement/1/content HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY3MTI1MTk3MCwiZXhwIjoxNjcxMjU1NTcwfQ.zWa4cYst2qK1vj7nFyb0N0eGDlc5hzjnmRw73SruEsY
Accept: application/json
Content-Length: 34
Host: docswant.zooneon.dev

{
  "content" : "update content"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 179

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 1,
    "title" : "title1",
    "content" : "update content",
    "status" : "UNREAD"
  }
}
```

### 문의사항 삭제

```http
DELETE /api/v1/patient/PATIENT001/requirement/1 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY3MTI1MTk3MSwiZXhwIjoxNjcxMjU1NTcxfQ.Rn8aPOnmOPcS-KRk8flv9PscIj-nKLikGd4s09Ke_p8
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 65

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31"
}
```

### 문의사항 조회

```http
GET /api/v1/patient/PATIENT001/requirement/1 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NzEyNTE5NzEsImV4cCI6MTY3MTI1NTU3MX0.5tNs2Cd--xDkJW_h6xe65ZUtICPW3YKfrDZqWFtDVDA
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 171

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "id" : 1,
    "title" : "title1",
    "content" : "content1",
    "status" : "READ"
  }
}
```

### 문의사항 리스트 조회

```http
GET /api/v1/patient/PATIENT001/requirement?page=1&size=3 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY3MTI1MTk3MSwiZXhwIjoxNjcxMjU1NTcxfQ.Rn8aPOnmOPcS-KRk8flv9PscIj-nKLikGd4s09Ke_p8
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 462

{
  "status" : "success",
  "timestamp" : "2022-12-17 04:39:31",
  "data" : {
    "page" : 1,
    "hasNext" : false,
    "content" : [ {
      "id" : 1,
      "title" : "title1",
      "content" : "content1",
      "status" : "UNREAD"
    }, {
      "id" : 2,
      "title" : "title2",
      "content" : "content2",
      "status" : "UNREAD"
    }, {
      "id" : 3,
      "title" : "title3",
      "content" : "content3",
      "status" : "UNREAD"
    } ]
  }
}
```

</details>

<details>
<summary>질문</summary>

### 질문 생성

```http
POST /api/v1/patient/PATIENT001/question HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTcsImV4cCI6MTY1NDI2NDc1N30.0PJ-OLx1iig4NWQIaXyJUGDcmnEdKixUq-ZbFAqWRDY
Accept: application/json
Content-Length: 27
Host: docswant.zooneon.dev

{
  "content" : "content"
}
```

```http
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 193

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "id" : 4,
    "content" : "content",
    "answerStatus" : "TODO",
    "createdAt" : "2022-06-03 21:59:17"
  }
}
```

### 질문 수정

```http
PATCH /api/v1/patient/PATIENT001/question/1/content HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTcsImV4cCI6MTY1NDI2NDc1N30.0PJ-OLx1iig4NWQIaXyJUGDcmnEdKixUq-ZbFAqWRDY
Accept: application/json
Content-Length: 34
Host: docswant.zooneon.dev

{
  "content" : "update content"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 200

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "id" : 1,
    "content" : "update content",
    "answerStatus" : "TODO",
    "createdAt" : "2022-05-15 12:00:00"
  }
}
```

### 질문 응답

```http
PATCH /api/v1/patient/PATIENT001/question/1/answer HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoiUEFUSUVOVDAwMSIsImlhdCI6MTY1NDI2MTE1NywiZXhwIjoxNjU0MjY0NzU3fQ.jsX39DG2QzQU_P8REwOsaJ6q2o_Aqg6rx4BldyvhAvw
Accept: application/json
Content-Length: 25
Host: docswant.zooneon.dev

{
  "answer" : "answer"
}
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 218

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "id" : 1,
    "content" : "content",
    "answer" : "answer",
    "answerStatus" : "DONE",
    "createdAt" : "2022-05-15 12:00:00"
  }
}
```

### 질문 삭제

```http
DELETE /api/v1/patient/PATIENT001/question/1 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTcsImV4cCI6MTY1NDI2NDc1N30.0PJ-OLx1iig4NWQIaXyJUGDcmnEdKixUq-ZbFAqWRDY
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 65

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17"
}
```

### 질문 조회

```http
GET /api/v1/patient/PATIENT001/question/2 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTcsImV4cCI6MTY1NDI2NDc1N30.0PJ-OLx1iig4NWQIaXyJUGDcmnEdKixUq-ZbFAqWRDY
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 220

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "id" : 2,
    "content" : "content2",
    "answer" : "answer2",
    "answerStatus" : "DONE",
    "createdAt" : "2022-05-15 12:00:00"
  }
}
```

### 질문 리스트 조회

```http
GET /api/v1/patient/PATIENT001/question?page=1&size=3 HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoiYWNjZXNzIHRva2VuIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE2NTQyNjExNTcsImV4cCI6MTY1NDI2NDc1N30.0PJ-OLx1iig4NWQIaXyJUGDcmnEdKixUq-ZbFAqWRDY
Host: docswant.zooneon.dev
```

```http
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 580

{
  "status" : "success",
  "timestamp" : "2022-06-03 21:59:17",
  "data" : {
    "page" : 1,
    "hasNext" : false,
    "content" : [ {
      "id" : 1,
      "content" : "content",
      "answerStatus" : "TODO",
      "createdAt" : "2022-05-15 12:00:00"
    }, {
      "id" : 2,
      "content" : "content2",
      "answer" : "answer2",
      "answerStatus" : "DONE",
      "createdAt" : "2022-05-15 12:00:00"
    }, {
      "id" : 3,
      "content" : "content3",
      "answer" : "answer3",
      "answerStatus" : "DONE",
      "createdAt" : "2022-05-15 12:00:00"
    } ]
  }
}
```

</details>

> ## 서버 아키텍처

<img src="https://user-images.githubusercontent.com/59433441/208224269-ccc8a587-dacc-4a25-ac9d-61d43a3ca6d9.png" width="800" />

> ## ERD

<img src="https://user-images.githubusercontent.com/59433441/208224637-a96ec51d-b877-4f13-bc32-3b1e5ec78378.png" width="800" />
