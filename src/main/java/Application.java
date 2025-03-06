import io.github.cdimascio.dotenv.Dotenv;
import model.dto.NaverAPIResultItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.search.SearchService;
import util.logger.MyLogger;

import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;

/**
 * 📝 Application 클래스 (프로그램 실행 메인 클래스)
 *
 * ✅ 역할:
 * - .env 파일에서 검색 키워드를 불러옴
 * - `SearchService`를 이용해 네이버 검색 API 요청을 보냄
 * - API 응답 데이터를 엑셀 파일(.xlsx)로 저장함
 *
 * ✅ 인풋(Input):
 * - .env 파일의 `SEARCH_KEYWORD` 값을 사용 (검색할 키워드)
 *
 * ✅ 아웃풋(Output):
 * - 네이버 검색 결과를 포함한 엑셀 파일 생성 (파일명: `{현재시간}_{키워드}.xlsx`)
 */
public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // .env 환경 변수 로드
        MyLogger logger = new MyLogger(Application.class);
        String searchKeyword = dotenv.get("SEARCH_KEYWORD"); // 검색 키워드 로드
        logger.info("검색 키워드: " + searchKeyword);

        SearchService searchAPI = new SearchService();
        String filename = "%d_%s";
        String mode = dotenv.get("MODE");
        if (mode.isBlank()) {
            throw new RuntimeException("mode is missing");
        }

        switch (mode) {
            case "DEV":
                filename += "_dev";
                break;
        }

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filename.formatted(System.currentTimeMillis(), searchKeyword) + ".xlsx")) {

            List<NaverAPIResultItem> result = searchAPI.searchByKeyword(searchKeyword);
            Sheet sheet = workbook.createSheet(searchKeyword);

            // 헤더 생성
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("날짜");
            headerRow.createCell(1).setCellValue("링크");
            headerRow.createCell(2).setCellValue("제목");
            headerRow.createCell(3).setCellValue("설명");

            // 검색 결과 추가
            int i = 0;
            for (NaverAPIResultItem item : result) {
                Row row = sheet.createRow(++i);
                row.createCell(0).setCellValue(item.pubDate());
                row.createCell(1).setCellValue(item.link());
                row.createCell(2).setCellValue(item.title());
                row.createCell(3).setCellValue(item.description());
            }

            // 엑셀 파일 저장
            workbook.write(fileOut);
            logger.info("엑셀 파일 저장 완료: " + filename.formatted(System.currentTimeMillis(), searchKeyword) + ".xlsx");

        } catch (Exception e) {
            logger.severe("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}
