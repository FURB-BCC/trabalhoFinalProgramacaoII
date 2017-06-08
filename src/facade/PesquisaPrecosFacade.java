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

		if (somLivreAdapter.conectar("furb", "furb")) {
			resultados.addAll(somLivreAdapter.procurar(chave));
		} else {
			throw new ConnectionRefusedException();
		}

		somLivreAdapter.desconectar();

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
		if (!base.exists()) {
			base.mkdirs();
		}
		File file = new File(PASTA_BASE + key + data + ".txt");

		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
		output.writeObject(cdsPesquisados);
		output.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<CD> ler(String key) {
		
		File base = new File(PASTA_BASE);
		if (!base.exists()){
			base.mkdirs();
		}

		ObjectInputStream input = null;
		try {
			File file = new File(PASTA_BASE + key);
			input = new ObjectInputStream(new FileInputStream(file));
			ArrayList<CD> ret = (ArrayList<CD>) input.readObject();
			input.close();
			return ret;
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<String> lerChaves() {

		File base = new File(PASTA_BASE);

		base.mkdirs();

		ArrayList<File> arquivos = listFilesForFolder(base);
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
