package Modelo.Sessao;

import java.util.ArrayList;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public class Normal extends SessaoAbstrata {
	
	public Normal(Usuario user){
		super(user);
	}
	
	public ArrayList<String> listarTarefas(String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().listarTarefas(projName);
	}
	
	public ArrayList<String> listarProjetos() throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().listarProjetos();
	}
	
	public ArrayList<String> listarArquivos() throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().listarArquivos();
	}
	
	public void adicionarArquivo(String titulo, String conteudo) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().adicionarArquivo(titulo, conteudo);
	}
	
	public void removerArquivo(String titulo) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().removerArquivo(titulo);
	}
	
	public InfoArquivo visualizarArquivo(String titulo) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().visualizarArquivo(titulo);
	}
	
	public void modificarArquivo(InfoArquivo info) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().modificarArquivo(info);
	}
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().visualizarTarefa(titulo, projName);
	}
}
