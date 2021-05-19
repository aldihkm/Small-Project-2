package test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class BookHotel {
//	private static final java.lang.String orderReferenceHistory = null;
//	private static final String String = orderReferenceHistory;
	public static String browser;
	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		BookHotel test = new BookHotel();
		test.setBrowser("Chrome");
		test.setBrowserConfig();
		test.navigateToUrl();
		test.searchHotel();
		test.listRooms();
		test.roomsPriceSummary();
		test.guestInformation();
		test.paymentInformation();
		test.orderConfirmation();
		test.orderHistory();
		test.myAddresses();
		test.personalInformation();
		test.logout();
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setBrowserConfig() {
		String projectLocation = System.getProperty("user.dir");
		if (browser.contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectLocation + "/lib/driver/chromedriver");
			driver = new ChromeDriver();
		}
		if (browser.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", projectLocation + "/lib/driver/geckodriver");
			driver = new FirefoxDriver();
		}
	}

	@Test
	public void navigateToUrl() {
//		Navigate to Url
		driver.get("http://128.199.90.160/id/");

//		Maximize Window
		driver.manage().window().maximize();

//		Verify Web Title
		String expectedTitle = "QA";
		String actualTitle = "";
		actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(expectedTitle, actualTitle);
			System.out.println("Title Match!");
		} catch (AssertionError e) {
			System.out.println("Title Not Match");
			driver.quit();
		}
	}

	public void searchHotel() {
//		Select Hotel
		driver.findElement(By.id("id_hotel_button")).click();
		driver.findElement(By.cssSelector(".search_result_li")).click();

//		Select Check In Date
		driver.findElement(By.id("check_in_time")).sendKeys("24-05-2021");

//		Select Check Out Date
		driver.findElement(By.id("check_out_time")).sendKeys("25-05-2021");

//		Click Search
		driver.findElement(By.xpath("//span[.='Search Now']")).click();

	}

	public void listRooms() throws InterruptedException {
//		Sort By Lowest Price
		driver.findElement(By.id("price_ftr")).click();
		driver.findElement(By.xpath("//a[.='Price : Lowest First']")).click();

//		Verify Sorted By Lowest Price
		String expectedLowestPriceText = "General Rooms";
		String actualLowestPriceText = "";
		actualLowestPriceText = driver.findElement(By.xpath("//p[.='General Rooms']")).getText();

		try {
			Assert.assertEquals(expectedLowestPriceText, actualLowestPriceText);
			System.out.println("Text Match");
		} catch (AssertionError e) {
			System.out.println("Text Not Match");
			driver.quit();
		}

//		Sort By Highest Price
		driver.findElement(By.id("price_ftr")).click();
		driver.findElement(By.xpath("//a[contains(.,'Price : Highest first')]")).click();

//		Verify Sort By Lowest Price
		String expectedHighestPriceText = "luxury Rooms";
		String actualHighestPriceText = "";
		actualHighestPriceText = driver.findElement(By.xpath("//p[.='luxury Rooms']")).getText();

		try {
			Assert.assertEquals(expectedHighestPriceText, actualHighestPriceText);
			System.out.println("Highest Price Match");
		} catch (AssertionError e) {
			System.out.println("Highest Price Not Match");
			driver.quit();
		}

//		Click Book Now
		driver.findElement(By.cssSelector("[rm_product_id='4'] > span")).click();

//		Click on Cart Summary
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[contains(.,'Proses pembayaran')]")).click();
		Thread.sleep(5000);

	}

	public void roomsPriceSummary() {
//		Verify selected room
		String actualRoom = driver.findElement(By.xpath("//div[@class='room-xs-info']//a[contains(.,'luxury Rooms')]"))
				.getText();
		String expectedRoom = "luxury Rooms";
		Assert.assertEquals(expectedRoom, actualRoom);
		System.out.println("Room Match");

//		Verify Check Price
		String actualCheckOutDate = driver
				.findElement(By.xpath("//div[@class='total_price_block']//span[@class='room_type_current_price']"))
				.getText();
		String expectedCheckOutDate = "2 750,00 Rp";
		Assert.assertEquals(expectedCheckOutDate, actualCheckOutDate);
		System.out.println("Price Match");

//		Click Proceed
		driver.findElement(By.xpath("//span[contains(.,'Proceed')]")).click();
	}

	public void guestInformation() throws InterruptedException {
//		Input First Name
		String randomFirstName = RandomStringUtils.randomAlphabetic(8);
		driver.findElement(By.id("customer_firstname")).sendKeys(randomFirstName);

//		Input Last Name
		String randomLastName = RandomStringUtils.randomAlphabetic(8);
		driver.findElement(By.id("customer_lastname")).sendKeys(randomLastName);

//		Input Email
		String randomEmail = RandomStringUtils.randomAlphabetic(8);
		driver.findElement(By.id("email")).sendKeys(randomEmail + "@mailnesia.com");

//		Input Password
		driver.findElement(By.id("passwd")).sendKeys("P@ssw0rd");

//		Input Phone Number
		String randomPhoneNumber = RandomStringUtils.randomNumeric(10);
		driver.findElement(By.id("phone_mobile")).sendKeys(randomPhoneNumber);

//		Click Save
		driver.findElement(By.xpath("//span[.='Save']")).click();

//		Wait
		Thread.sleep(3000);

//		Click Proceed Guest Info Summary
		driver.findElement(By.cssSelector("[title='Proceed to Payment'] > span")).click();
	}

	public void paymentInformation() throws InterruptedException {
//		Click TnC
		driver.findElement(By.id("cgv")).click();

//		Wait
		Thread.sleep(3000);

//		Click Payment Resource
		driver.findElement(By.cssSelector(".bankwire")).click();

//		Click Checkout Summary
		driver.findElement(By.xpath("//span[contains(.,'Konfirmasi pembelian')]")).click();
	}

	public void orderConfirmation() {
//		Verify Success appear
		boolean successNotif = driver.findElement(By.xpath("//p[@class='alert alert-success']")).isDisplayed();
		System.out.println("Success Notif displayed is: " + successNotif);

//		Verify Order confirmed
		boolean orderStatus = driver.findElement(By.xpath("//span[.='Confirmed']")).isDisplayed();
		System.out.println("Order Status is: " + orderStatus);

//		Verify Order Reference
		String orderReference = driver.findElement(By.xpath("//span[@class='bold']")).getText();
		System.out.println("Order Reference is:" + orderReference);

//		Click back to Order History
		driver.findElement(By.cssSelector(".htl-reservation-form-btn-small")).click();
	}

	public void orderHistory() throws InterruptedException {
//		Click Order History
		driver.findElement(By.cssSelector(".color-myaccount")).click();

//		Wait
		Thread.sleep(3000);

//		Verify Payment Method on Order History
		String expectedPaymentMethod = "Transfer Bank";
		String actualPaymentMethod = "";
		actualPaymentMethod = driver.findElement(By.cssSelector("span.color-myaccount")).getText();
		Assert.assertEquals(expectedPaymentMethod, actualPaymentMethod);
		System.out.println("Payment Method Match");

//		Scroll down
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,700)");

