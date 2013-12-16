package com.tls.liferaylms.main;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.tls.liferaylms.test.util.Sleep;

public class TestUpload {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		
		driver.get("https://www.virustotal.com/es/");

		File f = new File("docroot"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"resources"+File.separator+"encuesta.csv");
		
		System.out.println(f.getAbsolutePath());
		driver.findElement(By.id("file-choosen")).sendKeys(f.getAbsolutePath());
		
		Sleep.sleep(10000L);
		
		driver.findElement(By.id("frm-file")).submit();
	}
}
