package Modelo;

import Modelo.Infos.InfoPostIt;

public class PostIt {
	private InfoPostIt info;
	
	public PostIt(InfoPostIt info){
		this.info = info;
	}
	
	public InfoPostIt visualizarPostIt(){
		return info;
	}
	
	public void modificarPostIt(InfoPostIt info){
		this.info = info;
	}
}
