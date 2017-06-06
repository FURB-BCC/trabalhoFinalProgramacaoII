package facade;

import java.util.ArrayList;

import adapter.SomLivreAdapter;
import adapter.SubmarinoAdapter;
import model.CD;
import model.ConnectionRefusedException;

public class PesquisaPrecosFacade {
	
	private ArrayList<CD> resultados = new ArrayList<CD>();		
	private SomLivreAdapter somLivreAdapter;
	private SubmarinoAdapter submarinoAdapter;
	
	public ArrayList pesquisar(String chave) throws Exception{
//		Connectar, carregar todos os CDs da loja SomLivre e descontectar
		if(somLivreAdapter.conectar("furb", "furb"))
			resultados.addAll(somLivreAdapter.procurar("todos"));
		else
			throw new ConnectionRefusedException();
		somLivreAdapter.desconectar();
		
//		Connectar, carretar todos os CDs da loja Submarino e desconectar
		if(submarinoAdapter.conectar("furb", "furb"))
			resultados.addAll(submarinoAdapter.procurar("todos"));
		else
			throw new ConnectionRefusedException();
		submarinoAdapter.desconectar();
		
//		Criado ArrayList de retorno
		ArrayList<CD> resultadoPesquisa = new ArrayList<>();
		
//		Pesquisar o termo no artista e/ou título do cd
		for (CD cd : resultados) {
			if(cd.getArtista().matches(chave)){
				resultadoPesquisa.add(cd);
			}
			if(!resultadoPesquisa.contains(cd) && cd.getTitulo().matches(chave)){
				resultadoPesquisa.add(cd);
			}
			else
				return resultadoPesquisa;
		}
		return resultadoPesquisa;
	}
	
	public void salvar(){
		
	}
	
	public void ler(){
		
	}

}
