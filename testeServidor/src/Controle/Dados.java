package Controle;

import java.util.ArrayList;
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
	
	/*
	 * Retorna a instancia do Usuario pelo login fornecido.
	 */
	public Usuario getUsuario(String login) throws Exception{
		if(!usuarios.containsKey(login))
			throw new Exception("Usuario nao encontrado.");
		return usuarios.get(login);
	}
	
	public Equipe getEquipe(String equipeName) throws Exception{
		if(!equipes.containsKey(equipeName))
			throw new Exception("Equipe nao encontrado.");
		return equipes.get(equipeName);
	}
	
	public Usuario efetuarLogin(String login, String senha) throws Exception{
		if(usuarios.containsKey(login)){
			Usuario user = usuarios.get(login);
			if(user.isOn())
				throw new Exception("Usuario ja esta logado.");
			
			if(!user.confereSenha(senha))
				throw new Exception("Senha incorreta.");
			
			user.setOn(true); //Muda o estado do usuario para online
			return user;
		}else{
			throw new Exception("Usuario nao cadastrado.");
		}
	}
	
	public void cadastrarUsuario(Usuario user) throws Exception{
		if(usuarios.containsKey(user.getLogin()))
			throw new Exception("Usuario ja cadastrado.");
		usuarios.put(user.getLogin(), user);
	}
	
	/* Nao sei se fico muito bom isso mas... */
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
	
	@SuppressWarnings("unchecked")
	public Set<String> listarEquipes(){ //eh necessario?
		Set<String> lista = equipes.keySet();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> listarUsuarios(){ //eh necessario?
		Set<String> lista = usuarios.keySet();
		return lista;
	}
}
