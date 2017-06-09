package model;

import java.util.Comparator;

public class NomeBandaValorComparator implements Comparator<CD>{

	@Override
	public int compare(CD c1, CD c2) {

        String nome1 = c1.getArtista();
        String nome2 = c2.getArtista();
        int comparacaoNomeAlbum = nome1.compareTo(nome2);

        if (comparacaoNomeAlbum != 0) {
           return comparacaoNomeAlbum;
        } else {
        	return new ValorComparator().compare(c1, c2);
        }
	}
}
