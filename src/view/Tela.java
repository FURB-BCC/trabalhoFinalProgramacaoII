package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import facade.PesquisaPrecosFacade;
import model.CD;
import model.Pesquisa;

public class Tela extends JFrame {


    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField txtInput;
    private DefaultTableModel modeloTabela;
    private PesquisaPrecosFacade pesquisaFacade;
    private ArrayList<CD> cdsPesquisados;
    private JComboBox cbPesquisasAnteriores;


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
            try{
                pesquisar();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
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
                        new String[] { "Titulo", "Banda/Artista", "Pre�o", "Loja" }));

        jScrollPane.setViewportView(jTable);

        JButton btSalvar = new JButton("Salvar");
        btSalvar.addActionListener(arg0 -> {
            try{
                salvar();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro! " + e.getMessage());
            }
        });

        cbPesquisasAnteriores = new JComboBox();

        JButton btCarregar = new JButton("Carregar");
        btCarregar.addActionListener(e -> insereDadosNaTabela(cbPesquisasAnteriores.getSelectedItem().toString()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(cbPesquisasAnteriores, 0, 232, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btCarregar)
                                                .addGap(68)
                                                .addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
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
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                .addContainerGap())
        );
        getContentPane().setLayout(layout);

        try{
            atualizarComboBox();
        }catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        pack();
    }

    private void insereDadosNaTabela(String selectedItem) {


        ArrayList<Pesquisa> lista = (ArrayList) pesquisaFacade.ler();

        for (Pesquisa pesquisa : lista) {
            if(selectedItem.contains(pesquisa.getKey())){
                for (CD cd : pesquisa.getCds()) {
                    insereCDNaTabela(cd);
                }
            }
        }
    }

    private void atualizarComboBox() throws FileNotFoundException, IOException {

        ArrayList<Pesquisa> itens = (ArrayList) pesquisaFacade.ler();

        for (Pesquisa pesquisa : itens) {
            cbPesquisasAnteriores.addItem(pesquisa.getKey() + pesquisa.getData());
        }
    }

    private void salvar() throws IOException, ClassNotFoundException {
        String key = JOptionPane.showInputDialog("Informe a chave de salvamento");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String data = dtf.format(now); //2016/11/16 12:08:43

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

        for (Object object : retorno) {
            CD cd = (CD) object;
            insereCDNaTabela(cd);
        }
    }

    private void limpaTable(){
        modeloTabela.setRowCount(0);
    }

    private void insereCDNaTabela(CD cd){
        modeloTabela.addRow(new String[] {cd.getTitulo(), cd.getArtista(),String.valueOf(cd.getPreco()), cd.getLoja()});
        cdsPesquisados.add(cd);
    }
}
	