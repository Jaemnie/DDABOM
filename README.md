# 따봄, 자격증 조회 앱

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 소개
**따봄**은 자격증 정보를 API로부터 가져와 SQLite 데이터베이스에 저장하고, 이를 사용자에게 보여주는 Android 애플리케이션입니다. 이 앱은 Retrofit을 사용하여 데이터를 가져오고, RecyclerView를 사용하여 자격증 목록을 표시합니다.

## 기능
- 자격증 목록 조회
- 자격증 정보 로컬 데이터베이스 저장(SQLite)
- 로컬 데이터베이스에서 빠른 데이터 로드

## 스크린샷
![스크린샷](screenshot.png)

## 설치 방법
1. 이 저장소를 클론합니다.
    ```bash
    git clone https://github.com/yourusername/CertificationApp.git
    ```
2. Android Studio에서 프로젝트를 엽니다.
3. `build.gradle` 파일을 통해 필요한 종속성을 모두 다운로드합니다.
4. 앱을 빌드하고 실행합니다.

## 사용된 라이브러리
- [Retrofit](https://square.github.io/retrofit/): HTTP 클라이언트
- [Gson](https://github.com/google/gson): JSON 변환 라이브러리
- [Jsoup](https://jsoup.org/): HTML 파싱 라이브러리
- [SQLite](https://developer.android.com/training/data-storage/sqlite): 로컬 데이터베이스

## 코드 구조
- `MainActivity.java`: 메인 액티비티
- `CertificationFragment.java`: 자격증 목록을 표시하는 프래그먼트
- `CertificationAdapter.java`: RecyclerView 어댑터
- `CertificationItem.java`: 자격증 아이템 모델
- `JanetParser.java`: API 요청 및 데이터 파싱
- `DatabaseHelper.java`: SQLite 데이터베이스 헬퍼 클래스

## 기여 방법
1. 이 저장소를 포크합니다.
2. 새로운 브랜치를 만듭니다.
    ```bash
    git checkout -b feature/YourFeature
    ```
3. 기능을 추가하거나 버그를 수정합니다.
4. 변경 사항을 커밋합니다.
    ```bash
    git commit -m "Add some feature"
    ```
5. 브랜치에 푸시합니다.
    ```bash
    git push origin feature/YourFeature
    ```
6. 풀 리퀘스트를 생성합니다.

## 라이선스
이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참고하세요.

## 작성자
- [JAEMNI] (https://github.com/Jaemnie)
- [LEESBEOM] (https://github.com/LEESBEOM)
- [JSH9298] (https://github.com/jsh9298)
- [yoonsangha] (https://github.com/yoonsangha)

## 문의
질문이나 문의 사항이 있으면, [issues](https://github.com/Jaemnie/CertificationApp/issues) 섹션에 남겨주세요.
