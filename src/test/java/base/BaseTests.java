package base;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.io.Files;

import pages.HomePage;

public class BaseTests {
	
	private static WebDriver driver;
	protected HomePage homePage;
	
	@BeforeAll
	public static void inicializar() {
		driver = new FirefoxDriver();
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}
	
	@BeforeEach
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
	}
	
	public void capturarTela(String nomeTest, String resultado) {
		var camera =(TakesScreenshot) driver;
		File capturaTela = camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(capturaTela, new File("resources/screenshots/" + nomeTest + "__" + resultado + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	public static void finalizar() {
		driver.quit();
	}
}
