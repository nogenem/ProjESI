package Modelo.Sessao;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.Equipe;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoTarefa;

public class Normal extends SessaoAbstrata {
	
	public Normal(Usuario user){
		super(user);
	}
	
	public Set<String> listarTarefas(Equipe equipe, String projName) throws Exception{
		return equipe.listarTarefas(projName);
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
	
	public Set<String> listarEquipes(HashMap<String, Equipe> equipes) throws Exception{
		Equipe myEquipe = null;
		
		Collection<Equipe> eqs = equipes.values();
		for(Equipe e : eqs){
			if(e.isMember(getUser()))
				myEquipe = e;
		}
		
		Set<String> volta = new HashSet<>();
		if(myEquipe != null)
			volta.add(myEquipe.getNome());
		
		return volta;
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
	
	public InfoTarefa visualizarTarefa(String titulo, Equipe equipe, String projName) throws Exception{
		return equipe.visualizarTarefa(titulo, projName);
	}
}
