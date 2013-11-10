package Modelo.Infos;

public class InfoTarefa {
	private String titulo;
	private String descricao;
	private String dataInicio;
	private String dataTermino;
	private String recursos;
	private int id_projeto;
	
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
	
	public InfoTarefa(String titulo, String descricao, String dataInicio, String dataTermino, String recursos, int id_projeto){
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.recursos = recursos;
		this.id_projeto = id_projeto;
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
	
	public int getIdProjeto() {
		return id_projeto;
	}
	
	public void setIdProjeto(int id_projeto) {
		this.id_projeto = id_projeto;
	}
}
