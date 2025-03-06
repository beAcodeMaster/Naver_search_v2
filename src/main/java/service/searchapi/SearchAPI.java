package service.searchapi;

import java.util.List;

/**
 * 📝 SearchAPI 인터페이스 (검색 API의 공통 인터페이스)
 *
 * ✅ 역할:
 * - 검색 API의 기본적인 구조를 정의하는 인터페이스
 * - 실제 구현체(NaverSearchAPI 등)가 이 인터페이스를 따르게 만듦
 * - 검색 기능을 일관성 있게 유지할 수 있도록 함
 *
 * ✅ 인풋(Input):
 * - `String keyword`: 검색할 키워드
 *
 * ✅ 아웃풋(Output):
 * - `List<T>`: 검색 결과 리스트 (T는 검색 결과 타입, 예: NaverAPIResultItem)
 */
public interface SearchAPI<T> {
    /**
     * 🔹 searchByKeyword() 메서드
     * - 키워드를 기반으로 검색을 수행함
     * - 사용 예시:
     *   ```java
     *   List<NaverAPIResultItem> results = searchAPI.searchByKeyword("자바");
     *   ```
     *
     * @param keyword 검색할 키워드
     * @return 검색 결과 리스트
     */
    List<T> searchByKeyword(String keyword);
}