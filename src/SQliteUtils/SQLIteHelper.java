/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQliteUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.prefs.Preferences;
import Setting.ToolSetting;
import static Utils.Constants.STRING_NAME.DATABASELOG_NAME;
import static Utils.Constants.STRING_NAME.DATABASE_NAME;
import java.io.File;
/**
 * @author inet
 */
public class SQLIteHelper {

    public static Connection initConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:databases"+File.separator+DATABASE_NAME);
            c.setAutoCommit(false);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        if (c == null) {
            System.err.println("null");
        }
        return c;
    }

    public static void createTable(String sqlQuery) {
        Statement stmt = null;
        try {
            Connection c = SQLiteConnection.getInstance().getConnection();
            stmt = c.createStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static Boolean checkColumExist(String tableName, String colName) {
        Connection c = SQLiteConnection.getInstance().getConnection();
        String query = "select * from " + tableName;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            rs.findColumn(colName);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception e) {
            }
        }
    }

    public static String enCodeSQLString(String inString) {
        return inString.replaceAll("'", "#nhaydon#");
    }

    public static String deCodeSQLString(String inString) {
        return inString.replaceAll("#nhaydon#", "'");
    }

    public static Connection getConnectionLog() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:databases\\" + DATABASELOG_NAME);
            c.setAutoCommit(false);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        if (c == null) {
            System.err.println("null");
        }
        return c;
    }

    public static String executeQuery(String query) {
        //System.out.println("query "+query);
        Statement stmt = null;
        Connection c = SQLiteConnection.getInstance().getConnection();
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage() + " \n " + query;
        } finally {
            try {
                stmt.close();
                c.commit();
            } catch (Exception e) {
            }
        }
    }

    public static class Column {
        private String col_name;
        private String col_table;
        public static String dup_column_prefix = "duplicate column name: ";

        public String getCol_table() {
            return col_table;
        }

        public void setCol_table(String col_table) {
            this.col_table = col_table;
        }

        public String getCol_name() {
            return col_name;
        }

        public void setCol_name(String col_name) {
            this.col_name = col_name;
        }

        public Column(String col_name, String col_type, String col_default, String col_table) {
            this.col_name = col_name;
            this.col_type = col_type;
            this.col_default = col_default.length()==0?"":"DEFAULT '"+col_default+"'";
            this.col_table = col_table;
        }
        public Column(String col_name, String col_type, int col_default, String col_table) {
            this.col_name = col_name;
            this.col_type = col_type;
            this.col_default = "DEFAULT "+col_default;
            this.col_table = col_table;
        }
        public Column(String col_name, String col_type, String col_table) {
            this.col_name = col_name;
            this.col_type = col_type;
            this.col_default = "";
            this.col_table = col_table;
        }
        
        
        
        
        public String getCol_type() {
            return col_type;
        }

        public void setCol_type(String col_type) {
            this.col_type = col_type;
        }

        public String getCol_default() {
            return col_default;
        }

        public void setCol_default(String col_default) {
            this.col_default = col_default;
        }
        private String col_type;
        private String col_default;

    }

    public static void createTableFolderVideo(Connection c) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS FolderVideo(\n"
                    + "  video_id    VARCHAR (13)  PRIMARY KEY,\n"
                    + "  video_list     TEXT,\n"
                    + "  music_background VARCHAR (140) \n"
                    + ");");
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterTable(Column col) {
        Preferences pre = ToolSetting.getInstance().getPre();
        //if (pre.get(Column.dup_column_prefix + col.getCol_name() + " " + col.getCol_table(), "false").equals("true")) {
        //    return;
        //}
        Statement stmt = null;
        try {
            Connection c = SQLiteConnection.getInstance().getConnection();
            stmt = c.createStatement();
            stmt.executeUpdate("alter table " + col.getCol_table() + " add column " + col.getCol_name() + " " + col.getCol_type() + " " + col.getCol_default());
            stmt.close();
            c.commit();
            pre.put(Column.dup_column_prefix + col.getCol_name() + " " + col.getCol_table(), "");
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + col.getCol_table());
            pre.put(e.getMessage() + " " + col.getCol_table(), "true");
        }
    }

    public static boolean executeQueryNotClose(Connection c, String query) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            ToolSetting.getInstance().getPre().put(e.getMessage(), "");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean executeQueryNotExit(Connection c, String query) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
}
