package JPanels;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Infos.InfoArquivo;
import Infos.InfoTarefa;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ListaTarefas extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private String equipeName;//nome da equipe q essa lista pertence
	private String projName;//nome do projeto q essa lista pertence
	
	private String removedTaskName;//nome da tarefa que sera removido do projeto
	
	private JList<String> list;
	private DefaultListModel<String> listaTarefas = new DefaultListModel<>();  
	
	public ListaTarefas(PrintWriter escritor, String equipeName, String projName){
		this.escritor = escritor;
		this.equipeName = equipeName;
		this.projName = projName;
		
		setLayout(null);
		setSize(270, 364+29);
		
		JLabel lblListaDeTarefas = new JLabel("Lista de tarefas:");
		lblListaDeTarefas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDeTarefas.setBounds(10, 11, 128, 24);
		add(lblListaDeTarefas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 226, 184);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaTarefas);  
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
		scrollPane.setViewportView(list);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.setMargin(new Insets(0, 0, 0, 0));
		btnVisualizar.setBounds(20, 238, 89, 23);
		btnVisualizar.addActionListener(this);
		add(btnVisualizar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(157, 238, 89, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 286, 247, 64);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblPainelDeAdm = new JLabel("Painel de ADM:");
		lblPainelDeAdm.setBounds(10, 0, 85, 14);
		panel.add(lblPainelDeAdm);
		
		JButton btnAddTarefa = new JButton("Add Tarefa");
		btnAddTarefa.setMargin(new Insets(0, 0, 0, 0));
		btnAddTarefa.setBounds(10, 25, 114, 23);
		btnAddTarefa.addActionListener(this);
		panel.add(btnAddTarefa);
		
		JButton btnRemoveTarefa = new JButton("Remove Tarefa");
		btnRemoveTarefa.setMargin(new Insets(0, 0, 0, 0));
		btnRemoveTarefa.setBounds(127, 25, 110, 23);
		btnRemoveTarefa.addActionListener(this);
		panel.add(btnRemoveTarefa);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}

	@Override
	public void parsePacket(JSONObject packet) {
		if(packet.has("lista")){
			JSONArray array = packet.getJSONArray("lista");
			if(array.length() == 0)
				return;
	
			for(int i = 0; i<array.length(); i++)
				listaTarefas.addElement(array.get(i).toString());

			list.setSelectedIndex(0);
		}else if(packet.has("OK")){
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Tarefa removida")){
				listaTarefas.removeElement(removedTaskName);
				list.setSelectedIndex(list.getLastVisibleIndex());
			}
		}else if(packet.has("view")){
			JSONObject tmp = packet.getJSONObject("view");
			
			InfoTarefa info = new InfoTarefa(tmp.getString("titulo"), tmp.getString("descricao"), tmp.getString("dataInicio"),
											 tmp.getString("dataTermino"), tmp.getString("recursos"));
			
			next = new ViewTarefa(escritor, info, equipeName, projName);
			notificar(null);
		}
	}

	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}

	@Override
	public void notificar(JSONObject packet) {
		notificado.serNotificado(packet);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Visualizar")){
			String taskName = list.getSelectedValue();
			
			if(taskName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar uma tarefa antes.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", taskName);
			tmp.put("equipe", equipeName);
			tmp.put("projName", projName);
			
			packet.put("viewTarefa", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaProjs(escritor, equipeName);
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("equipe", equipeName);
			
			packet.put("listarProjetos", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Add Tarefa")){
			next = new ViewTarefa(escritor, null, equipeName, projName);
			
			notificar(null);
		}else if(arg0.getActionCommand().equals("Remove Tarefa")){
			String TaskName = list.getSelectedValue();
			
			if(TaskName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar uma tarefa antes.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			removedTaskName = TaskName;
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", TaskName);
			tmp.put("projName", projName);
			tmp.put("equipe", equipeName);
			
			packet.put("removeTarefa", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}
	}
}
