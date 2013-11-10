package Modelo.Persistencia;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.Projeto;

public class ProjetoDao extends Dao {

	public ProjetoDao(ConexaoBanco c, String tabela) {
		super(c, tabela);
	}
	
	public void add(String projName, int id_equipe) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put("NOME", projName);
		dados.put("ID_EQUIPE", ""+id_equipe);
		
		this.insert(dados);
	}
	
	public void remove(String projName, int id_equipe) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("NOME", projName);
		cond.put("ID_EQUIPE", ""+id_equipe);
		
		this.delete(cond);
	}
	
	public void removeAll(int id_equipe, TarefaDao tarefaDao) throws Exception //Vai remover todos os projetos da equipe vindo 
	{																		  //como parametro, removendo tb suas tarefas.
		this.select("");		
		ResultSet result = this.getResultSet();
		
		while(result != null && result.next())
		{
			tarefaDao.removeAll( result.getInt("ID_PROJETO") ); //deleta todas as tarefas 1*
		}
		
		
		HashMap<String, String> cond = new HashMap<>();
		cond.put("ID_EQUIPE", ""+id_equipe);
		
		this.delete(cond);
	}
	
	public Projeto getProj(String projName, int id_equipe) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("NOME", projName);
		cond.put("ID_EQUIPE", ""+id_equipe);
		
		this.select(cond);
		ResultSet result = this.getResultSet();
		
		if(result != null && result.next())
		{
			Projeto proj = new Projeto(result.getString("NOME"), id_equipe, result.getInt("ID_PROJETO"));
			return proj;
		}
		return null;
	}
	
	public Set<String> list() throws Exception
	{
		this.select("");
		
		ResultSet result = this.getResultSet();
		Set<String> retorno = new HashSet<>();
		
		while(result != null && result.next())
		{
			retorno.add(result.getString("NOME"));
		}
		return retorno;
	}
	
	public boolean exist(String projName, int id_equipe) throws Exception 
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("NOME", projName);
		cond.put("ID_EQUIPE", ""+id_equipe);
		
		this.select(cond);
		
		ResultSet result = this.getResultSet();
		return result != null && result.next();
	}

}
