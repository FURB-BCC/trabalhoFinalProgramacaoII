package adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import controller.FiltraLista;
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
		
		HashSet<CD> listaCDs = new HashSet<CD>();
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
		return FiltraLista.getUniqueinstance().filtraLista(chave, listaCDs);
	}

}
