package Modelo;

import java.util.HashMap;
import java.util.Set;
import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;

public class Equipe {

	private String nome;
	private HashMap<String, Arquivo> arquivos; //Lista dos arquivos que a equipe possui.
	private HashMap<String, Projeto> projetos; //Lista dos projetos que a equipe possui.
	private HashMap<String, Usuario> membros; //Lista dos membros que a equipe possui.
	private HashMap<String, PostIt> postIts; //Lista dos post-its que a equipe possui.

	public Equipe(String nome){
		this.nome = nome;
		this.arquivos = new HashMap<>();
		this.projetos = new HashMap<>();
		this.membros = new HashMap<>();
		this.postIts = new HashMap<>();
	}
	
	public String getNome(){
		return nome;
	}
	
	public Set<String> listarMembros(){
		return membros.keySet();
	}
	
	public Set<String> listarArquivos(){
		return arquivos.keySet();
	}
	
	public Set<String> listarProjetos(){
		return projetos.keySet();
	}
	
	public Set<String> listarPostIts(){
		return postIts.keySet();
	}
	
	public Set<String> listarTarefas(String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		Projeto proj = projetos.get(projName);
		return proj.listarTarefas();
	}
	
	public void adicionarArquivo(String titulo, String conteudo) throws Exception{
		if(arquivos.containsKey(titulo))
			throw new Exception("Arquivo ja adicionado.");
		InfoArquivo info = new InfoArquivo(titulo, conteudo);
		arquivos.put(titulo, new Arquivo(info));
	}
	
	public void removerArquivo(String titulo) throws Exception{
		if(!arquivos.containsKey(titulo))
			throw new Exception("Arquivo nao encontrado.");
		arquivos.remove(titulo);
	}
	
	public InfoArquivo visualizarArquivo(String titulo) throws Exception{
		if(!arquivos.containsKey(titulo))
			throw new Exception("Arquivo nao encontrado.");
		return arquivos.get(titulo).visualizarArquivo();
	}

	public void modificarArquivo(InfoArquivo info) throws Exception{
		if(!arquivos.containsKey(info.getTitulo()))
			throw new Exception("Arquivo nao encontrado.");
		arquivos.get(info.getTitulo()).modificarArquivo(info);
	}

	public void adicionarTarefa(InfoTarefa info, String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		projetos.get(projName).adicionarTarefa(info);
	}
	
	public void removerTarefa(String titulo, String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		projetos.get(projName).removerTarefa(titulo);
	}
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		return projetos.get(projName).visualizarTarefa(titulo);
	}
	
	public void modificarTarefa(InfoTarefa info, String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		projetos.get(projName).modificarTarefa(info);
	}	
	
	public void adicionarMembro(Usuario user) throws Exception{
		if(membros.containsKey(user.getLogin()))
			throw new Exception("Usuario ja � membro desta equipe.");
		else if(user.getEquipeName() != null)
			throw new Exception("Usuario ja � membro de uma equipe.");
		membros.put(user.getLogin(), user);
		user.setEquipeName(this.getNome());
	}
	
	public void removerMembro(Usuario user) throws Exception{
		if(!membros.containsKey(user.getLogin()))
			throw new Exception("Usuario nao pertence a esta equipe.");
		membros.remove(user.getLogin());
		user.setEquipeName(null);
	}
	
	public void adicionarProjeto(String projName) throws Exception{
		if(projetos.containsKey(projName))
			throw new Exception("Projeto ja existe.");
		projetos.put(projName, new Projeto(projName));
	}
	
	public void removerProjeto(String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		projetos.remove(projName);
	}
	
	public void adicionarPostIt(InfoPostIt info) throws Exception{
		if(postIts.containsKey(info.getTitulo()))
			throw new Exception("Ja existe um post-it com esse nome.");		
		postIts.put(info.getTitulo(), new PostIt(info));
	}
	
	public void removerPostIt(String titulo) throws Exception{
		if(!postIts.containsKey(titulo))
			throw new Exception("Post-it nao encontrado.");	
		postIts.remove(titulo);
	}
	
	public InfoPostIt visualizarPostIt(String titulo) throws Exception{
		if(!postIts.containsKey(titulo))
			throw new Exception("Post-it nao encontrado.");
		return postIts.get(titulo).visualizarPostIt();
	}
	
	public void modificarPostIt(InfoPostIt info) throws Exception{
		if(!postIts.containsKey(info.getTitulo()))
			throw new Exception("Post-it nao encontrado.");
		postIts.get(info.getTitulo()).modificarPostIt(info);
	}
	
	public boolean isMember(Usuario user){
		return membros.containsKey(user.getLogin());
	}
}
