package adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import conexao.SomLivreServidor;
import model.CD;
import model.Loja;

public class SomLivreAdapter extends SomLivreServidor implements Loja{

	@Override
	public boolean conectar(String usuario, String senha) {
		return registrar("conectar");
	}

	@Override
	public void desconectar() {
		registrar(null);
	}

	@Override
	public Collection procurar(String chave) {
		
		String[] listaCD = buscaCD();  
		ArrayList<CD> lista = new ArrayList<>();

		for (String string : listaCD) {
			String[] content = string.split("\\|");
			lista.add(new CD(content[1], content[0], Double.parseDouble(content[2]), "SomLivre"));
		}
		
		Set<String> artistas = lista.stream()
								    .map(CD::getArtista)
								    .filter(a -> a.contains(chave))
								    .collect(Collectors.toSet());
		
		Set<String> albuns = lista.stream()
								  .map(CD::getTitulo)
								  .filter(a -> a.contains(chave))
								  .collect(Collectors.toSet());
		
		return lista.stream()
					.filter(c -> artistas.contains(c.getArtista()) | albuns.contains(c.getTitulo()))
					.collect(Collectors.toList());
							     
		
	}

}
