package mozi.backend.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author murph
 */
public final class DBConnectionSource {

    private final DBConnection connection;

    private DBConnectionSource(){
            this.connection = new DBConnection(PropertiesReader.readProperties("./config/config.properties"));
    }

    public static DBConnectionSource getInstance(){
        return DBConnectionInstanceHolder.INSTANCE;
    }

    private static class DBConnectionInstanceHolder{
        private static final DBConnectionSource INSTANCE = new DBConnectionSource();
    }

    public Connection getConnection() throws SQLException {
        return connection.getConnection();
    }

    public long obtainNewId() throws SQLException{
        return connection.obtainNewId();
    }

}

