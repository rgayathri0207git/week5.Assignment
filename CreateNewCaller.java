package week5.Assignments;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.cucumber.java.Before;
import io.github.sukgu.Shadow;

public class CreateNewCaller extends BaseClass {
	
	@BeforeTest
	public void setExcelName(){
		excelName="ServiceNow";
		sheetName="createNewCaller";
	}

	@Test(dataProvider="fetchData")
	public void createNewCaller(String firstname,String lastName,String email,String phoneNo, String profile) throws InterruptedException {

		Shadow shadow_dom;
		shadow_dom=new Shadow(driver);
		
		//click All
		shadow_dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(2000);
		//enter "Callers" in filter
		shadow_dom.findElementByXPath("//input[@id='filter']").sendKeys("callers");
		shadow_dom.setImplicitWait(3);
		//click Callers
		shadow_dom.findElementByXPath("//a[@aria-label='Callers']").click();
		Thread.sleep(3000);
		//Click New
		WebElement mainFrame = shadow_dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(mainFrame);		
		driver.findElement(By.id("sysverb_new")).click();
		Thread.sleep(2000);
		//Enter caller firstName and LastName
		driver.findElement(By.xpath("//input[@id='sys_user.first_name']")).sendKeys(firstname);
		driver.findElement(By.xpath("//input[@id='sys_user.last_name']")).sendKeys(lastName);
		//enter business profile
		driver.findElement(By.xpath("//a[@id='lookup.sys_user.title']")).click();
		Set<String> windowHandles = driver.getWindowHandles();
		ArrayList<String> windowList = new ArrayList<String>(windowHandles);
		System.out.println(windowList.size());
		driver.switchTo().window(windowList.get(1));
		driver.findElement(By.xpath("//a[text()='"+profile+"']")).click();
		//enter email and phone
		driver.switchTo().window(windowList.get(0));
		driver.switchTo().frame(mainFrame);
		driver.findElement(By.id("sys_user.email")).sendKeys(email);
		driver.findElement(By.id("sys_user.mobile_phone")).sendKeys(phoneNo);
		//click submit
		driver.findElement(By.id("sysverb_insert_bottom")).click();
		// verify new caller created
		String title = driver.getTitle();
		driver.findElement(By.xpath("//span[contains(text(),'column search')]/parent::button")).click();
		driver.findElement(By.xpath("//input[@aria-label='Search column: last name']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@aria-label='Search column: first name']")).sendKeys(firstname, Keys.ENTER);
		String lastname = driver.findElement(By.xpath("//table[@id='sys_user_table']/tbody/tr/td[3]")).getText();
		String firstName = driver.findElement(By.xpath("//table[@id='sys_user_table']/tbody/tr/td[4]")).getText();
		if(title.contains("View")) {
			System.out.println("new caller is created");
			if(firstname.equals(firstName)==true &&lastname.equals(lastName)==true) {
				System.out.println("New caller record is found");
			}else {
				System.out.println("New caller record not found");
			}

		}else {
			System.out.println("New caller is not created");
		}


	}



}
