package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class S1_F2ExcelReader {
    public static List<String[]> getTestData(String filePath, String sheetName) {
        List<String[]> dataList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            for (Row row : sheet) {
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();
                dataList.add(new String[]{username, password});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}