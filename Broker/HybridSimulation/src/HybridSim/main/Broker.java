package HybridSim.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Broker extends Thread{
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
			handleMsgFromCooja("Hi");
			handleMsgFromOmnetpp("Hi");
			
			this.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void run() {
        super.run();
        try {
        	//handle msg from cooja
            byte[] cbuf = new byte[1024];
            int clen = 0;
            String cbuffer = "";
            while ((clen = C_INPUT.read(cbuf)) != -1) {
                //System.out.println(new String(cbuf, 0, clen));
                cbuffer = new String(cbuf, 0, clen);
                System.out.println(cbuffer);
                handleMsgFromCooja(cbuffer);
            }
            
            //handle msg from omnetpp
            byte[] obuf = new byte[1024];
            int olen = 0;
            String obuffer = "";
            while ((olen = O_INPUT.read(obuf)) != -1) {
                //System.out.println(new String(obuf, 0, olen));
                obuffer = new String(obuf, 0, olen);
                handleMsgFromOmnetpp(obuffer);
            }
            
                        
        } catch (IOException e) {
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
    
//    public static void main(String[] args) {  
//        System.out.println("Client started");  
//        System.out.println(" when command of \"OK\" is received, client terminates.\n"); 
//        while (true) {  
//            Socket socket = null;
//            try {
//                   
//                DataInputStream input = new DataInputStream(socket.getInputStream());  
//
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
//                System.out.print("Input: \t");  
//                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
//                out.writeUTF(str); 
//                
//                String ret = input.readUTF();   
//                System.out.println("Recved from server: " + ret);  
//  
//                //if ("OK".equals(ret)) {  
//                //    System.out.println("Client shutting down");  
//                //    Thread.sleep(500);  
//                //    break;  
//                //}  
//                
//                //out.close();
//                //input.close();
//            } catch (Exception e) {
//                System.out.println("Client Error:" + e.getMessage()); 
//            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        socket = null; 
//                        System.out.println("Client finally Error:" + e.getMessage()); 
//                    }
//                }
//            }
//        }  
//    }  
}
