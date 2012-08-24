/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Global;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecShellScript;


/**
 *
 * @author Anil.Kumar
 */
public class SSHConnect {
            
            ConnBean cb;
            SSHExec ssh;

            public SSHConnect(String host,String user,String pass) throws InterruptedException, Exception{

                cb = new ConnBean(host,user,pass);	
                ssh = SSHExec.getInstance(cb);          
                ssh.connect();
                Logger.writeToLog("Connected To The SSH\n");
                Thread.sleep(60000);
            }
            
            public void closeSSH() throws Exception{                         
                if(cb != null){
                    Logger.writeToLog("Test server"+cb.getHost());
                    ssh.disconnect();
                    Logger.writeToLog("Disconnected From SSH\n");               
                }                
            }
            
            public void execCommand(String path,String scriptname,String args) throws TaskExecFailException{
                //Execute shell script on remote system

                CustomTask ct1 = new ExecShellScript(path,scriptname,args);
                ssh.exec(ct1);
            }
                    
}