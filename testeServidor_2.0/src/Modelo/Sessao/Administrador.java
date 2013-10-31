package Modelo.Sessao;

import java.util.Set;
import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public class Administrador extends SessaoAbstrata {

	public Administrador(Usuario user){
		super(user);
	}

	public Set<String> listarTarefas(String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().listarTarefas(projName);
	}

	public Set<String> listarProjetos(Equipe equipe) throws Exception{
		return equipe.listarProjetos();
	}

	public Set<String> listarArquivos(Equipe equipe) throws Exception{
		return equipe.listarArquivos();
	}
	
	public Set<String> listarMembros(Equipe equipe) throws Exception{
		return equipe.listarMembros();
	}

	public void adicionarArquivo(String titulo, String conteudo, Equipe equipe) throws Exception{
		equipe.adicionarArquivo(titulo, conteudo);
	}

	public void removerArquivo(String titulo, Equipe equipe) throws Exception{
		equipe.removerArquivo(titulo);
	}

	public InfoArquivo visualizarArquivo(String titulo, Equipe equipe) throws Exception{
		return equipe.visualizarArquivo(titulo);
	}

	public void modificarArquivo(InfoArquivo info, Equipe equipe) throws Exception{
		equipe.modificarArquivo(info);
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

	public void adicionarMembro(Usuario user, Equipe equipe) throws Exception{
		equipe.adicionarMembro(user);
	}

	public void removerMembro(Usuario user, Equipe equipe) throws Exception{
		equipe.removerMembro(user);
	}

	public void adicionarProjeto(String projName, Equipe equipe) throws Exception{
		equipe.adicionarProjeto(projName);
	}

	public void removerProjeto(String projName, Equipe equipe) throws Exception{
		equipe.removerProjeto(projName);
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