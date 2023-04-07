# MusiCat

![프로젝트 로고](로고_이미지_링크)

프로젝트 주소: [프로젝트_주소](https://musicat.kr)

[UCC 영상 바로가기](UCC_영상_링크)

프로젝트 기간: 2023.02.27 ~ 2023.04.07

## 프로젝트 소개 (배경, 개요)
프로젝트 배경 및 개요에 대한 설명을 작성해주세요.

## 프로젝트 기능 소개
- 기능1
- 기능2
- 기능3
- 기능4

## 기술 소개
- 기술1
- 기술2
- 기술3
- 기술4

## 프로젝트 파일 구조
### 백엔드
```bash
.
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── musicat
    │   │           ├── Oauth
    │   │           ├── auth
    │   │           ├── config
    │   │           ├── controller
    │   │           │   ├── item
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   └── user
    │   │           ├── data
    │   │           │   ├── dto
    │   │           │   │   ├── alert
    │   │           │   │   │   ├── request
    │   │           │   │   │   └── response
    │   │           │   │   ├── chat
    │   │           │   │   ├── item
    │   │           │   │   ├── music
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   ├── socket
    │   │           │   │   ├── spotify
    │   │           │   │   ├── story
    │   │           │   │   └── user
    │   │           │   ├── entity
    │   │           │   │   ├── item
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   └── user
    │   │           │   └── repository
    │   │           │       ├── item
    │   │           │       ├── notice
    │   │           │       ├── radio
    │   │           │       └── user
    │   │           ├── handler
    │   │           ├── interceptor
    │   │           ├── jwt
    │   │           ├── service
    │   │           │   ├── chat
    │   │           │   ├── item
    │   │           │   ├── kafka
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   ├── socket
    │   │           │   └── user
    │   │           └── util
    │   │               └── builder
    │   └── resources
    │       └── static
    └── test
        └── java
            └── com
                └── musicat
```

### 프론트엔드
```bash
.
├── public
│   ├── graphic
│   │   ├── animation
│   │   ├── background
│   │   │   ├── 1
│   │   │   └── 2
│   │   └── cat
│   └── img
│       ├── background
│       ├── badge
│       ├── cd
│       ├── pagebackground
│       ├── tape
│       └── theme
└── src
    ├── asset
    │   ├── font
    │   └── img
    ├── atoms
    ├── components
    │   ├── broadcast
    │   │   ├── graphicCanvas
    │   │   │   └── background
    │   │   └── radioPlayer
    │   ├── common
    │   │   ├── board
    │   │   ├── button
    │   │   ├── input
    │   │   ├── modal
    │   │   ├── pagenation
    │   │   ├── selectBox
    │   │   └── songSearch
    │   ├── header
    │   │   ├── onairSign
    │   │   └── popover
    │   └── sideNav
    │       ├── mypageNav
    │       └── tapeNav
    │           ├── CDplayer
    │           ├── Tape
    │           ├── tapeButtons
    │           └── volumeBar
    ├── connect
    │   ├── axios
    │   │   └── queryHooks
    │   └── socket
    ├── customHooks
    ├── pages
    │   ├── common
    │   │   ├── loadingSpinner
    │   │   ├── loginSuccess
    │   │   └── page404
    │   ├── home
    │   │   ├── about
    │   │   ├── chat
    │   │   ├── songRequest
    │   │   │   └── songList
    │   │   │       └── songDetailModal
    │   │   └── story
    │   │       ├── contentBox
    │   │       └── contentPlus
    │   └── mypage
    │       ├── inventory
    │       │   └── inventoryModal
    │       ├── myinfo
    │       │   └── myinfoModal
    │       ├── notice
    │       ├── noticeDetail
    │       ├── noticeManage
    │       ├── noticeManageModify
    │       └── userManage
    │           └── SelectedUsers
    └── types
```
### 라디오 서버
```bash
.
├── api_chatgpt.py
├── api_naver_tts.py
├── database.py
├── kafka_handler.py
├── logic_chat.py
├── logic_empty.py
├── logic_music.py
├── logic_opening.py
├── logic_story.py
├── main.py
├── my_logger.py
├── my_util.py
├── radio_progress.py
├── shared_env.py
├── shared_state.py
└── tts
    └── mymusic
```

## 역할 분배
| 이름 | 사진 | 역할 | 정보 |
| ---- | ---- | ---- | ---- |
| 이름1 | ![이름1_사진](이름1_사진_링크) | 역할1 | 정보1 |
| 이름2 | ![이름2_사진](이름2_사진_링크) | 역할2 | 정보2 |
| 이름3 | ![이름3_사진](이름3_사진_링크) | 역할3 | 정보3 |
| 이름4 | ![이름4_사진](이름4_사진_링크) | 역할4 | 정보4 |
| 이름5 | ![이름5_사진](이름5_사진_링크) | 역할5 | 정보5 |
| 이름6 | ![이름6_사진](이름6_사진_링크) | 역할6 | 정보6 |

## 프로젝트 산출물 링크
- [프로젝트 노션](https://www.notion.so/ramen-buang/SSAFY-2-MusiCat-6ce1496529df4689bdae266db3d50466)
- [기능 명세서](https://www.notion.so/ramen-buang/e5cb5942f92144f0b3929a52d993e007)
- [목업(figma)](https://www.figma.com/file/PgLlj25F4Vg6RtcUwrPFdS/SSAFY_A702_PJT?node-id=0-1)
- [API 명세](https://www.notion.so/ramen-buang/API-89cbd59751cd49a78579e854a3fd91fa)

## 프로젝트 결과물 링크
- [결과물1](결과물1_링크)
- [결과물2](결과물2_링크)

## 서비스 동작 이미지와 설명
![서비스_동작_이미지](서비스_동작_이미지_GIF_링크)

- 설명1
- 설명2
- 설명

