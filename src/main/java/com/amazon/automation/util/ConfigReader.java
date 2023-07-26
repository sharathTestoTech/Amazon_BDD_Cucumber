package com.amazon.automation.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;
import com.amazon.automation.factory.DriverFactory;

public class ConfigReader extends DriverFactory{

	public static String URL;
	String fileName="./src/test/resources/config/config.properties";
	HashMap<String,String> hs= new HashMap<String,String>();
	public  Properties prop;

	private Logger logg;
	
	public ConfigReader(Logger logg) {
		this.logg=logg;
	}  
	

	public  Properties initprop() throws IOException  {
		prop = new Properties();
		FileInputStream ip = null ;
		try {
			ip	= new FileInputStream(fileName);
			prop.load(ip);
			logg.info("Log information from config reader file "+ip);
		} catch (FileNotFoundException e) {
			logg.info("Error in init_prop"+e);
		} catch (IOException e) {
			logg.info("Error in init_prop"+e);
		}
		finally {
			ip.close();
		}
		return prop;
	}
	

	public   String readAddressJsonFilePath() throws IOException {
		try
		{
		return prop.getProperty("Addressreadjson");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readMandatoryJsonFilePath() throws IOException {
		try
		{
		return prop.getProperty("Mandatoryreadjson");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String writeJsonFilePath() throws IOException {
		try
		{
		return prop.getProperty("writejson");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	
	public   String readExcel() throws IOException {
		try
		{
		return prop.getProperty("Excel");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	
	public   String writeExcel() throws IOException {
		try
		{
		return prop.getProperty("ExcelWrite");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readLoginURL() throws IOException {
		try{
			return prop.getProperty("loginURL");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readLoginURLIndia() throws IOException {
		try{
			return prop.getProperty("loginURLIndia");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readUserId() throws IOException {
		try{
			return prop.getProperty("userid");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readuserid1() {
		try{
			return prop.getProperty("userid1");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readuserid2() {
		try{
			return prop.getProperty("userid2");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}

	public   String readPswrd() {
		try{
			return prop.getProperty("pswrd");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readpswrd1() {
		try{
			return prop.getProperty("pswrd1");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readpswrd2() {
		try{
			return prop.getProperty("pswd2");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}

	public  String readTestSuiteDirectory()  {
		try{
			return prop.getProperty("testSuiteDirectory");         
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public  String readLoginEmpId()  {
		try
		{
			return prop.getProperty("LoginEmpId");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public  String readAmendVehicleCondition()  {
		try
		{
			return prop.getProperty("AmendVehicleCondition");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public  String readDeleteVehicleCondition()  {
		try
		{
			return prop.getProperty("DeleteVehicleCondition");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public  String getApplicationName()  {
		try
		{
			return prop.getProperty("ApplicationName");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public   String readJdbcDriver()  {
		try
		{
			return prop.getProperty("ApplicationName");
		}
		catch (Exception e){	
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}
	public  String getOS()  {
		try
		{
			return prop.getProperty("OS");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}

	public  String getEnvironment()  {
		try
		{
			return prop.getProperty("Environment");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}

	public  String readDownloadFolder()  {
		try
		{
			return prop.getProperty("DownloadFolder");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}


	public String readuserid() {
		try
		{
			return prop.getProperty("userid");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}


	public String readpswrd() {
		try
		{
			return prop.getProperty("pswrd");
		}
		catch (Exception e){
			logg.info("Error in init_prop"+e);
			throw e;
		}
	}

}
