package Controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;
import Modelo.Sessao.SessaoAbstrata;

public class Controle {
	
	private Dados dados;
	private SessaoAbstrata sessao;
	
	public Controle(Dados dados){
		this.dados = dados;
	}
	
	/*
	 * Retorna a key para criaçao de adms.
	 */
	public String getAdmKey(){
		return dados.getAdmKey();
	}
	
	/*
	 * Classe responsavel para interpretar as requisicoes dos clientes.
	 */
	public JSONObject parsePacket(JSONObject packet){
		if(packet.has("logar"))
			return efetuarLogin(packet);
		else if(packet.has("cadastrar"))
			return cadastrarUsuario(packet);
		else if(packet.has("desconectar"))
			return desconectar(packet);
		else if(packet.has("listarEquipes")) 
			return listarEquipes(packet);
		else if(packet.has("listarMembros")) 
			return listarMembros(packet);
		else if(packet.has("listarUsuarios")) //eh preciso?
			return listarUsuarios(packet);
		else if(packet.has("listarTarefas"))
			return listarTarefas(packet);
		else if(packet.has("listarProjetos"))
			return listarProjetos(packet);
		else if(packet.has("listarArquivos"))
			return listarArquivos(packet);
		else if(packet.has("addArquivo"))
			return adicionarArquivo(packet);
		else if(packet.has("removeArquivo"))
			return removerArquivo(packet);
		else if(packet.has("viewArquivo"))
			return visualizarArquivo(packet);
		else if(packet.has("editArquivo"))
			return modificarArquivo(packet);
		else if(packet.has("addTarefa"))
			return adicionarTarefa(packet);
		else if(packet.has("removeTarefa"))
			return removerTarefa(packet);
		else if(packet.has("viewTarefa"))
			return visualizarTarefa(packet);
		else if(packet.has("editTarefa"))
			return modificarTarefa(packet);
		else if(packet.has("addMembro"))
			return adicionarMembro(packet);
		else if(packet.has("removeMembro"))
			return removerMembro(packet);
		else if(packet.has("addProj"))
			return adicionarProjeto(packet);
		else if(packet.has("removeProj"))
			return removerProjeto(packet);
		else if(packet.has("addEquipe"))
			return adicionarEquipe(packet);
		else if(packet.has("removeEquipe"))
			return removerEquipe(packet);
		else if(packet.has("changeNivel"))
			return modificarNivel(packet);
		
		return null;
	}
	
	public JSONObject efetuarLogin(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("logar");
		String login = tmp.get("login").toString();
		String senha = tmp.get("senha").toString();
		
		try{
			Usuario user = dados.efetuarLogin(login, senha);
			sessao = dados.criarSessao(user);
		}catch(Exception e){
			packet = new JSONObject();
			packet.put("err", e.getMessage());
			
			return packet;
		}
		return listarEquipes(null); //ja retorna a lista de equipes
	}
	
