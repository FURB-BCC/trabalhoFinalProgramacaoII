package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import facade.PesquisaPrecosFacade;
import model.AlbumValorComparator;
import model.CD;
import model.NomeBandaValorComparator;
import model.ValorComparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Tela extends JFrame {

	private javax.swing.JScrollPane jScrollPane;
	private javax.swing.JTable jTable;
	private javax.swing.JTextField txtInput;
	private DefaultTableModel modeloTabela;
	private PesquisaPrecosFacade pesquisaFacade;
	private ArrayList<CD> cdsPesquisados;
	private JComboBox cbPesquisasAnteriores;
	private Comparator tipoComparacao;
	
	public static void main(String[] args) throws Exception {
		new Tela();
	}

	private Tela() {
		pesquisaFacade = new PesquisaPrecosFacade();
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		cdsPesquisados = new ArrayList<>();

		JButton btnPesquisar = new JButton();
		btnPesquisar.addActionListener(arg0 -> {
			try {
				pesquisar();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
			}
		});
		txtInput = new javax.swing.JTextField();
		jScrollPane = new javax.swing.JScrollPane();
		jTable = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btnPesquisar.setText("Pesquisar");

		jTable.setModel(modeloTabela = new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Titulo", "Banda/Artista", "Pre�o", "Loja" }));

		jScrollPane.setViewportView(jTable);

		JButton btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(arg0 -> {
			try {
				salvar();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
			}
		});

		cbPesquisasAnteriores = new JComboBox();

		JButton btCarregar = new JButton("Carregar");
		btCarregar.addActionListener(e -> insereDadosNaTabela(cbPesquisasAnteriores.getSelectedItem().toString()));
		
		JComboBox cbOrdenacao = new JComboBox();
		cbOrdenacao.setModel(new DefaultComboBoxModel(new String[] {"Decrescente de valor", "Alfabética pelo nome do álbum e crescente de valor", "Alfabética pelo nome do artista e decrescente de valor"}));
		cbOrdenacao.setSelectedIndex(0);
		cbOrdenacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alteraOrdenacao(cbOrdenacao.getSelectedIndex());
				} catch (Exception e1) {
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
							.addComponent(cbPesquisasAnteriores, 0, 232, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btCarregar)
							.addGap(68)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(cbOrdenacao, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtInput, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPesquisar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btSalvar)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPesquisar)
						.addComponent(btSalvar)
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbPesquisasAnteriores, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btCarregar))
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addComponent(cbOrdenacao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(layout);

		try {
			atualizarComboBox();
		} catch (Exception e1) {
			//System.out.println(e1.getMessage());
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}

		pack();
	}

	protected void alteraOrdenacao(int selectedIndex) throws Exception {

		switch (selectedIndex) {
		case 0:
			tipoComparacao = new ValorComparator();
			break;
			
		case 1:
			//alfabetica pelo nome do album e crescente de valor
			tipoComparacao = new AlbumValorComparator();
			break;
			
		case 2:
			//alfabetica pelo nome do artista e decrescente de valor
			tipoComparacao = new NomeBandaValorComparator();
			break;

		default:
			JOptionPane.showMessageDialog(null, "Erro no CB");
		}
		
		pesquisar();
	}

	private void insereDadosNaTabela(String selectedItem) {

		ArrayList<CD> lista = (ArrayList) pesquisaFacade.ler(selectedItem);
		
		limpaTable();
		
		atualizaTable(lista);
	}

	private void atualizarComboBox() throws FileNotFoundException, IOException {

		ArrayList<String> itens = pesquisaFacade.lerChaves();
		ArrayList<String> cbItens = getCbItens();
		for (String chave : itens) {
			if(!cbItens.contains(chave)){
				cbPesquisasAnteriores.addItem(chave);
			}
		}
	}

	private ArrayList<String> getCbItens(){
		
		ArrayList<String> itens = new ArrayList<>();
		int size = cbPesquisasAnteriores.getItemCount();
		for(int i = 0; i < size; i++){
			itens.add(cbPesquisasAnteriores.getItemAt(i).toString());
		}
		return itens;
		
	}
	private void salvar() throws IOException, ClassNotFoundException {
		String key = JOptionPane.showInputDialog("Informe a chave de salvamento");

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
		LocalDateTime now = LocalDateTime.now();
		String data = dtf.format(now);

		pesquisaFacade.salvar(key, cdsPesquisados, data);

		atualizarComboBox();

	}

	private void pesquisar() throws Exception {
		String parametroPesquisa = txtInput.getText();
		limpaTable();
		ArrayList retorno = pesquisaFacade.pesquisar(parametroPesquisa);
		atualizaTable(retorno);
	}

	private void atualizaTable(ArrayList retorno) {

		
		Collections.sort(retorno, tipoComparacao ==  null ? new ValorComparator() : tipoComparacao);
		
		for (Object object : retorno) {
			CD cd = (CD) object;
			insereCDNaTabela(cd);
		}
	}

	private void limpaTable() {
		modeloTabela.setRowCount(0);
	}

	private void insereCDNaTabela(CD cd) {
		modeloTabela
				.addRow(new String[] { cd.getTitulo(), cd.getArtista(), String.valueOf(cd.getPreco()), cd.getLoja() });
		cdsPesquisados.add(cd);
	}
}
