package JPanels;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class AdicionarProj extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo

	private String equipe;
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
	public void notificar(JSONObject packet) {
		notificado.serNotificado(packet);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}

	@Override
	public void parsePacket(JSONObject packet) {
		if(packet != null && packet.has("equipe")){
			this.equipe = packet.getString("equipe");
		}
	}

	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Adicionar")){
			if(textField.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Eh preciso expecificar um nome para o projeto.");
				return;
			}

			JSONObject packet = new JSONObject();

			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("nome", textField.getText());
			tmp.put("equipe", equipe);

			packet.put("addProj", tmp);

			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Voltar")){
			notificar(null);
		}
	}
}
