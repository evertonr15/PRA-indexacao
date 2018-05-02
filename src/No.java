

public class No {
	Pedido pedido;
	Integer fromListIndex;

	public No(Pedido pedido, int listaIndices) {
		this.pedido = pedido;
		this.fromListIndex = listaIndices;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public int getIndexList() {
		return fromListIndex;
	}

	public void setIndexList(int listaIndices) {
		this.fromListIndex = listaIndices;
	}

}