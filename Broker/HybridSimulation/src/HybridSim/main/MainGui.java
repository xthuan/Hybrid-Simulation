package HybridSim.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.Font;

public class MainGui extends JFrame{

	private JPanel contentPane;
    
	public static JTabbedPane tabbedPane_1;
	public static DefaultTableModel tableModel;
	public static JTable table;
	public static JScrollPane scrollPane_1;
	public static JTextField textField = new JTextField();
	public static JTextField textField_2 = new JTextField();;
	
	private boolean RUNNING = false;
	java.util.Timer running_timer;
	
	private String fileDir = "";
	private String fileName = "";
	private JTextField textField_3;
	
	private int id_counter = 1;
	
//	private LogHandler lch;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui frame = new MainGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGui() {
		setTitle("");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
        tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        
        String[] columnNames_1 = {"From","To","Message Contents"};
        
        String [][]tableVales_1={}; 
        tableModel = new DefaultTableModel(tableVales_1,columnNames_1);
        contentPane.setLayout(new BorderLayout(0, 0));
        table = new JTable(tableModel);
        table.setBackground(new Color(238, 238, 238));
		
        scrollPane_1 = new JScrollPane(table);
        
        tabbedPane_1.addTab("Message Center", null, scrollPane_1, null);
        contentPane.add(tabbedPane_1, BorderLayout.CENTER);
        
        JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane_2, BorderLayout.WEST);
        
        JPanel panel_1 = new JPanel();
        tabbedPane_2.addTab("  Control Center  ", null, panel_1, null);
        panel_1.setLayout(null);
        
        JLabel lblId = new JLabel("Cooja PORT:");
        lblId.setBounds(6, 12, 120, 16);
        panel_1.add(lblId);
        
        textField.setBounds(31, 40, 65, 28);
        panel_1.add(textField);
        textField.setColumns(10);
        
        JLabel lblCmd = new JLabel("OMNET++ PORT:");
        lblCmd.setBounds(6, 80, 120, 16);
        panel_1.add(lblCmd);
        
        textField_2.setColumns(10);
        textField_2.setBounds(30, 108, 66, 28);
        panel_1.add(textField_2);
        
        JButton btnSet = new JButton("CONNECT");
        btnSet.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Broker broker = new Broker(id_counter,Integer.parseInt(textField.getText()),Integer.parseInt(textField_2.getText()));
        		broker.initConnection();
        	}
        });
        btnSet.setBounds(6, 148, 120, 29);
        panel_1.add(btnSet);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(30, 227, 66, 28);
        panel_1.add(textField_3);
        
        JLabel lbNumConn = new JLabel("Number of CONNS:");
        lbNumConn.setBounds(6, 199, 125, 16);
        panel_1.add(lbNumConn);
        
        JButton btnAutoset = new JButton("AUTO-CONN");
        btnAutoset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        btnAutoset.setBounds(6, 267, 120, 29);
        panel_1.add(btnAutoset);
    }
	
	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}