//		Wait
		Thread.sleep(3000);

//		Click Back to My Account
		driver.findElement(By.xpath("//span[contains(.,'Back to Your Account')]")).click();

	}

	public void myAddresses() throws InterruptedException {
//		Click My Address
		driver.findElement(By.xpath("//span[.='My addresses']")).click();

//		Click Update Address
		driver.findElement(By.xpath("//span[.='Update']")).click();

//		Scroll down
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,700)");

//		Wait
		Thread.sleep(3000);

//		Input Alias Address
		WebElement aliasAddress = driver.findElement(By.id("alias"));
		aliasAddress.clear();
		aliasAddress.sendKeys("Home Sweet Home");

//		Save Update Address
		driver.findElement(By.xpath("//span[contains(.,'Save')]")).click();

//		Verify Alias Address
		String expectedaliasAddress = "HOME SWEET HOME";
		String actualAliasAddress = "";
		actualAliasAddress = driver.findElement(By.cssSelector(".page-subheading")).getText();
		Assert.assertEquals(expectedaliasAddress, actualAliasAddress);
		System.out.println("Address Match");

//		Click Add Address
		driver.findElement(By.xpath("//span[.='Add a new address']")).click();

//		Input Address
		String randomAddrress = RandomStringUtils.randomAlphabetic(5);
		driver.findElement(
				By.xpath("//form[@id='add_address']/div[5]/input[@class='is_required validate form-control']"))
				.sendKeys(randomAddrress);

