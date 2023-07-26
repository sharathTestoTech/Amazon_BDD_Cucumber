package com.amazon.automation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil extends ConfigReader {
	
	private Map<String, Integer> columns = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFCell cell = null;
	private Sheet newSheet;
	private Row row;
	private Cell cellC = null;
	private String cellvalue = "";
	private String eachcolumnName = null;
	private int lastcellnum =0; 
	private int headerindex = 0;
	private Boolean bool = false;
	private Logger logg;

	
	/**
	 * @author
	 * @Description 
	 * @param xlFilePath
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public ExcelUtil(String xlFilePath,Logger logg) throws IOException, InterruptedException {
			super(logg);
			this.logg=logg;
		
		try (FileInputStream fis = new FileInputStream(xlFilePath)){
			workbook = new XSSFWorkbook(fis);
		} 
		catch (FileNotFoundException e) {
			logg.severe("Error in ReadExcelUtil" + e);
		}
		catch (IOException e) {
			logg.severe("Error in ReadExcelUtil" + e);
		}	
	}

	@SuppressWarnings("incomplete-switch")
	public String getCellData(String SheetName, String ColumnName, int rownum) throws Exception {
		
		try {
			sheet = workbook.getSheet(SheetName);
			
			columns	= new HashMap<String,Integer>();
			
			sheet.getRow(headerindex).forEach(cellA -> {
				columns.put(cellA.getStringCellValue(), cellA.getColumnIndex());
			});
			
			cell = sheet.getRow(rownum).getCell(columns.get(ColumnName));
			
			if(cell == null) {
				cellvalue = "";
			}
			
			switch (cell.getCellType()) {
			case STRING:
				cellvalue = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellvalue = String.valueOf(cell.getDateCellValue());
				} else {
					cellvalue = String.valueOf((long) cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				cellvalue = Boolean.toString(cell.getBooleanCellValue());
				break;
			case BLANK:
				cellvalue = "";
				break;
			}
		}
		catch (Exception e) {
			logg.severe("Error in ReadExcelUtil" + e);
		}
		return cellvalue;
	}

	public void setCellData(String filepath, String sheetname, String columnName, int rownum, String value)
			throws IOException, InterruptedException {
		
		try (FileOutputStream out = new FileOutputStream(new File(filepath));
				FileInputStream fis = new FileInputStream(new File(filepath));
				) {
		
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				bool = workbook.getSheetName(i).toString().equalsIgnoreCase(sheetname);
				if (bool == true) {
					break;
				}
			}
			
			if (bool == false) {
				newSheet = workbook.createSheet(sheetname);
			} else {
				newSheet = workbook.getSheet(sheetname);
			}
			
			columns = new HashMap<>();
			
			if (newSheet.getLastRowNum() < 0) {
				newSheet.createRow(headerindex);
			}
			
			row = newSheet.getRow(rownum);
			
			if (checkIfRowIsEmpty(row) == false) {
				row = newSheet.getRow(rownum);
			} else {
				row = newSheet.createRow(rownum);
			}
			
			newSheet.getRow(headerindex).forEach(cellA -> {
				columns.put(cellA.getStringCellValue(), cellA.getColumnIndex());
			});
			
			if (newSheet.getRow(headerindex).getLastCellNum() < 0) {
				newSheet.getRow(headerindex).createCell(headerindex).setCellValue(columnName);
			}
			
			lastcellnum = newSheet.getRow(headerindex).getLastCellNum();
			
			if (columns.isEmpty()) {
				 columns.put(columnName, columns.size());
				newSheet.getRow(headerindex).createCell(lastcellnum - 1).setCellValue(columnName);
			}
			
			lastcellnum = newSheet.getRow(headerindex).getLastCellNum(); 
			
			for (int j = 0; j < lastcellnum; j++) {
				eachcolumnName = newSheet.getRow(headerindex).getCell(j).getStringCellValue().trim();
				if (!(columns.containsKey(eachcolumnName))) {
					columns.put(eachcolumnName, columns.size());
				}
			}
			
			if (!(columns.containsKey(columnName))) {
				newSheet.getRow(headerindex).createCell(newSheet.getRow(headerindex).getLastCellNum()).setCellValue(columnName);
				columns.put(columnName, columns.size());
			}
			
			if (newSheet.getRow(rownum).getLastCellNum() < 0) {
				newSheet.getRow(rownum).createCell(headerindex).setCellValue(value);
				
			} 
			else if (newSheet.getRow(rownum).getLastCellNum() == 1) {
						newSheet.getRow(rownum).createCell(1).setCellValue(value);
			}
			else {
				cellC = newSheet.getRow(rownum).createCell(columns.get(columnName));
				cellC.setCellValue(value);
			}
			
			workbook.write(out);
			out.flush();
		} 
		catch (Exception e) {
			logg.severe("Error in WriteExcelUtil" + e);
		}
	}

	private boolean checkIfRowIsEmpty(Row row) {
		try {
			if (row == null) {
				bool = true;
			} else if (row.getLastCellNum() >= 0) {
				bool=false;
			}
		} catch (Exception e) {
			logg.severe("Error in WriteExcelUtil" + e);
		}
		return bool;
	}

	public int getEmptyRowNumber(String filepath, String sheetname) throws IOException {
		int rownum = 0;
		try (FileInputStream fis = new FileInputStream(new File(filepath));
				XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
			if (workbook.getSheet(sheetname) != null) {
				newSheet = workbook.getSheet(sheetname);
				if (newSheet.getLastRowNum() < 0) {
					rownum++;
				} else {
					rownum = newSheet.getLastRowNum() + 1;
				}
			} else {
				rownum++;
			}
		} 
		catch (FileNotFoundException e) {
			logg.severe("Error in Write Excel" + e);
		}
		catch (IOException e) {
			logg.severe("Error in Write Excel" + e);
		}
		return rownum;
	}

}
