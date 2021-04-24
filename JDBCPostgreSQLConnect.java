package com.rtfour;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCPostgreSQLConnect {


    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "root";

    private void connect() throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);){
            if(connection !=null){
                System.out.println("Connected to PostgreSQL server successfully!");
            }else {
                System.out.println("Failed to connect PostgresSQL server");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JDBCPostgreSQLConnect sqlConnect = new JDBCPostgreSQLConnect();
        sqlConnect.connect();
    }
}
