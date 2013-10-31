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
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class ListaArquivos extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo
	
	private String equipeName;//nome da equipe q essa lista pertence
	
	private String removedFileName;//nome do arquivo que sera removido do projeto
	
	private JList<String> list;
	private DefaultListModel<String> listaArquivos = new DefaultListModel<>();  
	
	public ListaArquivos(PrintWriter escritor, String equipeName){
		this.escritor = escritor;
		this.equipeName = equipeName;
		
		setLayout(null);
		setSize(270, 364+29);
		
		JLabel lblListaDeArquivos = new JLabel("Lista de arquivos:");
		lblListaDeArquivos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDeArquivos.setBounds(10, 11, 128, 24);
		add(lblListaDeArquivos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 226, 184);
		add(scrollPane);
		
		list = new JList<>();
		list.setModel(listaArquivos);  
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
		
		JButton btnAddArquivo = new JButton("Add Arquivo");
		btnAddArquivo.setMargin(new Insets(0, 0, 0, 0));
		btnAddArquivo.setBounds(10, 25, 114, 23);
		btnAddArquivo.addActionListener(this);
		panel.add(btnAddArquivo);
		
		JButton btnRemoveArquivo = new JButton("Remove Arquivo");
		btnRemoveArquivo.setMargin(new Insets(0, 0, 0, 0));
		btnRemoveArquivo.setBounds(127, 25, 110, 23);
		btnRemoveArquivo.addActionListener(this);
		panel.add(btnRemoveArquivo);
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
				listaArquivos.addElement(array.get(i).toString());

			list.setSelectedIndex(0);
		}else if(packet.has("OK")){
			String tmp = packet.getString("OK");
			
			if(tmp.contains("Arquivo removido")){
				listaArquivos.removeElement(removedFileName);
				list.setSelectedIndex(list.getLastVisibleIndex());
			}
		}else if(packet.has("view")){
			JSONObject tmp = packet.getJSONObject("view");
			
			InfoArquivo file = new InfoArquivo(tmp.getString("titulo"), tmp.getString("conteudo"));
			
			next = new ViewFile(escritor, file, equipeName);
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
			String fileName = list.getSelectedValue();
			
			if(fileName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar um arquivo antes.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", fileName);
			tmp.put("equipe", equipeName);
			
			packet.put("viewArquivo", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new ListaEquipes(escritor);
			
			JSONObject packet = new JSONObject();
			packet.put("listarEquipes", "");
			
			escritor.println(packet.toString());
			escritor.flush();
			
		}else if(arg0.getActionCommand().equals("Add Arquivo")){
			next = new ViewFile(escritor, null, equipeName);
			
			notificar(null);
		}else if(arg0.getActionCommand().equals("Remove Arquivo")){
			String fileName = list.getSelectedValue();
			
			if(fileName == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar um arquivo antes.");
				return;
			}
			
			JSONObject packet = new JSONObject();
			removedFileName = fileName;
			
			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("titulo", fileName);
			tmp.put("equipe", equipeName);
			
			packet.put("removeArquivo", tmp);
			
			escritor.println(packet.toString());
			escritor.flush();
		}
	}
}
