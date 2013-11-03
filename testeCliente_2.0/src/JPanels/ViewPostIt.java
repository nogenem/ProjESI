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
import Infos.InfoPostIt;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ViewPostIt extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo.
	
	private InfoPostIt info;
	private String equipeName; //Nome da equipe para qual esse post-it pertence.	
	
	private JTextField tfTitulo;
	private JTextArea taConteudo;
	private JTextField tfEmissor;
															                   
	public ViewPostIt(PrintWriter escritor, InfoPostIt info, String equipeName){
		this.escritor = escritor;
		this.info = info;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(243+5, 295+25);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 26, 57, 14);
		add(lblTitulo);
		
		tfTitulo = new JTextField();
		tfTitulo.setBounds(60, 23, 174, 20);
		add(tfTitulo);
		tfTitulo.setColumns(10);
		
		JLabel lblConteudo = new JLabel("Conteudo:");
		lblConteudo.setBounds(12, 86, 79, 14);
		add(lblConteudo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 101, 226, 140);
		add(scrollPane);
		
		taConteudo = new JTextArea();
		scrollPane.setViewportView(taConteudo);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setMargin(new Insets(0, 0, 0, 0));
		btnSalvar.setBounds(10, 262, 89, 23);
		btnSalvar.addActionListener(this);
		add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(145, 262, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		JLabel lblEmissor = new JLabel("Emissor:");
		lblEmissor.setBounds(8, 58, 57, 14);
		add(lblEmissor);
		
		tfEmissor = new JTextField();
		tfEmissor.setBounds(60, 55, 174, 20);
		tfEmissor.setColumns(10);
		add(tfEmissor);
		
		if(this.info != null){
			tfTitulo.setText(this.info.getTitulo());
			tfEmissor.setText(this.info.getEmissor());
			taConteudo.setText(this.info.getConteudo());
			tfTitulo.setEditable(false);
		}
		tfEmissor.setEditable(false);
	}
	
	@Override
	public INossoPanel getNext() {
		return next;
	}
	
	@Override
	public void parsePacket(JSONObject packet) { //Trata os packets que vem do servidor.
		if(packet != null && packet.has("OK")){ 
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Post-it adicionado") || tmp.contains("Post-it modificado")){
				listaPostIts(); //Caso o post-it tenha sido modificado/adicionado com sucesso, deve-se voltar para a lista de post-its.
			}
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
			
			if(info != null && info.getConteudo().equals(taConteudo.getText())){
				listaPostIts();  //Caso o usuario nao tenha modificado nada no post-it.
				return;
			}

			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", tfTitulo.getText());
			tmp.put("conteudo", taConteudo.getText());
			tmp.put("equipe", equipeName);
			
			if(info == null)
				packet.put("addPostIt", tmp);
			else
				packet.put("editPostIt", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			listaPostIts();
		}
	}
	
	public void listaPostIts(){
		next = new ListaPostIt(escritor, equipeName);
		
		JSONObject packet = new JSONObject();
		
		HashMap<String, String> tmp = new HashMap<>();
		tmp.put("equipe", equipeName);
		
		packet.put("listarPostIts", tmp);
		
		escritor.println(packet.toString());
		escritor.flush();
	}
}
