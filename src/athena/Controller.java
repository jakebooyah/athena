/*
 * The MIT License (MIT)
 * 
 * Athena
 * Excel Sheet to CSV Converter
 * Copyright (c) 2016 Jake
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package athena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Controller 
{
	private View view;
	private Model model;
	private Workbook wb;
	
	public Controller(View view, Model model)
	{
		this.view = view;
		this.model = model;
	}
	
	public void init()
	{
		view.addSelectInputListener(new selectInputListener());
		view.addSelectOutputListener(new selectOutputListener());
		view.addConvertListener(new convertListener());
	}
	
	class selectInputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			view.getInputFile();
		}
	}
	
	class selectOutputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			view.getOutputFilePath();
		}
	}
	
	class convertListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (model.getInputFilePath() == null)
			{
				view.setOutput("No input file selected. Please select a file to be converted!");
			}
			
			if (model.getOutputFilePath() == null)
			{
				view.setOutput("No output folder selected. Please select a folder where the csv will be stored!");
			}
			
			if (model.getInputFilePath() != null && model.getOutputFilePath() !=null)
			{
				view.setOutput("\nConverting..");
				convertExceltoCSV(model.getInputFilePath(), model.getOutputFilePath());
			}
		}
	}
	
	private void convertExceltoCSV(String inputFile, String outputFilePath)
	{
		InputStream inp = null;
        try 
        {
            inp = new FileInputStream(inputFile);
            wb = new XSSFWorkbook(inp);

            for(int i = 0; i < wb.getNumberOfSheets(); i++) 
            {
				Sheet thisSheet = wb.getSheetAt(i);
				int rowEnd = Math.max(1400, thisSheet.getLastRowNum());
				
            	view.setOutput("Writting.. " + thisSheet.getSheetName());
				
                String csvRawString = "";
        		String outputFileName = outputFilePath + thisSheet.getSheetName() + ".csv";
        		
        		try
        		{
        			OutputStream os;
        			
        			File testFile = new File(outputFileName);
	    			
	    			if (testFile.exists() && !testFile.isDirectory())
	    			{
	    				os = new FileOutputStream(outputFilePath + thisSheet.getSheetName() + "(1).csv");
	    			}
	    			else
	    			{
	    				os = new FileOutputStream(outputFileName);
	    			}    				
        			
	    			PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
                
	                for (int j = 0; j < rowEnd; j++) 
	                {	
	                    Row row = thisSheet.getRow(j);
	                    
	                    if (row != null)
	                    {
		                    for (int k = 0; k < row.getLastCellNum(); k++)
		                    {
		                    	if (k == (row.getLastCellNum()-1))
		                    	{
		                    		if (row.getCell(k) != null)
			                    	{
			                    		csvRawString = csvRawString + row.getCell(k);
			                    	}	                    		
		                    	}
		                    	else
		                    	{
		                    		if (row.getCell(k) == null)
			                    	{
				                        csvRawString = csvRawString + ",";
			                    	}
			                    	else
			                    	{
			                    		csvRawString = csvRawString + row.getCell(k) + ",";
			                    	}
		                    	}
		                    }
	                    }
	                    else
	                    {
	                    	csvRawString = csvRawString + ",";
	                    }
	                    
	                    csvRawString = csvRawString + "\n";
	                    w.print(csvRawString);
	        			w.flush();
	        			csvRawString = "";
	                }
	                
    			    w.close();
    				view.setOutput("Done.. " + thisSheet.getSheetName());
        		}
                catch (FileNotFoundException e) 
                {
    				view.setOutput("I'm confused.. File not found!");
        		}
        		catch (UnsupportedEncodingException e) 
    			{
        			view.setOutput("Call 911.. or Jake");
    			} 
            }
        } 
        catch (IOException e) 
        {
			view.setOutput("Uh oh.. Fail to read file!");
		}
        
        finally
        {
        	try 
            {
    			inp.close();
				view.setOutput("Done conversion.. " + model.getInputFilePath() + "\n");
				model.setInputFilePath(null);
				model.setOutputFilePath(null);
				view.refreshIntputPath();
				view.refreshOutputPath();
    		} 
        	catch (IOException e) 
            {
        		view.setOutput("Damn input stream..");
    		}
        }
	}
}
