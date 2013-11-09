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

	public void addEquipe( String login, int idEquipe ) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "ID_EQUIPE" , ""+idEquipe );
		
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "LOGIN" , login );
		
		update( dados , condicoes ); 
	}
	
	public void removeEquipe( String login) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "ID_EQUIPE" , null );
		
		HashMap<String, String> condicoes = new HashMap<>();
		condicoes.put( "LOGIN" , login );
		
		update( dados , condicoes ); 
	}
	
	public void addUsuario( Usuario user ) throws Exception
	{
		HashMap<String, String> dados = new HashMap<>();
		dados.put( "NOME" , user.getNome() );
		dados.put( "LOGIN" , user.getLogin() );
		dados.put( "NIVEL" , user.getNivel()+"" );
		dados.put( "SENHA" , user.getSenha() );
		this.insert(dados);
	}
	
	public Set<Usuario> listUsuario() throws Exception
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
	
	public Set<String> listMembers(int id_equipe) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put("ID_EQUIPE", ""+id_equipe);
		
		this.select(cond);
		
		ResultSet result = this.getResultSet();
		Set<String> retorno = new HashSet<>();
		
		while(result != null && result.next())
		{
			retorno.add(result.getString("LOGIN"));
		}
		return retorno;
	}
	
	public Usuario getUsuario( String login ) throws Exception
	{
		HashMap<String, String> cond = new HashMap<>();
		cond.put( "LOGIN" , login );
		
		select( cond );
		ResultSet result = getResultSet();
		
		if( result != null && result.next() )
		{
			Usuario user =  new Usuario( login, result.getString( "SENHA" ) );
			user.setIdEquipe( result.getInt( "ID_EQUIPE" ) );
			user.setNivel( result.getInt( "NIVEL" ) );
			user.setIdUsuario( result.getInt("ID_USUARIO") );
			return user;
		}
		return null;
	}
}