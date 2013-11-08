package Modelo.Sessao;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import Modelo.Equipe;
import Modelo.Persistencia.EquipeDao;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;
import Modelo.Persistencia.ConexaoBanco;

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
	
	public Set<String> listarEquipes() throws Exception
	{
		EquipeDao equipeDao = new EquipeDao( new ConexaoBanco(), "EQUIPE");
		Equipe myEquipe = equipeDao.getEquipe( this.getUser().getIdEquipe() );
		
		Set<String> volta = new HashSet<>();
		if(myEquipe != null)
			volta.add(myEquipe.getNome());
		
		return volta;
	}
	
	public Set<String> listarPostIts(Equipe equipe) throws Exception{
		return equipe.listarPostIts();
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
	
	public void adicionarPostIt(InfoPostIt info, Equipe equipe) throws Exception{
		equipe.adicionarPostIt(info);
	}
	
	public void removerPostIt(String titulo, Equipe equipe) throws Exception{
		equipe.removerPostIt(titulo);
	}
	
	public InfoPostIt visualizarPostIt(String titulo, Equipe equipe) throws Exception{
		return equipe.visualizarPostIt(titulo);
	}
	
	public void modificarPostIt(InfoPostIt info, Equipe equipe) throws Exception{
		equipe.modificarPostIt(info);
	}
}
