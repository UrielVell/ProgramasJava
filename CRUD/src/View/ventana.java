/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Singleton;
import Controller.gestionActualizar;
import Controller.gestionEliminar;
import Controller.gestionSelectProducto;
import Controller.gestionGuardar;
import Controller.gestionLimpiar;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author uriel
 */
public class ventana extends JFrame{
    
    public JTextField txtID;
    public JTextField txtProducto;
    public JTextField txtPrecio;
    public JTextField txtCantidad;
    public DefaultTableModel tableModel;
    public String cosulta = "SELECT * FROM productos";
    
    public JTable tabla;
    public JButton btnGuardar;
    public JButton btnEliminar;
    public JButton btnEditar;
    public JButton btnLimpiar;
    
    String producto;
    int id, cantidad;
    float precio;
    
    public ventana(){
        setTitle("CRUD Formulario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de ingreso de datos
        JPanel formularioPanel = new JPanel(new GridLayout(6, 2));
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formularioPanel.add(new JLabel("ID:"));
        txtID = new JTextField();
        txtID.setEnabled(false);
        formularioPanel.add(txtID);

        formularioPanel.add(new JLabel("Platillo:"));
        txtProducto = new JTextField();
        formularioPanel.add(txtProducto);

        formularioPanel.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        formularioPanel.add(txtPrecio);

        formularioPanel.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        formularioPanel.add(txtCantidad);

        btnGuardar = new JButton("Guardar");
        gestionGuardar guardar = new gestionGuardar(this);
        btnGuardar.addActionListener(guardar);
        
        btnEliminar = new JButton("Eliminar");
        gestionEliminar ge = new gestionEliminar(this);
        btnEliminar.addActionListener(ge);
        btnEliminar.setEnabled(false);
        
        btnEditar = new JButton("Editar");
        btnEditar.setEnabled(false);
        gestionActualizar ga = new gestionActualizar(this);
        btnEditar.addActionListener(ga);
        
        btnLimpiar = new JButton("Limpiar");
        gestionLimpiar gl = new gestionLimpiar(this);
        btnLimpiar.addActionListener(gl);
 
        formularioPanel.add(btnGuardar);
        formularioPanel.add(btnEliminar);
        formularioPanel.add(btnEditar);
        formularioPanel.add(btnLimpiar);
      
        
        add(formularioPanel, BorderLayout.NORTH); 

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Cantidad");

        tabla = new JTable(tableModel);
        gestionSelectProducto gs = new gestionSelectProducto(this);
        tabla.addMouseListener(gs);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

       cargarInfo();

        setSize(600, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        
  
    }

    public void cargarInfo() {
        try {
            Connection con = Singleton.getInstance();
            PreparedStatement stmt = con.prepareStatement(cosulta);
            ResultSet rs = stmt.executeQuery();
            

            while (rs.next()) {
                id = rs.getInt("id");
                producto = rs.getString("producto");
                precio = rs.getFloat("precio");
                cantidad = rs.getInt("cantidad");
                Object[] filaDatos = { id, producto,precio,cantidad };
                tableModel.addRow(filaDatos);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
