package JPanels;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class EscolheEquipe extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo

	private JList list;

	public EscolheEquipe(PrintWriter escritor, INossoPanel next){
		this.escritor = escritor;
		this.next = next;

		setLayout(null);
		setSize(211+5, 242+25);

		JLabel lblEscolheEquipe = new JLabel("Escolhe equipe");
		lblEscolheEquipe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEscolheEquipe.setBounds(10, 11, 128, 22);
		add(lblEscolheEquipe);

		JLabel lblEquipes = new JLabel("Equipes:");
		lblEquipes.setBounds(10, 50, 63, 14);
		add(lblEquipes);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 183, 127);
		add(scrollPane);

		list = new JList();
		scrollPane.setViewportView(list);

		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setMargin(new Insets(0, 0, 0, 0));
		btnContinuar.setBounds(10, 204, 89, 23);
		btnContinuar.addActionListener(this);
		add(btnContinuar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setMargin(new Insets(0, 0, 0, 0));
		btnVoltar.setBounds(109, 204, 89, 23);
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
		JSONArray array = packet.getJSONArray("lista");
		String[] lista = new String[array.length()];

		for(int i = 0; i<array.length(); i++){
			lista[i] = array.get(i).toString();
		}
		list.setListData(lista);
		list.setSelectedIndex(0);
	}

	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Continuar")){
			if(list.getSelectedValue() == null){
				JOptionPane.showMessageDialog(this, "Eh preciso selecionar alguma equipe.");
			}

			String equipe = list.getSelectedValue().toString();

			JSONObject packet = new JSONObject();
			packet.put("equipe", equipe);

			notificar(packet);

		}else if(arg0.getActionCommand().equals("Voltar")){
			next = new MenuPrincipal(escritor);

			notificar(null);
		}
	}
}
