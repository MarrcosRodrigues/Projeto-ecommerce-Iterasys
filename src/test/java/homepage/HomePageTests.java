package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests{
	
	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}
	
	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
	}
	
	ProdutoPage produtoPage;
	
	String nomeProduto_ProdutoPage;
	String precoProduto_ProdutoPage;
	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice =0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);
		
		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);
		
		produtoPage = homePage.clicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);
		
		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
	}
	
	LoginPage loginpage;
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		//Clicar no bot?o Sign In na home page
		loginpage = homePage.clicarBotaoSignIn();
		
		//Preencher usuario e senha
		loginpage.preencherEmail("m4rcostestes@gmail.com");
		loginpage.preencherPassword("teste");
		//Clicar no botao Sign In para logar
		loginpage.clicarBotaoSignIn();
		
		//Validar se o usuario est? logado
		assertThat(homePage.estaLogado("Marcos Rodrigues"), is(true));
		
		carregarPaginaInicial();
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/massaTest_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTest, String email, String password, String nomeUsuario, String resultado) {
		//Clicar no bot?o Sign In na home page
		loginpage = homePage.clicarBotaoSignIn();
				
		//Preencher usuario e senha
		loginpage.preencherEmail(email);
		loginpage.preencherPassword(password);
		//Clicar no botao Sign In para logar
		loginpage.clicarBotaoSignIn();
				
		boolean esperado_loginOK;
		if(resultado.equals("positivo"))
				esperado_loginOK = true;
		else 
				esperado_loginOK = false;
				
		//Validar se o usuario est? logado
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_loginOK));
		
		capturarTela(nomeTest, resultado);
				
		if(esperado_loginOK)
				homePage.clicarBotaoSignOut();
				
		carregarPaginaInicial();
				
	}
	
	ModalProdutoPage modalProdutoPage;
	
	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
		
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;
		//--Pr?-condi??o
		//usu?rio logado
		if(!homePage.estaLogado("Marcos Rodrigues")) {
			testLoginComSucesso_UsuarioLogado();
		}
		//--Teste
		//Selecionando produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();
			
		//Selecionar tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());
		
		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);
		
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());
		
		//Selecionar cor
		produtoPage.selecionarCorPreta();
		
		//Selecionar quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);
		
		//Adicionar ao carrinho
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();
		
		//Valida??es
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
		
		System.out.println(modalProdutoPage.obterDescricaoProduto());
		
		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		
		String precoProdutoString = (modalProdutoPage.obterPrecoProduto());
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
		
		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
		
		String subtotalString = (modalProdutoPage.obterSubtotal());
		subtotalString = subtotalString.replace("$", "");
		Double subtotal = Double.parseDouble(subtotalString);
		
		Double subtotalCalculado = quantidadeProduto * precoProduto;
		
		assertThat(subtotal, is(subtotalCalculado));
		
	}
	
	//Valores Esperados
	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;
	
	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.0;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_totalTaxInclTotal = esperado_totalTaxExclTotal;
	Double esperado_taxesTotal = 0.0;
	
	String esperado_nomeCliente = "Marcos Rodrigues";
	
	
	CarrinhoPage carrinhoPage;
	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() {
		//--Pr?-Condic?es
		//Produto incluido na tela ModalProdutoPage
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
		
		//Teste
		
		//Validar todos elementos da tela
		System.out.println("*** TELA DO CARRINHO ***");
		
		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
		
		System.out.println("** ITENS DE TOTAIS **");
		
		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
		//Asser??es Hamcrest
		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()), is(esperado_input_quantidadeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()), is(esperado_subtotalProduto));
		
		assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()), is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()), is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()), is(esperado_totalTaxInclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()), is(esperado_taxesTotal));
		
		//Asser??o JUnit
		/*
		assertEquals(esperado_nomeProduto, carrinhoPage.obter_nomeProduto());
		assertEquals(esperado_precoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		assertEquals(esperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
		assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
		assertEquals(esperado_input_quantidadeProduto, Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()));
		assertEquals(esperado_subtotalProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
		
		assertEquals(esperado_numeroItensTotal, Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		assertEquals(esperado_shippingTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		assertEquals(esperado_totalTaxExclTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		assertEquals(esperado_totalTaxInclTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
		assertEquals(esperado_taxesTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		*/
	}
	
	CheckoutPage checkoutPage;
	
	@Test
	public void testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk() {
		//Pr?-condi??es
		
		//Produto disponivel no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();
		
		//Teste
		
		//Clicar no botao
		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
		
		//Preencher informacoes
		//Validar informa??es na tela
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxInclTotal()), is(esperado_totalTaxInclTotal));
		//assertThat(checkoutPage.obter_nomeCliente(), is(esperado_nomeCliente));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.clicarBotaoContinueAddress();
		
		String encontrado_shippingValor = checkoutPage.obter_shippingValor();
		encontrado_shippingValor = Funcoes.removeTexto(encontrado_shippingValor, " tax excl.");
		Double encontrado_shippingValorDouble = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);
		
		assertThat(encontrado_shippingValorDouble, is(esperado_shippingTotal));
		
		checkoutPage.clicarBotaoContinueShipping();
		
		//Selecionar op??o "Pay by Check"
		checkoutPage.SelecionarRadioPayByCheck();
		//Validar valor do cheque (amount)
		String encontrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck();
		encontrado_amountPayByCheck = Funcoes.removeTexto(encontrado_amountPayByCheck, " (tax incl.)");
		Double encontrado_amountPayByCheckDouble = Funcoes.removeCifraoDevolveDouble(encontrado_amountPayByCheck);
		
		assertThat(encontrado_amountPayByCheckDouble, is(esperado_totalTaxInclTotal));
		//Clicar na opcao "I agree"
		checkoutPage.selecionarCheckboxIAgree();
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
	}
	
	PedidoPage pedidoPage;
	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		//Pre-Condicoes
		//Checkout completamente concluido
		testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk();
		
		//Teste
		//Clicar no botao para confirmar o pedido
		pedidoPage = checkoutPage.clicarBotaoConfirmaPedido();
		
		//Validar valores da tela
		assertTrue(pedidoPage.obterTextoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
//		assertThat(pedidoPage.obterTextoPedidoConfirmado().toUpperCase(), is("YOUR ORDER IS CONFIRMED")); 
		
		assertThat(pedidoPage.obterEmail(), is("m4rcostestes@gmail.com"));
		assertThat(pedidoPage.obterTotalProdutos(), is(esperado_subtotalProduto));
		assertThat(pedidoPage.obterTotalTaxInc(), is(esperado_totalTaxInclTotal));
		assertThat(pedidoPage.obterMetodoPagamento(), is("check"));
	
	}
}
