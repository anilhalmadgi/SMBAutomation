/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Global;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;


/**
 * Class Reads the XML file and returns the value.
 * @author Anil.Kumar
 */

public class ReadXML {
    
    public String dhost,duser,dpass,shost,suser,spass;
    public List<String> caseDesc = new ArrayList<String>();
    public List<String> expRes = new ArrayList<String>();
    public List<String> initialVal = new ArrayList<String>();    
    public String acno,seUserId,changeReq;
    public static int NoOfTestcases,NoOfTestcasesPassed,NoOfTestcasesFailed,itr=0;
    public String script,debugLevel,date,startDate,endDate,distributionID;
    
    /**
     * 
     * @param Child element.
     * @param Element name passed to fetch the value.
     * @return String type with the item value.
     */
    
    String readElementValue(Element e,String elename){
          Element test = e.getFirstChildElement(elename);
          String elementvalue=test.getValue();
          return elementvalue;
          
        }
    
    /**
     * This Reads the XML File
     * @throws Exception 
     */
    
   public void readXMLFile() throws Exception{
              
       String filename = "E:/Projects/SMBAutomation/src/readxmlfile/testxml.xml";
       FileInputStream reader = new FileInputStream(new File(filename));
       Logger.writeToLog("=======================================");
       Logger.writeToLog("       Welcome to QA Automation        ");
       Logger.writeToLog("=======================================");
       Logger.writeToLog("Reading Values from XML file");
              
       Builder xmlBuilder = new Builder();                  // An nu.xom.Builder class.
       Document doc = xmlBuilder.build(reader);             // An nu.xom.Document Class.
       
       Element root = doc.getRootElement();                 // To get the root element from the XML file.
       
       Elements dbCon = root.getChildElements("dbcon");     // To get the Child element from the list.
       //String s= root.getQualifiedName();       
       //Logger.writeToLog("Root element " +s);
       
       Elements sshCon = root.getChildElements("sshcon");
       
       Elements accDetail = root.getChildElements("testAccountDetails");
       
       Elements testSuite = root.getChildElements("testSuiteDetails");  
       
       Elements testId = root.getChildElements("testCase");   // To get the Child element from the list.
              
       Element sshconEle = sshCon.get(0);
       
       shost = readElementValue(sshconEle,"host");
       suser = readElementValue(sshconEle,"user");
       spass = readElementValue(sshconEle,"pass");
       //System.out.print(shost+" "+suser+" "+spass);
       
       Element dbconEle = dbCon.get(0);
       
       dhost = readElementValue(dbconEle,"host");
       duser = readElementValue(dbconEle,"user");
       dpass = readElementValue(dbconEle,"pass");
       //System.out.print(dhost+" "+duser+" "+dpass);    
       
       Element accdetailEle = accDetail.get(0);
       
       acno = readElementValue(accdetailEle,"accountID");
       seUserId = readElementValue(accdetailEle,"seUserID");
       
       Element testSuiteEle = testSuite.get(0); 
       
       
       script=readElementValue(testSuiteEle,"script");
       debugLevel=readElementValue(testSuiteEle,"debugLevel");
       date = readElementValue(testSuiteEle,"date");
       startDate = readElementValue(testSuiteEle,"startDate");
       endDate = readElementValue(testSuiteEle,"endDate");
       distributionID = readElementValue(testSuiteEle,"distributionID");
       
       
       NoOfTestcases = testId.size();       
       
       for(int i =0;i<NoOfTestcases;i++)
       {       
           Element testidEle = testId.get(i);
           caseDesc.add(readElementValue(testidEle,"caseDescription"));
           changeReq=(readElementValue(testidEle,"changeRequest"));
           initialVal.add(readElementValue(testidEle,"initialValue"));
           expRes.add(readElementValue(testidEle,"expectedResult"));
       }       
    }
}
