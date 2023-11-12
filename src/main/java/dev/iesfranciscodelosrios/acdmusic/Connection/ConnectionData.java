package dev.iesfranciscodelosrios.acdmusic.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData {
    //Es opcional porque podria interesarnos cerrar la conexion
    private static java.sql.Connection conn =null;
    //bbdd local
    private final static String uri="jdbc:mysql://localhost:3306/rythm";
    //bbdd cloud
    //private final static String uri="jdbc:mysql://34.155.11.50:3306/rythm";
    private final static String user= "root";
    private final static String password="";

    public ConnectionData(){ }
    public static java.sql.Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection(uri,user,password);
            } catch (SQLException e) {
                e.printStackTrace();
                conn = null;
            }
        }
        return conn;
    }

    public static void close(){
        /*if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                conn= null;
            }
        }*/
    }
}