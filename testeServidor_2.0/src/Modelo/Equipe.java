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
import Modelo.Persistencia.UsuarioDao;

public class Equipe
{
	private String nome;
	private int	   id;
	private EquipeDao equipeDao;
	private PostitDao postitDao;
	private UsuarioDao usuarioDao;
	private ArquivoDao arquivoDao;
	
	private HashMap<String, Arquivo> arquivos; //Lista dos arquivos que a equipe possui.
	private HashMap<String, Projeto> projetos; //Lista dos projetos que a equipe possui.
	private HashMap<String, Usuario> membros; //Lista dos membros que a equipe possui.
	private HashMap<String, PostIt> postIts; //Lista dos post-its que a equipe possui.
	
	public Equipe( String nome ) throws Exception
	{
		EquipeDao equipeDao = new EquipeDao( new ConexaoBanco() , "EQUIPE");
		this.id = equipeDao.getId(nome);
		this.nome = nome;
		
		postitDao = new PostitDao(new ConexaoBanco(), "POSTIT");
		usuarioDao = new UsuarioDao(new ConexaoBanco(), "USUARIO");
		arquivoDao = new ArquivoDao(new ConexaoBanco(), "ARQUIVO");
		
		this.arquivos = new HashMap<>();
		this.projetos = new HashMap<>();
		this.membros = new HashMap<>();
		this.postIts = new HashMap<>();	
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
	
	public Set<String> listarProjetos(){
		return projetos.keySet();
	}
	
	public Set<String> listarTarefas(String projName) throws Exception{
		if(!projetos.containsKey(projName))
			throw new Exception("Projeto nao encontrado.");
		Projeto proj = projetos.get(projName);
		return proj.listarTarefas();
	}
	
	public void adicionarArquivo(InfoArquivo info) throws Exception{
		this.arquivoDao.add(info, id);
	}
	
	public void removerArquivo(String titulo) throws Exception{
		this.arquivoDao.remove(titulo);
	}
	
	public InfoArquivo visualizarArquivo(String titulo) throws Exception{
		return this.arquivoDao.view(titulo);
	}

	public void modificarArquivo(InfoArquivo info) throws Exception{
		this.arquivoDao.edit(info, id);
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
	
	public void adicionarMembro( String loginUser ) throws Exception
	{
		UsuarioDao usuario = new UsuarioDao( new ConexaoBanco(), "USUARIO" );
		usuario.addEquipe( loginUser, this.id );
	}
	
	public void removerMembro( String loginUser ) throws Exception
	{
		UsuarioDao usuario = new UsuarioDao( new ConexaoBanco(), "USUARIO" );
		usuario.removeEquipe( loginUser );
	}
	
	public void adicionarProjeto(String projName) throws Exception
	{
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
		this.postitDao.add(info, this.id);
	}
	
	public void removerPostIt(String titulo) throws Exception{
		this.postitDao.remove(titulo);
	}
	
	public InfoPostIt visualizarPostIt(String titulo) throws Exception{
		return this.postitDao.view(titulo);
	}
	
	public void modificarPostIt(InfoPostIt info) throws Exception{
		this.postitDao.edit(info);
	}
	
	public boolean isMember(Usuario user){
		return membros.containsKey(user.getLogin());
	}
}
