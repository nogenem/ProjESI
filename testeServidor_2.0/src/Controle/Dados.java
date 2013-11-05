package Controle;

import java.util.HashMap;
import java.util.Set;
import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Sessao.Administrador;
import Modelo.Sessao.Normal;
import Modelo.Sessao.SessaoAbstrata;

public class Dados {
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Equipe> equipes;
	private String admKey;
	
	public Dados(){
		usuarios = new HashMap<>();
		
		usuarios.put("admin", new Usuario("admin", "admin", "mr. admin", 0));/* teste */
		
		equipes = new HashMap<>();
		admKey = "A15S-7S8Q-9GR1-Q7WF-96M2";/* chave necessaria para criacao de um adm */
	}
	
	public String getAdmKey(){
		return admKey;
	}
	
	public HashMap<String, Equipe> getEquipes(){
		return equipes;
	}
	
	public HashMap<String, Usuario> getUsuarios(){
		return usuarios;
	}
	
	/*
	 * Retorna a instancia do Usuario pelo login fornecido.
	 */
	public Usuario getUsuario(String login) throws Exception{
		if(!usuarios.containsKey(login))
			throw new Exception("Usuario nao encontrado.");
		return usuarios.get(login);
	}
	
	/*
	 * Retorna a instancia da Equipe pelo nome fornecido.
	 * Caso nao encontre o nome da equipe, eh pq ela foi removida e o usuario
	 * precisa deslogar para atualizar a sessao dele.
	 */
	public Equipe getEquipe(String equipeName) throws Exception{
		if(!equipes.containsKey(equipeName))
			throw new Exception("Equipe nao encontrada. Por favor, deslogue para atualizar seus status.");
		return equipes.get(equipeName);
	}
	
	/*
	 * Efetua o login do usuario fazendo as devidas checagens antes.
	 */
	public Usuario efetuarLogin(String login, String senha) throws Exception{
		if(usuarios.containsKey(login)){
			Usuario user = usuarios.get(login);
			if(user.isOn())
				throw new Exception("Usuario ja esta logado.");
			
			if(!user.confereSenha(senha))
				throw new Exception("Senha incorreta.");
			
			user.setOn(true); //Muda o estado do usuario para online
			
			//Checa se a equipe do usuario ainda eh valida
			if(user.getEquipeName() != null && !equipes.containsKey(user.getEquipeName())) 
				user.setEquipeName(null);
			
			return user;
		}else{
			throw new Exception("Usuario nao cadastrado.");
		}
	}
	
	/*
	 * Coloca o login do usuario na lista de usuarios do servidor.
	 */
	public void cadastrarUsuario(Usuario user) throws Exception{
		if(usuarios.containsKey(user.getLogin()))
			throw new Exception("Usuario ja cadastrado.");
		usuarios.put(user.getLogin(), user);
	}
	
	/*
	 * Cria a sessao para o usuario, dependendo do nivel do mesmo.
	 */
	public SessaoAbstrata criarSessao(Usuario user){
		SessaoAbstrata sessao = null;
		
		if(user.getNivel() == 0)
			sessao = new Administrador(user);
		else if(user.getNivel() == 1)
			sessao = new Normal(user);
		
		return sessao;
	}
	
	public void adicionarEquipe(String equipeName) throws Exception{
		if(equipes.containsKey(equipeName))
			throw new Exception("Equipe ja adicionada.");
		equipes.put(equipeName, new Equipe(equipeName));
	}
	
	public void removerEquipe(String equipeName) throws Exception{
		if(!equipes.containsKey(equipeName))
			throw new Exception("Equipe nao encontrada.");
		equipes.remove(equipeName);
	}
	
	public Set<String> listarEquipes(){ 
		Set<String> lista = equipes.keySet();
		return lista;
	}
	
	public Set<String> listarUsuarios(){ //eh necessario?
		Set<String> lista = usuarios.keySet();
		return lista;
	}
}
