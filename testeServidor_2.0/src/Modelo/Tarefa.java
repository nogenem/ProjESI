package Modelo;

import Modelo.Infos.InfoTarefa;

public class Tarefa {

	private InfoTarefa info;
	
	public Tarefa(InfoTarefa info){
		this.info = info;
	}
	
	public void modificarTarefa(InfoTarefa info){
		this.info = info;
	}
	
	public InfoTarefa visualizarTarefa(){
		return info;
	}
}
