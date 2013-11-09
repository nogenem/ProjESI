package JPanels;

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

@SuppressWarnings("serial")
public class ListaMembros extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo.
	
	private String equipeName; //Nome da equipe q essa lista pertence.
	private String newMemberLogin; //Login do novo membro que sera adicionado a equipe.
	private String removedMemberLogin; //Login do membro que sera removido da equipe.
	
	private JList<String> list;
	private DefaultListModel<String> listaMembros = new DefaultListModel<>();  
	
	public ListaMembros(PrintWriter escritor, String equipeName){
		this.escritor = escritor;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(265+5, 360+25);
		
		JLabel lblListaDeMembros = new JLabel("Lista de membros:");
		lblListaDeMembros.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDeMembros.setBounds(10, 11, 128, 24);
		add(lblListaDeMembros);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 226, 184);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaMembros);  
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
		scrollPane.setViewportView(list);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setMargin(new Insets(0, 0, 0, 0));
		btnSelecionar.setBounds(20, 238, 89, 23);
		add(btnSelecionar);
		
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
		
		JButton btnAddMembro = new JButton("Add Membro");
		btnAddMembro.setMargin(new Insets(0, 0, 0, 0));
		btnAddMembro.setBounds(10, 25, 114, 23);
		btnAddMembro.addActionListener(this);
		panel.add(btnAddMembro);
		
		JButton btnRemoveMembro = new JButton("Remove Membro");
		btnRemoveMembro.setMargin(new Insets(0, 0, 0, 0));
		btnRemoveMembro.setBounds(127, 25, 110, 23);
		btnRemoveMembro.addActionListener(this);
		panel.add(btnRemoveMembro);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}
	
	@Override
	public void parsePacket(JSONObject packet) { //Trata os packets que vem do servidor.
		if(packet.has("lista")){
			JSONArray array = packet.getJSONArray("lista");
			if(array.length() == 0)
				return;
	
			for(int i = 0; i<array.length(); i++)
				listaMembros.addElement(array.get(i).toString());

			list.setSelectedIndex(0);
		}else if(packet.has("OK")){
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Usuario adicionado")){ //Caso o usuario tenha sido adicionado, eh preciso adicionar ele na lista.
				if(!listaMembros.contains(newMemberLogin)){
					listaMembros.addElement(newMemberLogin);
					list.setSelectedIndex(list.getLastVisibleIndex());
				}
			}else if(tmp.contains("Usuario removido")){ //Caso o usuario tenha sido removido, eh preciso retirar ele da lista.
				listaMembros.removeElement(removedMemberLogin);
				list.setSelectedIndex(list.getLastVisibleIndex());
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
		if(arg0.getActionCommand().equals("Selecionar")){
			//ver se eh realmente necessario
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaEquipes(escritor);
			
			JSONObject packet = new JSONObject();
			packet.put("listarEquipes", "");
			
        	escritor.println(packet.toString());
        	escritor.flush();
		}else if(arg0.getActionCommand().equals("Add Membro")){
			String login = JOptionPane.showInputDialog(this, "Informe o login do usuario a ser adicionado:");
			
			if(login == null)
				return;
			else if(login.equals("")){
				JOptionPane.showMessageDialog(this, "Voce deve informar o login do usuario.");
				return;
			}
			
			newMemberLogin = login; //Salva o login do usuario para ser adicionado na lista depois.
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("login", login);
			tmp.put("equipe", equipeName);
			
			packet.put("addMembro", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Remove Membro")){
			String login = list.getSelectedValue();
			
			if(login == null){
				JOptionPane.showMessageDialog(this, "Selecione um membro para ser removido.");
				return;
			}
			
			int resposta = JOptionPane.showConfirmDialog(this, "Voce tem certeza que deseja remover o usuario: "+
					login +"?");
			
			if(resposta == 0){
				removedMemberLogin = login; //Salva o login do usuario para ser removido da lista depois.
				JSONObject packet = new JSONObject();
				
				HashMap<String, String> tmp = new HashMap<>();
				tmp.put("login", login);
				tmp.put("equipe", equipeName);
				
				packet.put("removeMembro", tmp);
				
				escritor.println(packet.toString());
				escritor.flush();
			}
		}
	}
}
