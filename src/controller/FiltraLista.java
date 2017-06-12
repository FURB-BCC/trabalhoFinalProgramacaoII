package controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import model.CD;

public class FiltraLista {

	private static final FiltraLista uniqueInstance = new FiltraLista();

	public Collection<CD> filtraLista(String chave, HashSet<CD> lista) {
		Set<String> artistas = lista.stream()
								    .map(CD::getArtista)
								    .filter(a -> a.toLowerCase().contains(chave.toLowerCase()))
								    .collect(Collectors.toSet());
		
		Set<String> albuns = lista.stream()
								  .map(CD::getTitulo)
								  .filter(a -> a.toLowerCase().contains(chave.toLowerCase()))
								  .collect(Collectors.toSet());
		
		return lista.stream()
					.filter(c -> artistas.contains(c.getArtista()) | albuns.contains(c.getTitulo()))
					.collect(Collectors.toList());
	}
	
	public static FiltraLista getUniqueinstance() {
		return uniqueInstance;
	}
}
