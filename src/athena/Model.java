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

public class Model 
{
	private String inputFilePath = null;
	private String outputFilePath = null;
	private String statusMsg = "XLXS to UTF-8 CSV converter\nCopyright (c) 2016 Jake\n";
	
	public String getInputFilePath()
	{
		return inputFilePath;
	}
	
	public String getOutputFilePath()
	{
		return outputFilePath;
	}
	
	public String getStatusMsg()
	{
		return statusMsg;
	}
	
	public void setInputFilePath(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}
	
	public void setOutputFilePath(String outputFilePath)
	{
		this.outputFilePath = outputFilePath;
	}
	
	public void addStatusMsg(String statusMsg)
	{
		this.statusMsg = this.statusMsg + "\n" + statusMsg;
	}
}
