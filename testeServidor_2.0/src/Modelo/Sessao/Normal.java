package Modelo.Sessao;

import java.util.ArrayList;
import java.util.Set;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public class Normal extends SessaoAbstrata {
	
	public Normal(Usuario user){
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
		return equipe.listarArquivos();
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
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		if(getEquipe() == null)
			throw new Exception("Usuario sem equipe.");
		return getEquipe().visualizarTarefa(titulo, projName);
	}
}
