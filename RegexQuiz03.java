package day23_0509.api.regex;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RegexQuiz03 {

	public static void main(String[] args) {
		
		
		List<Product> list = new ArrayList<>();
		
		String path = "C:\\Users\\taeju\\eclipse-workspace\\JavaAPI\\src\\day23_0509\\api\\regex\\건담.txt";
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String str;
			while((str = br.readLine()) != null) {
				
				String pattenr1 = "[0-9]{8}-[0-9]{2}-[0-9]{12,13}"; 		// [0-9]{8}-[0-9]{2}-[0-9]{12,13} 내코드 - \\d{8}-\\d{2}-\\d+
				String pattenr2 = "[가-힣]+ [가-힣]+(점|)";					// [가-힣]+ [가-힣]+(점|) / 내코드 - 건담[베이스]\\s[가-힣]점
				String pattenr3 = "\\[[가-힣A-Z]+\\]";						// \\[[가-힣A-Z]+\\] or [가-힣A-Z[]] / 내코드 - \\[[A-Z가-힣]+\\]  
				String pattenr4 = "\\d+,*\\d+원";							// 
				
				Matcher m1 = Pattern.compile(pattenr1).matcher(str);	
				Matcher m2 = Pattern.compile(pattenr2).matcher(str);	
				Matcher m3 = Pattern.compile(pattenr3).matcher(str);	
				Matcher m4 = Pattern.compile(pattenr4).matcher(str);	
				
				if(m1.find() && m2.find() && m3.find() && m4.find()) {
					
					System.out.println("===========================================");
					String day = m1.group();
					String store = m2.group();
					String grade = m3.group();
					String detail = str.substring(m3.end() + 1, m4.start() -1);	//String detail = str.substring(시작인덱스, 끝인덱스미만);
					String price  = m4.group();	
					
					System.out.println(day);
					System.out.println(store);
					System.out.println(grade);
					System.out.println(detail);
					System.out.println(price);
				
					// 1행을 Product객체에 저장
					Product p = new Product(day, store, grade, detail, price);
					// product를 리스트
					list.add(p);
				}
			}	// end while
			
			// 엑셀 쓰기 호출
			createExcel(list);
			
//			System.out.println(list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}	//main
	
	public static void createExcel(List<Product> list)	{ //List list 도 가능
		
		// 엑셀 파일 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 시트
		XSSFSheet sheet = workbook.createSheet();
		// 행
		XSSFRow row1 = sheet.createRow(0);
		// 셀
		XSSFCell cell = row1.createCell(0);
//		cell.setCellValue("날짜");
		row1.createCell(0).setCellValue("날짜");
		row1.createCell(1).setCellValue("지점");
		row1.createCell(2).setCellValue("등급");
		row1.createCell(3).setCellValue("상세");
		row1.createCell(4).setCellValue("가격");
		
		// 리스트를 엑셀파일로 정리
		for(int i = 0; i < list.size(); i++) {	//22번 회전
			
			XSSFRow row = sheet.createRow(i + 1);
			
			Product p = list.get(i);	// 리스트 안에 객체
			
			row.createCell(0).setCellValue(p.getDay());
			row.createCell(1).setCellValue(p.getStore());
			row.createCell(2).setCellValue(p.getGrade());
			row.createCell(3).setCellValue(p.getDetail());
			row.createCell(4).setCellValue(p.getPrice());
			
		}
		
		String path = "C:\\Users\\taeju\\eclipse-workspace\\JavaAPI\\src\\day23_0509\\api\\regex\\건담.xlsx";
		
		// Buffered는 사용 안된다
		try(FileOutputStream fos = new FileOutputStream(path)) {//1바이트기반으로 빠르게

			workbook.write(fos);;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
