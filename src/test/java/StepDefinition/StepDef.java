package StepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import PageObject.SearchCustomerPage;
import Utilities.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
/*child class of base class*/
public class StepDef extends BaseClass{

	@Before("@Sanity")
	public void setup1() throws IOException
	{	
		readConfig = new ReadConfig();
		//we hv created already readconfig file so no need to write 2 lines
		
		/*-FileInputStream file = new FileInputStream("config.properties");
		readConfig.load(file);*/
		
		//initiate logger
		log = LogManager.getLogger("StepDef");

		System.out.println("Setup1-sanity method executed..");

		String browser = readConfig.getBrowser();

		//launch browser

		switch(browser.toLowerCase())
		{
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "msedge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		default:
			driver=null;
			break;		
		}

		log.fatal("Setup1 executed..");
		
		/*WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		log.trace("Setup1 executed..");*/
	}

	@Before("@regression")
	public void setup2()
	{
		log = LogManager.getLogger("StepDef");
		
		System.out.println("Setup2-Regression method executed..");
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		log.info("Setup2 executed..");
	}
	
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() 
	{
		loginPg= new LoginPage(driver);
		addNewCustPg= new AddNewCustomerPage(driver);
		SearchCustpg =new SearchCustomerPage(driver);
		log.info("chrome browser launched..");
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) 
	{
		driver.get(url);
		log.info("URL opened.");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String emailAdd, String password)
	{
		loginPg.enterEmail(emailAdd);
		loginPg.enterPassword(password);
		log.info("emailAdd And password entered..");
	}

	@When("Click on Login")
	public void click_on_login() 
	{
		loginPg.clickOnLoginButton();
		log.info("login button clicked.");
	}

