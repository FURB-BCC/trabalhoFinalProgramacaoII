package facade;

import java.util.ArrayList;

import adapter.SomLivreAdapter;
import adapter.SubmarinoAdapter;
import model.CD;
import model.ConnectionRefusedException;

public class PesquisaPrecosFacade {
	
	private ArrayList<CD> resultados = new ArrayList<CD>();		
	private SomLivreAdapter somLivreAdapter = new SomLivreAdapter();
	private SubmarinoAdapter submarinoAdapter = new SubmarinoAdapter();
	
	public ArrayList pesquisar(String chave) throws Exception{
//		Connectar, carregar todos os CDs da loja SomLivre e descontectar
		
		if(somLivreAdapter.conectar("furb", "furb")){
			resultados.addAll(somLivreAdapter.procurar("Pitty"));
		}
		else{
			throw new ConnectionRefusedException();
		}
		
		somLivreAdapter.desconectar();
		
//		Connectar, carretar todos os CDs da loja Submarino e desconectar
		
		if(submarinoAdapter.conectar("furb", "furb")){
			resultados.addAll(submarinoAdapter.procurar("Pitty"));
		}
		else{
			throw new ConnectionRefusedException();
		}
		
		submarinoAdapter.desconectar();
		

		return resultados;
	}
	
	public void salvar(){
		
	}
	
	public void ler(){
		
	}

}
