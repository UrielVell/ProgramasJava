/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author uriel
 */
public class Singleton {
    private static Connection con = null;
    
    Singleton(){
        
    }
    
    public static Connection getInstance() throws SQLException{
        
            try{
            if (con == null){
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda","root","uriel");
            System.out.println("Conectado a la base de datos");
            }
            }catch( SQLException e){
                System.out.println(e);
            }
        
        return con;
    }
    
}
