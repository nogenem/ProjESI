package Modelo.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

public abstract class Dao
{
	private	  ResultSet resultSet 	 = null;
	protected   PreparedStatement psmt = null;
	protected   Connection connection	 = null;
	protected   ConexaoBanco conexao	 = null;

	protected String tabela 	 	 = null;

	protected Dao( ConexaoBanco c, String tabela )
	{ 
		this.conexao = c;
		this.tabela = tabela;
		this.connection = c.getConexao();
	}
	
	protected void query( String sql )
	{
		try
		{
			this.psmt = this.connection.prepareStatement( sql );
		}
		catch( SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	final protected void executar()
	{
		try
		{
			this.resultSet = this.psmt.executeQuery();
		}
		catch( SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	final protected ResultSet getResultSet()
	{
		return this.resultSet;
	}
	
	protected void insert( HashMap<String, String> dados )
	{
		try
		{
			String SQL = "INSERT INTO "+this.tabela+" (";
			Set<String> campos = dados.keySet();
			int contador = 0;
			for( String campo : campos )
			{
				if( contador == ( campos.size() -1 ) )
				{
					SQL += campo + ")";
					contador++;
				}
				else
				{
					SQL += campo + ",";
					contador++;
				}
			}
			SQL += " VALUES (";

			/*
			 * INTEROGACAO
			 */

			for( int i = 0; i < dados.size(); i++ )
			{
				if( i == ( dados.size() -1 ) )
				{
					SQL += "?)";
				}
				else
				{
					SQL += "?,";
				}
			}
			this.query( SQL );
			contador = 1;
			for( String campo : campos )
			{
				this.psmt.setObject( contador, dados.get( campo ) );
				contador++;
			}
			this.psmt.execute();
		}
		catch( SQLException e)
		{
			System.out.println( "LINHA 90" );
			e.printStackTrace();
		}
	}
	
	protected void update( HashMap<String, String> dados, HashMap<String, String> where )
	{
		try
		{
			String SQL = "UPDATE "+this.tabela+" SET ";
			Set<String> campos = dados.keySet();
			int contador = 0;
			for( String campo : campos )
			{
				if( contador == ( campos.size() -1 ) )
				{
					SQL += campo + "=?";
					contador++;
				}
				else
				{
					SQL += campo + "=?,";
					contador++;
				}
			}
			
			SQL += " WHERE ";
			Set<String> condicoes = where.keySet();
			contador = 0;
			for (String condicao : condicoes)
			{
				if( contador == ( condicoes.size() -1 ) )
				{
					SQL += condicao + "=?";
				}
				else
				{
					SQL += condicao + "=? AND ";
				}
			}
			System.out.println(SQL);
			this.query( SQL );
			contador = 1;
			for( String campo : campos )
			{
				this.psmt.setObject( contador, dados.get( campo ) );
				contador++;
			}
			
			for (String condicao : condicoes)
			{
				this.psmt.setObject( contador, where.get( condicao ) );
				contador++;
			}
			this.psmt.execute();
		}
		catch( SQLException e)
		{
			System.out.println( "LINHA 159" );
			e.printStackTrace();
		}
	}

	protected void select( HashMap<String, String> where )
	{
		try
		{
			String SQL = "SELECT * FROM "+this.tabela+" WHERE ";
			
			Set<String> condicoes = where.keySet();
			for (String condicao : condicoes)
			{
				SQL += condicao + "=? AND ";
			}
			SQL = SQL.substring(0, SQL.length()-5 );
			System.out.println(SQL);
			this.query( SQL );
			int contador = 1;
			for (String condicao : condicoes)
			{
				this.psmt.setObject( contador, where.get( condicao ) );
				contador++;
			}
			this.executar();
		}
		catch( SQLException e)
		{
			System.out.println( "LINHA 159" );
			e.printStackTrace();
		}
	}
	
	protected void select( String s ) throws SQLException
	{
		String SQL = "";
		if( s.equals("") )
		{
			SQL = "SELECT * FROM "+this.tabela ;
		}
		else
		{
			SQL = "SELECT "+s+" FROM "+this.tabela ;
		}
		System.err.println(SQL);
		this.query( SQL );
		this.executar();
	}
	
	protected void delete( HashMap<String, String> where )
	{
		try
		{
			String SQL = "DELETE FROM "+this.tabela+" WHERE ";
			Set<String> condicoes = where.keySet();
			int contador = 0;
			for (String condicao : condicoes)
			{
				if( contador == ( condicoes.size() -1 ) )
				{
					SQL += condicao + "=?";
					contador++;
				}
				else
				{
					SQL += condicao + "=? AND ";
					contador++;
				}
			}
			System.out.println(SQL);
			this.query( SQL );
			
			for (String condicao : condicoes)
			{
				this.psmt.setObject( contador, where.get( condicao ) );
				contador++;
			}
			this.psmt.execute();
		}
		catch( SQLException e )
		{
			System.out.println("214");
			e.printStackTrace();
		}
	}
}