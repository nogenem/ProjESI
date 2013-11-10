package Modelo.Persistencia;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.Infos.InfoTarefa;

public class TarefaDao extends Dao {

	public TarefaDao(ConexaoBanco c, String tabela) {
		super(c, tabela);
	}
	
	public void add(InfoTarefa info) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put("ID_PROJETO", ""+info.getIdProjeto());
		dados.put("TITULO", info.getTitulo());
		dados.put("DESCRICAO", info.getDescricao());
		dados.put("DATA_INICIO", info.getDataInicio());
		dados.put("DATA_FINAL", info.getDataTermino());
		dados.put("RECURSOS", info.getRecursos());
		
		this.insert(dados);
	}
	
	public void edit(InfoTarefa info) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put("ID_PROJETO", ""+info.getIdProjeto());
		dados.put("TITULO", info.getTitulo());
		dados.put("DESCRICAO", info.getDescricao());
		dados.put("DATA_INICIO", info.getDataInicio());
		dados.put("DATA_FINAL", info.getDataTermino());
		dados.put("RECURSOS", info.getRecursos());
		
		HashMap<String, String> cond = new HashMap<>();
		cond.put("TITULO", info.getTitulo());
		cond.put("ID_PROJETO", ""+info.getIdProjeto());
		
		this.update(dados, cond);
	}
	
	public void remove(String titulo, int id_projeto) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("TITULO", titulo);
		cond.put("ID_PROJETO", ""+id_projeto);
		
		this.delete(cond);
	}
	
	public void removeAll(int id_projeto) throws Exception //Vai remover todas as tarefas do projeto q vem como parametro
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("ID_PROJETO", ""+id_projeto);
		
		this.delete(cond);
	}
	
	public InfoTarefa getTarefa(String titulo, int id_projeto) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("TITULO", titulo);
		cond.put("ID_PROJETO", ""+id_projeto);
		
		this.select(cond);
		
		ResultSet result = this.getResultSet();
		
		if(result != null && result.next())
		{
			InfoTarefa info = new InfoTarefa(titulo, result.getString("DESCRICAO"), result.getString("DATA_INICIO"), 
											 result.getString("DATA_FINAL"), result.getString("RECURSOS"), id_projeto);
			return info;
		}
		return null;
	}
	
	public Set<String> list(int id_projeto) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("ID_PROJETO", ""+id_projeto);
		
		this.select(cond);
		
		ResultSet result = this.getResultSet();
		Set<String> retorno = new HashSet<>();
		
		while(result != null && result.next())
		{
			retorno.add(result.getString("TITULO"));
		}
		return retorno;
	}
	
	public boolean exist(String titulo, int id_projeto) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("TITULO", titulo);
		cond.put("ID_PROJETO", ""+id_projeto);
		
		this.select(cond);
		
		ResultSet result = this.getResultSet();
		return result != null && result.next();
	}
}
