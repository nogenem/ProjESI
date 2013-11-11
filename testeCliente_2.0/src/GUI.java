import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.json.JSONObject;
import JPanels.INossoPanel;
import JPanels.Login;
import PP_Observer.Notificavel;

public class GUI implements Notificavel {
	
	private JFrame frmCliente;
	private PrintWriter escritor;
	private INossoPanel current;
	
	/*
	 * Construtor para inicializar a window e mudar o conteudo dela.
	 */
	public GUI(PrintWriter escritor){
		this.escritor = escritor;
		
		frmCliente = new JFrame();
		frmCliente.setTitle("Cliente");
		frmCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCliente.addWindowListener(new MyWindowAdapter(this));
		frmCliente.setResizable(false);
		
		setConteudo(new Login(escritor), null);
		frmCliente.setVisible(true);
	}
	
	public JFrame getFrame(){
		return frmCliente;
	}
	
	public PrintWriter getEscritor(){
		return escritor;
	}
	
	/*
	 * Evento que ocorrera quando a window for fechada.
	 */
	class MyWindowAdapter extends WindowAdapter{
        
        GUI myWindow = null;
      
        MyWindowAdapter(GUI myWindow){
        	this.myWindow = myWindow;
        }
       
        public void windowClosing(WindowEvent e) {
        	JSONObject packet = new JSONObject();
        	packet.put("desconectar", "");/* Usado para setar o 'inOn' do usuario, 
        									 para ele n ser mais considerado como online. */
        	
        	myWindow.getEscritor().println(packet.toString());
        	myWindow.getEscritor().flush();
        	myWindow.getEscritor().close();
        }
	}
	
	/*
	 * Muda o conteudo da janela.
	 */
	public void setConteudo(INossoPanel panel, JSONObject packet){
		if(panel == null){
			current.parsePacket(packet);
			return;
		}
		if(current != null)
			frmCliente.remove((Component) current);
		
		current = panel;
		current.parsePacket(packet);
		current.setarNotificavel(this);
		JPanel p = (JPanel) current;
		frmCliente.getContentPane().add(p);
		frmCliente.setSize(p.getSize());
	}
	
	/*
	 * Muda para o proximo conteudo.
	 */
	public void changeConteudo(JSONObject packet){
		setConteudo(current.getNext(), packet);
	}
	
	/*
	 * Funcao do padrao de projeto Observador.
	 */
	@Override
	public void serNotificado(JSONObject packet) {
		changeConteudo(packet);
	}
}
