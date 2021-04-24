import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;

public class Main {

    static class Value {
        public String header;
        public String content;
    }

    public static void main(String[] args) {

        //getting a pathway to a file.
        System.out.println("Пропишите путь");
        Scanner file_scan = new Scanner(System.in);
        String file_path1 = file_scan.nextLine();


        File exel_file_first = new File(file_path1);

        try {
            FileInputStream fis = new FileInputStream(exel_file_first);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet file_sheet = wb.getSheetAt(0);

            //int rowInt = file_sheet.getPhysicalNumberOfRows();
            //int columnsInt = file_sheet.getRow(0).getPhysicalNumberOfCells();


            int i = 0;

            Value nv = new Value();

            Iterator<Row> rownum = file_sheet.iterator();


            while (rownum.hasNext()) {
                //Map<String, String> values_storage = new HashMap<String, String>();
                Row rows = rownum.next();
                Iterator<Cell> cellIterator = rows.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String excel_values = cell.toString();
                    System.out.print(excel_values);
                    System.out.println();

                    //nv.header = excel_values;
                    //values_storage.put(nv.header, nv.content);
                    //System.out.println(values_storage);
                }
            }


            wb.close();
            fis.close();

        } catch (Exception except) {
            except.printStackTrace();
            System.err.println(except.getClass().getName() + ": " + except.getMessage());
            System.exit(111);
        }

        // /Users/imac/Desktop/test_excel.XLSX
    }
}
