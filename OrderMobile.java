package week5.Assignments;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import io.github.sukgu.Shadow;

public class OrderMobile extends BaseClass {


	@Test(dataProvider="fetchData")
	public void orderMobile(String isReplaced, String monthlyallowance, String color, String storage, String phNumber) throws InterruptedException {


		//3. Click-AllEnter Service catalog in filter navigator and press enter
		Shadow dom=new Shadow(driver);
		dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(2000);
		dom.findElementByXPath("//a[@aria-label='Service Catalog']").click();
		Thread.sleep(5000);

		//4. Click on  mobiles
		WebElement mainFrame = dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(mainFrame);
		dom.findElementByXPath(("//h2[contains(text(),'Mobiles')]/parent::span/parent::a")).click();		
		Thread.sleep(3000);

		//5.Select Apple iphone6s
		driver.findElement(By.xpath("//h2/strong")).click();
		Thread.sleep(3000);

		//update Mandatory fields
		driver.findElement(By.xpath("//input[@value='"+isReplaced+"']/parent::span")).click();

		if(isReplaced.equals("yes")) {
			driver.findElement(By.xpath("//span[contains(text(),'phone number')]/parent::label/parent::div/following-sibling::div/input[2]")).sendKeys(phNumber);
		}

		WebElement monthlyAllowance = driver.findElement(By.xpath("//select[contains(@class,'cat_item')]"));
		Select allowance=new Select(monthlyAllowance);
		allowance.selectByValue(monthlyallowance);
		driver.findElement(By.xpath("//input[@value='"+color+"']/parent::span")).click();
		driver.findElement(By.xpath("//input[@value='"+storage+"']/parent::span")).click();

		//click on order now
		driver.findElement(By.id("oi_order_now_button")).click();

		//Verify order is placed and copy the request number"
		String successMsg = driver.findElement(By.xpath("//div[contains(@class,'success')]/span")).getText();
		String reqNumber = driver.findElement(By.xpath("//a[@id='requesturl']/b")).getText();
		if(successMsg.contains("Thank you")) {
			System.out.println("order placed");
			System.out.println("Request number: "+reqNumber);
		}else {
			System.out.println("order is not placed");
		}



	}

}
