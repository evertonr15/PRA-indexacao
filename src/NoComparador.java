


import java.util.Comparator;

public class NoComparador implements Comparator<No> {

	@Override
	public int compare(No arg0, No arg1) {
		return arg0.getPedido().getCodigoCliente().compareTo(arg1.getPedido().getCodigoCliente());
	}
}
