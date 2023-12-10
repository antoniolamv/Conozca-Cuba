package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import dto.PlaceDTO;
import services.PlaceServices;
import services.ServicesLocator;
import utils.MiJPanel;
import utils.MyButtonModel;
import utils.Paneles;
import utils.PropiedadesComboBox;
import utils.Validaciones;

public class EditarLugar extends MiJPanel {
	
	private PlaceServices placeServices = ServicesLocator.getPlaceServices();

	private static final long serialVersionUID = 1L;
	private Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	private Color colorAzul = new Color(59, 165, 187);

	private JPanel panelSuperior;
	private JButton btnCerrar;
	private JLabel lblNombre;
	private JPanel panelInferior;
	private JButton btnAtras;
	private JButton btnEditar;
	
	private boolean costChanged = true;
	private boolean nameChanged = true;

	private JTextField txtNombre;
	private JTextField txtCosto;
	private JComboBox<String> cbTipo;

	private Principal padre;
	private Gestion anterior;
	private EditarLugar este;
	private PlaceDTO place;

	public EditarLugar(Principal pd, Gestion a, PlaceDTO p){
		este = this;
		padre = pd;
		anterior = a;
		place = p;
		setTipoPanel(Paneles.PANEL_EDITAR_LUGAR);
		padre.setPanelAbierto(getTipoPanel());
		padre.setPanelEditarLugar(este);
		setBounds(pantalla.width/2-181, pantalla.height/2-206, 362, 362);
		setBackground(Color.darkGray);
		setLayout(null);
		
		panelSuperior = new JPanel(null);
		panelSuperior.setBounds(1, 1, 360, 30);
		panelSuperior.setBackground(colorAzul);
		add(panelSuperior);
		
		lblNombre = new JLabel("Editar Lugar");
		lblNombre.setForeground(Color.black);
		lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
		lblNombre.setBounds(10, 0, 200, 30);
		panelSuperior.add(lblNombre);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/visual/imagenes/close.png"));
		Image image = img.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		Icon iconCerrar = new ImageIcon(image);

		btnCerrar = new JButton(iconCerrar);
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				padre.getPanelPrincipal().remove(este);
				padre.getPanelPrincipal().repaint();
				padre.setPanelAbierto(0);
			}
		});
		btnCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCerrar.setContentAreaFilled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCerrar.setContentAreaFilled(false);
			}
		});
		btnCerrar.setBounds(315, 0, 45, 30);
		btnCerrar.setBackground(Color.red);
		btnCerrar.setFocusable(false);
		btnCerrar.setBorderPainted(false);
		btnCerrar.setContentAreaFilled(false);
		btnCerrar.setModel(new MyButtonModel());
		panelSuperior.add(btnCerrar);
		
		panelInferior = new JPanel(null);
		panelInferior.setBounds(1, 31, 360, 330);
		panelInferior.setBackground(Color.white);
		add(panelInferior);
		
		img = new ImageIcon(getClass().getResource("/visual/imagenes/atras.png"));
		image = img.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		Icon iconAtras = new ImageIcon(image);
		
		btnAtras = new JButton(iconAtras);
		btnAtras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				padre.getPanelPrincipal().remove(este);
				padre.getPanelPrincipal().add(anterior);
				padre.getPanelPrincipal().repaint();
				padre.setPanelAbierto(anterior.getTipoPanel());
			}
		});
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setContentAreaFilled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setContentAreaFilled(false);
			}
		});
		btnAtras.setBounds(5, 5, 40, 40);
		btnAtras.setBackground(colorAzul);
		btnAtras.setFocusable(false);
		btnAtras.setBorderPainted(false);
		btnAtras.setContentAreaFilled(false);
		btnAtras.setModel(new MyButtonModel());
		panelInferior.add(btnAtras);
		
		img = new ImageIcon(getClass().getResource("/visual/imagenes/logo cc.png"));
		image = img.getImage().getScaledInstance(220, 67, Image.SCALE_SMOOTH);
		Icon iconLogo = new ImageIcon(image);

		JLabel logo = new JLabel(iconLogo);
		logo.setBounds(68, 15, 220, 67);
		panelInferior.add(logo);
		
		txtNombre = new JTextField(place.getPlaceName());
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Validaciones.letrasNumeros(e);
			}
		});
		txtNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtNombre.getText().equals("Nombre") && !nameChanged){
					txtNombre.setText("");
					nameChanged = true;
					txtNombre.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(txtNombre.getText().equals("")){
					txtNombre.setText("Nombre");
					nameChanged = false;
					txtNombre.setForeground(Color.gray);
				}
			}
		});
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNombre.setForeground(Color.black);
		txtNombre.setBorder(new MatteBorder(0, 0, 3, 0, colorAzul));
		txtNombre.setBounds(50, 110, 260, 30);
		panelInferior.add(txtNombre);
		
		txtCosto = new JTextField(String.valueOf(place.getCostPerPerson()));
		txtCosto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Validaciones.soloNumeroYUnaComa(e, txtCosto.getText());
				costChanged = true;
			}
		});
		txtCosto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtCosto.getText().equals("Costo Por Persona")){
					txtCosto.setText("");
					txtCosto.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(txtCosto.getText().equals("")){
					txtCosto.setText("Costo Por Persona");
					txtCosto.setForeground(Color.gray);
					costChanged = false;
				}
				else{
					String ca = txtCosto.getText();
					if(ca.charAt(ca.length()-1)=='.'){
						ca = ca.substring(0, ca.length()-1);
						txtCosto.setText(ca);
					}
				}
			}
		});
		txtCosto.setFont(new Font("Arial", Font.PLAIN, 16));
		txtCosto.setForeground(Color.black);
		txtCosto.setBorder(new MatteBorder(0, 0, 3, 0, colorAzul));
		txtCosto.setBounds(50, 160, 260, 30);
		panelInferior.add(txtCosto);
		
		cbTipo = new JComboBox<String>();
		cbTipo.setBounds(50, 210, 260, 30);
		cbTipo.setBackground(Color.white);
		cbTipo.setFocusable(false);
		cbTipo.setFont(new Font("Arial", Font.PLAIN, 16));
		cbTipo.setBorder(new MatteBorder(0, 0, 3, 0, colorAzul));
		cbTipo.setModel(utils.ComboBoxModel.tiposServiciosModel());
		cbTipo.setUI(PropiedadesComboBox.createUI(getRootPane(), cbTipo.getBounds()));
		panelInferior.add(cbTipo);
		cbTipo.setSelectedItem(place.getTypeOfService());
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Arial", Font.BOLD, 18));
		btnEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				padre.getPanelPrincipal().remove(este);
				padre.getPanelPrincipal().repaint();
				
				try{
					int codigo = place.getPlaceCode();
					String cadena = "";
					String costo = "";
					if(nameChanged) cadena = txtNombre.getText();
					if(costChanged) costo = txtCosto.getText();
					else throw new IllegalArgumentException("El campo del costo est� vac�o");
					double cos = Double.valueOf(costo);
					String tipo = cbTipo.getItemAt(cbTipo.getSelectedIndex());
					Validaciones.lugar(cadena);
					placeServices.updatePlace(codigo, cadena, cos, tipo);
					MensajeAviso ma = new MensajeAviso(null, padre, anterior, "El lugar fue editado con �xito", MensajeAviso.CORRECTO);
					ma.setVisible(true);
					anterior.ponerLugares();
				} catch(IllegalArgumentException | ClassNotFoundException | SQLException e1){
					MensajeAviso ma = new MensajeAviso(null, padre, este, e1.getMessage(), MensajeAviso.ERROR);
					ma.setVisible(true);
				}
			}
		});
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnEditar.setBackground(new Color(40, 113, 128));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnEditar.setBackground(colorAzul);
			}
		});
		btnEditar.setModel(new MyButtonModel());
		btnEditar.setBounds(50, 270, 260, 35);
		btnEditar.setBackground(colorAzul);
		btnEditar.setForeground(Color.black);
		btnEditar.setFocusable(false);
		btnEditar.setBorderPainted(false);
		panelInferior.add(btnEditar);
		
	}

}

