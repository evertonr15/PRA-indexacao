import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.stream.Stream;

public class GerenciadorDeArquivo {

	private String nomeDoArquivo = "PRA-vendas.txt";

	private File arquivo = null;

	FileWriter fWriter = null;

	BufferedWriter buffWriter = null;
	
	private File arquivoIndexado = null;
	
	FileWriter fWriterIndexado = null;
	
	BufferedWriter buffWriterIndexado = null;

	FileReader fReader = null;

	BufferedReader buffReader = null;
	
	FileReader fReaderIndexado = null;

	BufferedReader buffReaderIndexado = null;

	public void criarEAbrirArquivoParaEscrita() {
		try {
			if (arquivo == null) {
				arquivo = new File(nomeDoArquivo);
			}

			if (!arquivo.createNewFile()) {
				arquivo.delete();
				arquivo.createNewFile();
			}
			fWriter = new FileWriter(arquivo, true);
			buffWriter = new BufferedWriter(fWriter);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void fecharArquivoParaEscrita() {
		try {
			if (buffWriter != null) {
				buffWriter.close();
			}
			if (fWriter != null) {
				fWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvaPedido(Pedido pedido) { // Escreve no arquivo pedido a pedido
		try {
			if (buffWriter != null) {
				String informacoesDoPedido = "" + pedido.getCodigoPedido();
				informacoesDoPedido += ";" + pedido.getCliente().getCodigoCliente();
				informacoesDoPedido += ";" + pedido.getVendedor().getCodigoVendedor();
				informacoesDoPedido += ";" + pedido.getDataPedido();
				informacoesDoPedido += ";" + pedido.getTotalDaVenda();
				//informacoesDoPedido += ";" + pedido.getProdutos().toString();

				buffWriter.write(informacoesDoPedido);
				buffWriter.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvaPedidoPaginacao(List<Pedido> pedidos) { // Escreve no arquivo por meio de uma lista de pedidos
		try {
			if (buffWriter != null) {
				String informacoesDoPedido = "";
				for (Pedido pedido : pedidos) {
					informacoesDoPedido = "" + pedido.getCodigoPedido();
					informacoesDoPedido += ";" + pedido.getCliente().getCodigoCliente();
					informacoesDoPedido += ";" + pedido.getVendedor().getCodigoVendedor();
					informacoesDoPedido += ";" + pedido.getDataPedido();
					informacoesDoPedido += ";" + pedido.getTotalDaVenda();
					//informacoesDoPedido += ";" + pedido.getProdutos().toString();
					buffWriter.write(informacoesDoPedido);
					buffWriter.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void criarEAbrirArquivoParaEscritaIndexado() {
		try {
			if (arquivoIndexado == null) {
				arquivoIndexado = new File("PRA-Indexado.txt");
			}

			if (!arquivoIndexado.createNewFile()) {
				arquivoIndexado.delete();
				arquivoIndexado.createNewFile();
			}
			fWriterIndexado = new FileWriter(arquivoIndexado, false);
			buffWriterIndexado = new BufferedWriter(fWriterIndexado);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void fecharArquivoParaEscritaIndexado() {
		try {
			if (buffWriterIndexado != null) {
				buffWriterIndexado.close();
			}
			if (fWriterIndexado != null) {
				fWriterIndexado.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvaPedidoIndexado(List<Pedido> pedidos) { // Escreve no arquivo por meio de uma lista de pedidos
		try {
			if (buffWriterIndexado != null) {
				String informacoesDoPedido = "";
				for (Pedido pedido : pedidos) {
					informacoesDoPedido = "" + pedido.getCodigoPedido();
					informacoesDoPedido += ";" + pedido.getCliente().getCodigoCliente();
					informacoesDoPedido += ";" + pedido.getVendedor().getCodigoVendedor();
					informacoesDoPedido += ";" + pedido.getDataPedido();
					informacoesDoPedido += ";" + pedido.getTotalDaVenda();
					informacoesDoPedido += ";" + pedido.getProdutos().toString();
					buffWriterIndexado.write(informacoesDoPedido);
					buffWriterIndexado.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirArquivoParaLeitura() {
		try {
			fReader = new FileReader(nomeDoArquivo);
			buffReader = new BufferedReader(fReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharArquivoParaLeitura() {
		try {
			if (fReader != null) {
				fReader.close();
			}
			if (buffReader != null) {
				buffReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirArquivoParaLeituraIndexado() {
		try {
			fReaderIndexado = new FileReader("PRA-Indexado.txt");
			buffReaderIndexado = new BufferedReader(fReaderIndexado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharArquivoParaLeituraIndexado() {
		try {
			if (fReaderIndexado != null) {
				fReaderIndexado.close();
			}
			if (buffReaderIndexado != null) {
				buffReaderIndexado.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 
	 * 1-cliente.getCodigoCliente() 
	 * 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 
	 * 4-totalDaVenda 
	 * 5-produtos.toString()
	 */
	public void recuperarArquivoGUI() {// Recupera todo o arquivo
		String linhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		StringBuilder retornoDaLeitura = new StringBuilder();
		String[] registroDoPedido;
		String produtosToString;
		String[] produtos;
		String[] produtosSplit;
		try {
			while ((linhaAtual = buffReader.readLine()) != null) {// enquanto não ler até a ultima linha
				registroDoPedido = linhaAtual.split(";");

				retornoDaLeitura.append("\nCódigo do pedido: " + registroDoPedido[0]);
				retornoDaLeitura.append("\nCódigo do vendedor: " + registroDoPedido[2]);
				retornoDaLeitura.append("\nData do pedido: " + registroDoPedido[3]);
				retornoDaLeitura.append("\nCódigo do Cliente: " + registroDoPedido[1]);
				retornoDaLeitura.append("\nProdutos: ");
				
				produtosToString = registroDoPedido[5].substring(1, registroDoPedido[5].length() - 1);
				produtos = produtosToString.split("/");
				for (Object produto : produtos) {
					if (!produto.toString().equals("")) {
						produtosSplit = ((String) produto).split(":");
						retornoDaLeitura.append("\n      Código do produto: " + produtosSplit[0] + " Valor do produto: R$ "+ decimalFormater.format(Float.valueOf(produtosSplit[1].trim().substring(0, produtosSplit[1].length() - 2))));
					}
				}
				retornoDaLeitura.append("\nTotal do pedido: R$ " + decimalFormater.format(Float.valueOf(registroDoPedido[4]))+ "\n");
			}
			GUI.campoDeRetornoPaginacao.setText(retornoDaLeitura.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void recuperarArquivoGUIIndexados(int quantidadeDeListas, int tipoDeOrdenacao) {// Recupera todo o arquivo
		String linhaAtual = null;
		String[] registroDoPedido;
		List<Produtos> listaDeProdutos = new ArrayList<>();
		
		TreeSet<Map.Entry<Integer, Pedido>> pedidos = new TreeSet<>(new Comparator<Map.Entry<Integer, Pedido>>()
		  {
		    @Override
		    public int compare(Map.Entry<Integer, Pedido> o1, Map.Entry<Integer, Pedido> o2)
		    {
		      int valueComparison = o1.getValue().getCodigoCliente().compareTo(o2.getValue().getCodigoCliente());
		      
		      if(valueComparison == 0){
		    	  Date dataDoPedido1 = new Date(o1.getValue().getDataPedido());
		    	  Date dataDoPedido2 = new Date(o2.getValue().getDataPedido());
					
		    	  return dataDoPedido1.compareTo(dataDoPedido2);
		      } else {
		    	  return valueComparison;  
		      }
		    }
		  });
		
		try {
			while ((linhaAtual = buffReader.readLine()) != null) {// enquanto não ler até a ultima linha
				registroDoPedido = linhaAtual.split(";");

				Calendar data = Calendar.getInstance();
				data.setTime(new Date(registroDoPedido[3]));
				
				pedidos.add(new AbstractMap.SimpleEntry<>(Integer.valueOf(registroDoPedido[0]), new Pedido(Integer.valueOf(registroDoPedido[0]), new Vendedor(Integer.valueOf(registroDoPedido[2])), new Cliente(Integer.valueOf(registroDoPedido[1])), data, listaDeProdutos)));
			}
			
			int i = 0;
			int countPedidos = pedidos.size();
			int quantidadeDeItensPorLista = countPedidos / quantidadeDeListas;
			List<List<Pedido>> listasDePedidos = new ArrayList<>();
			List<Pedido> listaCdClienteA = new ArrayList<>();
			List<Pedido> listaCdClienteB = new ArrayList<>();
			List<Pedido> listaCdClienteC = new ArrayList<>();
			List<Pedido> listaCdClienteD = new ArrayList<>();
			List<Pedido> listaCdClienteE = new ArrayList<>();
			List<Pedido> listaCdClienteF = new ArrayList<>();
			List<Pedido> listaCdClienteG = new ArrayList<>();
			List<Pedido> listaCdClienteH = new ArrayList<>();
			
			switch (quantidadeDeListas) {
				case 2:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else {
							listaCdClienteB.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					break;
				case 3:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else {
							listaCdClienteC.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					break;
				case 4:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 3){
							listaCdClienteC.add(codigoPedido.getValue());
						} else {
							listaCdClienteD.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					listasDePedidos.add(listaCdClienteD);
					break;
				case 5:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 3){
							listaCdClienteC.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 4){
							listaCdClienteD.add(codigoPedido.getValue());
						} else {
							listaCdClienteE.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					listasDePedidos.add(listaCdClienteD);
					listasDePedidos.add(listaCdClienteE);
					break;
				case 6:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 3){
							listaCdClienteC.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 4){
							listaCdClienteD.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 5){
							listaCdClienteE.add(codigoPedido.getValue());
						} else {
							listaCdClienteF.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					listasDePedidos.add(listaCdClienteD);
					listasDePedidos.add(listaCdClienteE);
					listasDePedidos.add(listaCdClienteF);
					break;
				case 7:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 3){
							listaCdClienteC.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 4){
							listaCdClienteD.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 5){
							listaCdClienteE.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 6){
							listaCdClienteF.add(codigoPedido.getValue());
						} else {
							listaCdClienteG.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					listasDePedidos.add(listaCdClienteD);
					listasDePedidos.add(listaCdClienteE);
					listasDePedidos.add(listaCdClienteF);
					listasDePedidos.add(listaCdClienteG);
					break;
				case 8:
					for (Entry<?, Pedido> codigoPedido : pedidos){
						if(i <= quantidadeDeItensPorLista){
							listaCdClienteA.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 2){
							listaCdClienteB.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 3){
							listaCdClienteC.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 4){
							listaCdClienteD.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 5){
							listaCdClienteE.add(codigoPedido.getValue());
						} else if(i <= quantidadeDeItensPorLista * 6){
							listaCdClienteF.add(codigoPedido.getValue());
						}  else if(i <= quantidadeDeItensPorLista * 7){
							listaCdClienteG.add(codigoPedido.getValue());
						} else {
							listaCdClienteH.add(codigoPedido.getValue());
						}
						i++;
					}
					listasDePedidos.add(listaCdClienteA);
					listasDePedidos.add(listaCdClienteB);
					listasDePedidos.add(listaCdClienteC);
					listasDePedidos.add(listaCdClienteD);
					listasDePedidos.add(listaCdClienteE);
					listasDePedidos.add(listaCdClienteF);
					listasDePedidos.add(listaCdClienteG);
					listasDePedidos.add(listaCdClienteH);
					break;
			}
			
			pedidos.clear();
		    
			Ordena indexacao = new Ordena(tipoDeOrdenacao);
			indexacao.misturaLista(listasDePedidos);
			listaCdClienteA.clear();
			listaCdClienteB.clear();
			listaCdClienteC.clear();
			listaCdClienteD.clear();
			listaCdClienteE.clear();
			listaCdClienteF.clear();
			listaCdClienteG.clear();
			listaCdClienteH.clear();
			listasDePedidos.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 
	 * 1-cliente.getCodigoCliente() 
	 * 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 
	 * 4-totalDaVenda 
	 * 5-produtos.toString()
	 */
	public void recuperarArquivoGUIOrdenado() {// Recupera todo o arquivo
		String linhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		StringBuilder retornoDaLeitura = new StringBuilder();
		String[] registroDoPedido;
		String produtosToString;
		String[] produtos;
		String[] produtosSplit;
		abrirArquivoParaLeituraIndexado();
		try {
			while ((linhaAtual = buffReaderIndexado.readLine()) != null) {// enquanto não ler até a ultima linha
				registroDoPedido = linhaAtual.split(";");

				retornoDaLeitura.append("\nCódigo do pedido: " + registroDoPedido[0]);
				retornoDaLeitura.append("\nCódigo do vendedor: " + registroDoPedido[2]);
				retornoDaLeitura.append("\nData do pedido: " + registroDoPedido[3]);
				retornoDaLeitura.append("\nCódigo do Cliente: " + registroDoPedido[1]);
				retornoDaLeitura.append("\n");
			}
			GUI.campoDeRetornoPaginacao.setText(retornoDaLeitura.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		fecharArquivoParaLeituraIndexado();
	}

	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 
	 * 1-cliente.getCodigoCliente() 
	 * 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 
	 * 4-totalDaVenda 
	 * 5-produtos.toString()
	 */
	public void recuperarArquivoPaginacaoGUI(int qtdRegistroPagina) {// Recupera arquivo com paginação
		String informacaoLinhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		StringBuilder retornoDaLeitura = new StringBuilder();
		String[] registroDoPedido;
		String produtosToString;
		String[] produtos;
		String[] produtosSplit;
		GUI.fimPaginacao = true;
		try {
			int posicaoLinhaDeOrigem = qtdRegistroPagina * GUI.paginaAtual;// Multiplica a quantidade de registro por páginas selecionada pelo usuário pelo número da página atual para saber a partir de qual linha será lido o registro 
			int posicaoLinhaAtual = 0;
			while (posicaoLinhaAtual < posicaoLinhaDeOrigem && (informacaoLinhaAtual = buffReader.readLine()) != null) {// Enquanto não chegar na linha calculada e ainda tiver linhas no arquivo
				posicaoLinhaAtual++;
			}
			posicaoLinhaAtual = 0;
			while ((informacaoLinhaAtual = buffReader.readLine()) != null && posicaoLinhaAtual < qtdRegistroPagina) { //Enquanto não atingir a quantidade de linhas da paginação e ainda tiver linhas
				GUI.fimPaginacao = false;// Informa que não está na última página
				registroDoPedido = informacaoLinhaAtual.split(";");

				retornoDaLeitura.append("\nCódigo do pedido: " + registroDoPedido[0]);
				retornoDaLeitura.append("\nCódigo do vendedor: " + registroDoPedido[2]);
				retornoDaLeitura.append("\nData do pedido: " + registroDoPedido[3]);
				retornoDaLeitura.append("\nCódigo do Cliente: " + registroDoPedido[1]);
				retornoDaLeitura.append("\nProdutos: ");

				produtosToString = registroDoPedido[5].substring(1, registroDoPedido[5].length() - 1);
				produtos = produtosToString.split("/");
				for (Object produto : produtos) {
					if (!produto.toString().equals("")) {
						produtosSplit = ((String) produto).split(":");
						retornoDaLeitura.append("\n      Código do produto: " + produtosSplit[0] + " Valor do produto: R$ " + decimalFormater.format(Float.valueOf(produtosSplit[1].trim().substring(0, produtosSplit[1].length() - 2))));
					}
				}
				retornoDaLeitura.append("\nTotal do pedido: R$ " + decimalFormater.format(Float.valueOf(registroDoPedido[4])) + "\n");
				posicaoLinhaAtual++;
			}
			GUI.campoDeRetornoPaginacao.setText(retornoDaLeitura.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 
	 * 1-cliente.getCodigoCliente() 
	 * 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 
	 * 4-totalDaVenda 
	 * 5-produtos.toString()
	 */
	public void recuperarArquivoPaginacaoGUIIndexado(int qtdRegistroPagina) {// Recupera arquivo com paginação
		String informacaoLinhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		StringBuilder retornoDaLeitura = new StringBuilder();
		String[] registroDoPedido;
		String produtosToString;
		String[] produtos;
		String[] produtosSplit;
		GUI.fimPaginacao = true;
		try {
			int posicaoLinhaDeOrigem = qtdRegistroPagina * GUI.paginaAtual;// Multiplica a quantidade de registro por páginas selecionada pelo usuário pelo número da página atual para saber a partir de qual linha será lido o registro 
			int posicaoLinhaAtual = 0;
			while (posicaoLinhaAtual < posicaoLinhaDeOrigem && (informacaoLinhaAtual = buffReaderIndexado.readLine()) != null) {// Enquanto não chegar na linha calculada e ainda tiver linhas no arquivo
				posicaoLinhaAtual++;
			}
			posicaoLinhaAtual = 0;
			while ((informacaoLinhaAtual = buffReaderIndexado.readLine()) != null && posicaoLinhaAtual < qtdRegistroPagina) { //Enquanto não atingir a quantidade de linhas da paginação e ainda tiver linhas
				GUI.fimPaginacao = false;// Informa que não está na última página
				registroDoPedido = informacaoLinhaAtual.split(";");

				retornoDaLeitura.append("\nCódigo do pedido: " + registroDoPedido[0]);
				retornoDaLeitura.append("\nCódigo do vendedor: " + registroDoPedido[2]);
				retornoDaLeitura.append("\nData do pedido: " + registroDoPedido[3]);
				retornoDaLeitura.append("\nCódigo do Cliente: " + registroDoPedido[1]);
				retornoDaLeitura.append("\n");
				posicaoLinhaAtual++;
			}
			GUI.campoDeRetornoPaginacao.setText(retornoDaLeitura.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public long tamanhoDoArquivo() {
		return arquivo != null ? arquivo.length() : 0;
	}

	public long quantidadeDeLinhasDoArquivo() {
		long numOfLines = 0;
		if (arquivoIndexado == null) {
			arquivoIndexado = new File("PRA-Indexado.txt");
		}
		try (Stream<String> lines = Files.lines(arquivoIndexado.toPath(), Charset.defaultCharset())) {
			numOfLines = lines.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numOfLines;
	}
}