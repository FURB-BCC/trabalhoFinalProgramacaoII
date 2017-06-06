package model;

import java.util.Collection;

public interface Loja {
	
	public boolean conectar(String usuario, String senha);
	
	public void desconectar();
	
	public Collection procurar(String chave);
}
