package Modelo;

public class Usuario {
	
	private String login;
	private String senha;
	private String nome;
	private int nivel; //Nivel de acesso do usuario. 0 = adm; 1 = normal; 
	
	private int idUsuario;
	private int idEquipe;
	
	public Usuario(String login, String senha){
		this.login = login;
		this.senha = senha;
	}
	
	public Usuario( String login, String senha, String nome, int nivel )
	{
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.nivel = nivel;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public int getIdEquipe()
	{
		return this.idEquipe;
	}
	
	public int getIdUsuario()
	{
		return this.idUsuario;
	}
	
	public void setIdEquipe( int idEquipe )
	{
		this.idEquipe = idEquipe;
	}
	
	public void setIdUsuario( int idUsuario )
	{
		this.idUsuario = idUsuario;
	}
	
	public void setNivel(int nivel) throws Exception {
		if(nivel < 0 || nivel > 1)
			throw new Exception("Nivel invalido! Nivel 0 = Administrador, Nivel 1 = Usuario normal.");
		this.nivel = nivel;
	}

	public boolean confereSenha(String senha){
		return this.senha.equals(senha);
	}
	
	public void desconectar(){
		
	}
}
