package Modelo.Persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.Usuario;

public class UsuarioDao extends Dao
{
	public UsuarioDao(ConexaoBanco c, String tabela)
	{
		super(c, tabela);
	}

	public void adicionarEquipe( String login, int idEquipe ) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "ID_EQUIPE" , ""+idEquipe );
		
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "LOGIN" , login );
		
		update( dados , condicoes ); 
	}
	
	public void removerEquipe( String login) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "ID_EQUIPE" , "NULL" );
		
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "LOGIN" , login );
		
		update( dados , condicoes ); 
	}
	
	public void inserirUsuario( Usuario user ) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "NOME" , user.getNome() );
		dados.put( "LOGIN" , user.getLogin() );
		dados.put( "NIVEL" , user.getNivel()+"" );
		dados.put( "SENHA" , user.getSenha() );
		this.insert(dados);
	}
	
	public Set<Usuario> listAll() throws Exception
	{
		this.select("");
		ResultSet result = this.getResultSet();
		Set<Usuario> setReturn = new HashSet<>();
		
		while( result != null && result.next() )
		{
			Usuario user =  new Usuario( result.getString( "LOGIN" ) ,result.getString( "SENHA" ) );
			user.setIdEquipe( result.getInt( "ID_EQUIPE" ) );
			user.setNivel( result.getInt( "NIVEL" ) );
			setReturn.add(user);
		}
		return setReturn;
	}
	
	public Usuario getUsuario( String login ) throws Exception
	{
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "LOGIN" , login );
		
		select( condicoes );
		ResultSet result = getResultSet();
		
		if( result != null && result.next() )
		{
			Usuario user =  new Usuario( login ,result.getString( "SENHA" ) );
			user.setIdEquipe( result.getInt( "ID_EQUIPE" ) );
			user.setNivel( result.getInt( "NIVEL" ) );
			return user;
		}
		return null;
	}
}