	public JSONObject cadastrarUsuario(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("cadastrar");		
		/*
		 * Verifica se a key de adm vinda do cliente eh a correta.
		 * Caso seja, seta o novo usuario como adm, se n, seta como user normal
		 */		
		int nivel = tmp.getString("key").equals(getAdmKey()) ? 0 : 1; //Retornar erro caso entre com a key errada?
		
		Usuario user = new Usuario(tmp.getString("login"), tmp.getString("senha"), 
								   tmp.getString("nome"), nivel, false);
		
		packet = new JSONObject();
		try {
			dados.cadastrarUsuario(user);
			packet.put("OK", "Cadastro efetuado com sucesso!");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject desconectar(JSONObject packet){
		try{
			sessao.getUser().desconectar(); //Por enquanto soh muda o 'isOn' da classe usuario.
		}catch(Exception e){}
		
		if(packet.getString("desconectar").equals("sair")){//quando clicar no botao sair do menu principal
			packet = new JSONObject();
			packet.put("OK", "");
			
			return packet;
		}
		
		return null; //Retorna null para cancelar a thread desse usuario
	}
	
	public JSONObject listarTarefas(JSONObject packet){ //refazer depois
		packet = new JSONObject();
		try {
			Set<String> tarefas = sessao.listarTarefas("");
			packet.put("lista", tarefas);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarProjetos(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("listarProjetos");
		String equipeName = tmp.get("equipe").toString();
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			Set<String> projetos = sessao.listarProjetos(equipe);
			packet.put("lista", projetos);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarArquivos(JSONObject packet){ //refazer isso
		JSONObject tmp = packet.getJSONObject("listarArquivos");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			Set<String> arquivos = sessao.listarArquivos(equipe);
			packet.put("lista", arquivos);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarMembros(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("listarMembros");
		String equipeName = tmp.get("equipe").toString();
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			Set<String> membros = sessao.listarMembros(equipe);
			packet.put("lista", membros);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject adicionarArquivo(JSONObject packet){ 
		JSONObject tmp = packet.getJSONObject("addArquivo");
		String titulo = tmp.getString("titulo");
		String conteudo = tmp.getString("conteudo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			sessao.adicionarArquivo(titulo, conteudo, equipe);
			packet.put("OK", "Arquivo adicionado com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removerArquivo(JSONObject packet){ 
		JSONObject tmp = packet.getJSONObject("removeArquivo");
		String titulo = tmp.getString("titulo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			sessao.removerArquivo(titulo, equipe);
			packet.put("OK", "Arquivo removido com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject visualizarArquivo(JSONObject packet){ 
		JSONObject tmp = packet.getJSONObject("viewArquivo");
		String titulo = tmp.getString("titulo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			InfoArquivo info = sessao.visualizarArquivo(titulo, equipe);
			
			HashMap<String, String> tmp2 = new HashMap<>();
			tmp2.put("titulo", info.getTitulo());
			tmp2.put("conteudo", info.getConteudo());
			
			packet.put("view", tmp2);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject modificarArquivo(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("editArquivo");
		String titulo = tmp.getString("titulo");
		String conteudo = tmp.getString("conteudo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			sessao.modificarArquivo(new InfoArquivo(titulo, conteudo), equipe);
			packet.put("OK", "Arquivo modificado com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject adicionarTarefa(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("addTarefa");
		String titulo = tmp.getString("titulo");
		String descricao = tmp.getString("descricao");
		String dataInicio = tmp.getString("dataInicio");
		String dataTermino = tmp.getString("dataTermino");
		String recursos = tmp.getString("recursos");
		String projName = tmp.getString("projName");
		
		packet = new JSONObject();
		try {
			InfoTarefa info = new InfoTarefa(titulo, descricao, dataInicio, dataTermino, recursos);
			sessao.adicionarTarefa(info, projName);
			packet.put("OK", "Tarefa adicionada com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removerTarefa(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("removeTarefa");
		String titulo = tmp.getString("titulo");
		String projName = tmp.getString("projName");
		
		packet = new JSONObject();
		try {
			sessao.removerTarefa(titulo, projName);
			packet.put("OK", "Tarefa removida com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject visualizarTarefa(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("viewTarefa");
		String titulo = tmp.getString("titulo");
		String projName = tmp.getString("projName");
		
		packet = new JSONObject();
		try {
			InfoTarefa info = sessao.visualizarTarefa(titulo, projName);
			packet.put("view", info);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject modificarTarefa(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("editTarefa");
		String titulo = tmp.getString("titulo");
		String descricao = tmp.getString("descricao");
		String dataInicio = tmp.getString("dataInicio");
		String dataTermino = tmp.getString("dataTermino");
		String recursos = tmp.getString("recursos");
		String projName = tmp.getString("projName");
		
		packet = new JSONObject();
		try {
			InfoTarefa info = new InfoTarefa(titulo, descricao, dataInicio, dataTermino, recursos);
			sessao.modificarTarefa(info, projName);
			packet.put("OK", "Tarefa modificada com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}

	public JSONObject adicionarMembro(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("addMembro");
		String login = tmp.getString("login");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Usuario user = dados.getUsuario(login);
			Equipe equipe = dados.getEquipe(equipeName);
			
			sessao.adicionarMembro(user, equipe);
			packet.put("OK", "Usuario adicionado a equipe com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removerMembro(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("removeMembro");
		String login = tmp.getString("login");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Usuario user = dados.getUsuario(login);
			Equipe equipe = dados.getEquipe(equipeName);
			
			sessao.removerMembro(user, equipe);
			packet.put("OK", "Usuario removido da equipe com sucesso.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject adicionarProjeto(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("addProj");
		String projName = tmp.getString("nome");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Equipe equipe = dados.getEquipe(equipeName);
			
			sessao.adicionarProjeto(projName, equipe);
			packet.put("OK", "Projeto adicionado com sucesso.");
		}catch(Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removerProjeto(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("removeProj");
		String projName = tmp.getString("nome");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Equipe equipe = dados.getEquipe(equipeName);
			
			sessao.removerProjeto(projName, equipe);
			packet.put("OK", "Projeto removido com sucesso.");
		}catch(Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject adicionarEquipe(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("addEquipe");
		String equipeName = tmp.getString("nome");
		
		packet = new JSONObject();
		try{
			sessao.adicionarEquipe(equipeName); //gambiarra soh vai deixar ADMs usarem essa funçao
			dados.adicionarEquipe(equipeName);
			packet.put("OK", "Equipe adicionada com sucesso.");
		}catch(Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removerEquipe(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("removeEquipe");
		String equipeName = tmp.getString("nome");
		
		packet = new JSONObject();
		try{
			sessao.removerEquipe(equipeName); //gambiarra soh vai deixar ADMs usarem essa funçao
			dados.removerEquipe(equipeName);
			packet.put("OK", "Equipe removida com sucesso.");
		}catch(Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarEquipes(JSONObject packet){
		packet = new JSONObject();
		try {
			Set<String> equipes = dados.listarEquipes();
			packet.put("lista", equipes);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarUsuarios(JSONObject packet){
		packet = new JSONObject();
		try {
			//sessao.listarUsuarios();  //soh adm pode usar? eh necessaria?
			Set<String> usuarios = dados.listarUsuarios();
			packet.put("lista", usuarios);
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject modificarNivel(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("changeNivel");
		String login = tmp.getString("login");
		int nivel = tmp.getInt("nivel");
		
		packet = new JSONObject();
		try {
			sessao.modificarNivel(login, nivel); //gambiarra soh vai deixar ADMs usarem essa funçao
			Usuario user = dados.getUsuario(login);
			user.setNivel(nivel);
			packet.put("OK", "Nivel modificado com sucesso. O usuario tem que se desconectar para a mudança ocorrer.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
}
