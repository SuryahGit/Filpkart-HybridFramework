package com.flipkartHybrid.Utility;

import java.util.Hashtable;

public class DataUtil {

	public static Object[][] getData(Xls_Reader xls, String testcaseName) {

		int row = xls.getRowCount(Constants.Data_Sheet);
		System.out.println(row);
		int rowStartsfrom = 0;
		while (!xls.getCellData(Constants.Data_Sheet, 0, rowStartsfrom).equals(testcaseName)) {
			rowStartsfrom++;
		}
		System.out.println(rowStartsfrom);

		int DatastartsFrom = rowStartsfrom + 2;
		int colStartsFrom = rowStartsfrom + 1;
		int rows = 0;
		while (!xls.getCellData(Constants.Data_Sheet, 0, DatastartsFrom + rows).equals("")) {
			rows++;
		}
		System.out.println("Number of Datas " + rows);

		int col = 0;
		while (!xls.getCellData(Constants.Data_Sheet, col, colStartsFrom).equals("")) {
			col++;
		}
		Object[][] obj = new Object[rows][1];

		int rNums = 0;
		System.out.println("Number of Cols " + col);
		for (int rNum = DatastartsFrom; rNum < DatastartsFrom + rows; rNum++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < col; cNum++) {
				String Keys = xls.getCellData(Constants.Data_Sheet, cNum, colStartsFrom);
				String Value = xls.getCellData(Constants.Data_Sheet, cNum, rNum);
				table.put(Keys, Value);
				// obj[rNums][cNum] = xls.getCellData(sheetName, cNum, rNum);
				// System.out.print(xls.getCellData(sheetName, cNum, rNum));
			}
			obj[rNums][0] = table;
			rNums++;

		}
		return obj;
	}

	public static boolean isTestCaseRunnable(Xls_Reader xls, String testcaseName)
	{
		
		int row =  xls.getRowCount(Constants.TestCases_Sheet);
		for(int rNum=2; rNum<row;rNum++)
		{
			String TCName = xls.getCellData(Constants.TestCases_Sheet, Constants.TCID_Col, rNum);
			if(TCName.equals(testcaseName))
			{
				String Runmode = xls.getCellData(Constants.TestCases_Sheet, Constants.Runmode_Col, rNum);
				if(Runmode.equals("N"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return true;
		
	}
}
