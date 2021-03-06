import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	
	/*
	 * Vari�veis necess�rias para cria��o
	 * das janelas de intera��o com o usu�rio
	 */

	static JTextArea campoDeInformacoesGerais = new JTextArea(35, 90);
	
	static JTextArea campoDeRetornoPaginacao = new JTextArea(20, 70);
	
	static JTextField campoDeEntradaDoUsuario = new JTextField();
	
	static JTextField campoDeEntradaDoUsuarioPaginacao = new JTextField();
	
	static JTextField campoDeEntradaDoUsuarioListas = new JTextField();

	static GerenciadorDeArquivo arquivoDeVendas;
	
	static boolean fimPaginacao = false;
	
	static int paginaAtual = 0;
	
	static int totalPaginacao = 0;
	
	public static void main(String[] args) {

		final List<Cliente> clientes = Utils.criaClientes(); // instanciando uma lista de clientes
		final List<Produtos> produtos = Utils.criaProdutos(); // instanciando uma lista de produtos
		final List<Vendedor> vendedor = Utils.criaVendedor(); // instanciando uma lista de Vendedores
		arquivoDeVendas = new GerenciadorDeArquivo(); // instanciando um gerenciador de arquivo

		final JScrollPane outScrollInformacoesGerais = new JScrollPane(campoDeInformacoesGerais);
		campoDeInformacoesGerais.setEditable(false);
		final JScrollPane outScrollInformacoesDeRetorno = new JScrollPane(campoDeRetornoPaginacao);
		campoDeRetornoPaginacao.setEditable(false);

		JButton btnCriaRegQtd = new JButton("Criar pedidos por quantidade"); // Bot�o pedidos por quantidade
		btnCriaRegQtd.setToolTipText("Cria os pedidos de acordo com a quantidade digitada");
		btnCriaRegQtd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0"); // padr�o pagina��o igual a zero
				List<Pedido> paginaPedidos = new ArrayList<>(); // cria uma lista de pedidos
				Object[] entradaDoUsu�rio = {
					    "Quantidade de registros por p�gina (0 - Sem pagina��o): ", campoDeEntradaDoUsuarioPaginacao, // l� o campo de pagina��o digitado pelo usu�rio
					    "Quantidade de registros a serem criados: ", campoDeEntradaDoUsuario // l� o campo de quantidade de registros digitado pelo usu�rio
				}; // ac�o relacionado ao bot�o pedidos por quantidade

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Cria��o por quantidade de registros", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); // Caixa de di�logo para inserir a qntde de registros
				if (opt == 0) {
					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim()); // qtdRegistroPorPagina: campo que guarda a pagina��o
					int qtdRegistros = Integer.parseInt(campoDeEntradaDoUsuario.getText().trim()); //qtdRegistros: campo que guarda a quantidade de registros
					
					long tempoInicial = Calendar.getInstance().getTimeInMillis(); // in�cio da contagem de tempo da escrita do arquivo
					arquivoDeVendas.criarEAbrirArquivoParaEscrita(); // abertura do arquivo para escrita
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){ // se possui pagina��o
						while (nPedido <= qtdRegistros) {
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos); // caso a qntde de pedidos atingiu o limite que o usu�rio solicitou, salva a p�gina no arquivo.
								paginaPedidos.clear();
							} 
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor)); // gera uma linha de pedido e adiciona em paginaPedidos 
							nPedido++;
						}
						if(paginaPedidos.size() > 0){ // Certifica��o das situa��es em que o n�mero de pedidos n�o s�o m�ltiplos do n�mero da pagina��o
							arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
							paginaPedidos.clear();
						} 
					}else{ // caso n�o possui pagina��o
						while (nPedido <= qtdRegistros) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor)); //escreve linha a linha no arquivo
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita(); // fecha arquivo
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial; // c�lculo do tempo de escrita do arquivo em ms
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Cria��o de arquivos por quantidade");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nPagina��o selecionada: "+qtdRegistroPorPagina);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nQuantidade de registros selecionada: "+qtdRegistros);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnCriaRegTamanho = new JButton("Criar pedidos por tamanho (KB)"); // Bot�o pedidos por tamanho de arquivo
		btnCriaRegTamanho.setToolTipText("Cria os pedidos de acordo com o tamanho digitado (KB)");
		btnCriaRegTamanho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				List<Pedido> paginaPedidos = new ArrayList<>();
				Object[] entradaDoUsu�rio = {
					    "Quantidade de registros por p�gina (0 - Sem pagina��o): ", campoDeEntradaDoUsuarioPaginacao, // qtdRegistroPorPagina: campo que guarda a pagina��o
					    "Tamanho do arquivo (KB): ", campoDeEntradaDoUsuario // l� o campo de tamanho do arquivo digitado pelo usu�rio
				};// ac��o relacionado ao bot�o pedidos por tamanho de arquivo
				
				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Digite o tamanho desejado (KB)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); // Caixa de di�logo para inserir o tamanho do arquivo
				if (opt == 0) {
					long tamSolicitado;

					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim());
					tamSolicitado = Integer.valueOf(campoDeEntradaDoUsuario.getText().trim()) * 1024;

					long tempoInicial = Calendar.getInstance().getTimeInMillis(); // in�cio da contagem de tempo da escrita do arquivo
					arquivoDeVendas.criarEAbrirArquivoParaEscrita(); // abertura do arquivo para escrita
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){ // se possui pagina��o
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) { //verifica se o tamanho atual do arquivo 
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
								paginaPedidos.clear();
							}
							else if (qtdRegistroPorPagina/(tamSolicitado/1024)>1) { // verifica se a pagina��o � maior que o tamanho em KB do arquivo
								arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));// escreve linha a linha no arquivo
								nPedido++;
							}
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
					}else{ // sem pagina��o
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));// escreve linha a linha no arquivo
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Cria��o de arquivos por tamanho (KB)");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo selecionado: "+tamSolicitado/1024+ " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo gerado: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnConsultaGeral = new JButton("Consultar base"); // Bot�o consultar base
		btnConsultaGeral.setToolTipText("Exibe os registros do arquivo gerado");
		btnConsultaGeral.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("50");
				campoDeEntradaDoUsuarioListas.setText("2");
				paginaAtual = 0;
				Object[] entradaDoUsu�rio = {"Quantidade de registros por p�gina: ", campoDeEntradaDoUsuarioPaginacao, "Quantidade de listas - M�nimo 2: ", campoDeEntradaDoUsuarioListas};// ac�o relacionado ao bot�o consultar base

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Consulta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Ordenar por c�digo do cliente", "Ordenar por c�digo do cliente + Data do pedido"}, null); // Caixa de di�logo para consulta dos dados
				int tipoDeOrdenacao;
				if (opt == 0) {
					tipoDeOrdenacao = 1;
				} else {
					tipoDeOrdenacao = 2;
				}
					
				final int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim()); // verifica a qntd. de registros por p�gina
				final int qtdDeListas = Integer.parseInt(campoDeEntradaDoUsuarioListas.getText().trim()); // verifica a qntd. de Listas
				
				final JButton btnAnterior = new JButton("Anterior"); // Cria��o do bot�o anterior
				btnAnterior.setToolTipText("");
				btnAnterior.addActionListener(new ActionListener() {// A��o ao usar o bot�o
					@Override
					public void actionPerformed(ActionEvent e) {
						if(paginaAtual > 0){// Verifica se n�o � a primeira p�gina 
							GUI.paginaAtual--;
							GUI.campoDeRetornoPaginacao.setText(null);
							arquivoDeVendas.abrirArquivoParaLeituraIndexado();
							arquivoDeVendas.recuperarArquivoPaginacaoGUIIndexado(qtdRegistroPorPagina);
							arquivoDeVendas.fecharArquivoParaLeituraIndexado();
							GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
						}
					}
				});
				
				final JButton btnProximo = new JButton("Pr�ximo");// Cria��o do bot�o pr�ximo
				btnProximo.setToolTipText("");
				btnProximo.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
							GUI.paginaAtual++;
							GUI.campoDeRetornoPaginacao.setText(null);
							arquivoDeVendas.abrirArquivoParaLeituraIndexado();
							arquivoDeVendas.recuperarArquivoPaginacaoGUIIndexado(qtdRegistroPorPagina);
							if(GUI.fimPaginacao){// verifica se n�o � a �ltima p�gina
								GUI.paginaAtual--;
								arquivoDeVendas.fecharArquivoParaLeituraIndexado();
								arquivoDeVendas.abrirArquivoParaLeituraIndexado();
								arquivoDeVendas.recuperarArquivoPaginacaoGUIIndexado(qtdRegistroPorPagina);
							}
							arquivoDeVendas.fecharArquivoParaLeituraIndexado();
							GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
					}
				});
				
				long tempoInicial = Calendar.getInstance().getTimeInMillis();
				
				arquivoDeVendas.abrirArquivoParaLeitura();
				if(qtdRegistroPorPagina > 0){// Leitura com pagina��o
					
					arquivoDeVendas.recuperarArquivoGUIIndexados(qtdDeListas, tipoDeOrdenacao);// L� arquivo todo
					arquivoDeVendas.fecharArquivoParaLeitura();
					arquivoDeVendas = null;
					
					arquivoDeVendas = new GerenciadorDeArquivo();
					arquivoDeVendas.abrirArquivoParaLeituraIndexado();
					arquivoDeVendas.recuperarArquivoPaginacaoGUIIndexado(qtdRegistroPorPagina);
					long quantidadeTotalDeLinhas = arquivoDeVendas.quantidadeDeLinhasDoArquivo();// Busca quantas linhas tem o arquivo
					
					totalPaginacao = (int) (quantidadeTotalDeLinhas / qtdRegistroPorPagina);// Divide a pagina��o escolhida pelo usu�rio pela quantidade total de linhas
					if(quantidadeTotalDeLinhas % qtdRegistroPorPagina  != 0) {// Se a divis�o sobrar resto, adiciona uma p�gina
						totalPaginacao++;
					}
					arquivoDeVendas.fecharArquivoParaLeituraIndexado();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nTempo de Execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################\n");
					
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Leitura por pagina��o");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
			
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
					JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Pagina��o", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {btnAnterior, btnProximo}, null);
				}else{ // Leitura sem pagina��o
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################");
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\nOp��o selecionada: Consultar base");
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nRegistros: ");
					arquivoDeVendas.recuperarArquivoGUIIndexados(qtdDeListas, tipoDeOrdenacao);// L� arquivo todo
					arquivoDeVendas.fecharArquivoParaLeitura();
					arquivoDeVendas = null;
					
					arquivoDeVendas = new GerenciadorDeArquivo();
					arquivoDeVendas.abrirArquivoParaLeituraIndexado();
					arquivoDeVendas.recuperarArquivoGUIOrdenado();
					arquivoDeVendas.fecharArquivoParaLeituraIndexado();
					
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nTempo de Execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################\n");
					
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Leitura completa");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
			
					
					JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Total", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				}
			}
		});

		JOptionPane.showOptionDialog(null, outScrollInformacoesGerais, "PRA - Vendas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { btnCriaRegQtd,btnCriaRegTamanho,btnConsultaGeral }, btnConsultaGeral); // mostrando a p�gina principal
		
	}
	
	
}


