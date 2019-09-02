package HybridSim.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Broker{
	public int ID = 255;
    private String IP_ADDR = "localhost";
    private int C_PORT = 4242;
    private int O_PORT = 6001;
    private Socket C_SOCKET = null;
    private Socket O_SOCKET = null;
    private InputStream C_INPUT = null;
    private InputStream O_INPUT = null;
    private OutputStream C_OUTPUT = null;
    private OutputStream O_OUTPUT = null;
    
    public Broker(int id, int cport, int oport){
    	this.ID = id;
    	this.C_PORT = cport;
    	this.O_PORT = oport;
    }
    
    public void initConnection(){
    	try {
    		System.out.println("#BROKER -- Connecting Cooja node socket "+this.C_PORT+" with Omnet node socket "+this.O_PORT);  
			
    		C_SOCKET = new Socket(this.IP_ADDR,this.C_PORT);
			O_SOCKET = new Socket(this.IP_ADDR,this.O_PORT);
			
			C_INPUT = C_SOCKET.getInputStream();
			O_INPUT = O_SOCKET.getInputStream();
			C_OUTPUT = C_SOCKET.getOutputStream();
			O_OUTPUT = O_SOCKET.getOutputStream();
			
			System.out.println("#BROKER -- Writing a hi message to Cooja node socket "+this.C_SOCKET+" and Omnet node socket "+this.O_SOCKET);  
			handleMsgFromCooja("HiFromCooja");
			handleMsgFromOmnetpp("HiFromOmnetpp");
			
			CoojaTread ct = new CoojaTread();  
			OmnetppTread ot = new OmnetppTread();
	        ct.start();
	        ot.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void handleMsgFromCooja(String msg){
    	//ToDo
    	//Translate/reformate data
    	//Send to Omnetpp
    	String[] rowValues = {""+this.C_PORT,""+this.O_PORT,""+msg};
    	MainGui.tableModel.addRow(rowValues);
    	try {
			O_OUTPUT.write(msg.getBytes());
			O_OUTPUT.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void handleMsgFromOmnetpp(String msg){
    	//ToDo
    	//Translate/reformate data
    	//Send to Cooja
    	String[] rowValues = {""+this.O_PORT,""+this.C_PORT,""+msg};
    	MainGui.tableModel.addRow(rowValues);
    	try {
			C_OUTPUT.write(msg.getBytes());
			C_OUTPUT.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    class CoojaTread extends Thread{
        public void run(){
        	//handle msg from cooja
            byte[] cbuf = new byte[1024];
            int clen = 0;
            String cbuffer = "";
            try {
				while ((clen = C_INPUT.read(cbuf)) != -1) {
					System.out.println("MSG RECVED FROM COOJA: ");
				    //System.out.println(new String(cbuf, 0, clen));
				    cbuffer = new String(cbuf, 0, clen);
				    System.out.println(cbuffer);
				    handleMsgFromCooja(cbuffer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    class OmnetppTread extends Thread{
        public void run(){
        	//handle msg from omnetpp
            byte[] obuf = new byte[1024];
            int olen = 0;
            String obuffer = "";
            try {
				while ((olen = O_INPUT.read(obuf)) != -1) {
					System.out.println("MSG RECVED FROM OMNETPP: ");
				    //System.out.println(new String(obuf, 0, olen));
				    obuffer = new String(obuf, 0, olen);
				    handleMsgFromOmnetpp(obuffer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
}
