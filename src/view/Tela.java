package view;

import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Tela extends JFrame {


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField txtInput;
    // End of variables declaration                      
    
	public static void main(String[] args) throws Exception {
		new Tela();
	}

	Tela() {
		initComponents();
		setVisible(true);
	}

	  private void initComponents() {
		 btnPesquisar = new javax.swing.JButton();
	        txtInput = new javax.swing.JTextField();
	        jScrollPane = new javax.swing.JScrollPane();
	        jTable = new javax.swing.JTable();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	        btnPesquisar.setText("Pesquisar");

	        txtInput.setText("Nome");

	        jTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {
	                {null, null, null, null},
	                {null, null, null, null},
	                {null, null, null, null},
	                {null, null, null, null}
	            },
	            new String [] {
	                "Titulo", "Banda/Artista", "Preço", "Loja"
	            }
	        ));
	        jScrollPane.setViewportView(jTable);

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        layout.setHorizontalGroup(
	        	layout.createParallelGroup(Alignment.LEADING)
	        		.addGroup(layout.createSequentialGroup()
	        			.addContainerGap()
	        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
	        				.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
	        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
	        					.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
	        					.addPreferredGap(ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
	        					.addComponent(btnPesquisar)))
	        			.addContainerGap())
	        );
	        layout.setVerticalGroup(
	        	layout.createParallelGroup(Alignment.LEADING)
	        		.addGroup(layout.createSequentialGroup()
	        			.addContainerGap()
	        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
	        				.addComponent(btnPesquisar)
	        				.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	        			.addPreferredGap(ComponentPlacement.UNRELATED)
	        			.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
	        			.addContainerGap())
	        );
	        getContentPane().setLayout(layout);

	        pack();
	  }         

}
