package Lambdatesting;

import com.google.gson.JsonObject;
import com.microsoft.playwright.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.testng.Assert;
import org.testng.annotations.*;

public class DragAndDropSlider {
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	private Page page;
	
	
	@Test
	public void dragAndDropSliders() throws UnsupportedEncodingException {
		
		JsonObject capabilities = new JsonObject();
		JsonObject ltoptions = new JsonObject();
		
		String user = "subhadeepsenapati2003";

		String accesskey="LT_TloUaXebyVXxucwl0AazBEvsc2IiuRs4qEX3PfW3HSBD3da";

		capabilities.addProperty("browsername", "Chrome");

		capabilities.addProperty("browserVersion", "latest");
		

		ltoptions.addProperty("platform", "Windows 11");

		ltoptions.addProperty("name", "DragAndDropSlider");

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

		page.locator("text=Drag & Drop Sliders").click();

		Locator slider = page.locator("(//input[@type='range'])[3]");

		slider.evaluate(
				"el => { el.value = '15'; el.dispatchEvent(new Event('input', { bubbles: true })); el.dispatchEvent(new Event('change', { bubbles: true })); }");

		Locator rangeValue = page.locator("#rangeSuccess");
		rangeValue.waitFor(new Locator.WaitForOptions().setTimeout(3000));
		String text = rangeValue.textContent().trim();
		Assert.assertEquals(text, "15");

		if (context != null)
			context.close();
		
		if (browser != null)
			browser.close();
		
		if (playwright != null)
			playwright.close();
	}
}
