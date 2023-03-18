# ⭐ ComeWithMeShop
<image src="https://user-images.githubusercontent.com/105377279/225466085-7df17ae2-8476-4448-a60b-fd5accfc7ea9.png" > 

**ComeWithMe** 는 국내 온라인 쇼핑몰들을 참고하여 만들었습니다. 온라인 쇼핑몰의 주요 기능인 카테고리 분류, 장바구니, 결제 및 결제검증을 구현하였습니다. 또한 일반 구매자가 쇼핑할 수 있는 쇼핑몰 영역과 통계 및 상품 등록, 게시글 관리 등을 할 수 있는 관리자 페이지 영역을 나눠서 구축하였습니다.
 
<br>

## 📌 기술 스택
- Java 11
- Spring Boot 2.7.8
- MySQL 8.0.31
- Mybatis
- Thymeleaf

<br>

## ✔ 기능 소개

### 회원 가입
- 간단한 이메일, 패스워드를 통해 가입을 할 수 있습니다.
- 회원 가입을 성공하면 사용자의 정보(패스워드)는 암호화(SHA-256)를 통해 저장됩니다.
### 로그인
- 입력받은 패스워드를 암호화된 값과 비교하여 통과여부를 결정합니다.
- 로그인 시 입력한 아이디(이메일)을 통해 회원 상태를 파악하여 휴면, 탈퇴 복구를 묻습니다.
### 홈페이지(메인)
- 상품은 프론트의 필터 기능을 통해 간단하게 카테고리별로 정렬이 가능합니다.
- modal을 통해 상품에 대한 간략한 정보를 볼 수 있고, 장바구니 추가를 할 수 있습니다.
### 상품 목록 및 상품 페이지
- MySQL의 Limit, Offset 기능을 활용하여 페이징 처리가 되어있습니다.
- 해당 상품과 같은 카테고리의 상품을 연관 상품으로 갖고 옵니다.
- 장바구니 추가를 통해 상품 선택이 가능합니다.
- 상품 페이지마다 문의, 리뷰를 하단에서 작성하고 볼 수 있도록 페이징 처리가 되어있습니다.
### 장바구니
- 회원만 이용이 가능합니다.
- 쿠폰 등록, 배송 종류를 선택을 할 수 있습니다.
### 결제
- [아임포트(PortOne)](https://portone.io/korea/ko?utm_source=google&utm_medium=google_sa&utm_campaign=pf_conversion_2302_kr&utm_content=homepage&gclid=Cj0KCQjw2cWgBhDYARIsALggUhrTE5mMpXxNeGt3uHvFrnmdOo4cOCM8sXIrV4pFpwXnVXhzUIMvxyEaAr-aEALw_wcB)를 통해 개발하였습니다.
- 결제 검증을 통해 스크립트 조작으로 인한 자산 피해가 없도록 구현했습니다.
- 회원 등급에 따라 자동으로 할인 금액이 적용됩니다.
### 마이 페이지
- 주문 목록, 결제 목록(환불 요청) 등을 볼 수 있습니다.
- 리뷰글, 문의글에 대한 수정 및 등록을 할 수 있습니다.
### 관리자 페이지
- 매출, 일간 가입자, 일간 방문자 등에 대한 통계를 확인할 수 있습니다.
- 전체 회원, 주문 및 결제 리스트를 볼 수 있으며 고객의 요청을 처리할 수 있습니다.
- 최고 관리자의 경우 관리자 추가 및 삭제가 가능합니다.

<br>

## 🗓 프로젝트 기간
- 2023-02-08 ~ 2023-03-18

<br>  
  
## 📝 ERD
<image src="https://user-images.githubusercontent.com/105377279/225488767-afb59739-3b63-41e5-b49f-20ffbfcb8774.png" > 

## 🎞 시연 영상

  **고객페이지**


https://user-images.githubusercontent.com/105377279/225519263-529aa7f7-42a5-46e5-9aea-aca901fd91e5.mp4


  
  <br>
  <br>

  **회원 마이페이지**
  

https://user-images.githubusercontent.com/105377279/225519912-08b743de-86a2-4d2a-9a7d-6589ee2ffec1.mp4


  <br>
  <br>
  
  **회원 결제 / 환불 시스템**
  

https://user-images.githubusercontent.com/105377279/225520037-d07d8c70-5a9e-42d1-9104-984e1875474a.mp4



  <br>
  <br>

  **관리자페이지**


https://user-images.githubusercontent.com/105377279/225520479-745c9f73-9dd5-44fc-81a2-45518f6bf465.mp4


  
  
  <br>
  <br>

 
**페이지별 상세 설명, 코드 설명을 보시고 싶으시면** [<더보기>](https://fluttering-relish-3bc.notion.site/Come-With-Me-251d0633bc584df1aa4214732a4f00c5)**를 눌러주세요.**

 <br>
 
