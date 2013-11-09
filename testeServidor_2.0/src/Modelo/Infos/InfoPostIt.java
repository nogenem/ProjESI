package Modelo.Infos;

public class InfoPostIt {
	private String titulo;
	private String conteudo;
	private String login_emissor; ////Guarda o login de quem enviou o post-it.
	private int id_emissor; //Guarda o id de quem enviou o post-it.  
	
	public InfoPostIt(String titulo, String conteudo, int id_emissor){
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.id_emissor = id_emissor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public int getIdEmissor() {
		return id_emissor;
	}
	
	public String getLoginEmissor()
	{
		return login_emissor;
	}
	
	public void setLoginEmissor(String login)
	{
		this.login_emissor = login;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
