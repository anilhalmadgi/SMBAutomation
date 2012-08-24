/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Global;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 *
 * @author Anil.Kumar
 */
public class Logger {
    public static void writeToLog(String message) throws Exception {
       
        Calendar currentDate = Calendar.getInstance();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());
           
        FileWriter fstream = new FileWriter("E:/Projects/SMBAutomation/src/readxmlfile/out.txt",true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(dateNow +"]  "+ message);
        out.newLine();
        out.close();
    }
    public static void writeExceptionLog(Exception E) throws Exception{
                writeToLog(E.toString());
    }
    public static void testcaseExecSummary() throws Exception {
        writeToLog("=============================================");
        writeToLog("Summary of the test cases executed");
        writeToLog("=============================================");
        writeToLog("Total Number of test cases :" + ReadXML.NoOfTestcases);
        writeToLog("Total Number of test case(s) Passed:" + ReadXML.NoOfTestcasesPassed);
        writeToLog("Total Number of test case(s) Failed:" + ReadXML.NoOfTestcasesFailed);
        writeToLog("=============================================");
    }
}
