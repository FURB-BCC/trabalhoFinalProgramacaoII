package model;

import java.util.Comparator;

public class ValorComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {

		CD c1 = (CD) o1;
		CD c2 = (CD) o2;

		Double valor1 = c1.getPreco();
		Double valor2 = c2.getPreco();
		return valor1.compareTo(valor2);

	}

}
