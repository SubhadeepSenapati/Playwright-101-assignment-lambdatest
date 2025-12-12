package Lambdatesting;

import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SimpleFromDemo {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Test
    public void simpleFormDemo() throws UnsupportedEncodingException {
    	
    	JsonObject capabilities = new JsonObject();
		JsonObject ltoptions = new JsonObject();
		
		String user = "subhadeepsenapati2003";

		String accesskey="LT_TloUaXebyVXxucwl0AazBEvsc2IiuRs4qEX3PfW3HSBD3da";


		capabilities.addProperty("browsername", "Chrome");

		capabilities.addProperty("browserVersion", "latest");
		

		ltoptions.addProperty("platform", "Windows 11");

		ltoptions.addProperty("name", "SimpleFromDemo");

		ltoptions.addProperty("build", "Playwright Test");

		ltoptions.addProperty("user", user);

		ltoptions.addProperty("accessKey", accesskey);

		capabilities.add("LT:Options", ltoptions);
		
		Playwright playwright = Playwright.create();

		BrowserType chromium = playwright.chromium();

		String caps = URLEncoder.encode (capabilities.toString(), "utf-8");

		String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;

		Browser browser = chromium.connect(cdpUrl);

		Page page = browser.newPage();

    	
        page.navigate("https://www.lambdatest.com/selenium-playground");

        page.locator("text=Simple Form Demo").click();

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"),
                "URL should contain 'simple-form-demo', actual: " + currentUrl);

        String message = "Welcome to LambdaTest";
        page.fill("#user-message", message);
        page.click("#showInput");

        Locator output = page.locator("#message");
        output.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertEquals(output.textContent().trim(), message);
   
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
