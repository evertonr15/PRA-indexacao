package MergeSort;

public class No {
	Integer data;
	Integer fromListIndex;

	public No(int data, int listaIndices) {
		this.data = data;
		this.fromListIndex = listaIndices;
	}

	public Integer getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getIndexList() {
		return fromListIndex;
	}

	public void setIndexList(int listaIndices) {
		this.fromListIndex = listaIndices;
	}

}