package model;

import java.util.Comparator;

public class ValorComparator implements Comparator<CD> {

	@Override
	public int compare(CD c1, CD c2) {
		Double valor1 = c1.getPreco();
		Double valor2 = c2.getPreco();
		return valor2.compareTo(valor1);
	}
}
