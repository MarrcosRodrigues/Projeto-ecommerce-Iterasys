package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

public class VisualizaProdutosSteps {
	
	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
	
	@Before
	public static void inicializar() {
		driver = new FirefoxDriver();
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
	    homePage.carregarrPaginaInicial();
	    assertThat(homePage.obterTituloPagina(), is("Loja de Teste"));
	}

	@Quando("nao estou logado")
	public void nao_estou_logado() {
		assertThat(homePage.estaLogado(), is(false));
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
	   assertThat(homePage.contarProdutos(), is(int1));
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertThat(homePage.obterQuantidadeProdutosNoCarrinho(), is(0));
	}
	
	@After
	public static void finalizar() {
		driver.quit();
	}

}
