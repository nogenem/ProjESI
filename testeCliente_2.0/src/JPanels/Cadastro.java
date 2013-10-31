package JPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONObject;
import PP_Observer.Notificador;
import PP_Observer.Notificavel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Insets;

public class Cadastro extends JPanel implements ActionListener, INossoPanel, Notificador {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo

	private JTextField tfKey;
	private JTextField tfLogin;
	private JTextField tfNome;
	private JPasswordField pfSenha;

	/*
	 * Construtor que cria o conteudo do panel.
	 */
	public Cadastro(PrintWriter escritor){
		this.escritor = escritor;
		this.next = new Login(escritor); //Seta o next como o Login()

		setLayout(null);
		setSize(351, 339+30); //gambiarra hehe

		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCadastro.setBounds(10, 11, 92, 14);
		add(lblCadastro);

		JLabel lblLogin = new JLabel("Login: *");
		lblLogin.setBounds(10, 50, 46, 14);
		add(lblLogin);

		JLabel lblSenha = new JLabel("Senha: *");
		lblSenha.setBounds(10, 87, 78, 14);
		add(lblSenha);

		JLabel lblNome = new JLabel("Nome: *");
		lblNome.setBounds(10, 122, 46, 14);
		add(lblNome);

		JLabel lblAdministradoKey = new JLabel("Chave de administrador:");
		lblAdministradoKey.setToolTipText("");
		lblAdministradoKey.setBounds(10, 166, 145, 14);
		add(lblAdministradoKey);

		tfKey = new JTextField();
		lblLogin.setLabelFor(tfKey);
		tfKey.setBounds(165, 163, 165, 20);
		add(tfKey);
		tfKey.setColumns(10);

		tfLogin = new JTextField();
		lblSenha.setLabelFor(tfLogin);
		tfLogin.setBounds(165, 47, 165, 20);
		add(tfLogin);
		tfLogin.setColumns(10);

		tfNome = new JTextField();
		lblAdministradoKey.setLabelFor(tfNome);
		tfNome.setBounds(165, 119, 165, 20);
		add(tfNome);
		tfNome.setColumns(10);

		/* Todas essas labels aki sao uma bela gambiarra kk'
		 * aceito ideias para deixar melhor isso sei la ^^
		 */
		JLabel lblNotaCamposCom = new JLabel("Nota: Campos com * s\u00E3o obrigat\u00F3rios.");
		lblNotaCamposCom.setBounds(10, 208, 260, 14);
		add(lblNotaCamposCom);

		JLabel lblNotaAChave = new JLabel("Nota: A chave de administrador s\u00F3 deve ser colocado");
		lblNotaAChave.setBounds(10, 229, 320, 20);
		add(lblNotaAChave);

		JLabel lblColocadaCasoVoc = new JLabel("caso voc\u00EA possua a chave correta.");
		lblColocadaCasoVoc.setBounds(38, 249, 292, 14);
		add(lblColocadaCasoVoc);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrar.setBounds(57, 294, 89, 23);
		btnCadastrar.addActionListener(this);
		add(btnCadastrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setMargin(new Insets(0, 0, 0, 0));
		btnCancelar.setBounds(203, 294, 89, 23);
		btnCancelar.addActionListener(this);
		add(btnCancelar);

		pfSenha = new JPasswordField();
		pfSenha.setBounds(165, 84, 165, 20);
		add(pfSenha);
	}

	@Override
	public INossoPanel getNext() {
		return next;
	}

	@Override
	public void parsePacket(JSONObject packet) {
		//nao sera implementado nd aki
	}

	@Override
	public void setarNotificavel(Notificavel notificavel) {
		this.notificado = notificavel;
	}

	@Override
	public void notificar(JSONObject packet) { //Notifica a GUI para mudar para o proximo conteudo
		notificado.serNotificado(packet);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Cadastrar")){

			if(tfLogin.getText().equals("") || tfNome.getText().equals("") ||
					new String(pfSenha.getPassword()).equals("")){
				JOptionPane.showMessageDialog(this, "Algum campo obrigatorio nao foi preenchido.");
				return;
			}

			JSONObject packet = new JSONObject();

			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("login", tfLogin.getText());
			tmp.put("senha", new String(pfSenha.getPassword()));
			tmp.put("nome", tfNome.getText());
			tmp.put("key", tfKey.getText());

			packet.put("cadastrar", tmp);

			escritor.println(packet.toString());
			escritor.flush();
		}else if(arg0.getActionCommand().equals("Cancelar")){
			notificar(null); //Faz mudar para o next, q sera o login
		}
	}
}
