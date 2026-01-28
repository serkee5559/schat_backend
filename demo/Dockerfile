# 기존 openjdk 대신 아마존에서 관리하는 안정적인 이미지를 사용합니다.
FROM amazoncorretto:17-alpine-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 전체 파일 복사
COPY . .

# gradlew 실행 권한 부여 및 빌드
RUN chmod +x gradlew
RUN ./gradlew build -x test

# 실행 파일 위치 확인 및 실행
# (만약 빌드 후 jar 파일명이 다르면 이 부분을 수정해야 합니다)
ENTRYPOINT ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]