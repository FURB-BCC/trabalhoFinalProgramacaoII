package adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import conexao.SomLivreServidor;
import controller.FiltraLista;
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
	public Collection<CD> procurar(String chave) {
		
		String[] listaCD = buscaCD();  
		HashSet<CD> lista = new HashSet<>();

		for (String string : listaCD) {
			String[] content = string.split("\\|");
			lista.add(new CD(content[1], content[0], Double.parseDouble(content[2]), "SomLivre"));
		}
		
		return FiltraLista.getUniqueinstance().filtraLista(chave, lista);
	}



}
