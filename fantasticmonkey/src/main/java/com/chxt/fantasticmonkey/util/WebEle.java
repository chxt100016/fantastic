package com.chxt.fantasticmonkey.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebEle {

    private WebDriver driver;

    private WebElement element;

    public WebEle getById(String id){
        return new WebEle(driver, element.findElement(By.id(id)));
    }

    public List<WebEle> listById(String id) {
        return element.findElements(By.id(id)).stream().map(item -> new WebEle(driver, item)).collect(Collectors.toList());
    }

    public WebEle getByClass(String className){
        return new WebEle(driver, element.findElement(By.className(className)));
    }

    public List<WebEle> listByClass(String className) {
        return element.findElements(By.className(className)).stream().map(item -> new WebEle(driver, item)).collect(Collectors.toList());
    }

    public WebEle getByTag(String tagName) {
        return new WebEle(driver, element.findElement(By.tagName(tagName)));
    }

    public List<WebEle> listByTag(String tagName) {
        return element.findElements(By.tagName(tagName)).stream().map(item -> new WebEle(driver, item)).collect(Collectors.toList());
    }

    public String src() {
        return element.getAttribute("src");
    }

    public String href() {
        return element.getAttribute("href");
    }

    public String id() {return element.getAttribute("id");}

    public String name() {
        return element.getAttribute("name");
    }

    public String text() {return element.getText();}

    public void click() {
        element.click();
    }

    public void keys(String str) { element.sendKeys(str);}

    public boolean isEmpty() {
        return this.element == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public void scrollTo(int offset) {
        new Actions(driver).scrollByAmount(0, element.getRect().getY() + offset).perform();

    }

    @SneakyThrows
    public void scrollTo(int offset, int waitSecond) {
        new Actions(driver).scrollByAmount(0, element.getRect().getY() + offset).perform();
        TimeUnit.SECONDS.sleep(waitSecond);

    }

    public WebEle refresh(){
        WebElement html = driver.findElement(By.tagName("html"));
        return new WebEle(driver, html);
    }
}
