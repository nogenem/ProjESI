package JPanels;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import Infos.InfoArquivo;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ViewFile extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private InfoArquivo info;
	private String equipeName;
	
	private JTextField textField;
	private JTextArea textArea;
															                   
	public ViewFile(PrintWriter escritor, InfoArquivo info, String equipeName){
		this.escritor = escritor;
		this.info = info;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(244+10, 263+35);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 26, 57, 14);
		add(lblTitulo);
		
		textField = new JTextField();
		textField.setBounds(50, 23, 184, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblConteudo = new JLabel("Conteudo:");
		lblConteudo.setBounds(10, 64, 79, 14);
		add(lblConteudo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 79, 226, 140);
		add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setMargin(new Insets(0, 0, 0, 0));
		btnSalvar.setBounds(10, 230, 89, 23);
		btnSalvar.addActionListener(this);
		add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(145, 230, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		if(this.info != null){
			textField.setText(this.info.getTitulo());
			textArea.setText(this.info.getConteudo());
			textField.setEditable(false);
		}
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
		if(packet != null && packet.has("OK")){ //confirmacao q um arquivo foi adicionada/editado com sucesso
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Arquivo adicionado") || tmp.contains("Arquivo modificado")){
				listaArquivos();
			}
		}
	}
	
	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Salvar")){
			
			if(info != null && info.getConteudo().equals(textArea.getText())){
				listaArquivos();  //caso o usuario nao tenha modificado nd no arquivo
				return;
			}

			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", textField.getText());
			tmp.put("conteudo", textArea.getText());
			tmp.put("equipe", equipeName);
			
			if(info == null)
				packet.put("addArquivo", tmp);
			else
				packet.put("editArquivo", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			listaArquivos();
		}
	}
	
	public void listaArquivos(){
		next = new ListaArquivos(escritor, equipeName);
		
		JSONObject packet = new JSONObject();
		
		HashMap<String, String> tmp = new HashMap<>();
		tmp.put("equipe", equipeName);
		
		packet.put("listarArquivos", tmp);
		
		escritor.println(packet.toString());
		escritor.flush();
	}
}
