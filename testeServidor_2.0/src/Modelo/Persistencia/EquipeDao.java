package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.Persistencia.ConexaoBanco;
import Modelo.Persistencia.Dao;

public class EquipeDao extends Dao
{
	public EquipeDao( ConexaoBanco c, String tabela )
	{
		super( c, tabela );
	}	
	public int getIdEquipe( String nome ) throws Exception
	{
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "NOME" , nome );
		select( condicoes );
		ResultSet result = getResultSet();
		if( result != null && result.next() )
		{
			return result.getInt( "ID_EQUIPE" );
		}
		throw new Exception("Equipe não encontrada");
	}
	public void adicionarEquipe( String equipeName )throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "NOME" , equipeName );
		insert(dados);
	}
	
	public void removeEquipe( String equipeName )throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put( "NOME" , equipeName );
		delete(cond);
	}
	
	public Equipe getEquipe( String equipeName ) throws Exception
	{
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "NOME" , equipeName );
		
		select( condicoes  );
		
		ResultSet result = getResultSet();
		if( result != null && result.next() )
		{
			Equipe equipe = new Equipe( equipeName );
			equipe.setId(result.getInt( "ID_EQUIPE" ));
			return equipe;
		}
		return null;
	}
	
	public Set<String> listAllName() throws Exception
	{
		this.select("");
		ResultSet result = this.getResultSet();
		Set<String> setReturn = new HashSet<>();
		while( result != null && result.next() )
		{
			setReturn.add( result.getString( "NOME" ) );
		}
		return setReturn;
	}
	
	public Equipe getEquipe( int idEquipe ) throws Exception
	{
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "ID_EQUIPE" , idEquipe+"" );
		
		select( condicoes  );
		
		ResultSet result = getResultSet();
		if( result != null && result.next() )
		{
			Equipe equipe = new Equipe( result.getString("NOME") );
			equipe.setId( idEquipe );
			return equipe;
		}
		return null;
	}
}