/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQliteUtils;

import java.sql.Connection;

/**
 *
 * @author simpl
 */
public class SQLiteConnection {
    public static SQLiteConnection connection_instance = null;
    public Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    private SQLiteConnection() {
        connection = SQLIteHelper.initConnection();
    }
    public static SQLiteConnection getInstance() {
        if (connection_instance == null) {
            connection_instance = new SQLiteConnection();
        }
        return connection_instance;
    }

}
