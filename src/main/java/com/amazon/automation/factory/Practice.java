package com.amazon.automation.factory;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Practice {

	public static void main(String[] args) throws InterruptedException, IOException {
  
        FileInputStream file = new FileInputStream(
            "C:\\Users\\ASUS\\OneDrive\\Desktop\\TamotaDatanew.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sh = wb.getSheet("prices");
        DataFormatter formatter = new DataFormatter();
       List<Map<String,String>> data=new ArrayList<Map<String,String>>();
        for (int r = 1; r <= sh.getLastRowNum(); r++) {
        	 Map<String, String> map
             = new HashMap<String, String>();
   
        	for(int j=0;j<sh.getRow(r).getLastCellNum();j++) {
            Cell key = sh.getRow(0)
                          .getCell(j);
            
            String keyValue = formatter.formatCellValue(key);
            
                          
            Cell value = sh.getRow(r)
                               .getCell(j);
            
            String strValue = formatter.formatCellValue(value);
            
            map.put(keyValue, strValue);
        }
        	data.add(map);
  
    }

        wb.close();
        file.close();
        
       Map<String,List<Integer>> mvgMonth=getMonthlyMovingAvgs(data);
       StringBuilder txt1=new StringBuilder();
       
       txt1.append("Month&Year\tMvgMonth\n");
       
       Set<String> Keys=mvgMonth.keySet();
       
       for(String key:Keys) {
    	   
    	   List<Integer> al=mvgMonth.get(key);
    	   int sum = al.stream().mapToInt(Integer::intValue).sum();
    	   int avg=sum/al.size();
       txt1.append(key+"\t"+avg+"\n");
       }
       
       FileWriter fw=new FileWriter("C:\\Users\\ASUS\\OneDrive\\Desktop\\tamotalogic.txt");    
       fw.write(txt1.toString());    
       fw.close();    
    
       System.exit(0);
       
        StringBuilder txt=new StringBuilder();
          txt.append("Month&Year\tFrom Date\tToDate150\t150DaysAvg\t150days-NoOfInputs\tToDate120\t120DaysAvg\t120days-NoOfInputs\tToDate90\t90DaysAvg\t90days-NoOfInputs\tToDate60\t60DaysAvg\t60days-NoOfInputs\tToDate30\t30DaysVag\t30days-NoOfInputs\tToDate15\t15DaysVag\t15days-NoOfInputs\tToDate7\t7DaysAvg\t7days-NoOfInputs\tcurrentPrice\tpriceAfter65Days\tdate65\tAvgOfAvg\tIsIncreasing\tIsDecreasing\tRandom\tForeCastOneMonthAvf\tforecastlastDate\n");
        	  for(int i=150;i<data.size()-200;i++) {
        		  String fromDate=data.get(i).get("Price Date");
        		  String monthNdYear=fromDate.split("-")[1]+"-"+fromDate.split("-")[2];
        		   String currentPrice=data.get(i).get("Box Max  price");
        		   
        		   String Variety=data.get(i).get("Variety");
        				   
        				   if(!Variety.equals("Tomato")) {
        					   continue;
        				   }
        		   
        		   String price65="";
        		   
        		  Map<Integer,List<String>> rowMvgs=getMovingAvg(data,i);
        		  
        		  String toDate150=rowMvgs.get(150).get(2);
        		  String cnt150=rowMvgs.get(150).get(1);
        		  String mvg150=rowMvgs.get(150).get(0);
        		  
        		  String toDate120=rowMvgs.get(120).get(2);
        		  String cnt120=rowMvgs.get(120).get(1);
        		  String mvg120=rowMvgs.get(120).get(0);
        		  
        		  
        		  
        		  String toDate90=rowMvgs.get(90).get(2);
        		  String cnt90=rowMvgs.get(90).get(1);
        		  String mvg90=rowMvgs.get(90).get(0);
        		  
        		  String toDate60=rowMvgs.get(60).get(2);
        		  String cnt60=rowMvgs.get(60).get(1);
        		  String mvg60=rowMvgs.get(60).get(0);
        		
        		  String toDate30=rowMvgs.get(30).get(2);
        		  String cnt30=rowMvgs.get(30).get(1);
        		  String mvg30=rowMvgs.get(30).get(0);
        		  
        		  String toDate15=rowMvgs.get(15).get(2);
        		  String cnt15=rowMvgs.get(15).get(1);
        		  String mvg15=rowMvgs.get(15).get(0);
        		
        		
        		  String toDate7=rowMvgs.get(7).get(2);
        		  String cnt7=rowMvgs.get(7).get(1);
        		  String mvg7=rowMvgs.get(7).get(0);
        		  
        		  
        		  boolean isDecreasing=false;
        		  boolean isIncreasing =false;
        		  boolean random=false;
        		  
        		  
        		  int m150=Integer.parseInt(mvg150);
        		  int m120=Integer.parseInt(mvg120);
        		  int m90=Integer.parseInt(mvg90);
        		  int m60=Integer.parseInt(mvg60);
        		  int m30=Integer.parseInt(mvg30);
        		  int m7=Integer.parseInt(mvg7);
        		  
        		  int avgOfavg=(m150+m120+m90+m60+m30+m7)/6;
        		  
        		  if((m150>=m120)&& (m120>=m90) && (m90>=m60) && (m60>=m30)) {
        			  isDecreasing=true;  
        		  }
        		  
        		  if((m150<=m120)&& (m120<=m90) && (m90<=m60) && (m60<=m30)) {
        			  isIncreasing=true;  
        		  }
        		  
        		  if(isIncreasing==false && isDecreasing==false ) {
        			  random=true;
        		  }
        		   
        		  
        		  
        		  
        		 // String fromDate=data.get(index).get("Price Date");
               	  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MMM-yy");
               	 
               	  //convert String to LocalDate
               	  LocalDate fromDt = LocalDate.parse(fromDate, dtf);
               	  int cnt=1;
               	String to65Date="";
               	
               	String forCastPrice="";
               	String forcasteDate="";
               	
               	int forcastSum=0;
               	String forcasetLastDatePrice="";
               	String forcastLastDate="";
               	int forcastCnt=1;
               	int forcastAvg1month=0;
               	
               	  for(int k=i+1;cnt<=95;cnt++,k++) {
               		  
               		  
               		to65Date  =data.get(k).get("Price Date");
                   	 
                   	  //convert String to LocalDate
                   	  LocalDate to65Dt = LocalDate.parse(to65Date, dtf);
                   	 
                   price65=	data.get(k).get("Box Max  price");
                   
                     	long daysDiff = ChronoUnit.DAYS.between( fromDt,to65Dt);
                     	if(daysDiff>=65 && daysDiff<=95) {
                     		forcastCnt++;
                     		forcastSum=forcastSum+Integer.parseInt(data.get(k).get("Box Max  price"));
                     		forcastLastDate=data.get(k).get("Price Date");
                     		forcasetLastDatePrice=data.get(k).get("Box Max  price");
                     	}else
                     	
                            if(daysDiff<65) {
                       	 forCastPrice=price65;
                       	forcasteDate=to65Date;
                            }else {
                            	break;
                            }
                     	
               	  }
               	forcastAvg1month=forcastSum/forcastCnt;
               	  
               	  
               	  
		       	if(Integer.parseInt(cnt30)>15) {
               	 		  
        		  txt.append(monthNdYear+"\t"+fromDate+"\t"+toDate150+"\t"+mvg150+"\t"+cnt150+"\t"+toDate120+"\t"+mvg120+"\t"+cnt120+"\t"+toDate90+"\t"+mvg90+"\t"+cnt90+"\t"+toDate60+"\t"+mvg60+"\t"+cnt60+"\t"+toDate30+"\t"+mvg30+"\t"+cnt30+"\t"+toDate15+"\t"+mvg15+"\t"+cnt15+"\t"+toDate7+"\t"+mvg7+"\t"+cnt7+"\t"+currentPrice+"\t"+forCastPrice+"\t"+forcasteDate+"\t"+avgOfavg+"\t"+isIncreasing+"\t"+isDecreasing+"\t"+random+"\t"+forcastAvg1month+"\t"+forcastLastDate+"\n");
		       	}
        		//  System.out.println(txt.toString());
        		  
        	  }
        	 
          
          FileWriter fw1=new FileWriter("C:\\Users\\ASUS\\OneDrive\\Desktop\\tamotalogic.txt");    
          fw1.write(txt.toString());    
          fw1.close();    
          
	}
	
	
	
	   public static  Map<Integer,List<String>> getMovingAvg( List<Map<String,String>> data,int index) {
		   int mvg150=0;
		   int mvg120=0;
		   int mvg90=0;
		   int mvg60=0;
		   int mvg30=0;
		   int mvg15=0;
		   int oneWeek=0;
		   
		   int sum150=0;
		   int sum120=0;
		   int sum90=0;
		   int sum60=0;
		   int sum30=0;
		   int sum15=0;
		   int sumWeek=0;
		   
		   int cnt150=1;
           int cnt120=1;
		   int cnt90=1;
		   int cnt60=1;
		   int cnt30=1;
		   int cnt15=1;
		   int cntWeek=1;
		   
		   String date150="";
		   String date120="";
		   String date90 = "";
		   String date60="";
		   String date30="";
		   String date15="";
		   String date7="";
		   
		  Map<Integer, List<String> >mavgs=new HashMap<Integer, List<String>>();
		     int cnt=1;
		     
		     String fromDate=data.get(index).get("Price Date");
       	  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MMM-yy");
       	 
       	  //convert String to LocalDate
       	  LocalDate fromDt = LocalDate.parse(fromDate, dtf);
       	       
       	  
		      for(int i=index-1;i>0;i--) {
		    	  
		    	  if(cnt>=150) {
		    		  break;
		    	  }
		    	  cnt++;
		    	  String toDate=data.get(i).get("Price Date");
		       	  LocalDate toDt = LocalDate.parse(toDate, dtf);
		       	 
		       	long daysDiff = ChronoUnit.DAYS.between( toDt,fromDt);
		       	
		       	
		       	 if(daysDiff<7) {
		       		cntWeek++;
		       		sumWeek=sumWeek+Integer.parseInt(data.get(i).get("Box Max  price"));
		       	  date7=data.get(i).get("Price Date");
		       	 }
		       	 
		       	 if(daysDiff<15) {
			       		cnt15++;
			       		sum15=sum15+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date15=data.get(i).get("Price Date"); 
		       	 }
			    
		       	 
		       	 if(daysDiff<30) {
			       		cnt30++;
			       		sum30=sum30+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date30=data.get(i).get("Price Date"); 
		       	 }
			    	
		       	 if(daysDiff<60) {
			       		cnt60++;
			       		sum60=sum60+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date60=data.get(i).get("Price Date");  	 
		       	 }
			    	
		       	 if(daysDiff<90) {
			       		cnt90++;
			       		sum90=sum90+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date90=data.get(i).get("Price Date");  	 
		       	 }
		    	 if(daysDiff<120) {
			       		cnt120++;
			       		sum120=sum120+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date120=data.get(i).get("Price Date");  	 
		       	 }
		    	 
		    	 if(daysDiff<150) {
			       		cnt150++;
			       		sum150=sum150+Integer.parseInt(data.get(i).get("Box Max  price"));
			       		date150=data.get(i).get("Price Date");  	 
		       	 }
		       	
		       	 
		        
		    	  
		      }
		     
		      
		      List<String> row150=new ArrayList<String>();
		      
		      List<String> row120=new ArrayList<String>();
 
		      List<String> row90=new ArrayList<String>();

		      List<String> row60=new ArrayList<String>();

		      List<String> row30=new ArrayList<String>();
		      
		      List<String> row15=new ArrayList<String>();


		      List<String> rowWeek=new ArrayList<String>();
		      
		      row150.add(sum150/cnt150+"");
		      row150.add(cnt150+"");
		      row150.add(date150);
		     
		      row120.add(sum120/cnt120+"");
		      row120.add(cnt120+"");
		      row120.add(date120);
		    
		      
		      row90.add(sum90/cnt90+"");
		      row90.add(cnt90+"");
		      row90.add(date90);
		      
		      
		      row60.add(sum60/cnt60+"");
		      row60.add(cnt60+"");
		      row60.add(date60);
		      
		      
		      row30.add(sum30/cnt30+"");
		      row30.add(cnt30+"");
		      row30.add(date30);
		      
		      row15.add(sum15/cnt15+"");
		      row15.add(cnt15+"");
		      row15.add(date15);

			      
		      rowWeek.add(sumWeek/cntWeek+"");
		      rowWeek.add(cntWeek+"");
		      rowWeek.add(date7);
		      
		      mavgs.put(150, row150);
		      
		      mavgs.put(120, row120);
		      
		      mavgs.put(90, row90);
		      
		      mavgs.put(60, row60);
		      
		      mavgs.put(30, row30);
		      
		      mavgs.put(15, row15);
		      
		      
		      mavgs.put(7, rowWeek);
		      
		   return mavgs;
		   
		   
	   }
	   
	   
	   public static  Map<String,List<Integer>> getMonthlyMovingAvgs( List<Map<String,String>> data) {
		   int mvg30=0;
		   
		   int sum30=0;
		   
		   int cnt30=1;
		   
		   String date30="";
		   
		   Map<String,List<Integer>>mavgs=new LinkedHashMap<String,List<Integer>>();
		      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MMM-yy");
       	 
       	  //convert String to LocalDate
       	       
       	  
		      for(int i=0;i<data.size();i++) {
		    	  String toDate=data.get(i).get("Price Date");
		       	  LocalDate toDt = LocalDate.parse(toDate, dtf);
		       int price=	Integer.parseInt(data.get(i).get("Box Max  price"));
		       
		       int year=toDt.getYear();
		       int month=toDt.getMonthValue();
		         String key=month+"-"+year;
		            	    if(mavgs.containsKey(key)) {
		            	    	
		            	    	List<Integer> days=mavgs.get(key);
		            	    	  days.add(price);
		            	    	  mavgs.put(key,days);
		            	    }else {
		            	    	List<Integer> days=new ArrayList<Integer>();
		            	    	days.add(price);
		            	    	mavgs.put(key,days);
		            	    }
		              
		       
		       	  
		      }		      
		   return mavgs;
		   
		   
	   }
	   

	
	
} 

