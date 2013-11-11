package Modelo;

import Modelo.Infos.InfoTarefa;
import Modelo.Persistencia.ConexaoBanco;
import Modelo.Persistencia.TarefaDao;

public class Projeto {

	private String nome;
	private int id_equipe;
	private int id_proj;
	private TarefaDao tarefaDao;
	
	public Projeto(String nome){
		this.nome = nome;
		this.tarefaDao = new TarefaDao(new ConexaoBanco(), "TAREFA");
	}
	
	public Projeto(String nome, int id_equipe, int id_proj)
	{
		this.nome = nome;
		this.id_equipe = id_equipe;
		this.id_proj = id_proj;
		this.tarefaDao = new TarefaDao(new ConexaoBanco(), "TAREFA");
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getIdEquipe()
	{
		return id_equipe;
	}
	
	public void setIdEquipe(int id_equipe)
	{
		this.id_equipe = id_equipe;
	}
	
	public int getIdProj()
	{
		return id_proj;
	}
	
	public void setIdProj(int id_proj)
	{
		this.id_proj = id_proj;
	}
	
	public void adicionarTarefa(InfoTarefa info) throws Exception{
		if( tarefaDao.exist(info.getTitulo(), this.id_proj) )
			throw new Exception("Ja existe uma tarefa com esse titulo no seu projeto.");
		
		info.setIdProjeto(this.id_proj);
		tarefaDao.add(info);
	}
	
	public void removerTarefa(String titulo) throws Exception{
		if( !tarefaDao.exist(titulo, this.id_proj) )
			throw new Exception("Tarefa nao encontrada.");
		
		tarefaDao.remove(titulo, this.id_proj);
	}
	
	public InfoTarefa visualizarTarefa(String titulo) throws Exception{
		InfoTarefa info = tarefaDao.getTarefa(titulo, this.id_proj);
		
		if(info == null)
			throw new Exception("Tarefa nao encontrada.");
		
		return info;
	}
	
	public void modificarTarefa(InfoTarefa info) throws Exception{
		if( !tarefaDao.exist(info.getTitulo(), this.id_proj) )
			throw new Exception("Tarefa nao encontrada.");
		
		info.setIdProjeto(this.id_proj);
		tarefaDao.edit(info);
	}	
}
