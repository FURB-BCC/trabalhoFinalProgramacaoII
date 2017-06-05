package adapter;

import java.util.ArrayList;
import java.util.Collection;

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
		
		conectar("","");
		
		String[] listaCD = buscaCD();  
		ArrayList<CD> lista = new ArrayList<CD>();
		
		for (String string : listaCD) {
			String[] content = string.split("\\|");
			lista.add(new CD(content[1], content[0], Double.parseDouble(content[2]), "SomLivre"));
		}
		
		return lista;
	}

}
