package com.chxt.fantasticmonkey.util.crawler;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Crawler {

    private WebDriver driver;

    private WebElement now;


    static {
        System.setProperty("webdriver.chrome.driver","/Users/chenxintong/Downloads/chromedriver");
    }

    public static WebDriver localWebDriver(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setCapability("pageLoadStrategy", "none");
        chromeOptions.setHeadless(headless);
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        driver.setLogLevel(Level.OFF);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }


    public static Crawler getLocal(boolean headless) {
        Crawler instance = new Crawler();
        instance.driver = localWebDriver(headless);
        return instance;
    }

    private Crawler() {

    }

    private Crawler(WebDriver driver, WebElement now) {
        this.driver = driver;
        this.now = now;
    }

    public void go(String url){
        this.driver.get(url);
    }

    public Crawler getById(String id){
        return this.now == null ? new Crawler(driver, this.driver.findElement(By.id(id))) : new Crawler(driver, this.now.findElement(By.id(id)));
    }

    public List<Crawler> listChildren() {
        return this.now.findElements(By.xpath("./*")).stream().map(item -> new Crawler(driver, item)).collect(Collectors.toList());
    }
    public List<Crawler> listById(String id) {
        return this.now.findElements(By.id(id)).stream().map(item -> new Crawler(driver, item)).collect(Collectors.toList());
    }

    public Crawler getByClass(String className){
        return this.now == null ?
                new Crawler(driver, this.driver.findElement(By.className(className))) :
                new Crawler(driver, this.now.findElement(By.className(className)));
    }

    public List<Crawler> listByClass(String className) {
        return this.now == null ?
                this.driver.findElements(By.className(className)).stream().map(item -> new Crawler(driver, item)).collect(Collectors.toList()) :
                this.now.findElements(By.className(className)).stream().map(item -> new Crawler(driver, item)).collect(Collectors.toList());
    }

    public Crawler getByTag(String tagName) {
        try {
            return new Crawler(driver, this.now.findElement(By.tagName(tagName)));
        } catch (Exception e) {
            return null;
        }

    }

    public List<Crawler> listByTag(String tagName) {
        return this.now.findElements(By.tagName(tagName)).stream().map(item -> new Crawler(driver, item)).collect(Collectors.toList());
    }

    public Crawler switchToFrame(int index) {
        WebDriver frame = this.driver.switchTo().frame(index);
        WebElement frameNow = frame.findElement(By.tagName("html"));
        return new Crawler(frame, frameNow);
    }

    public String src() {
        return this.now.getAttribute("src");
    }

    public String href() {
        return this.now.getAttribute("href");
    }

    public String id() {return this.now.getAttribute("id");}

    public String name() {
        return this.now.getAttribute("name");
    }

    public String text() {return this.now.getText();}

    public void click() {
        this.now.click();
    }

    public String title() {
        return this.driver.getTitle();
    }

    public void keys(String str) { this.now.sendKeys(str);}

    public boolean isEmpty() {
        return this.now == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public void scrollTo(int offset) {
        new Actions(driver).scrollByAmount(0, this.now.getRect().getY() + offset).perform();

    }

    @SneakyThrows
    public void scrollTo(int offset, int waitSecond) {
        new Actions(driver).scrollByAmount(0, this.now.getRect().getY() + offset).perform();
        TimeUnit.SECONDS.sleep(waitSecond);

    }

    public Crawler reset(){
        WebElement html = driver.findElement(By.tagName("html"));
        return new Crawler(driver, html);
    }

    public void close() {
        this.driver.close();
    }
}
