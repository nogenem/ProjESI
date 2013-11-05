package JPanels;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONObject;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

@SuppressWarnings("serial")
public class ChangeNivel extends JPanel implements ActionListener, INossoPanel, Notificador, ListSelectionListener {
	
	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo.
	
	private JSONObject listOfUsers; //Guarda a lista dos logins dos usuarios e seus niveis.
	private HashMap<String, String> types = new HashMap<>(); //Guarda os tpws de usuarios.
	
	private JList<String> list;
	private DefaultListModel<String> listaUsuarios = new DefaultListModel<>(); 	
	private JComboBox<String> comboBox;
	
	public ChangeNivel(PrintWriter escritor){
		this.escritor = escritor;
		types.put("Adiministrador", "0");
		types.put("Usuario", "1");
		types.put("0", "Adiministrador");
		types.put("1", "Usuario");
		
		setLayout(null);
		setSize(222+5, 331);
		
		JLabel lblMudarNivelDe = new JLabel("Mudar nivel de um usuario:");
		lblMudarNivelDe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMudarNivelDe.setBounds(10, 11, 240, 22);
		add(lblMudarNivelDe);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 204, 182);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaUsuarios);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		scrollPane.setViewportView(list);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(10, 235, 204, 20);
		add(comboBox);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setMargin(new Insets(0, 0, 0, 0));
		btnSalvar.setBounds(10, 276, 89, 23);
		btnSalvar.addActionListener(this);
		add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(125, 276, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}

	@Override
	public void parsePacket(JSONObject packet) {
		if(packet.has("lista")){
			
			JSONObject tmp = packet.getJSONObject("lista");
			listOfUsers = tmp;
			String[] array = tmp.getNames(tmp);
				
			if(array.length == 0)
				return;
		
			for(int i = 0; i<array.length; i++)
				listaUsuarios.addElement(array[i].toString());
	
			list.setSelectedIndex(0);
		}
	}

	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}
	
	@Override
	public void notificar(JSONObject packet) { //Notifica a GUI para mudar para o proximo conteudo.
		notificado.serNotificado(packet);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { //Trata os eventos onClick do panel.
		if(arg0.getActionCommand().equals("Salvar")){
			String login = list.getSelectedValue();
			String nivel = types.get(comboBox.getSelectedItem());
			
			JSONObject packet = new JSONObject();
			HashMap<String, String> tmp = new HashMap<>();
			
			tmp.put("login", login);
			tmp.put("nivel", nivel);
			
			packet.put("changeNivel", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaEquipes(escritor);
			
			JSONObject packet = new JSONObject();
			packet.put("listarEquipes", "");
			
			escritor.println(packet.toString());
			escritor.flush();
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (arg0.getValueIsAdjusting() == false) { 
			comboBox.removeAllItems();
			
			JList tmp = (JList) arg0.getSource();
			String login = (String) tmp.getSelectedValue();

			String nivel = listOfUsers.get(login).toString();
	
			comboBox.addItem( types.get(nivel) );
			comboBox.addItem( nivel.equals("0") ? types.get("1") : types.get("0") );
		}
	}
}
