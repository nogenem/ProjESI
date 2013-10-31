package Modelo;

import java.util.HashMap;
import java.util.Set;

import Modelo.Infos.InfoTarefa;

public class Projeto {

	private String nome;
	private HashMap<String, Tarefa> tarefas;
	
	public Projeto(String nome){
		this.nome = nome;
		this.tarefas = new HashMap<>();
	}
	
	public String getNome() {
		return nome;
	}
	
	public HashMap<String, Tarefa> getTarefas() {
		return tarefas;
	}
	
	public Set<String> listarTarefas() throws Exception{
		return tarefas.keySet();
	}
	
	public void adicionarTarefa(InfoTarefa info) throws Exception{
		if(tarefas.containsKey(info.getTitulo()))
			throw new Exception("Tarefa ja adicionada a esse projeto.");
		tarefas.put(info.getTitulo(), new Tarefa(info));
	}
	
	public void removerTarefa(String titulo) throws Exception{
		if(!tarefas.containsKey(titulo))
			throw new Exception("Tarefa nao encontrada.");
		tarefas.remove(titulo);
	}
	
	public InfoTarefa visualizarTarefa(String titulo) throws Exception{
		if(!tarefas.containsKey(titulo))
			throw new Exception("Tarefa nao encontrada.");
		
		return tarefas.get(titulo).visualizarTarefa();
	}
	
	public void modificarTarefa(InfoTarefa info) throws Exception{
		if(!tarefas.containsKey(info.getTitulo()))
			throw new Exception("Tarefa nao encontrada.");
		
		tarefas.get(info.getTitulo()).modificarTarefa(info);
	}
}
