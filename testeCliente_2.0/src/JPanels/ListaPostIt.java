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
import Infos.InfoPostIt;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

@SuppressWarnings("serial")
public class ListaPostIt extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo.
	
	private String equipeName; //Nome da equipe para qual essa lista pertence.
	private String removedPostItName; //Nome do post-it que sera removido da equipe.
	
	private JList<String> list;
	private DefaultListModel<String> listaPostIts = new DefaultListModel<>();  
	
	public ListaPostIt(PrintWriter escritor, String equipeName){
		this.escritor = escritor;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(270, 364+29);
		
		JLabel lblListaDePostIts = new JLabel("Lista de post-its:");
		lblListaDePostIts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDePostIts.setBounds(10, 11, 128, 24);
		add(lblListaDePostIts);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 226, 184);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaPostIts);  
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
		
		JButton btnAddPostIt = new JButton("Add Post-It");
		btnAddPostIt.setMargin(new Insets(0, 0, 0, 0));
		btnAddPostIt.setBounds(10, 25, 114, 23);
		btnAddPostIt.addActionListener(this);
		panel.add(btnAddPostIt);
		
		JButton btnRemovePostIt = new JButton("Remove Post-It");
		btnRemovePostIt.setMargin(new Insets(0, 0, 0, 0));
		btnRemovePostIt.setBounds(127, 25, 110, 23);
		btnRemovePostIt.addActionListener(this);
		panel.add(btnRemovePostIt);
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
				listaPostIts.addElement(array.get(i).toString());

			list.setSelectedIndex(0);
		}else if(packet.has("OK")){
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Post-it removido")){ //Caso o post-it tenha sido removido, eh preciso retirar ele da lista.
				listaPostIts.removeElement(removedPostItName);
				list.setSelectedIndex(list.getLastVisibleIndex());
			}
		}else if(packet.has("view")){
			JSONObject tmp = packet.getJSONObject("view");
			InfoPostIt postIt = new InfoPostIt(tmp.getString("titulo"), tmp.getString("conteudo"), tmp.getString("emissor"));
			
			next = new ViewPostIt(escritor, postIt, equipeName);
			notificar(null);
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
		if(arg0.getActionCommand().equals("Visualizar")){
			String postItName = list.getSelectedValue();
			
			if(postItName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar um post-it antes.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", postItName);
			tmp.put("equipe", equipeName);
			
			packet.put("viewPostIt", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaEquipes(escritor);
			
			JSONObject packet = new JSONObject();
			packet.put("listarEquipes", "");
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Add Post-It")){
			next = new ViewPostIt(escritor, null, equipeName);
			
			notificar(null);
		}else if(arg0.getActionCommand().equals("Remove Post-It")){
			String postItName = list.getSelectedValue();
			
			if(postItName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar um post-it antes.");
				return;
			}
			
			int resposta = JOptionPane.showConfirmDialog(this, "Voce tem certeza que deseja remover o post-it: "+
					postItName +"?");
			
			if(resposta == 0){
				removedPostItName = postItName; //Salva o nome da equipe para ser removida da lista depois.
				JSONObject packet = new JSONObject();
				
				HashMap<String, String> tmp = new HashMap<>();
				tmp.put("titulo", postItName);
				tmp.put("equipe", equipeName);
				
				packet.put("removePostIt", tmp);
				
				escritor.println(packet.toString());
				escritor.flush();
			}
		}
	}
}
