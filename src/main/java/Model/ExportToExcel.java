package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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