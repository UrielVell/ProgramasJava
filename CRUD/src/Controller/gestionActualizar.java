/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Producto;
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
public class gestionActualizar implements ActionListener {

    ventana v;

    String consultaActualizar = "UPDATE productos SET producto = ?, precio = ?, cantidad = ? WHERE id = ?;";

    public gestionActualizar(ventana x) {
        this.v = x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String id2 = v.txtID.getText();
        String producto = v.txtProducto.getText();
        String precio2 = v.txtPrecio.getText();
        String cantidad2 = v.txtCantidad.getText();

        if ("".equals(producto) || "".equals(precio2) || "".equals(cantidad2)|| "".equals(id2)) {
            JOptionPane.showMessageDialog(null,
                    "Ingrese todos los datos",
                    "Atencion",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                
                int id = Integer.parseInt(id2);
                float precio = Float.parseFloat(precio2);
                int cantidad = Integer.parseInt(cantidad2);
                
                try {
                    Connection con = Singleton.getInstance();
                    PreparedStatement ps = con.prepareStatement(consultaActualizar);
                    ps.setString(1, producto);
                    ps.setFloat(2, precio);
                    ps.setInt(3, cantidad);
                    ps.setInt(4, id);
                    ps.execute();

                    PreparedStatement ps2 = con.prepareStatement(v.cosulta);
                    ps2.executeQuery();
                    
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
                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } catch (NumberFormatException n) {

            }
            
        }
    }

}
