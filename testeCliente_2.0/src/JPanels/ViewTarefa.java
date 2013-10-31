package JPanels;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.json.JSONObject;

import Infos.InfoTarefa;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ViewTarefa extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private InfoTarefa info;
	private String equipeName;
	private String projName;
	
	private JTextField tfTitulo;
	private JTextArea taDescricao;
	private JTextArea taRecursos;
	private JFormattedTextField ftfInicio;	
	private JFormattedTextField ftfTermino;
	
	public ViewTarefa(PrintWriter escritor, InfoTarefa info, String equipeName, String projName){
		this.escritor = escritor;
		this.info = info;
		this.equipeName = equipeName;
		this.projName = projName;
		
		setLayout(null);
		setSize(282+5, 426+25);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 26, 57, 14);
		add(lblTitulo);
		
		tfTitulo = new JTextField();
		tfTitulo.setBounds(88, 23, 184, 20);
		add(tfTitulo);
		tfTitulo.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(12, 122, 79, 14);
		add(lblDescricao);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 137, 262, 120);
		add(scrollPane);
		
		taDescricao = new JTextArea();
		scrollPane.setViewportView(taDescricao);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setMargin(new Insets(0, 0, 0, 0));
		btnSalvar.setBounds(34, 392, 89, 23);
		btnSalvar.addActionListener(this);
		add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(157, 392, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		JLabel lblDataInicio = new JLabel("Data Inicio:");
		lblDataInicio.setBounds(8, 57, 67, 14);
		add(lblDataInicio);
		
		ftfInicio = new JFormattedTextField();
		ftfInicio.setBounds(88, 54, 148, 20);
		add(ftfInicio);
		
		JLabel lblDataTermino = new JLabel("Data Termino:");
		lblDataTermino.setBounds(10, 88, 79, 14);
		add(lblDataTermino);
		
		ftfTermino = new JFormattedTextField();
		ftfTermino.setBounds(88, 85, 148, 20);
		add(ftfTermino);
		
		JLabel lblRecursos = new JLabel("Recursos:");
		lblRecursos.setBounds(10, 277, 65, 14);
		add(lblRecursos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 297, 262, 84);
		add(scrollPane_1);
		
		taRecursos = new JTextArea();
		scrollPane_1.setViewportView(taRecursos);
		
		MaskFormatter maskInicio;
		MaskFormatter maskTermino;
		try {
			maskInicio = new MaskFormatter("##/##/####");
			maskInicio.setValidCharacters("0123456789");
			maskInicio.setValueContainsLiteralCharacters(false);
			maskInicio.install(ftfInicio);
			
			maskTermino = new MaskFormatter("##/##/####");
			maskTermino.setValidCharacters("0123456789");
			maskTermino.setValueContainsLiteralCharacters(false);
			maskTermino.install(ftfTermino);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(this.info != null){
			tfTitulo.setText(this.info.getTitulo());
			ftfInicio.setText(this.info.getDataInicio());
			ftfTermino.setText(this.info.getDataTermino());
			taDescricao.setText(this.info.getDescricao());
			taRecursos.setText(this.info.getRecursos());
			tfTitulo.setEditable(false);
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
		if(packet != null && packet.has("OK")){ //confirmacao q uma tarefa foi adicionada/editada com sucesso
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Tarefa adicionada") || tmp.contains("Tarefa modificada")){
				listaProjetos();
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
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", tfTitulo.getText());
			tmp.put("descricao", taDescricao.getText());
			tmp.put("dataInicio", ftfInicio.getText());
			tmp.put("dataTermino", ftfTermino.getText());
			tmp.put("recursos", taRecursos.getText());
			tmp.put("projName", projName);
			tmp.put("equipe", equipeName);
			
			if(info == null)
				packet.put("addTarefa", tmp);
			else
				packet.put("editTarefa", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			listaProjetos();
		}
	}
	
	public void listaProjetos(){
		next = new ListaTarefas(escritor, equipeName, projName);
		
		JSONObject packet = new JSONObject();
		
		HashMap<String, String> tmp = new HashMap<>();
		tmp.put("equipe", equipeName);
		tmp.put("projName", projName);
		
		packet.put("listarTarefas", tmp);
		
		escritor.println(packet.toString());
		escritor.flush();
	}
}
