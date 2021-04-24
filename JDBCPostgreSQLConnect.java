package com.rtfour;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class JDBCPostgreSQLConnect {

    static class Value {
        public String header;
        public String content;
    }

    private static final String url = "jdbc:postgresql://localhost/postgres";
    private static final String user = "postgres";
    private static final String password = "root";

    public static void main(String[] args) throws SQLException {


        try (Connection connection = DriverManager.getConnection(url, user, password);) {


            if (connection != null) {
                System.out.println("Connected to PostgreSQL server successfully!");
            } else {
                System.out.println("Failed to connect PostgresSQL server");
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM JC_CONTACT");

            while (rs.next()) {
                String str = rs.getString("contact_id") + ":" + rs.getString(2);
                System.out.println("Contact:" + str);
            }

//            Map<String, String> example = new HashMap<String, String>();
//            nv.header  = "aaa";
//            nv.content = "bbb";
//            example.put(nv.header, nv.content);
//            System.out.println(example);

            addData(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void addData(Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO jc_contact (first_name, last_name, phone, email) "
                        + "VALUES (?, ?, ?, ?)", new String[]{"contact_id"})) {

//            for (int i = 0; i < 10; i++) {
//                stmt.setString(1, "FirstName_" + i);
//                stmt.setString(2, "LastNAme_" + i);
//                stmt.setString(3, "phone_" + i);
//                stmt.setString(4, "email_" + i);
//                stmt.addBatch();
//            }

            System.out.println("Пропишите путь");
            Scanner file_scan = new Scanner(System.in);
            String file_path1 = file_scan.nextLine();
            File exel_file_first = new File(file_path1);
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
                int number = 1;
                while (cellIterator.hasNext()) {
                    if (number == 5) {
                        number = 1;
                        stmt.addBatch();
                    }
                    Cell cell = cellIterator.next();
                    String excel_values = cell.toString();
                    System.out.print(excel_values);
                    System.out.println();
                    stmt.setString(number, excel_values);
                    number++;
                    //nv.header = excel_values;
                    //values_storage.put(nv.header, nv.content);
                    //System.out.println(values_storage);
                }
            }
            stmt.executeBatch();
            ResultSet gk = stmt.getGeneratedKeys();
            while (gk.next()) {
                System.out.println("Inserted:" + gk.getString(1));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
