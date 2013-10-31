package Modelo.Sessao;

import java.util.Set;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public abstract class SessaoAbstrata {
	
	private Usuario user; //Ao meu ver assim fica melhor mas...
	
	public SessaoAbstrata(Usuario user){
		this.user = user;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public Equipe getEquipe(){
		return user.getEquipe();
	}
	
	public Set<String> listarTarefas(String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarProjetos(Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarArquivos(Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public Set<String> listarMembros(Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void adicionarArquivo(String titulo, String conteudo, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void removerArquivo(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public InfoArquivo visualizarArquivo(String titulo, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void modificarArquivo(InfoArquivo info, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void adicionarTarefa(InfoTarefa info, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void removerTarefa(String titulo, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void modificarTarefa(InfoTarefa info, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void adicionarMembro(Usuario user, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void removerMembro(Usuario user, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void adicionarProjeto(String projName, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void removerProjeto(String projName, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void adicionarEquipe(String equipeName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void removerEquipe(String equipeName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
	public void modificarNivel(String login, int nivel) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta açao.");
	}
	
}
