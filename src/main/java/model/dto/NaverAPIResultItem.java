package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 📝 NaverAPIResultItem 레코드 (네이버 검색 결과 항목)
 *
 * ✅ 역할:
 * - 네이버 검색 API에서 반환된 개별 뉴스 결과를 저장하는 데이터 모델
 * - 검색 결과에서 필요한 정보(제목, 링크, 설명, 날짜)를 저장
 *
 * ✅ 인풋(Input):
 * - JSON 응답에서 개별 뉴스 항목을 자동으로 매핑함
 *
 * ✅ 아웃풋(Output):
 * - `String title`: 뉴스 제목
 * - `String link`: 뉴스 기사 URL
 * - `String description`: 뉴스 내용 요약
 * - `String pubDate`: 뉴스 게시 날짜
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverAPIResultItem(String title, String link, String description, String pubDate) {
}
