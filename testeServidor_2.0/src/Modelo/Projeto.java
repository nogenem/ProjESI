package Modelo;

import java.util.HashMap;
import java.util.Set;
import Modelo.Infos.InfoTarefa;

public class Projeto {

	private String nome;
	private int id_equipe;
	private int id_proj;
	
	public Projeto(String nome){
		this.nome = nome;
	}
	
	public Projeto(String nome, int id_equipe, int id_proj)
	{
		this.nome = nome;
		this.id_equipe = id_equipe;
		this.id_proj = id_proj;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getIdEquipe()
	{
		return id_equipe;
	}
	
	public void setIdEquipe(int id_equipe)
	{
		this.id_equipe = id_equipe;
	}
	
	public int getIdProj()
	{
		return id_proj;
	}
	
	public void setIdProj(int id_proj)
	{
		this.id_proj = id_proj;
	}
}
