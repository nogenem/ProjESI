package JPanels;

import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyEventPostProcessor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import PP_Observer.Notificador;
import PP_Observer.Notificavel;

public class Login extends JPanel implements ActionListener, INossoPanel, Notificador,FocusListener,KeyListener {

	private Notificavel notificado;
	private PrintWriter escritor;
	private INossoPanel next; //Proximo conteudo

	private JTextField tfLogin;
	private JPasswordField pfSenha;
	
	private JButton btnLogar = new JButton("Logar");

	public Login(PrintWriter escritor)
	{
		this.escritor = escritor;
		setLayout(null);
		setSize(188, 224+30); //gambiarra hehe

		JLabel lblLogar = new JLabel("Logar");
		lblLogar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogar.setBounds(10, 11, 46, 22);
		add(lblLogar);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(10, 55, 46, 14);
		add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 89, 46, 14);
		add(lblSenha);

		tfLogin = new JTextField();
		lblLogin.setLabelFor(tfLogin);
		tfLogin.setBounds(54, 52, 112, 20);
		add(tfLogin);
		tfLogin.setColumns(10);

		pfSenha = new JPasswordField();
		lblSenha.setLabelFor(pfSenha);
		pfSenha.setBounds(54, 86, 112, 20);
		pfSenha.addKeyListener(this);
		add(pfSenha);

		btnLogar.setMargin(new Insets(0, 0, 0, 0));
		btnLogar.setBounds(10, 127, 73, 23);
		btnLogar.addActionListener(this);
		add(btnLogar);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setMargin(new Insets(0, 0, 0, 0));
		btnFechar.setBounds(93, 127, 73, 23);
		btnFechar.addActionListener(this);
		add(btnFechar);

		JLabel lblNoTemConta = new JLabel("N\u00E3o tem conta ainda?"); //bunitin n? kk'
		lblNoTemConta.setBounds(10, 161, 156, 14);
		add(lblNoTemConta);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrar.setBounds(47, 186, 89, 23);
		btnCadastrar.addActionListener(this);
		add(btnCadastrar);
	}

	@Override
	public void actionPerformed( ActionEvent arg0 )
	{
		if( arg0.getActionCommand().equals( "Logar" ) )
		{
			next = new ListaEquipes(escritor); //editar aki
			
			JSONObject packet = new JSONObject();

			HashMap<String, String> tmp = new HashMap<>();
			tmp.put("login", tfLogin.getText());
			tmp.put("senha", new String(pfSenha.getPassword()));

			packet.put("logar", tmp);

			escritor.println(packet.toString());
			escritor.flush();
		}
		else if( arg0.getActionCommand().equals( "Fechar" ) )
		{
			JSONObject tmp = new JSONObject();
			tmp.put( "desconectar", "" );

			escritor.println( tmp.toString() );
			escritor.flush();

			System.exit(0);
		}
		else if( arg0.getActionCommand().equals( "Cadastrar" ) )
		{
			next = new Cadastro(escritor); //muda o next para o cadastro
			notificar(null); //notifica a GUI para mudar para o next, q sera o cadastro
		}
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
	public void keyPressed(KeyEvent arg0)
	{
		if( arg0.getComponent().equals( this.pfSenha ))
		{
			if( arg0.getKeyCode() == KeyEvent.VK_ENTER)
			{
				this.btnLogar.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}