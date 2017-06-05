package adapter;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import didatico.SubmarinoProducts;
import model.CD;
import model.Loja;

public class SubmarinoAdapter implements Loja{

	
	SubmarinoProducts sP = SubmarinoProducts.getInstance();
	
	
	@Override
	public boolean conectar(String usuario, String senha){
		try {
			sP.connect("furb", "furb");
		} catch (Exception e) {
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
		if(!sP.isConnected()){
			conectar("furb", "furb");
		}
		
		ArrayList<CD> listaCDs = new ArrayList<CD>(); 
		String[][] matriz;
		try{ 
			matriz = sP.getCDProducts();
		}catch(Exception e){
			return null;
		}
		
		String[] values = new String[4];		
		
		for (int linha = 0; linha < matriz.length; linha++) {
			values[0] = matriz[linha][0];
			values[1] = matriz[linha][1];
			values[2] = matriz[linha][2];
			values[3] = matriz[linha][3];
			listaCDs.add(new CD(values[0], values[2],Double.parseDouble(values[3]), "Submarino"));
		}
		return listaCDs;
	}

}
