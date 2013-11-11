package Modelo;

import java.util.HashMap;
import java.util.Set;

import Modelo.Infos.InfoArquivo;
import Modelo.Infos.InfoPostIt;
import Modelo.Infos.InfoTarefa;
import Modelo.Persistencia.ArquivoDao;
import Modelo.Persistencia.ConexaoBanco;
import Modelo.Persistencia.EquipeDao;
import Modelo.Persistencia.PostitDao;
import Modelo.Persistencia.ProjetoDao;
import Modelo.Persistencia.TarefaDao;
import Modelo.Persistencia.UsuarioDao;

public class Equipe
{
	private String nome;
	private int	   id;
	private PostitDao postitDao;
	private UsuarioDao usuarioDao;
	private ArquivoDao arquivoDao;
	private ProjetoDao projetoDao;
	private TarefaDao tarefaDao;
	
	public Equipe( String nome ) throws Exception
	{
		this.nome = nome;
		
		postitDao = new PostitDao(new ConexaoBanco(), "POSTIT");
		usuarioDao = new UsuarioDao(new ConexaoBanco(), "USUARIO");
		arquivoDao = new ArquivoDao(new ConexaoBanco(), "ARQUIVO");
		projetoDao = new ProjetoDao(new ConexaoBanco(), "PROJETO");
		tarefaDao = new TarefaDao(new ConexaoBanco(), "TAREFA");
	}
	
	public void setId( int id )
	{
		this.id = id;
	}
	
	public String getNome(){
		return nome;
	}
	
	public Set<String> listarMembros() throws Exception{
		return usuarioDao.listMembers(this.id);
	}
	
	public Set<String> listarArquivos() throws Exception{
		return arquivoDao.list(this.id);
	}
	
	public Set<String> listarPostIts() throws Exception{
		return postitDao.list(this.id);
	}
	
	public Set<String> listarProjetos() throws Exception{
		return projetoDao.list();
	}
	
	public Set<String> listarTarefas(String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if(proj == null)
			throw new Exception("Projeto nao encontrado.");
		return tarefaDao.list(proj.getIdProj());
	}
	
	public void adicionarArquivo(InfoArquivo info) throws Exception{
		if( arquivoDao.exist(info.getTitulo(), this.id) )
			throw new Exception("Ja existe um arquivo com esse titulo em sua equipe.");
		this.arquivoDao.add(info, id);
	}
	
	public void removerArquivo(String titulo) throws Exception{
		if( !arquivoDao.exist(titulo, this.id) )
			throw new Exception("Arquivo nao encontrado.");
		this.arquivoDao.remove(titulo, this.id);
	}
	
	public InfoArquivo visualizarArquivo(String titulo) throws Exception{
		if( !arquivoDao.exist(titulo, this.id) )
			throw new Exception("Arquivo nao encontrado.");
		
		InfoArquivo info = this.arquivoDao.view(titulo, this.id);
		
		if( info == null )
			throw new Exception("Arquivo nao encontrado.");
		
		return info;
	}

	public void modificarArquivo(InfoArquivo info) throws Exception{
		if( !arquivoDao.exist(info.getTitulo(), this.id) )
			throw new Exception("Arquivo nao encontrado.");
		this.arquivoDao.edit(info, id);
	}

	public void adicionarTarefa(InfoTarefa info, String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if(proj == null)
			throw new Exception("Projeto nao encontrado.");
		
		proj.adicionarTarefa(info);
	}
	
	public void removerTarefa(String titulo, String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if(proj == null)
			throw new Exception("Projeto nao encontrado.");
		
		proj.removerTarefa(titulo);
	}
	
	public InfoTarefa visualizarTarefa(String titulo, String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if(proj == null)
			throw new Exception("Projeto nao encontrado.");
		
		return proj.visualizarTarefa(titulo);
	}
	
	public void modificarTarefa(InfoTarefa info, String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if(proj == null)
			throw new Exception("Projeto nao encontrado.");
		
		proj.modificarTarefa(info);
	}	
	
	public void adicionarMembro( String loginUser ) throws Exception
	{
		Usuario user = usuarioDao.getUsuario(loginUser);
		if( user == null )
			throw new Exception("Usuario nao encontrado.");
		else if( user.getIdEquipe() == this.id )
			throw new Exception("Usuario ja é membro dessa equipe.");
		
		usuarioDao.addEquipe( loginUser, this.id );
	}
	
	public void removerMembro( String loginUser ) throws Exception
	{
		Usuario user = usuarioDao.getUsuario(loginUser);
		if( user == null )
			throw new Exception("Usuario nao encontrado.");
		else if( user.getIdEquipe() != this.id )
			throw new Exception("Usuario nao é membro desta equipe.");
		
		usuarioDao.removeEquipe( loginUser );
	}
	
	public void adicionarProjeto(String projName) throws Exception
	{
		if( projetoDao.exist(projName, this.id) )
			throw new Exception("Ja existe um projeto com esse nome nesta equipe.");
		projetoDao.add(projName, this.id);
	}
	
	public void removerProjeto(String projName) throws Exception{
		Projeto proj = projetoDao.getProj(projName, this.id);
		if( proj == null )
			throw new Exception("Projeto nao encontrado.");
		
		tarefaDao.removeAll(proj.getIdProj()); //remove todas as tarefas desse projeto antes...
		projetoDao.remove(projName, this.id);
	}
	
	public void adicionarPostIt(InfoPostIt info) throws Exception{
		if( postitDao.exist(info.getTitulo(), this.id) )
			throw new Exception("Ja existe um post-it com esse titulo na sua equipe.");
		
		this.postitDao.add(info, this.id);
	}
	
	public void removerPostIt(String titulo) throws Exception{
		if( !postitDao.exist(titulo, this.id) )
			throw new Exception("Post-it nao encontrado.");
		
		this.postitDao.remove(titulo, this.id);
	}
	
	public InfoPostIt visualizarPostIt(String titulo) throws Exception{
		if( !postitDao.exist(titulo, this.id) )
			throw new Exception("Post-it nao encontrado.");
		
		return this.postitDao.view(titulo, this.id);
	}
	
	public void modificarPostIt(InfoPostIt info) throws Exception{
		if( !postitDao.exist(info.getTitulo(), this.id) )
			throw new Exception("Post-it nao encontrado.");
		
		this.postitDao.edit(info, this.id);
	}
	
	public void removeAllData() throws Exception{
		postitDao.removeAll(this.id);
		arquivoDao.removeAll(this.id);
		usuarioDao.removeAllMembers(this.id);
		projetoDao.removeAll(this.id, tarefaDao);
	}
}
