package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
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
        exportToExcel.exportToFile("/tesst.xlsx");
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

    public static void ExportToFileExcel(HashMap<String, CopyOnWriteArrayList<Enterprise>> list, String path_file) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        Set set = list.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            XSSFSheet sheet = workbook.createSheet((String) me.getKey());

            CopyOnWriteArrayList<Enterprise> enterprises = (CopyOnWriteArrayList<Enterprise>) me.getValue();
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
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(path_file);
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