package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPage {
	
	private WebDriver driver;
	
	private By textoPedidoConfirmado = By.cssSelector("#content-hook_order_confirmation h3");
	
	private By email = By.cssSelector("#content-hook_order_confirmation p");
	
	private By totalProdutos = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
	
	private By totalTaxInc = By.cssSelector("div.order-confirmation-table table tr.total-value td:nth-child(2)");
	
	private By metodoPagamento = By.cssSelector("#order-details ul li:nth-child(2)");
	
	public PedidoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterTextoPedidoConfirmado() {
		return driver.findElement(textoPedidoConfirmado).getText();
	}
	public String obterEmail() {
//		m4rcostestes@gmail.com
		String texto = driver.findElement(email).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto, " address.");
		return texto;
	}
	
	public Double obterTotalProdutos() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalProdutos).getText());
	}
	public Double obterTotalTaxInc() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalTaxInc).getText());
	}
	
	public String obterMetodoPagamento() {
		return Funcoes.removeTexto(driver.findElement(metodoPagamento).getText(), "Payment method: Payments by ");
	}

}
