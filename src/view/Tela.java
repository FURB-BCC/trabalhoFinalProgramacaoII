package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import facade.PesquisaPrecosFacade;
import model.AlbumValorComparator;
import model.CD;
import model.NomeBandaValorComparator;
import model.ValorComparator;

public class Tela extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private javax.swing.JScrollPane jScrollPane;
	private javax.swing.JTable jTable;
	private javax.swing.JTextField txtInput;
	private DefaultTableModel modeloTabela;
	private PesquisaPrecosFacade pesquisaFacade;
	private ArrayList<CD> cdsPesquisados;
	private JComboBox<String> cbPesquisasAnteriores;
	private Comparator<CD> tipoComparacao;
	
	public static void main(String[] args) throws Exception {
		new Tela();
	}

	private Tela() {
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		pesquisaFacade = new PesquisaPrecosFacade();
		cdsPesquisados = new ArrayList<>();

		JButton btnPesquisar = new JButton();
		btnPesquisar.addActionListener(arg0 -> {
			try {
				pesquisar();
			} catch (Exception e) {
				trataExcecao(e);
			}
		});
		txtInput = new JTextField();
		jScrollPane = new JScrollPane();
		jTable = new JTable(){
			
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		btnPesquisar.setText("Pesquisar");

		jTable.setModel(modeloTabela = new DefaultTableModel(new Object[][] {},
				new String[] { "Album", "Banda/Artista", "Preço", "Loja" }));

		jScrollPane.setViewportView(jTable);

		JButton btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(arg0 -> {
			try {
				salvar();
			} catch (Exception e) {
				trataExcecao(e);
			}
		});

		cbPesquisasAnteriores = new JComboBox<String>();

		JButton btCarregar = new JButton("Carregar");
		btCarregar.addActionListener(e -> insereDadosNaTabela(cbPesquisasAnteriores.getSelectedItem() == null ? "" : cbPesquisasAnteriores.getSelectedItem().toString()));
		
		JComboBox<String> cbOrdenacao = new JComboBox<String>();
		
		cbOrdenacao.setModel(new DefaultComboBoxModel<String>(new String[] 
							 {"Decrescente de valor", 
							 "Alfabética pelo nome do Album e crescente de valor",
							 "Alfabética pelo nome do artista e decrescente de valor"}));
		
		cbOrdenacao.setSelectedIndex(0);
		cbOrdenacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alteraOrdenacao(cbOrdenacao.getSelectedIndex());
				} catch (Exception e1) {
					trataExcecao(e1);
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
			trataExcecao(e1);
		}

		setResizable(false);
		pack();
	}

	private void trataExcecao(Exception e) {
		if(e.getMessage() != null){
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(this, "Exceção " + e.getClass().toGenericString() + " foi lançada.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void alteraOrdenacao(int selectedIndex) throws Exception{

		switch (selectedIndex) {
		case 0:
			tipoComparacao = new ValorComparator();
			break;
		case 1:
			tipoComparacao = new AlbumValorComparator();
			break;
		case 2:
			tipoComparacao = new NomeBandaValorComparator();
			break;

		default:
			tipoComparacao = new ValorComparator();
		}
		
		pesquisar();
	}

	private void insereDadosNaTabela(String selectedItem) {

		ArrayList<CD> lista = pesquisaFacade.ler(selectedItem);
		
		if(lista == null)
			return;
		
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
		ArrayList<CD> retorno = pesquisaFacade.pesquisar(parametroPesquisa);
		atualizaTable(retorno);
	}

	private void atualizaTable(ArrayList<CD> retorno) {
		
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
		modeloTabela.addRow(new String[] { cd.getTitulo(),
										   cd.getArtista(),
										   String.valueOf(cd.getPreco()),
										   cd.getLoja() 
									     });
		cdsPesquisados.add(cd);
	}
}
