import java.awt.Component;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

	private PrintWriter escritor;
	private Socket socket;
	private Scanner leitor;
	private GUI gui;
	
	public Main(){
		configuraRede();
		gui = new GUI(escritor);
	}
	
	/*
	 * Funcao para inicializar a comunicacao.
	 * Caso o servidor nao esteja online ira gerar uma excecao que sera tratada pelo catch.
	 */
	public void configuraRede()
	{
		try {
			socket = new Socket("127.0.0.1", 5000);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new EscutaServidor()).start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Servidor offline.");
			System.exit(0);
		}
	}
	
	/*
	 * Funcao responsavel por escutar as respostas do servidor.
	 */
	private class EscutaServidor implements Runnable{

		public void run() {
			try{
				String texto;
				while((texto = leitor.nextLine()) != null){
					System.out.println("Cliente: "+texto); //lembrar de tirar isso depois ^^
					parsePacket(new JSONObject(texto));
				}
			}catch(Exception e){}
		}
	}
	
	/*
	 * Funcao responsavel pelo primeiro tratamento das informacoes vindas do servidor.
	 */
	public void parsePacket(JSONObject packet){
		if(packet.has("err")){
			String erro = packet.get("err").toString();
			JOptionPane.showMessageDialog(gui.getFrame(), erro);
		}else if(packet.has("OK")){
			String msg = packet.getString("OK");
			
			if(!msg.equals(""))
				JOptionPane.showMessageDialog(gui.getFrame(), msg);
			
			gui.changeConteudo(packet);
		}else if(packet.has("lista") || packet.has("view")){
			gui.changeConteudo(packet);
		}
			
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
