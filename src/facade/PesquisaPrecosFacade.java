package facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

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
	
	public void salvar(String key, Collection cdsPesquisados, String data) throws FileNotFoundException, IOException{
		
		File file = new File("C:\\Temp\\TrabalhoFinalProgramacaoII.log");
		
		ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(file));

		FileWriter buff = new FileWriter(file,true);
		
		buff.write(key + " " + data);
		buff.close();
	
		output.writeObject(cdsPesquisados);		
		output.close();
		
		buff = new FileWriter(file,true);
		buff.write(" \\" + key);
		buff.close();
	}
	
	public Collection ler(){
		return null;
	}

}
