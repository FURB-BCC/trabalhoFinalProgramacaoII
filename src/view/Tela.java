package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import facade.PesquisaPrecosFacade;
import model.CD;

public class Tela extends JFrame {

	// Variables declaration - do not modify
	private javax.swing.JButton btnPesquisar;
	private javax.swing.JScrollPane jScrollPane;
	private javax.swing.JTable jTable;
	private javax.swing.JTextField txtInput;
	private DefaultTableModel modeloTabela;
	private PesquisaPrecosFacade pesquisaFacade;
	private ArrayList<CD> cdsPesquisados;
	// End of variables declaration

	public static void main(String[] args) throws Exception {
		new Tela();
	}

	Tela() {
		pesquisaFacade = new PesquisaPrecosFacade();
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		cdsPesquisados = new ArrayList();
				
		
		btnPesquisar = new javax.swing.JButton();
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					pesquisar();
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
				}
			}
		});
		txtInput = new javax.swing.JTextField();
		jScrollPane = new javax.swing.JScrollPane();
		jTable = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btnPesquisar.setText("Pesquisar");

		jTable.setModel(
				modeloTabela = new javax.swing.table.DefaultTableModel(
						new Object[][]{},
						new String[] { "Titulo", "Banda/Artista", "Preço", "Loja" }));

		jScrollPane.setViewportView(jTable);
		
		JButton btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					salvar();
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
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
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPesquisar)
						.addComponent(btSalvar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(layout);

		pack();
	}

	protected void salvar() throws IOException, ClassNotFoundException {
		String key = JOptionPane.showInputDialog("Informe a chave de salvamento");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String data = dtf.format(now); //2016/11/16 12:08:43
		
		pesquisaFacade.salvar(key, cdsPesquisados, data);
		
	}

	protected void pesquisar() throws Exception {
		String parametroPesquisa = txtInput.getText();
		
		ArrayList retorno = pesquisaFacade.pesquisar(parametroPesquisa);
		
		atualizaTable(retorno);
		
	}

	private void atualizaTable(ArrayList retorno) {
		limpaTable();
		for (Object object : retorno) {
			CD cd = (CD) object;
			if(cdsPesquisados != null)
				cdsPesquisados.add(cd);
			
			modeloTabela.addRow(new String[] {cd.getTitulo(), cd.getArtista(),String.valueOf(cd.getPreco()), cd.getLoja()});
		}
	}
	
	private void limpaTable(){
		modeloTabela.setRowCount(0);
	}
}
	