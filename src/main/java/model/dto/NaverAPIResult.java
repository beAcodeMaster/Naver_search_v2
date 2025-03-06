package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * 📝 NaverAPIResult 레코드 (네이버 검색 결과 전체 응답)
 *
 * ✅ 역할:
 * - 네이버 검색 API에서 반환된 전체 응답을 저장하는 데이터 모델
 * - 여러 개의 `NaverAPIResultItem`을 포함함 (뉴스 목록)
 * - API 응답의 `lastBuildDate`(마지막 업데이트 시간)도 저장
 *
 * ✅ 인풋(Input):
 * - JSON 응답에서 전체 검색 결과를 자동으로 매핑함
 *
 * ✅ 아웃풋(Output):
 * - `List<NaverAPIResultItem> items`: 검색된 뉴스 목록
 * - `String lastBuildDate`: API 응답의 마지막 업데이트 날짜
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverAPIResult(List<NaverAPIResultItem> items, String lastBuildDate) {}