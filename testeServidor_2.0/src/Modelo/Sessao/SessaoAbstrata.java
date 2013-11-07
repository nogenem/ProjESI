package Modelo.Sessao;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import Modelo.Equipe;
import Modelo.EquipeDao;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;
import Modelo.Persistencia.ConexaoBanco;

public abstract class SessaoAbstrata
{	protected EquipeDao equipeDao;
	private Usuario user; //Ao meu ver assim fica melhor mas...
	
	public SessaoAbstrata(Usuario user){
		this.user = user;
		this.equipeDao = new EquipeDao( new ConexaoBanco(), "EQUIPE");
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public Set<String> listarTarefas(Equipe equipe, String projName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarProjetos(Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarArquivos(Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarMembros(Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarEquipes() throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarPostIts(Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarArquivo(String titulo, String conteudo, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerArquivo(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public InfoArquivo visualizarArquivo(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void modificarArquivo(InfoArquivo info, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarTarefa(InfoTarefa info, Equipe equipe, String projName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerTarefa(String titulo, Equipe equipe, String projName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public InfoTarefa visualizarTarefa(String titulo, Equipe equipe, String projName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void modificarTarefa(InfoTarefa info, Equipe equipe, String projName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarMembro( String loginUsuario, Equipe equipe ) throws Exception
	{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerMembro(Usuario user, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarProjeto(String projName, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerProjeto(String projName, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarEquipe(String equipeName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerEquipe(String equipeName) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public HashMap<String, Integer> getLoginsAndNiveis(HashMap<String, Usuario> usuarios) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void modificarNivel(String login, int nivel) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void adicionarPostIt(InfoPostIt info, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void removerPostIt(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public InfoPostIt visualizarPostIt(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
	public void modificarPostIt(InfoPostIt info, Equipe equipe) throws Exception{
		throw new Exception("Voce nao tem permissao para executar esta açao.");
	}
	
}
