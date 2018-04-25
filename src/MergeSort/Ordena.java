package MergeSort;

import java.util.ArrayList;

import java.util.List;
import java.util.PriorityQueue;

/*
 *
 * Given K Sorted List merge them into a sort list
 */

public class Ordena {
	PriorityQueue<No> fila;

	public Ordena() {
		fila = new PriorityQueue<No>(50, new NoComparador());
	}

	public List<?> misturaLista(List<List<Integer>> entrada) {
		List<Integer> saida = new ArrayList<Integer>();
		if (entrada == null)
			return null;
		if (entrada.isEmpty())
			return entrada;
		int[] indice = new int[entrada.size()];
		montarPilha(entrada, indice);
		while (!fila.isEmpty()) {
			No no = fila.remove();
			int listaIndices = no.getIndexList();
			saida.add(no.getData());
			if (indice[listaIndices] < entrada.get(listaIndices).size()) {
				fila.add(new No(entrada.get(listaIndices).get(indice[listaIndices]),
						listaIndices));
				indice[listaIndices] = ++indice[listaIndices];
			}
		}
		return saida;
		
	}
	
   /*
    * Creating an initial Heap.
    */

	private void montarPilha(List<List<Integer>> input, int[] indice) {

		for (int i = 0; i < input.size(); i++) {
			if (!input.get(i).isEmpty()) {
				fila.add(new No(input.get(i).get(0), i));
				indice[i] = ++indice[i];
			} else
				input.remove(i);
		}

	}

	/*public static void main(String[] args) {
		Ordena k = new Ordena();

		List<Integer> entrada1 = new ArrayList<Integer>();
		entrada1.add(823);
		entrada1.add(664);
		entrada1.add(582);
		entrada1.add(727);
		entrada1.add(511);

		List<Integer> entrada2 = new ArrayList<Integer>();
		entrada2.add(216);
		entrada2.add(868);
		entrada2.add(915);
		entrada2.add(304);
		entrada2.add(218);

		List<Integer> entrada3 = new ArrayList<Integer>();
		entrada3.add(3);
		entrada3.add(7);
		entrada3.add(11);

		List<List<Integer>> entradaA = new ArrayList<List<Integer>>();
		entradaA.add(entrada1);
		entradaA.add(entrada2);
		entradaA.add(entrada3);

		System.out.println("Merged List:" + k.mergeKList(entradaA));

	}*/

}