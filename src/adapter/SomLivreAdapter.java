package adapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import conexao.SomLivreServidor;
import model.Loja;

public class SomLivreAdapter extends SomLivreServidor implements Loja{

	@Override
	public boolean conectar(String usuario, String senha) {
		return registrar("conectar");
	}

	@Override
	public void desconectar() {
//		null ou "" desconecta;
		registrar(null);
		
	}

	@Override
	public Collection procurar(String chave) {
		List<String> listaCD = Arrays.asList(buscaCD());  
		return listaCD;
	}

}
