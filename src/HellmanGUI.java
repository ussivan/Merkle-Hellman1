import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HellmanGUI extends JFrame implements KeyListener, ActionListener{
	static String data = "";
	static String dir = new File("").getAbsolutePath();
	static String dirpr= new File("").getAbsolutePath()+ "\\privateKey.txt";
	static String dirpub = new File("").getAbsolutePath()+ "\\publicKey.txt";
	static int n = 512;
	static String mode = "";
	static JPanel controlpanel = new JPanel(null);
	static JPanel settingspanel = new JPanel(null);
	JLabel up = new JLabel("Number of bits your message will contain");
	JLabel dirlbl = new JLabel("Directory where your generated files(keys) will be stored");
	JLabel dirprlbl = new JLabel("Directory where your private key is stored");
	JLabel dirpublbl = new JLabel("Directory where your public key is stored");
	JButton submit = new JButton("OK");
	JButton decryptbt = new JButton("Decrypt");
	JButton encryptbt = new JButton("Encrypt");
	JButton genbt = new JButton("Generate");
	JTextField txt0 = new JTextField(n);
	JTextField txt = new JTextField("Write here path to file to decrypt and press decrypt");
	JTextField pathtxt = new JTextField(dir);
	JTextField pathprtxt = new JTextField(dirpr);
	JTextField pathpubtxt = new JTextField(dirpub);
	JTextField txt2 = new JTextField("Write here word you need to encrypt and press enter/encrypt");
	JTextField txt3 = new JTextField("Write here a seed and press generate to generate new keys");
	JMenuBar menuBar = new JMenuBar();
	JMenuItem menuFile = new JMenuItem("Settings");
	CryptLord cp = new CryptLord();


	public HellmanGUI() {
		super("Merkle-Hellman cryptosystem by shirogiro");
		setBounds(0,0,850,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlpanel.setLayout(null);
		controlpanel.setFocusable(true);
		controlpanel.setBounds(0, 0, 850, 500);
		settingspanel.setLayout(null);
		settingspanel.setBounds(0, 0, 850, 500);
		settingspanel.setVisible(false);
		txt0.setText(n + "");
		up.setBounds(30,30,600,30);
		txt0.setBounds(30,60,300,30);
		add(controlpanel);
		add(settingspanel);
		menuFile.addActionListener(this);
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
		dirprlbl.setBounds(30, 50, 600, 30);
		dirpublbl.setBounds(30, 150, 600, 30);
		dirlbl.setBounds(30, 250, 600, 30);
		pathprtxt.setBounds(30, 80, 600, 30);
		pathpubtxt.setBounds(30, 180, 600, 30);
		pathtxt.setBounds(30, 280, 600, 30);
		pathtxt.setText(dir);
		pathprtxt.setText(dirpr);
		pathpubtxt.setText(dirpub);
		settingspanel.add(dirlbl);
		settingspanel.add(dirprlbl);
		settingspanel.add(dirpublbl);
		settingspanel.add(pathprtxt);
		settingspanel.add(pathpubtxt);
		settingspanel.add(pathtxt);
		submit.addActionListener(this);
		decryptbt.addActionListener(this);
		encryptbt.addActionListener(this);
		genbt.addActionListener(this);
		decryptbt.setBounds(660, 100, 90, 30);
		encryptbt.setBounds(660, 150, 90, 30);
		genbt.setBounds(660, 200, 90, 30);
		submit.setBounds(400, 400, 90, 30);
		settingspanel.add(submit);
		txt.setBounds(30, 100, 600, 30);
		txt2.setBounds(30, 150, 600, 30);
		txt3.setBounds(30, 200, 600, 30);
		txt2.addKeyListener(this);
		controlpanel.addKeyListener(this);
		controlpanel.add(txt);
		controlpanel.add(up);
		controlpanel.add(txt0);
		controlpanel.add(decryptbt);
		controlpanel.add(encryptbt);
		controlpanel.add(genbt);
		controlpanel.add(txt2);
		controlpanel.add(txt3);
	}

	
	public static void main(String[] args) throws IOException {
		dataGetter();
		HellmanGUI app = new HellmanGUI();
		app.setVisible(true);
	}

	
	public static String dataGetter() throws IOException{
		String curData = "";
		String fileName = System.getProperty("user.home") + "\\AppData\\Local\\Merkle-Hellman\\data\\data.in";
		try {
			curData = read(fileName);
			dir = curData.split(":=")[1];
			dirpr = curData.split(":=")[2];
			dirpub = curData.split(":=")[3];
			n = Integer.parseInt(curData.split(":=")[4].trim());
		} catch (Exception e) {
			File f = new File(System.getProperty("user.home") + "\\AppData\\Local\\Merkle-Hellman\\data");
			System.out.println("here");
			System.out.println(f.mkdirs());
		}
		if(dirpr == "") {
			dirpr = "\\privateKey.txt";
		}
		if(dirpub == "") {
			dirpub = "\\publicKey.txt";
		}
		return curData;
	}

	
	public static String getDir(){
		Scanner in = new Scanner(System.in);
		String dir = "";
		settingspanel.setVisible(true);
		controlpanel.setVisible(false);
		in.close();
		return dir;
	}

	
	public static String read(String fileName) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsoluteFile()), "utf-8"));
			try {
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				in.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		return sb.toString();
	}

	
	public static void write(String fileName, String text) {
		File file = new File(fileName);
		try {
			if(!file.exists()){
				file.getParentFile().mkdirs();
				System.out.println(file.getAbsolutePath());
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());
			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menuFile) {
			controlpanel.setVisible(false);
			settingspanel.setVisible(true);
		}
		if(e.getSource() == submit) {
			String curData = "";
			String fileName = System.getProperty("user.home") + "\\AppData\\Local\\Merkle-Hellman\\data\\data.in";
			controlpanel.setVisible(true);
			settingspanel.setVisible(false);
			dir = pathtxt.getText();
			dirpr = pathprtxt.getText();
			dirpub = pathpubtxt.getText();
			curData = "dir:=" + dir;
			curData += ":=" + dirpr;
			curData += ":=" + dirpub;
			curData += ":=" + n;
			write(fileName, curData);
		}
		if(e.getSource() == genbt) {
			try {
				n = Integer.parseInt(txt0.getText());
				String curData = "";
				curData = "dir:=" + dir;
				curData += ":=" + dirpr;
				curData += ":=" + dirpub;
				curData += ":=" + n;
				String fileName = System.getProperty("user.home") + "\\AppData\\Local\\Merkle-Hellman\\data\\data.in";
				write(fileName, curData);
				cp.generateKeys(n, txt3.getText(), dir);
				JOptionPane.showMessageDialog(null, "Success!");
			} catch (FileNotFoundException | UnsupportedEncodingException | NoSuchAlgorithmException e1) {
				JOptionPane.showMessageDialog(null, "Something went wrong(Incorrect path or number of bits)");
				e1.printStackTrace();
			}
		}
		if(e.getSource() == encryptbt) {
			try {
				write(dir + "\\encrypted_message.txt", cp.encrypt(txt2.getText().trim(), cp.fileToKey(new File(dirpub.trim()))));
				JOptionPane.showMessageDialog(null, "Success!");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Incorrect path or message is too long");
				e1.printStackTrace();
			}
		}
		if(e.getSource() == decryptbt) {
			try {
				write(dir + "\\decrypted_message.txt", cp.decrypt(read(txt.getText()).trim(), cp.fileToKey(new File(dirpr.trim()))));
				JOptionPane.showMessageDialog(null, "Success!");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Incorrect path or message is too long");
				e1.printStackTrace();
			}
		}

	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			encryptbt.doClick();
		}
	}

	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}