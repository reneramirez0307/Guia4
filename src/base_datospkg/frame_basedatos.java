package base_datospkg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class frame_basedatos {

	private JFrame frame;
	private JTextField datoID;
	private JTextField datoNombre;

	/**
	 * Launch the application.
	 */
	Connection conexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	private void limpiarCuadrosDeTexto() {
		datoID.setText(null);
		datoNombre.setText(null);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame_basedatos window = new frame_basedatos();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public frame_basedatos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DATOS DE USUARIO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(136, 11, 166, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Identificación");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(23, 57, 78, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(23, 105, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		datoID = new JTextField();
		datoID.setBounds(111, 57, 154, 20);
		frame.getContentPane().add(datoID);
		datoID.setColumns(10);
		
		datoNombre = new JTextField();
		datoNombre.setBounds(111, 96, 272, 23);
		frame.getContentPane().add(datoNombre);
		datoNombre.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conexion = Conexion.conectar();
				try {
					preparedStatement = conexion
							.prepareStatement("Insert into listado (id,nombre) values(?,?)");
					preparedStatement.setString(1, datoID.getText());
					preparedStatement.setString(2, datoNombre.getText());
					
					int resultado = preparedStatement.executeUpdate();
					if (resultado>0) {
						JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
						limpiarCuadrosDeTexto();
						conexion.close();
					}else {
						JOptionPane.showMessageDialog(null, "No fue posible agregar el registro");
					}
						
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Se presentó un error al acceder a la base de datos");
				}
				
			}

		});
		btnCrear.setBounds(10, 149, 89, 23);
		frame.getContentPane().add(btnCrear);
		
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conexion = Conexion.conectar();
				try {
					preparedStatement = conexion
							.prepareStatement("UPDATE listado SET nombre =? Where id =?");
					preparedStatement.setString(1, datoNombre.getText());
					preparedStatement.setString(2, datoID.getText());
					
					int resultado = preparedStatement.executeUpdate();
					if (resultado>0) {
						JOptionPane.showMessageDialog(null, "Registro modificado exitosamente");
						limpiarCuadrosDeTexto();
						conexion.close();
					}else {
						JOptionPane.showMessageDialog(null, "No fue posible modificar el registro");
					}
						
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Se presentó un error al acceder a la base de datos");
				}
				
			}
			
		});
		btnModificar.setBounds(119, 149, 89, 23);
		frame.getContentPane().add(btnModificar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conexion = Conexion.conectar();
				try {
					preparedStatement = conexion
							.prepareStatement("DELETE FROM listado Where id = ?");
					
					preparedStatement.setString(1, datoID.getText());
										
					int resultado = preparedStatement.executeUpdate();
					if (resultado>0) {
						JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
						limpiarCuadrosDeTexto();
						conexion.close();
					}else {
						JOptionPane.showMessageDialog(null, "No fue posible eliminar el registro");
					}
						
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Se presentó un error al acceder a la base de datos");
				}
				
			}
			
		});
		btnBorrar.setBounds(226, 149, 89, 23);
		frame.getContentPane().add(btnBorrar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(335, 149, 89, 23);
		frame.getContentPane().add(btnSalir);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				conexion = Conexion.conectar();
				try {
					preparedStatement = conexion
							.prepareStatement("Select nombre from listado Where id = ?");
					
					preparedStatement.setString(1, datoID.getText());
					resultSet = preparedStatement.executeQuery();
					
					if (resultSet.next()) {
						datoNombre.setText(resultSet.getString("nombre"));
					}else {
						JOptionPane.showMessageDialog(null, "Identificación no encontrada en la base de datos");
					}
					conexion.close();

			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Se presentó un error al acceder a la base de datos");
			}
			}	
		});
		btnBuscar.setBounds(294, 56, 89, 23);
		frame.getContentPane().add(btnBuscar);
	}
}
