package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
	
	private WebDriver driver;
	
	private By totalTaxInclTotal = By.cssSelector("div.cart-total span.value");
	
	private By nomeCliente = By.cssSelector("div.address");
	
	private By botaoContinueAddress = By.name("confirm-addresses");
	
	private By shippingValor = By.cssSelector("span.carrier-price");
	
	private By botaoContinueShipping = By.name("confirmDeliveryOption");
	
	private By radioPayByCheck = By.id("payment-option-1");
	
	private By amountPayByCheck = By.cssSelector("#payment-option-1-additional-information > section > dl > dd:nth-child(2)");
	
	private By checkboxIAgree = By.id("conditions_to_approve[terms-and-conditions]");
	
<<<<<<< HEAD
	private By botaoConfirmaPedido = By.cssSelector("#payment-confirmation button");
	
=======
>>>>>>> f530d5c9d2c5f0de0fd743868fdf8a78f403a6b1
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obter_totalTaxInclTotal() {
		return driver.findElement(totalTaxInclTotal).getText();
	}
	
	public String obter_nomeCliente() {
		return driver.findElement(nomeCliente).getText();
	}
	
	public void clicarBotaoContinueAddress() {
		driver.findElement(botaoContinueAddress).click();
	}
	
	public String obter_shippingValor() {
		return driver.findElement(shippingValor).getText();
	}
	
	public void clicarBotaoContinueShipping() {
		driver.findElement(botaoContinueShipping).click();
	}
	
	public void SelecionarRadioPayByCheck() {
		driver.findElement(radioPayByCheck).click();
	}
	
	public String obter_amountPayByCheck() {
		return driver.findElement(amountPayByCheck).getText();
	}
	
	public void selecionarCheckboxIAgree() {
		driver.findElement(checkboxIAgree).click();
	}
	
	public boolean estaSelecionadoCheckboxIAgree() {
		return driver.findElement(checkboxIAgree).isSelected();
	}
<<<<<<< HEAD
	
	public PedidoPage clicarBotaoConfirmaPedido() {
		driver.findElement(botaoConfirmaPedido).click();
		return new PedidoPage(driver);
	}
=======
>>>>>>> f530d5c9d2c5f0de0fd743868fdf8a78f403a6b1
}
