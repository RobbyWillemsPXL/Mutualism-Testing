package be.pxl.mutualism.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseUtil {

    public static void clearTreesTable(String dbUrl, String user, String password) {
        String query = "TRUNCATE TABLE trees;";
        try (Connection connection = DriverManager.getConnection(dbUrl, user, password);
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Tabel 'trees' succesvol leeggemaakt.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fout bij het legen van de tabel 'trees'.", e);
        }
    }
}

