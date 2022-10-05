package week5.Assignments;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	
	public ChromeDriver driver;
	
	@Parameters({"url","username","password"})
	@BeforeMethod
	public void preCondition(String url,String username, String password) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(url);
		driver.findElement(By.id("user_name")).sendKeys(username);
		driver.findElement(By.id("user_password")).sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.id("sysverb_login")).click();
		Thread.sleep(10000);
	}
	
	@AfterMethod
	public void postCondition() {
		driver.close();
	}
	
	@DataProvider(name="fetchData")
public static String[][] readExcel() throws IOException {		
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook("./TestData/ServiceNow.xlsx");
		XSSFSheet sheet = hssfWorkbook.getSheet("Sheet1");

		int lastRowNum = sheet.getLastRowNum();
		//System.out.println(lastRowNum);
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		//System.out.println(lastCellNum);
		String[][] data=new String[lastRowNum][lastCellNum];
		for (int i = 1; i <=lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j < lastCellNum; j++) {
				String stringCellValue = row.getCell(j).getStringCellValue();
				System.out.println(stringCellValue);
				data[i-1][j]=stringCellValue;
			}
			System.out.println("*****************");
		}
		
		hssfWorkbook.close();
		return data;
	}
	
}
