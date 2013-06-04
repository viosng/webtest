import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        WebDriver driver = new FirefoxDriver();
        boolean testAll = false;
        if (testAll) {
            ArrayList<String> urlList = new ArrayList<String>();
            Scanner in = new Scanner(new File("url.txt"));
            while(in.hasNextLine()){
                urlList.add(in.nextLine());
            }
            in.close();
            for (String url : urlList) {
                System.out.println(url + "\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
                driver.get(url);
                // find text and wrap it to <i></i>

                //System.out.println(((JavascriptExecutor) driver).executeScript("return document.body.innerHTML;"));
                TestGenerator tg = new TestGenerator(driver, "tagsWithText.txt", "testTags.txt");
            }
        }else{
            driver.get("http://frame-template.narod.ru/");
            //System.out.println(((JavascriptExecutor) driver).executeScript("return document.body.innerHTML;"));
            TestGenerator tg = new TestGenerator(driver, "tagsWithText.txt", "testTags.txt");
            tg.test();
        }
        driver.quit();
    }
}