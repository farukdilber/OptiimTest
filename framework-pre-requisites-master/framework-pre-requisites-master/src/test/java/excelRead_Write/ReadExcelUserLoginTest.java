package excelRead_Write;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.interactions.Actions;

public class ReadExcelUserLoginTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		File file = new File(System.getProperty("user.dir") + "\\TestData\\" + "Regression_TestData" + ".xlsx");
		FileInputStream inputstream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputstream);
		XSSFSheet sheet = wb.getSheet("LoginDetails");

		/* Open Login page */
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.saucedemo.com/");
		Actions actions= new Actions(driver);
		// Storing Values in to Variables ( In this case, Usernames and Passwords )
		XSSFRow row = null;
		XSSFCell cell = null;
		String userName = null;
		String password = null;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);

				if (j == 0) // We can use Column Name as well, will see in upcoming sessions
				{
					userName = cell.getStringCellValue();
				}
				if (j == 1) // We can use Column Name as well, will see in upcoming sessions
				{
					password = cell.getStringCellValue();
				}
			}

			driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(userName);
			driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
			driver.findElement(By.xpath("//*[@id='login-button']")).click();

			String result = null;
			try {
				Boolean isLoggedIn = driver.findElement(By.xpath("//*[@id='react-burger-menu-btn']")).isDisplayed();
				if (isLoggedIn == true) {
					result = "PASS";
					// Writing to an excel


				}
				System.out.println("User Name : " + userName + " ---- > " + "Password : " + password + "-----> Login success ? ------> " + result);
				//System.out.println("Login successfull : " + isLoggedIn);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-backpack']")).click();
				driver.findElement(By.xpath("//*[@id='shopping_cart_container']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='remove-sauce-labs-backpack']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='continue-shopping']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='react-burger-menu-btn']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='logout_sidebar_link']")).click();
				Thread.sleep(1000);
			}

			catch(Exception e)
			{

				Boolean isError=driver.findElement(By.xpath("//*[@class='error-message-container error']")).isDisplayed();
				if(isError==true)
				{
					result="FAIL";
					WebElement usernameText = driver.findElement(By.xpath("//*[@id='user-name']"));
					usernameText.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));;
					WebElement passwordText = driver.findElement(By.xpath("//*[@id='password']"));
					passwordText.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));;
//					actions.sendKeys(Keys.CLEAR).perform();

				}
				System.out.println("User Name : " + userName + " ---- > " + "Password : "  + password + "-----> Login success ? ------> " + result);
				Thread.sleep(5000);
//				driver.get("https://www.saucedemo.com/");
//				WebElement usernameText = driver.findElement(By.xpath("//*[@id='user-name']"));
//				usernameText.click();
//				actions.sendKeys(Keys.BACK_SPACE).perform();
//				driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(userName);
//				driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);

			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='login-button']")).click();
		}

		}

	}



