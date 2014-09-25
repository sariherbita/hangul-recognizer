/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import com.mysql.jdbc.Connection;
import entity.Graph;
import entity.Vertex;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS A450LCI5 64
 */
public class DataBaseConnector {
    Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    private final String url = "jdbc:mysql://localhost:3306/hangul_graph";
    private final String user = "root";
    private final String password = "";
    
    public DataBaseConnector(){
        connectDataBase();
    }
    
    private void connectDataBase(){
        try {
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet selectFromDB(){
        String query = "select * from hangul";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    public void insertDataBase(String name,Graph g){
        try {
            preparedStatement = connection.prepareStatement("insert into hangul (hangul_character,graph) values (?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setBytes(2, toBytes(g));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private byte[] toBytes(Object object){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                oos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return baos.toByteArray();
    }
}
