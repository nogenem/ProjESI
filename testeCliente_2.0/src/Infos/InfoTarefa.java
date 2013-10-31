package Infos;

public class InfoTarefa {
	private String titulo;
	private String descricao;
	private String dataInicio;
	private String dataTermino;
	private String recursos;
	
	public InfoTarefa(String titulo, String descricao){
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public InfoTarefa(String titulo, String descricao, String dataInicio, String dataTermino, String recursos){
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.recursos = recursos;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	
	public String getDataTermino() {
		return dataTermino;
	}
	
	public String getRecursos() {
		return recursos;
	}
}
