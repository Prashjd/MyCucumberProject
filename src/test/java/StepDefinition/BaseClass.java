package StepDefinition;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;


import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import PageObject.SearchCustomerPage;
import Utilities.ReadConfig;

public class BaseClass {

	public static	WebDriver driver;
	public	LoginPage loginPg;
	public SearchCustomerPage SearchCustpg;
	public AddNewCustomerPage addNewCustPg;
	public static Logger log;
	public ReadConfig readConfig;
	
	//generate unique email id
	public String generateEmailId() {
		
		return(RandomStringUtils.randomAlphabetic(5));
	}
}
