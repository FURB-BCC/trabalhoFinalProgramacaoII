package adapter;

import java.util.Collection;

import didatico.SubmarinoProducts;
import model.Loja;

public class SubmarinoAdapter implements Loja{

	
	SubmarinoProducts sP;
	
	
	@Override
	public boolean conectar(String usuario, String senha) {
		try {
			sP.connect("furb", "furb");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sP.isConnected();
	}

	
	@Override
	public void desconectar() {
		sP.disconnect();
		
	}

	@Override
	public Collection procurar(String chave) {
		// TODO Auto-generated method stub
		return null;
	}

}
