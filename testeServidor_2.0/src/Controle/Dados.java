package Controle;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;
import Modelo.Equipe;
import Modelo.Persistencia.EquipeDao;
import Modelo.Usuario;
import Modelo.Persistencia.ConexaoBanco;
import Modelo.Persistencia.UsuarioDao;
import Modelo.Sessao.Administrador;
import Modelo.Sessao.Normal;
import Modelo.Sessao.SessaoAbstrata;

public class Dados {
	private EquipeDao equipeDao;
	private UsuarioDao usuarioDao;
	
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Equipe> equipes;
	
	public Dados(){
		equipeDao = new EquipeDao(new ConexaoBanco(), "EQUIPE");
		usuarioDao = new UsuarioDao(new ConexaoBanco(), "USUARIO");
		
		usuarios = new HashMap<>();
		usuarios.put("admin", new Usuario("admin", "admin", "mr. admin", 0));/* teste */
		equipes = new HashMap<>();
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
	public Usuario getUsuario(String login) throws Exception
	{
		return usuarioDao.getUsuario(login);
	}
	
	/*
	 * Retorna a instancia da Equipe pelo nome fornecido.
	 * Caso nao encontre o nome da equipe, eh pq ela foi removida e o usuario
	 * precisa deslogar para atualizar a sessao dele.
	 */
	public Equipe getEquipe(String equipeName) throws Exception
	{
		return equipeDao.getEquipe( equipeName );
	}
	
	/*
	 * Efetua o login do usuario fazendo as devidas checagens antes.
	 */
	public Usuario efetuarLogin(String login, String senha) throws Exception
	{	
		Usuario usuario = this.getUsuario(login);
		if( usuario == null )
		{
			throw new Exception("Usuario nao encontrado.");
		}
		else if( usuario.confereSenha(senha) )
		{
			return usuario;
		}
		throw new Exception("Senha incorreta.");
	}
	
	/*
	 * Coloca o login do usuario na lista de usuarios do servidor.
	 */
	public void cadastrarUsuario(Usuario user) throws Exception
	{
		
		if( this.getUsuario( user.getLogin() ) == null )
		{
			usuarioDao.addUsuario(user);
			return;
		}
		throw new Exception("Usuario ja cadastrado.");
	}
	
	/*
	 * Cria a sessao para o usuario, dependendo do nivel do mesmo.
	 */
	public SessaoAbstrata criarSessao(Usuario user)
	{
		SessaoAbstrata sessao = null;
		
		if(user.getNivel() == 0)
			sessao = new Administrador(user);
		else if(user.getNivel() == 1)
			sessao = new Normal(user);
		return sessao;
	}
	
	public void adicionarEquipe(String equipeName) throws Exception
	{
		if( this.getEquipe( equipeName ) == null )
		{
			equipeDao.add(equipeName);
		}
		else
		{
			throw new Exception("Equipe ja adicionada.");
		}
	}
	
	public void removerEquipe(String equipeName) throws Exception
	{
		if( this.getEquipe( equipeName ) != null )
		{
			equipeDao.remove( equipeName );
		}
		else
		{
			throw new Exception("Equipe nao encontrada.");
		}
	}
	
	public Set<String> listarEquipes()throws Exception
	{ 
		return equipeDao.list();
	}
}