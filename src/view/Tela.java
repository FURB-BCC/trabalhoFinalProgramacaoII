package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Titulo", "Banda/Artista", "Preço", "Loja" }));

		jScrollPane.setViewportView(jTable);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE).addGroup(
								Alignment.TRAILING,
								layout.createSequentialGroup()
										.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 99,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
										.addComponent(btnPesquisar)))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(btnPesquisar).addComponent(
						txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	protected void pesquisar() throws Exception {
		String parametroPesquisa = txtInput.getText();
		
		ArrayList retorno = pesquisaFacade.pesquisar(parametroPesquisa);
		
		for (Object object : retorno) {
			CD cd = (CD) object;
			modeloTabela.addRow(new String[] {cd.getTitulo(), cd.getArtista(),String.valueOf(cd.getPreco()), cd.getLoja()});
		}
		
	}

}
