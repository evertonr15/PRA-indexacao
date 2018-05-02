
import java.util.Comparator;
import java.util.Date;

public class NoComparadorData implements Comparator<No>{
	
	@Override
	public int compare(No arg0, No arg1) {
		int comparacao = arg0.getPedido().getCodigoCliente().compareTo(arg1.getPedido().getCodigoCliente());
		if(comparacao != 0){
			return comparacao;
		} else {
			Date dataDoPedido0 = new Date(arg0.getPedido().getDataPedido());
			Date dataDoPedido1 = new Date(arg1.getPedido().getDataPedido());
			
			return dataDoPedido0.compareTo(dataDoPedido1);
		}
	}
}
