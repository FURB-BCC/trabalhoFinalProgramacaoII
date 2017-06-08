package adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import didatico.SubmarinoProducts;
import model.CD;
import model.Loja;

public class SubmarinoAdapter implements Loja {

	SubmarinoProducts sP = SubmarinoProducts.getInstance();

	@Override
	public boolean conectar(String usuario, String senha) {
		try {
			sP.connect("furb", "furb");
		} catch (Exception e) {
			return false;
		}
		return sP.isConnected();
	}

	@Override
	public void desconectar() {
		sP.disconnect();
	}

	@Override
	public Collection<CD> procurar(String chave) {
		
		ArrayList<CD> listaCDs = new ArrayList<CD>();
		String[][] matriz;
		try {
			matriz = sP.getCDProducts();
		} catch (Exception e) {
			return null;
		}

		String[] values = new String[4];

		for (String[] aMatriz : matriz) {
			values[0] = aMatriz[0];
			values[1] = aMatriz[1];
			values[2] = aMatriz[2];
			values[3] = aMatriz[3];
			listaCDs.add(new CD(values[0], values[2], Double.parseDouble(values[3]), "Submarino"));
		}
		
		Set<String> artistas = listaCDs.stream()
									    .map(CD::getArtista)
									    .filter(a -> a.toLowerCase().contains(chave.toLowerCase()))
									    .collect(Collectors.toSet());

		Set<String> albuns = listaCDs.stream()
									  .map(CD::getTitulo)
									  .filter(a -> a.toLowerCase().contains(chave.toLowerCase()))
									  .collect(Collectors.toSet());

		return listaCDs.stream()
						.filter(c -> artistas.contains(c.getArtista()) | albuns.contains(c.getTitulo()))
						.collect(Collectors.toList());

	}

}
