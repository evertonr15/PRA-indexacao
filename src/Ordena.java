

import java.util.ArrayList;

import java.util.List;
import java.util.PriorityQueue;

/*
 *
 * Given K Sorted List merge them into a sort list
 */

public class Ordena {
	PriorityQueue<No> fila;

	public Ordena(int tipoDeOrdenacao) {
		if(tipoDeOrdenacao == 1){
			fila = new PriorityQueue<>(50, new NoComparador());
		} else{
			fila = new PriorityQueue<>(50, new NoComparadorData());
		}
	}

	public List<Pedido> misturaLista(List<List<Pedido>> entrada) {
		List<Pedido> saida = new ArrayList<>();
		if (entrada == null || entrada.isEmpty())
			return null;

		int[] indice = new int[entrada.size()];
		montarPilha(entrada, indice);
		GerenciadorDeArquivo arquivoDeVendasIndexado = new GerenciadorDeArquivo();
		arquivoDeVendasIndexado.criarEAbrirArquivoParaEscritaIndexado();
		while (!fila.isEmpty()) {
			No no = fila.remove();
			int listaIndices = no.getIndexList();			
			saida.add(no.getPedido());
			if(saida.size() == 10000){
				arquivoDeVendasIndexado.salvaPedidoIndexado(saida);
				saida.clear();
			}
			if (indice[listaIndices] < entrada.get(listaIndices).size()) {
				fila.add(new No(entrada.get(listaIndices).get(indice[listaIndices]),
						listaIndices));
				indice[listaIndices] = ++indice[listaIndices];
			}
		}
		if(!saida.isEmpty()){
			arquivoDeVendasIndexado.salvaPedidoIndexado(saida);
			saida.clear();
		}
		arquivoDeVendasIndexado.fecharArquivoParaEscritaIndexado();
		return saida;
		
	}
	
   /*
    * Creating an initial Heap.
    */

	private void montarPilha(List<List<Pedido>> input, int[] indice) {

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