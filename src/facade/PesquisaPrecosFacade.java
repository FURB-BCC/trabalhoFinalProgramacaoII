package facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import adapter.SomLivreAdapter;
import adapter.SubmarinoAdapter;
import model.CD;
import model.ConnectionRefusedException;
import model.Pesquisa;

public class PesquisaPrecosFacade {

	private ArrayList<CD> resultados = new ArrayList<CD>();
	private SomLivreAdapter somLivreAdapter = new SomLivreAdapter();
	private SubmarinoAdapter submarinoAdapter = new SubmarinoAdapter();

	public ArrayList pesquisar(String chave) throws Exception {
		// Connectar, carregar todos os CDs da loja SomLivre e descontectar

		if (somLivreAdapter.conectar("furb", "furb")) {
			resultados.addAll(somLivreAdapter.procurar(chave));
		} else {
			throw new ConnectionRefusedException();
		}

		somLivreAdapter.desconectar();

		// Connectar, carretar todos os CDs da loja Submarino e desconectar

		if (submarinoAdapter.conectar("furb", "furb")) {
			resultados.addAll(submarinoAdapter.procurar(chave));
		} else {
			throw new ConnectionRefusedException();
		}

		submarinoAdapter.desconectar();

		return resultados;
	}

	public void salvar(String key, Collection cdsPesquisados, String data) throws FileNotFoundException, IOException, ClassNotFoundException {

		File file = new File("C:\\Temp\\TrabalhoFinalProgramacaoII.log");

		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));

		Collection listaPesquisasJaGravadas = ler();
		
		// se não existirem registros
		if(listaPesquisasJaGravadas == null)
			listaPesquisasJaGravadas = new ArrayList<>();

		listaPesquisasJaGravadas.add(new Pesquisa(data, key, cdsPesquisados));

		output.writeObject(listaPesquisasJaGravadas);
		output.close();

	}

	public Collection ler() throws FileNotFoundException, IOException {

		File file = new File("C:\\Temp\\TrabalhoFinalProgramacaoII.log");

		ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

		try{
			return (Collection) input.readObject();
		}catch (Exception e) {
			return null;
		}
	}

}
