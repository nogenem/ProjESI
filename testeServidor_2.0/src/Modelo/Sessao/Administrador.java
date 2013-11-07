package Modelo.Sessao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;

import Modelo.Equipe;
import Modelo.EquipeDao;
import Modelo.Usuario;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;

public class Administrador extends SessaoAbstrata {
	
	public Administrador(Usuario user){
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
		return equipe.listarMembros();
	}
	
	public Set<String> listarEquipes() throws Exception{
		return this.equipeDao.listAllName();
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

	public void adicionarTarefa(InfoTarefa info, Equipe equipe, String projName) throws Exception{
		equipe.adicionarTarefa(info, projName);
	}

	public void removerTarefa(String titulo, Equipe equipe, String projName) throws Exception{
		equipe.removerTarefa(titulo, projName);
	}

	public InfoTarefa visualizarTarefa(String titulo, Equipe equipe, String projName) throws Exception{
		return equipe.visualizarTarefa(titulo, projName);
	}

	public void modificarTarefa(InfoTarefa info, Equipe equipe, String projName) throws Exception{
		equipe.modificarTarefa(info, projName);
	}

	public void adicionarMembro( String loginUsuario, Equipe equipe) throws Exception{
		equipe.adicionarMembro( loginUsuario );
	}

	public void removerMembro( String loginUsuario, Equipe equipe) throws Exception{
		equipe.removerMembro( loginUsuario );
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
    
    public HashMap<String, Integer> getLoginsAndNiveis(HashMap<String, Usuario> usuarios) throws Exception{
		
    	Collection<Usuario> users = usuarios.values();  
    	HashMap<String, Integer> result = new HashMap<>();
    	
    	for(Usuario u : users){
    		result.put(u.getLogin(), u.getNivel());
    	}
    	return result;
	}
    
    public void modificarNivel(String login, int nivel) throws Exception{
		//gambiarra
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
