package org.hillel.repository;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private final Properties properties = new Properties();

    public DataSource() throws IOException {
        load("application.properties");
    }

    private void load(final String fileName) throws IOException {
        if (!StringUtils.hasText(fileName)) throw new IllegalArgumentException("fileName must be set");
        try (InputStream is = DataSource.class.getClassLoader().getResourceAsStream(fileName)){
            properties.load(is);
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("datasource.driver-class-name"));
        return DriverManager.getConnection(
                properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password"));
    }

    public void close(Connection con) throws SQLException {
        if (con != null){
            con.close();
        }
    }
}
