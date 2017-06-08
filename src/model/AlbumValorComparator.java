package model;

import java.util.Comparator;



public class AlbumValorComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {

		CD c1 = (CD) o1;
		CD c2 = (CD) o2;
		
        String album1 = c1.getTitulo();
        String album2 = c2.getTitulo();
        int comparacaoNomeAlbum = album1.compareTo(album2);

        if (comparacaoNomeAlbum != 0) {
           return comparacaoNomeAlbum;
        } else {
           Double valor1 = c1.getPreco();
           Double valor2 = c2.getPreco();
           return valor1.compareTo(valor2);
           
        }
		
	}
}
