package model;

import java.util.Comparator;

public class NomeBandaValorComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {

		CD c1 = (CD) o1;
		CD c2 = (CD) o2;
		
        String nome1 = c1.getArtista();
        String nome2 = c2.getArtista();
        int comparacaoNomeAlbum = nome1.compareTo(nome2);

        if (comparacaoNomeAlbum != 0) {
           return comparacaoNomeAlbum;
        } else {
           Double valor1 = c1.getPreco();
           Double valor2 = c2.getPreco();
           return valor2.compareTo(valor1);
           
        }
		
	}
}
