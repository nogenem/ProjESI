package Controle;

import java.util.HashMap;
import java.util.Set;
import org.json.JSONObject;
import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;
import Modelo.Sessao.SessaoAbstrata;

public class Controle {
	
	private Dados dados;
	private SessaoAbstrata sessao;
	private String admKey;
	
	public Controle(Dados dados)
	{
		this.dados = dados;
		admKey = "A15S-7S8Q-9GR1-Q7WF-96M2";
	}
	
	/*
	 * Retorna a key para criaçao de adms.
	 */
	public String getAdmKey(){
		return admKey;
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
		else if(packet.has("listarTarefas"))
			return listarTarefas(packet);
		else if(packet.has("listarProjetos"))
			return listarProjetos(packet);
		else if(packet.has("listarArquivos"))
			return listarArquivos(packet);
		else if(packet.has("listarPostIts"))
			return listarPostIts(packet);
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
		else if(packet.has("getNiveis"))
			return getLoginsAndNiveis(packet);
		else if(packet.has("changeNivel")) //fazer isso!
			return modificarNivel(packet);
		else if(packet.has("addPostIt"))
			return adicionarPostIt(packet);
		else if(packet.has("removePostIt"))
			return removePostIt(packet);
		else if(packet.has("viewPostIt"))
			return visualizarPostIt(packet);
		else if(packet.has("editPostIt"))
			return modificarPostIt(packet);
		
		return null;
	}
	
	public JSONObject efetuarLogin(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("logar");
		String login = tmp.getString("login");
		String senha = tmp.getString("senha");
		
		try
		{
			Usuario user = dados.efetuarLogin( login, senha );
			sessao = dados.criarSessao(user);
		}catch(Exception e)
		{
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
	
	public JSONObject listarTarefas(JSONObject packet){ 
		JSONObject tmp = packet.getJSONObject("listarTarefas");
		String equipeName = tmp.getString("equipe");
		String projName = tmp.getString("projName");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			
			Set<String> tarefas = sessao.listarTarefas(equipe, projName);
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
	
	public JSONObject listarArquivos(JSONObject packet){ 
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
		String equipeName = tmp.getString("equipe");
		
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
	
	public JSONObject listarPostIts(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("listarPostIts");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			Set<String> postIts = sessao.listarPostIts(equipe);
			packet.put("lista", postIts);
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
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			InfoTarefa info = new InfoTarefa(titulo, descricao, dataInicio, dataTermino, recursos);
			
			sessao.adicionarTarefa(info, equipe, projName);
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
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			sessao.removerTarefa(titulo, equipe, projName);
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
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			InfoTarefa info = sessao.visualizarTarefa(titulo, equipe, projName);

			HashMap<String, String> tmp2 = new HashMap<>();
			tmp2.put("titulo", info.getTitulo());
			tmp2.put("descricao", info.getDescricao());
			tmp2.put("dataInicio", info.getDataInicio());
			tmp2.put("dataTermino", info.getDataTermino());
			tmp2.put("recursos", info.getRecursos());
			
			packet.put("view", tmp2);
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
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try {
			Equipe equipe = dados.getEquipe(equipeName);
			InfoTarefa info = new InfoTarefa(titulo, descricao, dataInicio, dataTermino, recursos);
			
			sessao.modificarTarefa(info, equipe, projName);
		
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
		try
		{
			Equipe equipe = new Equipe( equipeName );
			sessao.adicionarMembro( login, equipe );
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
			sessao.adicionarEquipe(equipeName); //gambiarra soh vai deixar ADMs usarem essa fun�ao
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
			sessao.removerEquipe(equipeName); //gambiarra soh vai deixar ADMs usarem essa fun�ao
			dados.removerEquipe(equipeName);
			packet.put("OK", "Equipe removida com sucesso.");
		}catch(Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject listarEquipes(JSONObject packet)
	{
		packet = new JSONObject();
		try
		{	
			Set<String> equipes = sessao.listarEquipes();
			packet.put("lista", equipes);
		}
		catch (Exception e)
		{
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject getLoginsAndNiveis(JSONObject packet){ //Retorna os logins e niveis de todos os usuarios.
		packet = new JSONObject();
		try{
			HashMap<String, Integer> result = sessao.getLoginsAndNiveis(dados.getUsuarios());
			packet.put("lista", result);			
		}catch(Exception e){
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
			sessao.modificarNivel(login, nivel); //gambiarra soh vai deixar ADMs usarem essa fun�ao
			Usuario user = dados.getUsuario(login);
			user.setNivel(nivel);
			packet.put("OK", "Nivel modificado com sucesso. O usuario tem que se desconectar para a mudanca ocorrer.");
		} catch (Exception e) {
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject adicionarPostIt(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("addPostIt");
		String titulo = tmp.getString("titulo");
		String conteudo = tmp.getString("conteudo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Usuario user = sessao.getUser();
			Equipe equipe = dados.getEquipe(equipeName);
			InfoPostIt info = new InfoPostIt(titulo, conteudo, user.getLogin());
			
			sessao.adicionarPostIt(info, equipe);
			packet.put("OK", "Post-it adicionado com sucesso.");
		}catch (Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject removePostIt(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("removePostIt");
		String titulo = tmp.getString("titulo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Equipe equipe = dados.getEquipe(equipeName);
			
			sessao.removerPostIt(titulo, equipe);
			packet.put("OK", "Post-it removido com sucesso.");
		}catch (Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
	public JSONObject visualizarPostIt(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("viewPostIt");
		String titulo = tmp.getString("titulo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Equipe equipe = dados.getEquipe(equipeName);	
			InfoPostIt info = sessao.visualizarPostIt(titulo, equipe);
			
			HashMap<String, String> tmp2 = new HashMap<>();
			tmp2.put("titulo", info.getTitulo());
			tmp2.put("conteudo", info.getConteudo());
			tmp2.put("emissor", info.getEmissor());
			
			packet.put("view", tmp2);
		}catch (Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}

	public JSONObject modificarPostIt(JSONObject packet){
		JSONObject tmp = packet.getJSONObject("editPostIt");
		String titulo = tmp.getString("titulo");
		String conteudo = tmp.getString("conteudo");
		String equipeName = tmp.getString("equipe");
		
		packet = new JSONObject();
		try{
			Usuario user = sessao.getUser();
			Equipe equipe = dados.getEquipe(equipeName);
			InfoPostIt info = new InfoPostIt(titulo, conteudo, user.getLogin());
			
			sessao.modificarPostIt(info, equipe);
			packet.put("OK", "Post-it modificado com sucesso.");
		}catch (Exception e){
			packet.put("err", e.getMessage());
		}
		return packet;
	}
	
}
