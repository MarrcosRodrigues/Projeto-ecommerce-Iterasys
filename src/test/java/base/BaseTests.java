package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import pages.HomePage;

public class BaseTests {
	
	private static WebDriver driver;
	protected HomePage homePage;
	
	@Before
	public void inicializar() {
		driver = new FirefoxDriver();
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
	}
	
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}
	
	
	@After
	public void finalizar() {
		driver.quit();
	}
}
