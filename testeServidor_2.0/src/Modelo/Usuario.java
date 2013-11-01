package Modelo;

public class Usuario {
	
	private String login;
	private String senha;
	private String nome;
	private int nivel; /* Nivel de acesso do usuario. 0 = adm; 1 = normal; */
	private boolean isOn; /* Variavel para checar se o usuario esta online no momento */
	private String equipeName; /* Variavel para guardar o nome da equipe q o usuario pertence */
	
	public Usuario(String login, String senha){
		this.login = login;
		this.senha = senha;
		this.isOn = false;
	}
	
	public Usuario(String login, String senha, String nome, int nivel){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.nivel = nivel;
		this.isOn = false;
	}
	
	public Usuario(String login, String senha, String nome, int nivel, boolean isOn){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.nivel = nivel;
		this.isOn = isOn;
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
	
	public boolean isOn() {
		return isOn;
	}
	
	public String getEquipeName(){
		return equipeName;
	}
	
	public void setNivel(int nivel) throws Exception {
		if(nivel < 0 || nivel > 1)
			throw new Exception("Nivel invalido! Nivel 0 = Administrador, Nivel 1 = Usuario normal.");
		this.nivel = nivel;
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	

	public void setEquipeName(String equipeName){
		this.equipeName = equipeName;
	}
	
	public boolean confereSenha(String senha){
		return this.senha.equals(senha);
	}
	
	public void desconectar(){
		setOn(false);
	}
}