//		Input Zip Code
		String randomZipCode = RandomStringUtils.randomNumeric(5);
		driver.findElement(By.id("postcode")).sendKeys(randomZipCode);

//		Select City
		driver.findElement(By.id("city")).sendKeys("Jakarta");
		Select drdState = new Select(driver.findElement(By.id("id_state")));
		drdState.selectByVisibleText("Bangka");

//		Input Home Phone Number
		String randomHomePhone = RandomStringUtils.randomNumeric(10);
		driver.findElement(By.id("phone")).sendKeys(randomHomePhone);

//		Input Mobile Phone Number
		String randomMobilePhone = RandomStringUtils.randomNumeric(10);
		driver.findElement(By.id("phone_mobile")).sendKeys(randomMobilePhone);

//		Scroll down
		JavascriptExecutor jsc = (JavascriptExecutor) driver;
		jsc.executeScript("window.scrollBy(0,300)");

//		Input Title Address
		WebElement titleAddress = driver.findElement(By.id("alias"));
		titleAddress.clear();
		titleAddress.sendKeys("Office Sweet Office");

//		Click Save Address
		driver.findElement(By.xpath("//span[contains(.,'Save')]")).click();

//		Verify Title Address
		String expectedNewAddress = "OFFICE SWEET OFFICE";
		String actualNewAddress = "";
		actualNewAddress = driver.findElement(By.cssSelector(".last_item .page-subheading")).getText();
		Assert.assertEquals(expectedNewAddress, actualNewAddress);
		System.out.println("New Address Match");

//		Delete Address
		driver.findElement(By.xpath("//ul[@class='last_item alternate_item box']//span[.='Delete']")).click();
		Alert deleteAddress = driver.switchTo().alert();
		deleteAddress.accept();

//		Click back to My Account Page
		driver.findElement(By.xpath("//span[contains(.,'Back to your account')]")).click();
	}

	public void personalInformation() {
//		Click My Personal Information
		driver.findElement(By.xpath("//span[.='My personal information']")).click();

//		Select Day of Birth
		Select drdDateBirth = new Select(driver.findElement(By.id("days")));
		drdDateBirth.selectByValue("7");

//		Select Month of Birth
		Select drdMonthBirth = new Select(driver.findElement(By.id("months")));
		drdMonthBirth.selectByValue("5");

//		Select Year of Birth
		Select drdYearBirth = new Select(driver.findElement(By.id("years")));
		drdYearBirth.selectByValue("1991");

//		Input old password
		driver.findElement(By.id("old_passwd")).sendKeys("P@ssw0rd");

//		Input new password
		driver.findElement(By.id("passwd")).sendKeys("qwerty");

//		Input confirmation password
		driver.findElement(By.id("confirmation")).sendKeys("qwerty");

//		Click Save
		driver.findElement(By.xpath("//span[.='Save']")).click();

//		Verify Notification update
		boolean successNotif = driver.findElement(By.xpath("//p[@class='alert alert-success']")).isDisplayed();
		System.out.println("Success Notif displayed is: " + successNotif);

//		Click to Home
		driver.findElement(By.xpath("//span[contains(.,'Home')]")).click();
	}

	public void logout() {
//		Click Account
		driver.findElement(By.cssSelector(".account_user_name")).click();

//		Click Logout
		driver.findElement(By.partialLinkText("Logout")).click();

//		Verify Url after Logout
		String expectedUrl = "http://128.199.90.160/id/";
		String actualUrl = "";
		actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(expectedUrl, actualUrl);
		System.out.println("URL Match");

//		Close browser
		driver.close();
		System.out.println("Congratulations, All Test Passed!");
	}
}
