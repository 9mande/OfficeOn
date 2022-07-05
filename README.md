# Intro
## Topic
> 몰입캠프 1주차 공통과제 : 탭 구조를 활용한 안드로이드 앱 제작
- tap1 : 휴대폰 내부 저장소나 JSON 형식을 이용한 연락처 구축
- tap2 : 이미지 20개 정도가 들어간 갤러리 구축
- tap3 : 자유주제

## Concept
- 각 tap에 주어진 별도의 과제를 한 가지로 아우를 수 있는 주제를 생각해보았습니다.
- office를 주제로 업무 연락처, 명함집, to-do list, 기온별 오피스룩 추천 기능을 구상했습니다.
- 이후 home을 만들어 각각의 프레그먼트에 이동할 수 있는 화면을 제작했습니다.

# 어플리케이션 소개 

![git_home2](https://user-images.githubusercontent.com/92452192/177323213-09a065a9-8be7-4cf8-9b87-097c7303a0dc.gif))
![git_home1](https://user-images.githubusercontent.com/92452192/177323223-6a7c0e98-9169-4e9d-9851-3ab326d9af0c.gif)
![gif_home3](https://user-images.githubusercontent.com/92452192/177323338-dbbdd481-a404-421a-875c-d4c687640f07.gif

## Home 화면
- tap을 모두 제작한 후 이들을 하나로 묶기 위해서 제작하였습니다.
- 각 tap으로 넘어가는 기능 및 개인 프로필, 타이머, 날씨를 확인할 수 있습니다. 
- 시간 관계상 타이머를 기능은 추가하지 못하였고, xml 디자인만 들어가 있습니다.
- 귀여운 고양이에 이스터 에그를 숨겨두었습니다:)

![gif_phone1](https://user-images.githubusercontent.com/92452192/177324534-91521ad9-bce9-4ff6-a901-2847acb2d581.gif)
![gif_phone3](https://user-images.githubusercontent.com/92452192/177324542-89a63f74-84ad-4a41-988a-cd9fa69caefb.gif)
![gif_phone2](https://user-images.githubusercontent.com/92452192/177324548-14b3223a-9229-4504-b1d3-d5dced7edd8d.gif)

## Tap1 연락처
- JSON 파일에 있는 정보를 불러와 Recycler Veiw를 사용해 화면에 표시 합니다.
- 전화번호부 플로팅 베너를 클릭하면 핸드폰 내부 저장소에 접근하여 이름 및 전화번호를 불러올 수 있습니다.
- 사람을 추가하는 플로팅 베너를 클릭하면 연락처를 직접 추가할 수 있고, 이름, 전화번호, 회사는 필수 입력 요소입니다.
- 전화번호를 클릭하면 상세페이지로 이동하며 이름, 회사, 직군, 전화번호, 이메일을 표시합니다. 전화를 걸거나, 문자를 보내고 공유를 할 수 있는 기능을 추가했습니다.
- 전화 모양을 클릭하면 바로 전화를 걸 수 있습니다.
- 서치바를 사용하여 연락처를 쉽게 찾을 수 있습니다. (이름, 전화번호로 검색 가능)


## Tap2 명함집
- JSON 파일에 있는 정보를 불러와 Recycler Veiw를 사용해 화면에 표시 합니다.
- 명함 크기의 Card Veiw를 사용했습니다.
- 앨범 플로팅 베너를 통해 핸드폰 내부 저장소에 접근하여 사진을 추가할 수 있습니다.
- 카메라 버튼을 이용해 직접 찍은 사진을 추가할 수 있습니다.
- 사진을 오래 클릭하면, 사진을 삭제하겠냐는 메세지 창이 뜨고 삭제와 취소를 선택합니다.
- 사진을 짧게 클릭하면, 더 큰 화면으로 볼 수 있으며 옆으로 밀면서 다른 사진을 함께 확인할 수 있습니다. 

![gif_todo](https://user-images.githubusercontent.com/92452192/177324896-4ff69017-6e37-4e77-9a68-2d3ba294c4f9.gif)

## Tap3 To-do list
- 오늘의 날짜가 자동으로 생성되고, 할 일을 입력할 수 있습니다. 
- Check Box와 Recycler Veiw를 사용하여 하루에 할 일을 표시했습니다. 
- 이미 한 일은 원을 체크하여 표시할 수 있습니다. 

![gif_style](https://user-images.githubusercontent.com/92452192/177324903-92064926-0133-48b3-9adb-e00a88780b91.gif)

## Tap4 날씨별 옷차림 추천
- 오늘의 날짜가 자동으로 생성되고, 2시 5시 8시 11시 14시 17시 20시 23시에 기상청 API를 사용하여 정보를 받아옵니다.
- 맑음, 흐림, 구름, 비에 따라서 사진이 변경됩니다.
- 위치 정보를 바탕으로 자동으로 날씨를 검색해 표시하는 기능은 아직 구현하지 못했습니다.
- 날씨 정보에 따른 옷차림을 랜덤하게 추천해줍니다.
- stylist 버튼을 누르면 다른 스타일을 볼 수 있습니다. 

# Developer
[김효정](https://github.com/rb37lu71)
[구민재](https://github.com/9mande)
