package Modelo.Infos;

public class InfoPostIt {
	private String titulo;
	private String conteudo;
	private String emissor; //Guarda o login de quem enviou o post-it.  -eh necessario?-
	
	public InfoPostIt(String titulo, String conteudo, String emissor){
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.emissor = emissor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public String getEmissor() {
		return emissor;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
