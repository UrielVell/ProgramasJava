/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author uriel
 */
public class gestionEliminar implements ActionListener {

    ventana v;

    String consultaEliminar = "DELETE FROM productos WHERE id = ?;";

    public gestionEliminar(ventana x) {
        this.v = x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(this.v, "¿Deseas realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(v.txtID.getText());
                try{
                     Connection con = Singleton.getInstance();
                    PreparedStatement ps = con.prepareStatement(consultaEliminar);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    
                    v.txtID.setText(null);
                    v.txtProducto.setText(null);
                    v.txtPrecio.setText(null);
                    v.txtCantidad.setText(null);

                    v.tableModel.getDataVector().removeAllElements();
                    v.cargarInfo();
                    v.txtProducto.setRequestFocusEnabled(true);
                    v.btnEditar.setEnabled(false);
                    v.btnEliminar.setEnabled(false);
                    v.btnGuardar.setEnabled(true);
                    
                    
                }catch(SQLException sql){
                    System.out.println(sql);
                }
           
        } else if (result == JOptionPane.NO_OPTION) {
            
        }
    }

}
