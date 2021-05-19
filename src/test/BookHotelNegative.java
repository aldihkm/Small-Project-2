package test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;

public class BookHotelNegative {
	public static String browser;
	static WebDriver driver;
	
	public static void main(String[] args) {
		BookHotelNegative test = new BookHotelNegative();
		test.setBrowser("Chrome");
		test.setBrowserConfig();
		test.navigateToUrl();
		test.searchHotelNegative();
	}
	
	public void setBrowser (String browser) {
		this.browser = browser;
	}
	
	public void setBrowserConfig() {
		String projectLocation = System.getProperty("user.dir");
		if(browser.contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectLocation + "/lib/driver/chromedriver");
			driver = new ChromeDriver();
		}
		if(browser.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", projectLocation+ "/lib/driver/geckodriver");
			driver = new FirefoxDriver();
		}
	}
	
	@Test
	public void navigateToUrl() {
		driver.get("http://128.199.90.160/id/");
		driver.manage().window().maximize();
	}
	
	public void searchHotelNegative() {
		driver.findElement(By.xpath("//span[.='Search Now']")).click();
		String expected = "#ff0000";
		WebElement drdsearchHotel = driver.findElement(By.id("id_hotel_button"));
		String drdColor = drdsearchHotel.getCssValue("border");
		System.out.println(drdColor);
		
		
		String hexColor = Color.fromString(drdColor).asHex();
		System.out.println(hexColor);
	}
		
	

}
