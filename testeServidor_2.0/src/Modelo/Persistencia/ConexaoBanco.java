package Modelo.Persistencia;


import java.sql.Connection;
import java.sql.DriverManager;


public class ConexaoBanco
{
	private static Connection conexao;
	private String host, login, senha,banco;
	
	public ConexaoBanco( String host, String banco, String login, String senha )
	{
		this.host = host;
		this.login = login;
		this.senha = senha;
		this.banco = banco;
		this.getConexao();
	}
	
	public ConexaoBanco( String banco, String login, String senha )
	{
		this.host = "localhost";
		this.login = login;
		this.senha = senha;
		this.banco = banco;
		this.getConexao();
	}
	
	public ConexaoBanco()
	{
		this.host = "localhost";
		this.banco = "GerenciamentoEquipe";
		this.login = "root";
		this.senha = "";
		this.getConexao();
	}
	
	public Connection getConexao()
	{
		if( this.conexao != null )
		{
			return this.conexao;
		}
		this.newConexao( this.host, this.login, this.senha );
		return this.conexao;
	}
	
	private void newConexao( String url, String login, String senha )
	{
		try
		{
			this.conexao = DriverManager.getConnection( "jdbc:mysql://"+this.host+"/"+this.banco, this.login, this.senha );
		}
		catch( Exception e )
		{
			System.out.println( e );
		}
	}
}