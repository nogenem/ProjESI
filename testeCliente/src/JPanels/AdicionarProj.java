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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;

//ta funcionando mas ainda n tem o adicionarEquipe e nem o addMembro pra equipe entao... ^^
public class AdicionarProj extends JPanel implements ActionListener, INossoPanel, Notificador {
	
	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	private JTextField textField;
	
	public AdicionarProj(PrintWriter escritor){
		this.escritor = escritor;
		this.next = new MenuPrincipal(escritor);
		
		setLayout(null);
		setSize(225+5, 142+25);
		
		JLabel lblAdicionarProjetos = new JLabel("Adicionar Projeto");
		lblAdicionarProjetos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdicionarProjetos.setBounds(10, 11, 125, 17);
		add(lblAdicionarProjetos);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 53, 46, 14);
		add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(49, 50, 148, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setMargin(new Insets(0, 0, 0, 0));
		btnAdicionar.setBounds(10, 101, 89, 23);
		btnAdicionar.addActionListener(this);
		add(btnAdicionar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(120, 101, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
	}
	
	@Override
	public void notificar() {
		notificado.serNotificado();
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
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("nome", textField.getText());
			
			packet.put("addProj", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Voltar")){
			notificar();
		}
	}
}
