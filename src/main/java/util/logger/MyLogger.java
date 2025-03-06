package util.logger;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 📝 MyLogger 클래스 (로그를 관리하는 클래스)
 *
 * ✅ 역할:
 * - 프로그램 실행 중 발생하는 이벤트(정보, 오류 등)를 기록하는 역할
 * - .env 파일에서 `MODE` 값을 읽어 로그 레벨을 설정함
 * - `DEV` 모드: INFO 레벨의 로그 출력 (모든 로그 표시)
 * - `PROD` 모드: SEVERE 레벨의 로그 출력 (중요한 오류만 표시)
 *
 * ✅ 인풋(Input):
 * - `Class<?> clazz`: 로깅을 사용할 클래스 (어떤 클래스에서 로그를 남기는지 기록하기 위해 필요)
 * - `String msg`: 로그 메시지 (출력할 텍스트)
 *
 * ✅ 아웃풋(Output):
 * - 콘솔 또는 로그 파일에 메시지를 출력함 (실제 메서드는 반환값이 없음)
 */
public class MyLogger {
    private final Logger logger;

    /**
     * 🔹 생성자(Constructor)
     * - 특정 클래스에서 사용할 로거(Logger)를 생성함
     * - .env 파일에서 `MODE` 값을 불러와 로그 레벨을 설정함
     */
    public MyLogger(Class<?> clazz) {
        Dotenv dotenv = Dotenv.load(); // .env 파일에서 환경 변수 불러오기
        this.logger = Logger.getLogger(clazz.getName()); // 클래스 이름 기반으로 로거 생성

        String mode = dotenv.get("MODE"); // 실행 모드 가져오기
        if (mode.isBlank()) {
            throw new RuntimeException("mode is missing"); // mode 값이 없으면 예외 발생
        }

        switch (mode) {
            case "DEV":
                this.logger.setLevel(Level.INFO); // 개발 모드에서는 INFO 레벨 로그 표시
                break;
            case "PROD":
                this.logger.setLevel(Level.SEVERE); // 운영 모드에서는 SEVERE 레벨 로그만 표시
                break;
        }
    }

    /**
     * 🔹 info() 메서드
     * - 일반적인 정보 로그를 남김
     * - 사용 예시: `myLogger.info("서버 시작됨");`
     */
    public void info(String msg) {
        LogRecord record = new LogRecord(Level.INFO, msg);
        record.setSourceClassName(logger.getName()); // 원래 클래스 이름 설정
        logger.log(record);
    }

    /**
     * 🔹 severe() 메서드
     * - 심각한 오류 로그를 남김 (예: 시스템 충돌, 치명적인 예외 발생 등)
     * - 사용 예시: `myLogger.severe("데이터베이스 연결 실패!");`
     */
    public void severe(String msg) {
        LogRecord record = new LogRecord(Level.SEVERE, msg);
        record.setSourceClassName(logger.getName());
        logger.log(record);
    }
}