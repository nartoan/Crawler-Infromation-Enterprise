package Model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by toan on 3/18/2017.
 */
public class ExportToExcel {
    private String path_file = "";

    public static void main(String[] args) throws IOException {
        ExportToExcel exportToExcel = new ExportToExcel();
        exportToExcel.exportToFile("C:/a/tesst.xlsx");
    }

    private void exportToFile(String path_file) throws IOException {
        this.path_file = path_file;

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Test.Demo");

        Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
        };


        int rowNum = 0;
        System.out.println("Creating excel");

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }


        FileOutputStream outputStream = new FileOutputStream(path_file);
        workbook.write(outputStream);
        workbook.close();
    }

    public static void ExportToFileExcel(CopyOnWriteArrayList<Enterprise> enterprises, String province, String district, String village) throws IOException, InvalidFormatException {
        Workbook workbook;
        if (new File("C:/" + province + "/" + district + ".xlsx").exists()) {
            FileInputStream inputStream = new FileInputStream(new File("C:/" + province + "/" + district + ".xlsx"));
            workbook = WorkbookFactory.create(inputStream);
        } else {
            workbook = new XSSFWorkbook();
        }
        Sheet sheet = workbook.createSheet(village);
        int rowNum = 0;
        for (Enterprise enterprise : enterprises) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;

            Cell cell = row.createCell(colNum++);
            cell.setCellValue(enterprise.getTaxCode());
            cell = row.createCell(colNum++);
            cell.setCellValue(enterprise.getName());
            cell = row.createCell(colNum++);
            cell.setCellValue(enterprise.getTimeUpdate());
            cell = row.createCell(colNum++);
            cell.setCellValue(enterprise.getTypeOfTax());
        }

        try {
            if (!new File("C:/" + province).exists())
                new File("C:/" + province).mkdir();

            FileOutputStream outputStream = new FileOutputStream("C:/" + province + "/" + district + ".xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ExportToExcel() {

    }

//    public void exportToFile(List<Enterprise> enterprise, String path_file) throws IOException {
//        this.path_file = path_file;
//
//        XSSFWorkbook workbook = new XSSFWorkbook(this.path_file);
//
//        XSSFSheet sheet = workbook.createSheet("Test.Demo");
//
//        FileOutputStream outputStream = new FileOutputStream(path_file);
//        workbook.write(outputStream);
//        workbook.close();
//    }
}