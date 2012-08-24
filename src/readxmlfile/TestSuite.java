/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readxmlfile;

import com.Global.Logger;
import com.Global.MyConnection;
import com.Global.ReadXML;
import com.Global.SSHConnect;
import com.mysql.jdbc.ResultSet;

/**
 *
 * @author Anil.Kumar
 */
public class TestSuite {

    /**
     * @param args the command line arguments
     */
    
        static ReadXML x = new ReadXML();
        static MyConnection myconn;
        static SSHConnect s;        
        ResultSet result;
        String id,name,seuid,status=null;
        
    public void gce_testSuite() throws Exception
    {
        try
        {
         
         int cpid=0;
         String query = "SELECT c.* FROM tsacommon.accounts a,tsacommon.searchEngineUsers s, content_editor_google.gce_campaign c "+
"WHERE s.accountID = a.id AND s.id = c.searchEngineUserID AND s.distributionID=3 AND a.id= "+x.acno+" LIMIT 1";
         
         result = (ResultSet) myconn.queryExec(query);
         
         while (result.next()) {
            cpid = result.getInt(1);
            }
         Logger.writeToLog("Campaign id "+cpid);
        for(int i=0;i<ReadXML.NoOfTestcases;i++){
                        
            Logger.writeToLog("Executing Test case "+(i+1)+" : "+x.caseDesc.get(i));         

            String changeRequest = "INSERT INTO gce_campaignPendingChange (campaignID,TYPE) VALUES ("+cpid+",'"+x.changeReq+"')";
            String statusChange = "INSERT INTO gce_campaignPendingUpdate (campaignId,STATUS) VALUES ("+cpid+",'"+x.initialVal.get(i) +"')";
            myconn.addTobatchnExecute(changeRequest,statusChange);	

            Logger.writeToLog("Batch Executed for loop " + i);

            Logger.writeToLog("Execute Script now " + i);
            Thread.sleep(30000);

            Logger.writeToLog("Sleeping for 60 sec " + i);
            Thread.sleep(60000);


            //Script exec   Path->Scriptname->Parameters
            // SSH close
            Logger.writeToLog("Waiting 60 sec for the database to update " + i);
            Thread.sleep(60000);
            
            String query1="select * from gce_campaign where id = "+ cpid;
            result = (ResultSet) myconn.queryExec(query1);

                while (result.next()) {
                    id = result.getString(1);//
                    seuid = result.getString(2);//serachengineuserid
                    name = result.getString(4);//Camp name
                    status = result.getString(10);//Status
                    }

                if(status.equals(x.expRes.get(i))){            
                    Logger.writeToLog("Test case executed successfully");
                    Logger.writeToLog("The campaign status is " + status);
                    ReadXML.NoOfTestcasesPassed++;
                }
                else{
                    Logger.writeToLog("Test case Failed");
                    ReadXML.NoOfTestcasesFailed++;
                }
            
            } //End of for loop
        } //End of  Try Block Close
        catch(Exception E){
           //s.closeSSH();
           //myconn.closeConn();
           Logger.writeExceptionLog(E);
        }  // End of Catch Block
        //ReadXML.itr++;    
    } // End of Method gce_testSuite()
    
    
    public static void main(String[] args) throws Exception{
        
        TestSuite t = new TestSuite();
        x.readXMLFile();
        myconn = new MyConnection(x.dhost,x.duser,x.dpass);
        //s= new SSHConnect(x.shost,x.suser,x.spass);        
        t.gce_testSuite(); 
        //s.closeSSH();
        myconn.closeConn(); 
        Logger.testcaseExecSummary();
    }
}
