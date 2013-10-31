package Infos;

public class InfoArquivo {
	private String titulo;
	private String conteudo;
	
	public InfoArquivo(String titulo, String conteudo){
		this.titulo = titulo;
		this.conteudo = conteudo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getConteudo(){
		return conteudo;
	}
}
