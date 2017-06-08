package model;

import java.util.Comparator;



public class AlbumValorComparator implements Comparator<CD>{

	@Override
	public int compare(CD c1, CD c2) {

        String album1 = c1.getTitulo();
        String album2 = c2.getTitulo();
        int comparacaoNomeAlbum = album1.compareTo(album2);

        if (comparacaoNomeAlbum != 0) {
           return comparacaoNomeAlbum;
        } else {
           return new ValorComparator().compare(c1, c2);
        }
	}
}
