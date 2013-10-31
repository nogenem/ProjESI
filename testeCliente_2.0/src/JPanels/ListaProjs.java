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

import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ListaProjs extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private String equipeName;//nome da equipe q o projeto esta associado
	private String newProjName;//nome do novo projeto a ser adicionado
	private String removedProjName;//nome do projeto que sera removido
	
	private JList<String> list;
	private DefaultListModel<String> listaProjs = new DefaultListModel<>();  
	
	public ListaProjs(PrintWriter escritor, String equipeName){
		this.escritor = escritor;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(264+15, 342+30);
		
		JLabel lblListaDeProjs = new JLabel("Lista de projetos:");
		lblListaDeProjs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDeProjs.setBounds(10, 11, 128, 24);
		add(lblListaDeProjs);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 226, 184);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaProjs);  
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
		scrollPane.setViewportView(list);
		
		JButton btnListTarefas = new JButton("Listar Tarefas");
		btnListTarefas.setMargin(new Insets(0, 0, 0, 0));
		btnListTarefas.setBounds(20, 238, 104, 23);
		btnListTarefas.addActionListener(this);
		add(btnListTarefas);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(142, 238, 104, 23);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 272, 247, 59);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblPainelDeAdm = new JLabel("Painel de ADM:");
		lblPainelDeAdm.setBounds(10, 0, 85, 14);
		panel.add(lblPainelDeAdm);
		
		JButton btnAddProj = new JButton("Add Projeto");
		btnAddProj.setMargin(new Insets(0, 0, 0, 0));
		btnAddProj.setBounds(6, 25, 114, 23);
		btnAddProj.addActionListener(this);
		panel.add(btnAddProj);
		
		JButton btnRemoveProj = new JButton("Remove Projeto");
		btnRemoveProj.setMargin(new Insets(0, 0, 0, 0));
		btnRemoveProj.setBounds(127, 25, 110, 23);
		btnRemoveProj.addActionListener(this);
		panel.add(btnRemoveProj);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}

	@Override
	public void parsePacket(JSONObject packet) {
		if(packet.has("lista")){ //lista de projetos vinda do servidor
			JSONArray array = packet.getJSONArray("lista");
			if(array.length() == 0)
				return;
	
			for(int i = 0; i<array.length(); i++)
				listaProjs.addElement(array.get(i).toString());

			list.setSelectedIndex(0);
		}else if(packet.has("OK")){ //confirmacao q um prjeto foi adicionado/removido com sucesso
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Projeto adicionado")){//ser for adicionado novo proj, add nova label na lista
				listaProjs.addElement(newProjName);
				list.setSelectedIndex(list.getLastVisibleIndex());
			}else if(tmp.contains("Projeto removido")){//ser for removido um proj, remove uma label da lista
				listaProjs.removeElement(removedProjName);
				list.setSelectedIndex(list.getLastVisibleIndex());
			}
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
		if(arg0.getActionCommand().equals("Listar Tarefas")){
			String projName = list.getSelectedValue();
			
			if(projName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar um projeto antes.");
				return;
			}
			
			next = new ListaTarefas(escritor, equipeName, projName);
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("equipe", equipeName);
			tmp.put("projName", projName);
			
			packet.put("listarTarefas", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaEquipes(escritor);
			
			JSONObject packet = new JSONObject();
			packet.put("listarEquipes", "");
			
        	escritor.println(packet.toString());
        	escritor.flush();
		}else if(arg0.getActionCommand().equals("Add Projeto")){
			String projName = JOptionPane.showInputDialog(this, "Informe o nome do novo projeto:");
			
			if(projName == null)
				return;
			else if(projName.equals("")){
				JOptionPane.showMessageDialog(this, "Voce deve informar um nome para o novo projeto.");
				return;
			}
			
			newProjName = projName;
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("nome", projName);
			tmp.put("equipe", equipeName);
			
			packet.put("addProj", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Remove Projeto")){
			String toRemove = list.getSelectedValue();
			
			if(toRemove == null){
				JOptionPane.showMessageDialog(this, "Selecione um projeto antes.");
				return;
			}
			
			int resposta = JOptionPane.showConfirmDialog(this, "Voce tem certeza que deseja remover o projeto: "+
														toRemove +"?");
			if(resposta == 0){
				removedProjName = toRemove;
				JSONObject packet = new JSONObject();
				
				HashMap<String, String> tmp = new HashMap<>();
				tmp.put("nome", toRemove);
				tmp.put("equipe", equipeName);
				
				packet.put("removeProj", tmp);
				
				escritor.println(packet.toString());
				escritor.flush();
			}
		}
	}
}
