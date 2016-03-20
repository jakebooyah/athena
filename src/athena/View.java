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

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View 
{
	private Model model;
	
	private JFrame mainpage;
	private JFileChooser inputFileSelector;
	private JFileChooser outputFolderSelector;
	private JButton browseInputFile;
	private JButton browseOutputFile;
	private JTextArea selectedInputPath;
	private JTextArea selectedOutputPath;
	private JButton convertFile;
	private JTextArea logOutput;
	
	public View(Model model)
	{
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedLookAndFeelException e) 
		{
			e.printStackTrace();
		}

		this.model = model;
		
		initMain();
		
		mainpage.setVisible(true);
	}
	
	public void initMain()
	{
		mainpage = new JFrame();
		mainpage.setResizable(false);
		mainpage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainpage.setTitle("Athena");
		mainpage.setBounds(200, 200, 600, 400);
		mainpage.getContentPane().setLayout(null);
		
		JLabel selectFile = new JLabel("Select excel file to be converted to csv:");
		selectFile.setFont(new Font("Tahoma", Font.PLAIN,12));
		selectFile.setBounds(20, 20, 300, 20);
		mainpage.getContentPane().add(selectFile);
		
		JLabel outputFilePath = new JLabel("Select output folder:");
		outputFilePath.setFont(new Font("Tahoma", Font.PLAIN,12));
		outputFilePath.setBounds(20, 80, 300, 20);
		mainpage.getContentPane().add(outputFilePath);
		
		FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Excel Files (.xlxs)","xlsx");
		
		inputFileSelector = new JFileChooser();
		inputFileSelector.setDialogTitle("Select File");
		inputFileSelector.addChoosableFileFilter(excelFilter);
		inputFileSelector.setAcceptAllFileFilterUsed(false);
		
		outputFolderSelector = new JFileChooser();
		outputFolderSelector.setDialogTitle("Select Output Folder");
		outputFolderSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		outputFolderSelector.setAcceptAllFileFilterUsed(false);
		
		selectedInputPath = new JTextArea("");
		selectedInputPath.setFont(new Font("Tahoma", Font.PLAIN,12));
		selectedInputPath.setBounds(130, 47, 320, 25);
		selectedInputPath.setEditable(false);
		mainpage.getContentPane().add(selectedInputPath);
		
		selectedOutputPath = new JTextArea("");
		selectedOutputPath.setFont(new Font("Tahoma", Font.PLAIN,12));
		selectedOutputPath.setBounds(130, 107, 320, 25);
		selectedOutputPath.setEditable(false);
		mainpage.getContentPane().add(selectedOutputPath);
		
		browseInputFile = new JButton("Select");
		browseInputFile.setBounds(20, 45, 100, 30);
		browseInputFile.setFont(new Font("Tahoma", Font.PLAIN,11));
		mainpage.getContentPane().add(browseInputFile);
		
		browseOutputFile = new JButton("Select");
		browseOutputFile.setBounds(20, 105, 100, 30);
		browseOutputFile.setFont(new Font("Tahoma", Font.PLAIN,11));
		mainpage.getContentPane().add(browseOutputFile);
		
		convertFile = new JButton("Convert");
		convertFile.setBounds(460, 20, 120, 120);
		convertFile.setFont(new Font("Tahoma", Font.PLAIN,11));
		mainpage.getContentPane().add(convertFile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 150, 560, 200);
		
		logOutput = new JTextArea();
		logOutput.setEditable(false);
		logOutput.setFont(new Font("Tahoma", Font.PLAIN,11));
		logOutput.setText(model.getStatusMsg());
		
		scrollPane.setViewportView(logOutput);
		mainpage.add(scrollPane);
	}
	
	public void addSelectInputListener(ActionListener selectInput)
	{
		browseInputFile.addActionListener(selectInput);
	}
	
	public void addSelectOutputListener(ActionListener selectOutput)
	{
		browseOutputFile.addActionListener(selectOutput);
	}
	
	public void addConvertListener(ActionListener convert)
	{
		convertFile.addActionListener(convert);
	}
	
	public void setOutput(String outputMsg)
	{
		model.addStatusMsg(outputMsg);
		logOutput.setText(model.getStatusMsg());
	}
	
	public void refreshIntputPath()
	{
		selectedInputPath.setText(model.getInputFilePath());
	}
	
	public void refreshOutputPath()
	{
		selectedOutputPath.setText(model.getOutputFilePath());
	}
	
	public void getInputFile()
	{				
		int returnVal = inputFileSelector.showOpenDialog(browseInputFile);
		
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File inputFile = inputFileSelector.getSelectedFile();
			String inputFilePath = inputFileSelector.getCurrentDirectory() + File.separator + inputFile.getName();
			setOutput("Selected input file: " + inputFilePath);
			model.setInputFilePath(inputFilePath);
			refreshIntputPath();
		}
	}
	
	public void getOutputFilePath()
	{
		int returnVal = outputFolderSelector.showOpenDialog(browseOutputFile);
		
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File outputFile = outputFolderSelector.getSelectedFile();
			String outputFilePath = outputFolderSelector.getCurrentDirectory() + File.separator + outputFile.getName() + File.separator;
			setOutput("Selected output file path: " + outputFilePath);
			model.setOutputFilePath(outputFilePath);
			refreshOutputPath();
		}
	}
}
