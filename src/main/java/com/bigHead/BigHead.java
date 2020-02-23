package com.bigHead;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BigHead {
    private static String filePath = "C:\\java_project\\source.xlsx";
    private static String destFilePath = "C:\\java_project\\dest.xlsx";
    private static int RANDOM_NUM = 10;
    private static long id = 1;
    private List<Sheet> sheetList = new ArrayList<Sheet>();
    private List<List<SafetyEntity>> safetyEntities = new ArrayList<List<SafetyEntity>>();

    public static void main(String[] args) {
        BigHead bigHead = new BigHead();
        bigHead.initSheetList();
        bigHead.processSheetList();
        List<List<SafetyEntity>> list = bigHead.getEveryMonthRandomSafetyEntiy();
        bigHead.writeResultToExcel(list);
    }

    private void writeResultToExcel(List<List<SafetyEntity>> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (int i = 0; i < list.size(); i++) {
            List<SafetyEntity> entities = list.get(i);
            XSSFSheet sheet = null;
            boolean flag = true;
            int rownum = 0;
            for (SafetyEntity sf : entities) {
                if (flag) {
                    sheet = workbook.createSheet(sf.getSheetName());
                    flag = false;
                }
                Row row = sheet.createRow(rownum++);
                setSafetyEntityToRow(row, sf);
            }
        }
        FileOutputStream outputStream = null;
        try {
            File file = new File(destFilePath);
            file.createNewFile();
            outputStream = new FileOutputStream(destFilePath);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSafetyEntityToRow(Row row, SafetyEntity sf) {
        Cell cell = row.createCell(0);
        cell.setCellValue(sf.getCompanyName());

        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(sd.format(sf.getActiveDate()));

        Cell cell2 = row.createCell(2);
        cell2.setCellValue(sf.getPolicyNumber());

        Cell cell3 = row.createCell(3);
        cell3.setCellValue(sf.getSafeNumer());

        Cell cell4 = row.createCell(4);
        cell4.setCellValue(sf.getProtectMoney());

        Cell cell5 = row.createCell(5);
        cell5.setCellValue((sf.getMonth() + 1)+" month");
    }

    public void initSheetList() {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(10000)
                .bufferSize(4096)
                .open(is);
        for (Sheet sheet : workbook) {
            sheetList.add(sheet);
            System.out.println(sheet.getSheetName());
        }

    }

    public void processSheetList() {
        Sheet sheet = sheetList.get(0);
        for (int i = 0; i < sheetList.size(); i++) {
            sheet = sheetList.get(i);
            List<SafetyEntity> list = new ArrayList();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            if (sheet.getSheetName().equals("660305") || sheet.getSheetName().equals("660405")) {
                dateFormat = new SimpleDateFormat("yyyyMMdd");
            }
            for (Row r : sheet) {
                if (r.getRowNum() == 0) {
                    continue;
                }
                generateSafetyEntity(sheet, dateFormat, r, list);
            }
            safetyEntities.add(list);
        }
    }

    private void generateSafetyEntity(Sheet sheet, DateFormat dateFormat, Row r, List<SafetyEntity> list) {
        SafetyEntity safetyEntity = new SafetyEntity();
        boolean isNomalData = true;
        safetyEntity.setSheetName(sheet.getSheetName());
        safetyEntity.setId(id);
        id++;
        safetyEntity.setCompanyName(r.getCell(0).getStringCellValue());
        safetyEntity.setPolicyNumber(r.getCell(1).getStringCellValue());
        safetyEntity.setSafeNumer(r.getCell(2).getStringCellValue());
        DataFormatter dataFormatter = new DataFormatter();
        try {
            if (r.getCell(3).getCellType() == CellType.STRING) {
                String formatValue = dataFormatter.formatCellValue(r.getCell(3));
                safetyEntity.setActiveDate(dateFormat.parse(formatValue));
            } else {
                safetyEntity.setActiveDate(r.getCell(3).getDateCellValue());
            }
            if (r.getCell(4).getCellType() == CellType.STRING) {
                String formatValue2 = dataFormatter.formatCellValue(r.getCell(4));
                safetyEntity.setWriteDate(dateFormat.parse(formatValue2));
            } else {
                safetyEntity.setWriteDate(r.getCell(4).getDateCellValue());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        safetyEntity.setProtectMoney(r.getCell(5).getNumericCellValue());
        Calendar cal = Calendar.getInstance();
        if (safetyEntity.getActiveDate() != null) {
            cal.setTime(safetyEntity.getActiveDate());
            safetyEntity.setMonth(cal.get(Calendar.MONTH));
        } else {
            isNomalData = false;
        }
        if (isNomalData) {
            list.add(safetyEntity);
        } else {
            System.out.println(safetyEntity.toString());
        }
    }

    public List<List<SafetyEntity>> getEveryMonthRandomSafetyEntiy() {
        List<List<SafetyEntity>> allYearSafetyEntities = new ArrayList();
        for (int k = 0; k < safetyEntities.size(); k++) {
            List<SafetyEntity> yearTemp = new ArrayList();
            for (int j = 0; j < 12; j++) {
                List<SafetyEntity> monthTemp = new ArrayList();
                for (int i = 0; i < safetyEntities.get(k).size(); i++) {
                    SafetyEntity safetyEntity = safetyEntities.get(k).get(i);
                    if (safetyEntity.getMonth() == j) {
                        monthTemp.add(safetyEntity);
                    }
                }
                if (monthTemp.size() == 0) {
                    System.out.println(j + 1 + "month");
                    System.out.println(k + 1);
                }
                getRandomSafetyEntity(monthTemp, yearTemp);
            }
            allYearSafetyEntities.add(yearTemp);
        }
        return allYearSafetyEntities;
    }

    private void getRandomSafetyEntity(List<SafetyEntity> monthTemp, List<SafetyEntity> yearSafetyEntities) {
        if (monthTemp.size() == 0) return;
        Random rand = new Random();
        Set<SafetyEntity> set = new HashSet();
        if (monthTemp.size() <= RANDOM_NUM) {
            set.addAll(monthTemp);
        } else {
            while (set.size() < RANDOM_NUM) {
                set.add(monthTemp.get(rand.nextInt(monthTemp.size())));
            }
        }
        for (SafetyEntity sf : set) {
            yearSafetyEntities.add(sf);
        }
    }
}
