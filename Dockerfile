# ✅ 1. Maven 빌드 스테이지
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

# ✅ 전체 프로젝트 복사 (기존 pom.xml, src만 복사하는 방식에서 변경)
COPY . .

# ✅ 의존성 미리 다운로드
RUN mvn dependency:go-offline

# ✅ Maven 빌드 실행
RUN mvn clean package -DskipTests

# ✅ 2. Tomcat 배포 스테이지
FROM tomcat:10.1.17-jdk17
WORKDIR /usr/local/tomcat/webapps/

# ✅ 빌드된 WAR 파일 복사
COPY --from=builder /app/target/*.war ./ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
