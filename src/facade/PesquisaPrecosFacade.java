package facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import adapter.SomLivreAdapter;
import adapter.SubmarinoAdapter;
import model.CD;
import model.ConnectionRefusedException;

public class PesquisaPrecosFacade {

	private ArrayList<CD> resultados;
	private final String PASTA_BASE = "C:\\Temp\\TrabalhoFinalProgramacaoII\\"; 
	public ArrayList<CD> pesquisar(String chave) throws Exception {

		resultados = new ArrayList<>();
		SubmarinoAdapter submarinoAdapter = new SubmarinoAdapter();
		SomLivreAdapter somLivreAdapter = new SomLivreAdapter();
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

	public void salvar(String key, ArrayList<CD> cdsPesquisados, String data)
			throws IOException, ClassNotFoundException {

		File base = new File(PASTA_BASE);
		if(!base.exists())
			base.mkdirs();
		
		
		File file = new File(PASTA_BASE + key + data + ".txt");
		

		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
		output.writeObject(cdsPesquisados);
		output.close();

	}

	public ArrayList<CD> ler(String key, String data) {

		File base = new File(PASTA_BASE);
		if(!base.exists())
			base.mkdirs();
		
		try {
			File file = new File(PASTA_BASE + key + data);
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			return (ArrayList<CD>) input.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	//TODO: REFATORAR REPETICAO
	public ArrayList<CD> ler(String key) {

		File base = new File(PASTA_BASE);
		if(!base.exists())
			base.mkdirs();
		
		try {
			File file = new File(PASTA_BASE + key);
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			return (ArrayList<CD>) input.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	public ArrayList<String> lerChaves(){
		
		ArrayList<File> arquivos = listFilesForFolder(new File(PASTA_BASE));
		ArrayList<String> chaves = new ArrayList<>();
		for (File file : arquivos) {
			chaves.add(file.getName());
		}
		return chaves;
	}

	public ArrayList<File> listFilesForFolder(final File folder) {
		ArrayList<File> files = new ArrayList<>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            files.add(fileEntry);
	        }
	    }
	    return files;
	}
}
