package com.demo;

import org.codehaus.jettison.json.JSONException;

import java.sql.*;

public class test {


    public static void main(String args[]) throws JSONException {
        String jdbcURL = "jdbc:mysql://pcorslsqlsweu01.database.windows.net/PulseCoreSalesDWH";
        String username = "GenieEuropePULSE";
        String password = "Gen!Eur#429422";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT TOP (5) [MRA Code]" +
                    "FROM [WEU].[FactPrimarySales]" +
                    "WHERE  [Calendar Month] = '202205'";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);
            System.out.println(result.toString());
            statement.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        }
    }



}
