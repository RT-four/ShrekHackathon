package com.rtfour;

import java.sql.*;

public class JDBCPostgreSQLConnect {

    private static final String url = "jdbc:postgresql://localhost/postgres";
    private static final String user = "postgres";
    private static final String password = "root";

    public static void main(String[] args) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);){
            if(connection !=null){
                System.out.println("Connected to PostgreSQL server successfully!");
            }else {
                System.out.println("Failed to connect PostgresSQL server");
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM JC_CONTACT");

            while (rs.next()) {
                String str = rs.getString("contact_id") + ":" + rs.getString(2);
                System.out.println("Contact:" + str);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
