package consumer;

import entities.UvIndex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    private ExcelUtils(){
        
    }
    
    public static void outputUvDataToExcel(String key, String value, File file) {
        try {
            UvIndex index = ConsumerUtils.convertMessageToUvIndex(key, value);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            Sheet sheet = workbook.getSheetAt(0);
            updateSheetWithNewUvData(index, sheet);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch(IOException e) {
            System.out.printf("[ERROR] Failed to output message data to excel. [Key: %s, Value: %s]", key, value);
        }
    }

    private static void updateSheetWithNewUvData(UvIndex index, Sheet sheet) {
        int rowNum = sheet.getLastRowNum() + 1;
        Map<Integer, Object[]> data = prepareData(rowNum, index);
        addDataToWorkbook(sheet, rowNum, data);
    }

    private static Map<Integer, Object[]> prepareData(int rowNum, UvIndex index) {
        Map<Integer, Object[]> data = new HashMap<>();
        data.put(
            rowNum,
            new Object[]{
                index.getCity(),
                index.getUvMin(),
                convertEpochToLocalDate(index.getUvMinTime()),
                index.getUvMax(),
                convertEpochToLocalDate(index.getUvMaxTime()),
                convertEpochToLocalDate(index.getSunriseTime()),
                convertEpochToLocalDate(index.getSunsetTime())
            }
        );
        return data;
    }

    private static void addDataToWorkbook(Sheet sheet, int rowNum, Map<Integer, Object[]> data) {
        Set<Integer> keySet = data.keySet();
        for (Integer num : keySet) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = data.get(num);
            int cellNum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }
    }
    
    private static String convertEpochToLocalDate(long epochTime) {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (epochTime*1000));
    }
}
