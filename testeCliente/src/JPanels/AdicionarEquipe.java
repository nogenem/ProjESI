package JPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JPanel;

import org.json.JSONObject;

import PP_Observer.Notificador;
import PP_Observer.Notificavel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;

public class AdicionarEquipe extends JPanel implements ActionListener, INossoPanel, Notificador {
	
	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private JTextField textField;
	
	public AdicionarEquipe(PrintWriter escritor){
		this.escritor = escritor;
		this.next = new MenuPrincipal(escritor);
		
		setLayout(null);
		setSize(213+5, 143+25);
		
		JLabel lblAdicionarEquipe = new JLabel("Adicionar equipe");
		lblAdicionarEquipe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdicionarEquipe.setBounds(10, 11, 113, 23);
		add(lblAdicionarEquipe);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 56, 58, 14);
		add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(57, 53, 140, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setMargin(new Insets(0, 0, 0, 0));
		btnAdicionar.setBounds(10, 98, 89, 23);
		btnAdicionar.addActionListener(this);
		add(btnAdicionar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(108, 98, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		
	}
	
	@Override
	public void notificar(JSONObject packet) {
		notificado.serNotificado(packet);
	}
	@Override
	public INossoPanel getNext() {
		return next;
	}
	@Override
	public void parsePacket(JSONObject packet) {
		//
	}
	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Adicionar")){
			if(textField.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Eh preciso expecificar um nome para a equipe.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("nome", textField.getText());
			
			packet.put("addEquipe", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Voltar")){
			notificar(null);
		}
	}
}
