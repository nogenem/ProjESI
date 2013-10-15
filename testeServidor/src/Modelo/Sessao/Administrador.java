package Modelo.Sessao;

import java.util.ArrayList;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public class Administrador extends SessaoAbstrata {

	public Administrador(Usuario user){
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

	public void adicionarTarefa(InfoTarefa info, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().adicionarTarefa(info, projName);
	}

	public void removerTarefa(String titulo, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().removerTarefa(titulo, projName);
	}

	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().visualizarTarefa(titulo, projName);
	}

	public void modificarTarefa(InfoTarefa info, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().modificarTarefa(info, projName);
	}

	public void adicionarMembro(Usuario user) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().adicionarMembro(user);
	}

	public void removerMembro(Usuario user) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().removerMembro(user);
	}

	public void adicionarProjeto(String projName, Equipe equipe) throws Exception{
		equipe.adicionarProjeto(projName);
	}

	public void removerProjeto(String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		getEquipe().removerProjeto(projName);
	}

	public void adicionarEquipe(String equipeName) throws Exception{
		//gambiarra
	}

    public void removerEquipe(String equipeName) throws Exception{
    	//gambiarra
	}
    
    public void modificarNivel(String login, int nivel) throws Exception{
		//gambiarra
	}
}
