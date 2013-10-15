package Modelo.Sessao;

import java.util.ArrayList;
import Modelo.*;
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
	
	public ArrayList<String> listarTarefas() throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public ArrayList<String> listarProjetos() throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public ArrayList<String> listarArquivos() throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void adicionarArquivo(String titulo, String conteudo) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void removerArquivo(String titulo) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public InfoArquivo visualizarArquivo(String titulo) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void modificarArquivo(InfoArquivo info) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void adicionarTarefa(InfoTarefa info, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void removerTarefa(String titulo, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void modificarTarefa(InfoTarefa info, String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void adicionarMembro(Usuario user) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void removerMembro(Usuario user) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void adicionarProjeto(String projName, Equipe equipe) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void removerProjeto(String projName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void adicionarEquipe(String equipeName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void removerEquipe(String equipeName) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
	public void modificarNivel(String login, int nivel) throws Exception{
		throw new Exception("Usuario nao tem permissao para executar esta a�ao.");
	}
	
}
