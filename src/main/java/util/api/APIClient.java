package util.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.APIClientParam;
import util.logger.MyLogger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 📝 APIClient 클래스 (외부 API를 호출하는 역할)
 *
 * ✅ 역할:
 * - 네이버 API와 같은 외부 서비스에 HTTP 요청을 보냄
 * - 요청을 보낼 URL, HTTP 메서드(GET, POST 등), 요청 본문(body), 헤더(headers) 등을 설정하여 전송
 * - 응답을 받아서 문자열로 반환
 *
 * ✅ 인풋(Input):
 * - `APIClientParam param`: API 요청을 보낼 때 필요한 정보를 담은 객체
 *   - `String url`: 요청을 보낼 API 주소
 *   - `String method`: 요청 방식 (예: GET, POST)
 *   - `Map<String, String> body`: 요청 본문 (데이터)
 *   - `String... headers`: 추가적인 요청 헤더들
 *
 * ✅ 아웃풋(Output):
 * - API 요청을 통해 얻은 응답 데이터를 `String` 형태로 반환
 */
public class APIClient {
    private final MyLogger logger = new MyLogger(this.getClass()); // 로깅을 위한 MyLogger 객체
    private final HttpClient httpClient = HttpClient.newHttpClient(); // HTTP 요청을 보내는 Java 내장 클라이언트

    /**
     * 🔹 생성자(Constructor)
     * - API 클라이언트를 초기화하고, 로그를 남김
     */
    public APIClient() {
        logger.info("Initializing API client");
    }

    /**
     * 🔹 callAPI() 메서드
     * - 지정된 API에 요청을 보낸 후 응답을 문자열로 반환
     * - 사용 예시:
     *   ```java
     *   APIClientParam param = new APIClientParam("https://example.com", "GET", new HashMap<>(), "Authorization", "Bearer token");
     *   String response = apiClient.callAPI(param);
     *   ```
     *
     * @param param API 요청을 위한 설정 값 (URL, 메서드, 요청 본문, 헤더 포함)
     * @return API 응답 문자열
     * @throws IOException 네트워크 오류 발생 시
     * @throws InterruptedException 요청이 인터럽트될 경우
     */
    public String callAPI(APIClientParam param) throws IOException, InterruptedException {
        logger.info("Calling API client");
        ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환을 위한 ObjectMapper 객체
        String body = objectMapper.writeValueAsString(param.body()); // 요청 본문을 JSON 문자열로 변환

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(param.url()))
                .method(param.method(), HttpRequest.BodyPublishers.ofString(body))
                .headers(param.headers())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response Status: " + response.statusCode()); // 응답 상태 코드 로깅
        return response.body(); // API 응답을 문자열로 반환
    }
}
