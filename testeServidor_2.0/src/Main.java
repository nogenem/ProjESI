import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONObject;
import Controle.Controle;
import Controle.Dados;

public class Main {

	private Dados dados;

	/*
	 * Construtor usado para inicializar a base de dados e
	 * ficar esperando clientes.
	 */
	public Main(){
		dados = new Dados();
		System.out.println("Servidor online...");

		ServerSocket server;
		try{
			server = new ServerSocket(5000);
			while(true){
				Socket socket = server.accept();
				new Thread(new EscutaCliente(socket, dados)).start();
			}
		}catch(Exception e){}
	}

	/*
	 * Classe responsavel por escutar as requisicoes do cliente.
	 */
	private class EscutaCliente implements Runnable{

		private Scanner leitor;
		private PrintWriter escritor;
		private Controle control;

		public EscutaCliente(Socket socket, Dados dados){
			try {
				leitor = new Scanner(socket.getInputStream());
				escritor = new PrintWriter(socket.getOutputStream());
				control = new Controle(dados);
			} catch (Exception e) {}
		}

		public void run() {
			try{
				String texto;
				JSONObject packet;

				while((texto = leitor.nextLine()) != null){
					try {
						System.out.println("Servidor: " +texto); //lembrar de tirar isso depois ^^
						packet = control.parsePacket(new JSONObject(texto));

						if(packet == null) //Quando o usuario manda o comando de 'desconectar'.
							return;

						escritor.println(packet.toString());
						escritor.flush();
					} catch(Exception e){e.printStackTrace();}
				}
			}catch(Exception e){}
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
