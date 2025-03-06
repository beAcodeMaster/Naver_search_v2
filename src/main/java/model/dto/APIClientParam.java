package model.dto;

import java.util.Map;

/**
 * 📝 APIClientParam 레코드 (API 요청을 위한 데이터 모델)
 *
 * ✅ 역할:
 * - API 요청을 보낼 때 필요한 정보를 저장하는 데이터 모델
 * - URL, HTTP 메서드(GET, POST 등), 요청 본문(body), 헤더(headers) 포함
 *
 * ✅ 인풋(Input):
 * - `String url`: 요청을 보낼 API 주소
 * - `String method`: 요청 방식 (예: GET, POST)
 * - `Map<String, String> body`: 요청 본문 (JSON 형태의 데이터)
 * - `String... headers`: API 요청에 필요한 헤더들 (가변 인자)
 *
 * ✅ 아웃풋(Output):
 * - API 요청을 보낼 때 사용됨 (직접 반환값 없음)
 */
public record APIClientParam(String url, String method, Map<String, String> body, String... headers) {
}