	///////////////////////////Login Feature////////////////////////////////

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expectedTitle) throws InterruptedException {

		String actualTitle=driver.getTitle();

		if(actualTitle.equals(expectedTitle))
		{
			log.warn("Test passed: Login feature :Page title matched.");
			Assert.assertTrue(true);
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed: Login feature :Page title mis-matched.");
		}		
		Thread.sleep(2000);
	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() {

		loginPg.clickOnLogOutButton();
		log.info("user clicked on logout link.");

	}

	/*@Then("close browser")
	public void close_browser() {
		driver.close();
		log.info("Browser closed.");
		//driver.quit();

	}*/

	////////////////////Add New Customer////////////////////////////////////

	@Then("User can view Dashboad")
	public void user_can_view_dashboad() 
	{
		String actuaTitle=addNewCustPg.getPageTitle();
		String expectedTitle= "Dashboard / nopCommerce administration";

		if(actuaTitle.equals(expectedTitle))
		{
			log.info("user can veiw dashboard test passed");
			Assert.assertTrue(true);
		}
		else
		{
			Assert.assertTrue(false);
			log.info("user can veiw dashboard test failed");
		}
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_menu() throws InterruptedException {
		addNewCustPg.clickOnCustomersMenu();
		log.info("customer menu clicked.");
		Thread.sleep(2000);;
	}

	@When("click on customers Menu Item")
	public void click_on_customers_menu_item() {
		addNewCustPg.clickOnCustomersMenuItem();
		log.info("customer menu item clicked.");
	}

	@When("click on Add new button")
	public void click_on_add_new_button() {
		addNewCustPg.clickOnAddnew();
		log.info("click on add new button.");
	}

	@Then("User can view Add new customer page")
	public void user_can_view_add_new_customer_page() {

		String actualTitle = addNewCustPg.getPageTitle();
		String expectedTitle = "Add a new customer / nopCommerce administration";

		if(actualTitle.equals(expectedTitle))
		{
			log.info("User can view Add new customer page- passed");
			Assert.assertTrue(true);//pass
		}
		else
		{
			log.info("User can view Add new customer page- failed");
			Assert.assertTrue(false);//fail
		} 
	}

	@When("User enter customer info")
	public void user_enter_customer_info() {
		//addNewCustPg.enterEmail("cs123456@gmail.com");
		addNewCustPg.enterEmail(generateEmailId() + "@gmail.com");
		addNewCustPg.enterPassword("test1");
		addNewCustPg.enterFirstName("Prashant");
		addNewCustPg.enterLastName("Jadhav");
		addNewCustPg.enterGender("Male");
		addNewCustPg.enterDob("07/15/1990");
		addNewCustPg.enterCompanyName("codestudio");
		addNewCustPg.enterAdminContent("Admin content");
		addNewCustPg.enterManagerOfVendor("Vendor 1");   

		log.info("customer inforamtion entered.");
	}

	@When("click on Save button")
	public void click_on_save_button() {
		addNewCustPg.clickOnSave();
		log.info("clicked on save button.");
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String exptectedConfirmationMsg) {
		String bodyTagText = driver.findElement(By.tagName("Body")).getText();
		if(bodyTagText.contains(exptectedConfirmationMsg))
		{
			Assert.assertTrue(true);//pass
			log.info("User can view confirmation message - passed");
		}
		else
		{
			log.warn("User can view confirmation message - failed");
			Assert.assertTrue(false);//fail
		}
	}

	//////////////////Search customer by email//////////////////
	@When("Enter customer EMail")
	public void enter_customer_e_mail() {
		SearchCustpg.enterEmailAdd("victoria_victoria@nopCommerce.com");
		log.info("email add entered.");
	}

	@When("Click on search button")
	public void click_on_search_button() throws InterruptedException {
		SearchCustpg.clickOnSearchButton();
		log.info("clicked on search button.");
		Thread.sleep(2000);
	}

	@Then("User should found Email in the Search table")
	public void user_should_found_email_in_the_search_table()
	{
		String expectedEmail="victoria_victoria@nopCommerce.com";
		//Assert.assertTrue(SearchCustpg.searchCustomerByEmail(expectedEmail));

		if( SearchCustpg.searchCustomerByEmail(expectedEmail) ==true)
		{
			Assert.assertTrue(true);
			log.info("User should found Email in the Search table - passed");
		}
		else 
		{
			log.info("User should found Email in the Search table - passed");
			Assert.assertTrue(false);
		}
	}

	@Then("close browse")
	public void close_browse() {
		driver.close();
		driver.quit();
		log.info("browser closed onad quitted.");
	}
	/////////////search customer by name//////////////

	@When("Enter customer FirstName")
	public void enter_customer_first_name() {
		SearchCustpg.enterFirstName("Victoria");
	}

	@When("Enter customer LastName")
	public void enter_customer_last_name() {
		SearchCustpg.enterLastName("Terces");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_name_in_the_search_table() {
		String expectedName="Victoria Terces";

		if( SearchCustpg.searchCustomerByName(expectedName) ==true)
		{
			Assert.assertTrue(true);
		}
		else 
		{
			Assert.assertTrue(false);
		}
	}
	//@After
	/*public void teardown(Scenario sc)
	{
		System.out.println("Teardown method executed..");
		if(sc.isFailed()==true)
		{
			//Convert web driver object to TakeScreenshot

			String fileWithPath = "C:\\Users\\admin\\eclipse-workspace\\Cucumberframework\\Screenshots\\failedScreenshot.png";
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			//Move image file to new destination
			File DestFile=new File(fileWithPath);

			//Copy file at destination

			try 
			{
				FileUtils.copyFile(SrcFile, DestFile);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			driver.quit();
		}*/
		
		@AfterStep
		public void addScreenshot(Scenario scenario) 
		{	
			if(scenario.isFailed())
			{
			final byte[] screenshot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
			}
		}
		
		/*@After  
	public void teardown2()
	{
		System.out.println("Teardown method executed..");
		driver.quit();
	}*/

		/*@BeforeStep
	public void beforeSteoMethodDemo()
	{
		System.out.println("THis is Before Step..");
	}

	@AfterStep
	public void afterSteoMethodDemo()
	{
		System.out.println("THis is After Step..");
	}*/
	}


