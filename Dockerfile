# ✅ 1️⃣ Maven 빌드 스테이지 (의존성 캐싱 추가)
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline  # ✅ 의존성 미리 다운로드 (빌드 속도 향상)

COPY src ./src
RUN mvn clean package -DskipTests  # ✅ Maven 빌드 실행

# ✅ 2️⃣ Tomcat 배포 스테이지
FROM tomcat:10.1.17-jdk17
WORKDIR /usr/local/tomcat/webapps/

COPY --from=builder /app/target/*.war ./naver-search.war  # ✅ 애플리케이션 배포

EXPOSE 8080
CMD ["sh", "-c", "catalina.sh run"]
