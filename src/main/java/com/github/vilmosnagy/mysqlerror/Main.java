package com.github.vilmosnagy.mysqlerror;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.UUID;

public class Main {

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&connectionCollation=utf8mb4_unicode_ci";

    public static void main(String[] args) throws Exception {
        Driver driver = DriverManager.getDriver(jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, "test", "test");
        PreparedStatement psmt = connection.prepareStatement("insert into foobar (id, author, updated_time, content) values (?, ?, ?, ?)");
        psmt.executeQuery("SET NAMES utf8mb4");
        psmt.executeQuery("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
        psmt.setString(1, UUID.randomUUID().toString());
        psmt.setString(2, "árvíztűrő tükörfúrógép");
        psmt.setDate(3, Date.valueOf(LocalDate.now()));
        psmt.setString(4, "\uD83D\uDC9C");
        psmt.executeUpdate();

        PreparedStatement query = connection.prepareStatement("select * from foobar");
        ResultSet resultSet = query.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getDate(3));
            System.out.println(resultSet.getString(4));
        }
    }
}
