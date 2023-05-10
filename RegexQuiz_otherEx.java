package day23_0509.api.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RegexQuiz_otherEx {
   public static void main(String[] args) {
      /*
       * 1. BufferedReader를 사용해서 건담.txt를 읽는다
       * 2. 정규표현식을 이용해서 날짜 / 지점 / 등급 / 내용 / 가격을 패턴분석
       * 3. 한줄씩 읽어서 List<Product>에 저장
       * 
       * 4. 외부 라이브러리 (자바 라이브러리 아닌것) POI - 자바에서 엑셀파일 .xlsx 형태 파일을 쓸 수 있도록 하는 기능
       *  하나의 시트에 각 행데이터를 엑셀 파일로 출력
       *  
       */
      List<Product> list = new ArrayList<>();
      String path = "C:\\Users\\taeju\\eclipse-workspace\\JavaAPI\\src\\day23_0509\\api\\regex\\건담.txt";
      
      try (BufferedReader br = new BufferedReader(new FileReader(path))) {
         String pattern = "(\\d{8}-\\d{2}-\\d{10,}) (건담[베]?[이]?[스]?\\s[가-힣]+) (\\[[A-Z가-힣]+\\]) (.+) ([0-9,]+.원)";
         String s;
         while ((s = br.readLine()) != null) {
            
            Matcher m = Pattern.compile(pattern).matcher(s);
            
            if (m.find()) {
               list.add(new Product(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)));
               System.out.println("------------------------------------------------");
               System.out.println(m.group(1));
               System.out.println(m.group(2));
               System.out.println(m.group(3));
               System.out.println(m.group(4));
               System.out.println(m.group(5));
            }
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      //엑셀 파일 생성
      XSSFWorkbook workbook = new XSSFWorkbook();
      
      //엑셀 파일 안에 시트 생성
      XSSFSheet sheet = workbook.createSheet("건담");
      
      
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setWrapText(true);
      
      XSSFRow row = null;
      XSSFCell cell = null;
      
      for (int i = 0; i < list.size(); i++) {
         row = sheet.createRow(i);
         
         cell = row.createCell(0);
         cell.setCellValue(list.get(i).getDay());
         cell = row.createCell(1);
         cell.setCellValue(list.get(i).getStore());
         cell = row.createCell(2);
         cell.setCellValue(list.get(i).getGrade());
         cell = row.createCell(3);
         cell.setCellValue(list.get(i).getDetail());
         cell = row.createCell(4);
         cell.setCellValue(list.get(i).getPrice());
      }
      
      try {
         File xlsxFile = new File("C:\\Users\\taeju\\eclipse-workspace\\JavaAPI\\src\\day23_0509\\api\\regex\\new.xlsx");
         FileOutputStream fos = new FileOutputStream(xlsxFile);
         workbook.write(fos);
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      
   }
}