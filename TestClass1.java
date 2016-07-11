package amazontest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * Created by Олександр on 04.07.2016.
 */

class BookOnSearchPage {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String url = "";
    String name = "";
    String price = "";
}

public class TestClass1 {
    WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", "C:\\TEMP\\chromedriver\\chromedriver.exe");
    }

    @BeforeTest
    public void beforeTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
    }

    @Test
    public void bestSels() {
        List<BookOnSearchPage> booksOnSearchPage = new ArrayList<BookOnSearchPage>();
        driver.get("https://www.amazon.com/gp/new-releases/books/ref=b_sn_bs_nr");
        String title = driver.getTitle();
        assertEquals(title, "Amazon.com Hot New Releases: The best-selling new & future releases in Books", "https://www.amazon.com/gp/new-releases/books/ref=b_sn_bs_nr");
        List<WebElement> elements = driver.findElements(By.className("zg_itemImmersion"));
        assertEquals(elements.size(), 20, "Check that 20 books exist on bestsellers page");
        for (WebElement element : elements) {
            BookOnSearchPage bookOnSearchPage = new BookOnSearchPage();
            bookOnSearchPage.setUrl(element.findElement(By.xpath(".//a")).getAttribute("href").trim());
            bookOnSearchPage.setName(element.findElement(By.xpath(".//div[@class='zg_title']")).getText());
            bookOnSearchPage.setPrice(element.findElement(By.xpath(".//div[@class='zg_price']/*[@class='price']")).getText());
            booksOnSearchPage.add(bookOnSearchPage);

        }

        for(BookOnSearchPage bookOnSearchPage: booksOnSearchPage ){
            String url = bookOnSearchPage.getUrl();
            driver.get(url);
            String price = driver.findElement(By.xpath("//span[@class='a-color-price']")).getText();
            assertEquals(price, bookOnSearchPage.getPrice(), "Check prices for book" + bookOnSearchPage.getName());
        }


    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
