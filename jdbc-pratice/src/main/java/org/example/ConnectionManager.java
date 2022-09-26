package org.example;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final int MAX_POOL_SIZE = 40;

    // DataSource를 하나만 생성
    private static final DataSource ds;

    static {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername("sa");
        hikariDataSource.setPassword("");
        // DB CONNECTION POOL
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE);
        ds = hikariDataSource;
    }

    public static Connection getConnection(){
//        String url = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
//        String id = "sa";
//        String password = "";
//        try{
//            Class.forName("org.h2.Driver");
//            return DriverManager.getConnection(url,id,password);
//        }catch (Exception ex){
//            return null;
//        }

        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }


//    public static DataSource getDataSource() {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(DB_DRIVER);
//        hikariDataSource.setJdbcUrl(DB_URL);
//        hikariDataSource.setUsername("sa");
//        hikariDataSource.setPassword("");
//        // DB CONNECTION POOL
//        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
//        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE);
//        return hikariDataSource;
//    }
    public static DataSource getDataSource() {
        return ds;
    }
}
