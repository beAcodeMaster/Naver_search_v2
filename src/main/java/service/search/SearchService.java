package service.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import model.dto.APIClientParam;
import model.dto.NaverAPIResult;
import model.dto.NaverAPIResultItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.api.APIClient;
import util.logger.MyLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 📝 SearchService 클래스 (네이버 검색 서비스)
 *
 * ✅ 역할:
 * - 네이버 검색 API에 요청을 보내고 결과를 받아옴
 * - API 요청을 위한 인증 정보(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET)를 사용함
 * - 검색 결과를 `List<NaverAPIResultItem>` 형태로 반환함
 *
 * ✅ 인풋(Input):
 * - `String keyword`: 검색할 키워드
 *
 * ✅ 아웃풋(Output):
 * - `List<NaverAPIResultItem>`: 네이버 검색 결과 리스트
 */
public class SearchService {
    private static final Logger log = LogManager.getLogger(SearchService.class);
    private final String clientID;
    private final String clientSecret;
    private final MyLogger logger;
    private final APIClient apiClient;

    /**
     * 🔹 생성자(Constructor)
     * - .env 파일에서 네이버 API 인증 정보를 가져와 설정
     * - API 요청을 담당할 APIClient 객체를 초기화
     * - 로깅 기능(MyLogger)을 사용하여 API 상태 출력
     */
    public SearchService() {
        Dotenv dotenv = Dotenv.load();
        this.clientID = dotenv.get("NAVER_CLIENT_ID");
        this.clientSecret = dotenv.get("NAVER_CLIENT_SECRET");
        if (clientID == null || clientSecret == null) {
            throw new RuntimeException("SearchService: clientID or clientSecret are missing");
        }
        this.logger = new MyLogger(SearchService.class);
        this.apiClient = new APIClient();
        logger.info("SearchService initialized");
    }

    /**
     * 🔹 searchByKeyword() 메서드
     * - 입력된 키워드를 사용하여 네이버 검색 API 요청을 보냄
     * - APIClient를 사용하여 HTTP 요청을 수행하고 응답을 JSON으로 파싱하여 반환
     *
     * @param keyword 검색할 키워드
     * @return 검색 결과 리스트
     * @throws IOException 네트워크 오류 발생 시
     * @throws InterruptedException 요청이 인터럽트될 경우
     */
    public List<NaverAPIResultItem> searchByKeyword(String keyword) throws IOException, InterruptedException {
        logger.info("Search by keyword: " + keyword);
        HashMap<String, String> body = new HashMap<>();
        APIClientParam param = new APIClientParam(
                "https://openapi.naver.com/v1/search/news.json?query=%s".formatted(keyword),
                "GET",
                body,
                "X-Naver-Client-Id", clientID, "X-Naver-Client-Secret", clientSecret
        );
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(apiClient.callAPI(param), NaverAPIResult.class).items();
    }
}
