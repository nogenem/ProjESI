package Controle;

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
	
	public Dados(){
		equipeDao = new EquipeDao(new ConexaoBanco(), "EQUIPE");
		usuarioDao = new UsuarioDao(new ConexaoBanco(), "USUARIO");
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
		usuarioDao.addUsuario(user);
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
}