package chat_bot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;


//Class
public class Tester extends JPanel{
	static String fname = null;
	static Object lock = new Object();
	public Tester() {
		// TODO Auto-generated constructor stub
		JPanel jPanel = new JPanel();
		jPanel.setBounds(0, 0, 50, 50);
		jPanel.show();
	}
	
	
	public static void FileCopy() throws FileNotFoundException, IOException, InterruptedException{
		JFileChooser jFileChooser = new JFileChooser();
		
		System.out.println("Super Program Started");
		//Original File Selector
		jFileChooser.setFileSelectionMode(0);
		jFileChooser.setDialogTitle("Select the original file");
		int i1 = jFileChooser.showOpenDialog(jFileChooser);
		if(i1==jFileChooser.CANCEL_OPTION){
			System.exit(0);
		}
	
		String original = jFileChooser.getSelectedFile().getPath();
		String[] org_split = original.split("\\\\");
		original = org_split[0]+"\\\\";
		for (int i=1;i<org_split.length;i++){
			if(i!=org_split.length-1)
			original = original + org_split[i] +"\\\\";
			else original = original + org_split[i];
		}
		System.out.println(original);
		int l2 = org_split.length;
		System.out.println(org_split[l2-1]);
		String[] format_array = org_split[l2-1].split("\\.");
		int l = format_array.length;
		String format = format_array[l-1];
		
		//Copy Directory selector
		jFileChooser.setFileSelectionMode(1);
		jFileChooser.setDialogTitle("Select the directory to copy");
		int i2 = jFileChooser.showOpenDialog(jFileChooser);
		if(i2==jFileChooser.CANCEL_OPTION){
			System.exit(0);
		}
		String r_maded = jFileChooser.getSelectedFile().getPath();
		
		String filename;
		synchronized (lock) {
			filename();
			lock.wait();
		}
		
		//JTextArea jTextArea = new JTextArea();
		//JPanel jPanel = new JPanel();
		//jPanel.add(jTextArea);
		//jPanel.show();
		
		String[] r_m_split = r_maded.split("\\\\");
		r_maded = r_m_split[0];
		for (int i=1;i<r_m_split.length;i++){
			if(i!=r_m_split.length-1)
			r_maded = r_maded + r_m_split[i] +"\\\\";
			else r_maded = r_maded + r_m_split[i]+"\\\\"+fname+"."+format;
		}
		System.out.println(r_maded);
		
		String orig = new String(original);
		String r_made = new String(r_maded);
		File red = new File(orig);
		File wri = new File(r_made);
		FileImageInputStream fileImageInputStream = new FileImageInputStream(red);
		FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(wri);
		int k = 0;
		while(true){
			System.out.println("iteration:"+k++);
			if(wri.length()<red.length())
			fileImageOutputStream.write(fileImageInputStream.read());
			else break;
		}
		fileImageInputStream.close();
		fileImageOutputStream.close();
	
	}
	public static void filename() throws InterruptedException{
		
		Dimension screendimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_height = (int)screendimension.getHeight();
		int screen_width = (int)screendimension.getWidth();
		JFrame jFrame = new JFrame();
		JPanel jPanel = new JPanel();
		jFrame.setSize(450, 250);
		
		
		Dimension framedimension = jFrame.getSize();
		int frame_height = (int)framedimension.getHeight();
		int frame_width = (int)framedimension.getWidth();
		
		//jPanel.setBounds(0, 0, frame_width, frame_height);
		
		JTextField jTextField = new JTextField();
		jTextField.setBounds(100, 0, 500, 50);
		
		
		JButton jButton = new JButton("Okay");
		jButton.setBounds(0, 0, 100, 50);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				synchronized (lock){
					fname = jTextField.getText();
					lock.notify();
					jFrame.dispose();
				}
			}
		});
		
		jFrame.setLocation(screen_width/2 - frame_width/2, screen_height/2 - frame_height/2);
		
		jFrame.add(jTextField);
		jFrame.add(jButton);
		jFrame.add(jPanel);
		jFrame.setResizable(false);
		jFrame.show();
	}
	public static void main(String args[]) throws IOException, InterruptedException{
		FileCopy();
	}
}


