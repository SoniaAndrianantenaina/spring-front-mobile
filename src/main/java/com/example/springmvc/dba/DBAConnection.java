package com.example.springmvc.dba;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAConnection {

    public static Connection connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://kashin.db.elephantsql.com/cysgxvla", "cysgxvla",
                    "P31JdoNDqvHSCU6PIseFHe6DLgF3bkpd");
        } catch (Exception e) {
            throw e;
        }
        return connection;
    }
}
