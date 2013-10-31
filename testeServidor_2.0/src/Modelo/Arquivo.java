package Modelo;

import Modelo.Infos.InfoArquivo;

public class Arquivo {
	private InfoArquivo info;
	
	public Arquivo(InfoArquivo info){
		this.info = info;
	}
	
	public InfoArquivo visualizarArquivo(){
		return info;
	}
	
	public void modificarArquivo(InfoArquivo info){
		this.info = info;
	}
}
