package view;

import adapter.SomLivreAdapter;
import facade.PesquisaPrecosFacade;

public class Tela {

	public static void main(String[] args) throws Exception {
		
		SomLivreAdapter s = new SomLivreAdapter();

		PesquisaPrecosFacade p = new  PesquisaPrecosFacade();
		p.pesquisar("Pitty").forEach(re -> System.out.println(re.toString()));
		
	}
}
