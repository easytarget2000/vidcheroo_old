import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

public class ControlWindow extends JFrame {

	/**
	 * 
	 */
	private VidcherooEngine engine;
	private static final long serialVersionUID = -5226040153136542409L;
	private JPanel contentPane;
	private JTextField tfTempo;
	private JButton btnLoadFiles = new JButton("Find Media Files");
	private JLabel lblInfo;


	/**
	 * Create the frame.
	 */
	public ControlWindow() {
		setTitle("Vidcheroo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBounds(new Rectangle(20, 20, 250, 450));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelRun = new JPanel();
		panelRun.setBackground(new Color(51, 51, 51));
		panelRun.setBounds(0, 0, 234, 80);
		contentPane.add(panelRun);
		panelRun.setLayout(null);
		
		JPanel panelFiles = new JPanel();
		panelFiles.setBackground(new Color(51, 51, 51));
		panelFiles.setBounds(0, 309, 234, 80);
		contentPane.add(panelFiles);
		panelFiles.setLayout(null);
		
		JPanel panelStatus = new JPanel();
		panelStatus.setBackground(Color.BLACK);
		panelStatus.setBounds(0, 388, 234, 23);
		contentPane.add(panelStatus);
		panelStatus.setLayout(null);
		
		lblInfo = new JLabel("");
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setBounds(10, 5, 214, 14);
		panelStatus.add(lblInfo);
		
		// PLAY Button:
		JButton btnRun = new JButton("Play");
		btnRun.setBackground(Color.GRAY);
		btnRun.setForeground(Color.WHITE);
		btnRun.setFont(new Font("Arial", Font.BOLD, 11));
		btnRun.setBounds(10, 11, 214, 23);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.play();
			}
		});
		panelRun.add(btnRun);
		
		// PAUSE Button:
		JButton btnStop = new JButton("Pause");
		btnStop.setBackground(Color.GRAY);
		btnStop.setForeground(Color.WHITE);
		btnStop.setFont(new Font("Arial", Font.BOLD, 11));
		btnStop.setBounds(10, 45, 100, 23);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.pause();
			}
		});
		panelRun.add(btnStop);
		
		// FULLSCREEN Button
		JButton btnFullscreen = new JButton("Fullscreen");
		btnFullscreen.setForeground(Color.WHITE);
		btnFullscreen.setFont(new Font("Arial", Font.BOLD, 11));
		btnFullscreen.setBackground(Color.GRAY);
		btnFullscreen.setBounds(124, 45, 100, 23);
		btnFullscreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.toggleFullscreen();
			}
		});
		panelRun.add(btnFullscreen);
		
		// FIND MEDIA FILES Button
		btnLoadFiles.setBackground(Color.GRAY);
		btnLoadFiles.setForeground(Color.WHITE);
		btnLoadFiles.setFont(new Font("Arial", Font.BOLD, 11));
		btnLoadFiles.addActionListener(openDirSelector);
		btnLoadFiles.setBounds(10, 11, 214, 23);
		panelFiles.add(btnLoadFiles);
		
		// FIND VLC Button
		JButton btnFindVlc = new JButton("Find VLC");
		btnFindVlc.setForeground(Color.WHITE);
		btnFindVlc.setFont(new Font("Arial", Font.BOLD, 11));
		btnFindVlc.setBackground(Color.GRAY);
		btnFindVlc.setBounds(10, 45, 214, 23);
		panelFiles.add(btnFindVlc);
		
		// TEMPO Textfield
		tfTempo = new JTextField();
		tfTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double dTempo = 0.0;
				// Attempt to change the tempo.
				// If the input is a valid double set the new tempo.
				try {
					dTempo = Double.parseDouble(tfTempo.getText());
				}
				// Otherwise don't try to change the tempo.
				catch(Exception ex) {}
				
				// Only replace the tempo if a valid BPM value was given.
				if(dTempo >= 60 && dTempo <= 180) {
					engine.setTempo(dTempo);
				}
				else {
					tfTempo.setText(engine.getTempo() + "");
					engine.setStatusMessage("60.0 < Tempo < 180.0!");
				}
					
			}
		});
		tfTempo.setBackground(new Color(178, 34, 34));
		tfTempo.setFont(new Font("Arial", Font.BOLD, 11));
		tfTempo.setForeground(Color.WHITE);
		tfTempo.setBounds(66, 91, 158, 20);
		contentPane.add(tfTempo);
		tfTempo.setColumns(10);
		
		JLabel lblTempo = new JLabel("Tempo");
		lblTempo.setForeground(Color.WHITE);
		lblTempo.setBounds(10, 94, 46, 14);
		contentPane.add(lblTempo);
		
		JButton btnBeatSixteenth = new JButton("1/16");
		btnBeatSixteenth.setBackground(Color.GRAY);
		btnBeatSixteenth.setForeground(Color.WHITE);
		btnBeatSixteenth.setFont(new Font("Arial", Font.BOLD, 11));
		btnBeatSixteenth.setBounds(10, 156, 100, 35);
		btnBeatSixteenth.addActionListener(beatFracChanged);
		contentPane.add(btnBeatSixteenth);
		
		JButton btnBeatEigth = new JButton("1/8");
		btnBeatEigth.setBackground(Color.GRAY);
		btnBeatEigth.setForeground(Color.WHITE);
		btnBeatEigth.setFont(new Font("Arial", Font.BOLD, 11));
		btnBeatEigth.setBounds(124, 156, 100, 35);
		btnBeatEigth.addActionListener(beatFracChanged);
		contentPane.add(btnBeatEigth);
		
		JButton btnBeatFourth = new JButton("1/4");
		btnBeatFourth.setBackground(Color.GRAY);
		btnBeatFourth.setForeground(Color.WHITE);
		btnBeatFourth.setFont(new Font("Arial", Font.BOLD, 11));
		btnBeatFourth.setBounds(10, 202, 100, 35);
		btnBeatFourth.addActionListener(beatFracChanged);
		contentPane.add(btnBeatFourth);
		
		JButton btnBeatHalf = new JButton("1/2");
		btnBeatHalf.setBackground(Color.GRAY);
		btnBeatHalf.setForeground(Color.WHITE);
		btnBeatHalf.setFont(new Font("Arial", Font.BOLD, 11));
		btnBeatHalf.setBounds(124, 202, 100, 35);
		btnBeatHalf.addActionListener(beatFracChanged);
		contentPane.add(btnBeatHalf);
	}
	
	ActionListener openDirSelector = new ActionListener() {
	
		public void actionPerformed(ActionEvent e) {
			// Initialise a JFileChooser acting as "directories only".
			JFileChooser dirChooser = new JFileChooser();
			String strDir;
			
			dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			dirChooser.showSaveDialog(null);
			strDir = dirChooser.getSelectedFile().getAbsolutePath();

			switch (e.getActionCommand()) {
			case ("Find Media Files"):
				engine.loadFiles(strDir);
				break;
			default:
				engine.setVlcPath(strDir);
				break;
			}
		}
	};
	
	ActionListener beatFracChanged = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("Changing speed.");

			switch (e.getActionCommand()) {
			case "1/16":
				engine.setBeatFraction(4.0);
				break;
			case "1/8":
				engine.setBeatFraction(2.0);
				break;
			case "1/2":
				engine.setBeatFraction(0.5);
				break;
			default:
				engine.setBeatFraction(1.0);
				break;
			}
			
		}
	};
	
	
	public void setEngine(VidcherooEngine e) {
		engine = e;
		tfTempo.setText(engine.getTempo() + "");
	}

	public void setStatusMessage(String message) {
		lblInfo.setText(message);
	}


}
