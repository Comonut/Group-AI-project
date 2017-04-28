package VirtualPiano;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.View;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JSlider;
import javax.swing.JScrollBar;

public class VirtualPiano extends JFrame implements MouseListener {

	private JPanel contentPane;
	private JTextField txtUserUsername;
	private JTextField txtNotePlayed;
	private JTextField txtNumberOfNotes;
	private JPanel panel_4;
	private JTextField txtSettings;
	private JTextField txtSave;
	private JTextField txtStart;
	private JTextField txtFinish;
	private JTextField txtPlay;
	public static JTextField Display;
	public static JButton btnStart;
	public static JButton btnFinish;
	private JTextField txtSpeedOfReplay;
	public JTextField ReplaySpeedField; // ---
	private JTextField txtInsertANumber;
	private JTextField txtSettings_1;
	private JTextField txtNumberOfNotes_1;
	private JTextField generatedNotesField;
	private JTextField textField_1;
	protected JPanel mainPanel;
	protected JButton btnSaveChanges;

	private long tStart = 0;
	private double duration;
	private String note_Code = "";
	private String note_Octave = "3";
	private String pattern = "dd-MM-yyyy";

	protected String aud;
	protected int note_Number;
	protected Object key;
	protected String sequence = "6,0,2,0.5;1,0,3,0.5;2,0,3,0.5;1,0,3,0.5;2,0,3,0.5;2,0,3,0.5;2,0,3,0.5;5,0,3,0.5;4,0,3,0.5;3,0,3,0.5;2,0,3,0.5;3,0,3,0.5;";
	protected String copy = "";
	protected boolean start;
	protected DBController database;

	TrackPlayer player = new TrackPlayer();
	private JTextField txtPlayGenerated;
	private JTextField txtName;
	private JTextField txtDate;
	private JTable tblUsers;
	private JTextField txtSetVolume;

	public VirtualPiano() {
		setResizable(false);
		player.setSpeed(1);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1171, 468);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		mainPanel = new JPanel();
		mainPanel.setForeground(Color.WHITE);
		mainPanel.setBackground(Color.BLACK);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(459, 33, 257, 66);
		panel_3.setLayout(null);
		
		JButton btnAdministrator = new JButton("Administrator");
		
		Object[] row = new Object[3];
		
		Object[] columns = {"Player ID", "Name", "Date Of Creation"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		Font tblFont = new Font("", 1, 22);
		
		int numUser = 1;
		
		JPanel SettingsPanel = new JPanel();
		SettingsPanel.setBounds(242, 46, 866, 384);
		mainPanel.add(SettingsPanel);
		SettingsPanel.setLayout(null);
		
		
		//scrollPane.setViewportView(tblUsers);

		txtSpeedOfReplay = new JTextField();
		txtSpeedOfReplay.setEditable(false);
		txtSpeedOfReplay.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		txtSpeedOfReplay.setBackground(SystemColor.window);
		txtSpeedOfReplay.setText("       Speed of Replay: ");
		txtSpeedOfReplay.setBounds(39, 80, 208, 55);
		SettingsPanel.add(txtSpeedOfReplay);
		txtSpeedOfReplay.setColumns(10);
		
		ReplaySpeedField = new JTextField();
		ReplaySpeedField.setBounds(377, 96, 80, 26);
		SettingsPanel.add(ReplaySpeedField);
		ReplaySpeedField.setColumns(10);
		
		txtInsertANumber = new JTextField();
		txtInsertANumber.setEditable(false);
		txtInsertANumber.setBackground(SystemColor.window);
		txtInsertANumber.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		txtInsertANumber.setText("Insert a number from ");
		txtInsertANumber.setBounds(469, 96, 251, 26);
		SettingsPanel.add(txtInsertANumber);
		txtInsertANumber.setColumns(10);
		
		JButton btnBackSettings = new JButton("Back");
		btnBackSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsPanel.setVisible(false);
				SettingsPanel.setEnabled(false);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(true);
			}
		});
		btnBackSettings.setBounds(6, 6, 117, 29);
		SettingsPanel.add(btnBackSettings);
		
		txtSettings_1 = new JTextField();
		txtSettings_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtSettings_1.setBackground(SystemColor.window);
		txtSettings_1.setText("  Settings Menu");
		txtSettings_1.setBounds(394, 0, 164, 32);
		SettingsPanel.add(txtSettings_1);
		txtSettings_1.setColumns(10);
		SettingsPanel.setVisible(false);
		SettingsPanel.setEnabled(false);
		
		txtNumberOfNotes_1 = new JTextField();
		txtNumberOfNotes_1.setText("       Number of notes generated: ");
		txtNumberOfNotes_1.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		txtNumberOfNotes_1.setEditable(false);
		txtNumberOfNotes_1.setColumns(10);
		txtNumberOfNotes_1.setBackground(SystemColor.window);
		txtNumberOfNotes_1.setBounds(39, 146, 296, 55);
		SettingsPanel.add(txtNumberOfNotes_1);
		
		generatedNotesField = new JTextField();
		generatedNotesField.setColumns(10);
		generatedNotesField.setBounds(377, 163, 80, 26);
		SettingsPanel.add(generatedNotesField);
		
		textField_1 = new JTextField();
		textField_1.setText("Insert a number from ");
		textField_1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(476, 163, 251, 26);
		SettingsPanel.add(textField_1);
		
		btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setBounds(364, 279, 117, 29);
		SettingsPanel.add(btnSaveChanges);
		
		txtSetVolume = new JTextField();
		txtSetVolume.setEditable(false);
		txtSetVolume.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		txtSetVolume.setBackground(SystemColor.window);
		txtSetVolume.setText("      Set Volume:");
		txtSetVolume.setBounds(39, 223, 164, 48);
		SettingsPanel.add(txtSetVolume);
		txtSetVolume.setColumns(10);
		
		JSlider volSlider = new JSlider();
		volSlider.setMinimum(-50);
		volSlider.setMaximum(6);
		volSlider.setBounds(350, 242, 200, 26);
		SettingsPanel.add(volSlider);
		
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int volValue = volSlider.getValue();
				player.setVolume(volValue);
				if (!ReplaySpeedField.equals("")) {
					try {
						double speed = Double.parseDouble(ReplaySpeedField.getText());
						player.setSpeed(speed);
					} catch (NumberFormatException e1) {
						ReplaySpeedField.setText("Invalid");
						player.setSpeed(1);
					}
				}

			}
		});
		
		
		JPanel adminPanel = new JPanel();
		adminPanel.setBounds(175, 6, 597, 319);
		mainPanel.add(adminPanel);
		adminPanel.setLayout(null);
		adminPanel.setVisible(false);
		adminPanel.setEnabled(false);
		
		
		JButton btnAdminBack = new JButton("Back");
		btnAdminBack.setBounds(0, 0, 60, 23);
		adminPanel.add(btnAdminBack);
		//btnBack.setIcon(back);
		btnAdminBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adminPanel.setVisible(false);
				adminPanel.setEnabled(false);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(true);
			}
		});
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(218, 256, 95, 43);
		adminPanel.add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.setBounds(348, 256, 95, 43);
		adminPanel.add(btnDeleteUser);
		
		JButton btnLoad = new JButton("Load Users");
		btnLoad.setBounds(474, 256, 95, 43);
		adminPanel.add(btnLoad);
		
		txtName = new JTextField();
		txtName.setBounds(10, 89, 135, 20);
		adminPanel.add(txtName);
		txtName.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setEnabled(false);
		txtDate.setEditable(false);
		txtDate.setBounds(10, 147, 135, 20);
		adminPanel.add(txtDate);
		txtDate.setColumns(10);
		
		JLabel lblName = new JLabel("Profile:");
		lblName.setBounds(10, 74, 50, 14);
		adminPanel.add(lblName);
		
		JLabel lblDate = new JLabel("Date Of Creation:");
		lblDate.setBounds(6, 121, 139, 14);
		adminPanel.add(lblDate);
		
		tblUsers = new JTable();
		tblUsers.setModel(model);
		tblUsers.setBackground(Color.cyan);
		tblUsers.setForeground(Color.white);
		tblUsers.setFont(tblFont);
		tblUsers.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(tblUsers);
		scrollPane.setBounds(190, 15, 389, 230);
		adminPanel.add(scrollPane);
		
		//DBController users = new DBController();
		
		JLabel lblError = new JLabel("");
		lblError.setBounds(14, 270, 142, 14);
		adminPanel.add(lblError);
		
		JButton btnBrowseMusic = new JButton("Browse Music");
		btnBrowseMusic.setBounds(24, 256, 95, 43);
		adminPanel.add(btnBrowseMusic);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(txtName.getText().equals(""))){
					String dateInString = new SimpleDateFormat(pattern).format(new Date());
					String name = txtName.getText();
					row[0] = numUser;
					row[1] = name;
					row[2] = dateInString;
					
					model.addRow(row);
					//DBController.createProfile(name);
					lblError.setText("");
				}else{
					lblError.setText("Invalid name!");
				}
				
			}
		});
		
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selRow = tblUsers.getSelectedRow();
				if(selRow >= 0){
					model.removeRow(selRow);
				}else{
					lblError.setText("Deletion Error!");
				}
			}
		});
		
		JPanel helpPanel = new JPanel();
		helpPanel.setBackground(Color.DARK_GRAY);
		helpPanel.setBounds(0, 6, 508, 487);
		mainPanel.add(helpPanel);
		helpPanel.setLayout(null);
		helpPanel.setVisible(false);
		helpPanel.setEnabled(false);
		
		JButton btnKeyboardImage = new JButton("");
		btnKeyboardImage.setEnabled(true);
		btnKeyboardImage.setBounds(0, 38, 507, 150);
		ImageIcon icon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Keyboard_IMG.png");
		Image img = icon.getImage();
		
		Image newImg = 	img.getScaledInstance(btnKeyboardImage.getWidth(), btnKeyboardImage.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		icon = new ImageIcon(newImg);
		btnKeyboardImage.setIcon(icon);
		helpPanel.add(btnKeyboardImage);
		 
		JButton btnInfoOne = new JButton("");
		btnInfoOne.setBounds(0, 188, 507, 111);
		ImageIcon infoOneIcon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Info_One_IMG.png");
		Image oneImg = infoOneIcon.getImage();
		Image oneNewImg = oneImg.getScaledInstance(btnInfoOne.getWidth(), btnInfoOne.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		infoOneIcon = new ImageIcon(oneNewImg);
		btnInfoOne.setIcon(infoOneIcon);
		helpPanel.add(btnInfoOne);
		
		JButton btnInfoTwo = new JButton();
		btnInfoTwo.setBounds(0, 302, 507, 127);
		ImageIcon infoTwoIcon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Info_Two_IMG.png");
		Image twoImg = infoTwoIcon.getImage();
		Image twoNewImg = twoImg.getScaledInstance(btnInfoTwo.getWidth(), btnInfoTwo.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		infoTwoIcon = new ImageIcon(twoNewImg);
		btnInfoTwo.setIcon(infoTwoIcon);
		helpPanel.add(btnInfoTwo);
		
		JButton btnHelpBack = new JButton("Back");
		btnHelpBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				helpPanel.setVisible(false);
				helpPanel.setEnabled(false);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(true);
			}
		});
		btnHelpBack.setBounds(10, 11, 89, 23);
		ImageIcon backHelpIcon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Back_Button.png");
		Image helpBck = backHelpIcon.getImage();
		Image newHelpBack = helpBck.getScaledInstance(btnHelpBack.getWidth(), btnHelpBack.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		backHelpIcon = new ImageIcon(newHelpBack);
		btnHelpBack.setIcon(backHelpIcon);
		helpPanel.add(btnHelpBack);
		ImageIcon backAdminIcon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Back_Button.png");
		Image adminBck = backAdminIcon.getImage();
		
		btnAdministrator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mainPanel.setEnabled(false);
				adminPanel.setVisible(true);
				adminPanel.setEnabled(true);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(false);

			}
		});
		ImageIcon backSettingsIcon = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Back_Button.png");
		Image settingsBck = backSettingsIcon.getImage();
		btnAdministrator.setBounds(149, 6, 122, 15);
		mainPanel.add(btnAdministrator);

		JButton btnHelp = new JButton("Help");
		btnHelp.setBackground(Color.WHITE);
		btnHelp.setBounds(6, 6, 88, 15);
		mainPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				helpPanel.setVisible(true);
				helpPanel.setEnabled(true);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(false);
			}
		});
	

		Display = new JTextField();
		Display.setEditable(false);
		Display.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		Display.setBounds(446, 46, 258, 44);
		mainPanel.add(Display);
		Display.setColumns(10);

		txtUserUsername = new JTextField();
		txtUserUsername.setEditable(false);
		txtUserUsername.setForeground(Color.WHITE);
		txtUserUsername.setBackground(Color.DARK_GRAY);
		txtUserUsername.setText("User: Username");
		txtUserUsername.setBounds(1025, 6, 130, 15);
		mainPanel.add(txtUserUsername);
		txtUserUsername.setColumns(10);

		txtNotePlayed = new JTextField();
		txtNotePlayed.setBounds(63, 5, 130, 26);
		txtNotePlayed.setText("Note Played");
		panel_3.add(txtNotePlayed);
		txtNotePlayed.setColumns(10);

		txtNumberOfNotes = new JTextField();
		txtNumberOfNotes.setText("Number of notes played");
		txtNumberOfNotes.setBounds(51, 34, 174, 26);
		panel_3.add(txtNumberOfNotes);
		txtNumberOfNotes.setColumns(10);

		panel_4 = new JPanel();
		panel_4.setEnabled(false);
		panel_4.setBackground(SystemColor.inactiveCaption);
		panel_4.setBounds(486, 6, 182, 15);
		mainPanel.add(panel_4);

		JButton Do_Sharp_ = new JButton();
		Do_Sharp_.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Do_Sharp_.setName("Do_Sharp_");
		Do_Sharp_.setBounds(808, 111, 45, 193);
		mainPanel.add(Do_Sharp_);
		Do_Sharp_.addMouseListener(this);

		JButton Sol_Sharp_ = new JButton();
		;
		Sol_Sharp_.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Sol_Sharp_.setName("Sol_Sharp_");
		Sol_Sharp_.setBounds(1034, 111, 45, 193);
		mainPanel.add(Sol_Sharp_);
		Sol_Sharp_.addMouseListener(this);

		JButton La_Sharp_ = new JButton();
		La_Sharp_.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		La_Sharp_.setName("La_Sharp_");
		La_Sharp_.setBounds(1091, 111, 45, 193);
		mainPanel.add(La_Sharp_);
		La_Sharp_.addMouseListener(this);

		JButton Fa_Sharp_ = new JButton();
		Fa_Sharp_.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Fa_Sharp_.setName("Fa_Sharp_");
		Fa_Sharp_.setBounds(977, 111, 45, 193);
		mainPanel.add(Fa_Sharp_);
		Fa_Sharp_.addMouseListener(this);

		JButton Re_Sharp_ = new JButton();
		Re_Sharp_.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Re_Sharp_.setName("Re_Sharp_");
		Re_Sharp_.setBounds(865, 111, 45, 193);
		mainPanel.add(Re_Sharp_);
		Re_Sharp_.addMouseListener(this);

		JButton _Re_Sharp = new JButton();
		_Re_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		_Re_Sharp.setBounds(96, 111, 45, 193);
		mainPanel.add(_Re_Sharp);
		_Re_Sharp.setName("_Re_Sharp");
		_Re_Sharp.addMouseListener(this);

		JButton _Do_Sharp = new JButton();
		_Do_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		_Do_Sharp.setBounds(39, 111, 45, 193);
		mainPanel.add(_Do_Sharp);
		_Do_Sharp.setName("_Do_Sharp");
		_Do_Sharp.addMouseListener(this);

		JButton _Sol_Sharp = new JButton();
		_Sol_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		_Sol_Sharp.setBounds(262, 111, 45, 193);
		mainPanel.add(_Sol_Sharp);
		_Sol_Sharp.setName("_Sol_Sharp");
		_Sol_Sharp.addMouseListener(this);

		JButton _Fa_Sharp = new JButton();
		_Fa_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		_Fa_Sharp.setBounds(205, 111, 45, 193);
		mainPanel.add(_Fa_Sharp);
		_Fa_Sharp.setName("_Fa_Sharp");
		_Fa_Sharp.addMouseListener(this);

		JButton _La_Sharp = new JButton();
		_La_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		_La_Sharp.setBounds(319, 111, 45, 193);
		mainPanel.add(_La_Sharp);
		_La_Sharp.setName("_La_Sharp");
		_La_Sharp.addMouseListener(this);

		JButton Do_Sharp = new JButton();
		Do_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Do_Sharp.setBounds(423, 111, 45, 193);
		mainPanel.add(Do_Sharp);
		Do_Sharp.setName("Do_Sharp");
		Do_Sharp.addMouseListener(this);

		JButton La_Sharp = new JButton();
		La_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		La_Sharp.setBounds(706, 111, 45, 193);
		mainPanel.add(La_Sharp);
		La_Sharp.setName("La_Sharp");
		La_Sharp.addMouseListener(this);

		JButton Sol_Sharp = new JButton();
		Sol_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Sol_Sharp.setBounds(649, 111, 45, 193);
		mainPanel.add(Sol_Sharp);
		Sol_Sharp.setName("Sol_Sharp");
		Sol_Sharp.addMouseListener(this);

		JButton Fa_Sharp = new JButton();
		Fa_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Fa_Sharp.setBounds(592, 111, 45, 193);
		mainPanel.add(Fa_Sharp);
		Fa_Sharp.setName("Fa_Sharp");
		Fa_Sharp.addMouseListener(this);

		JButton Re_Sharp = new JButton();
		Re_Sharp.setIcon(new ImageIcon("/Users/gabriela/Desktop/Ai Project/Virtual_Piano/Black_Gradient.jpg"));
		Re_Sharp.setBounds(480, 111, 45, 193);
		mainPanel.add(Re_Sharp);
		Re_Sharp.setName("Re_Sharp");
		Re_Sharp.addMouseListener(this);

		JButton Do = new JButton();
		Do.setForeground(Color.WHITE);
		Do.setBounds(391, 111, 57, 319);
		mainPanel.add(Do);
		Do.setName("Do");
		Do.addMouseListener(this);

		JButton Re = new JButton();
		Re.setForeground(Color.WHITE);
		Re.setBounds(446, 111, 57, 319);
		mainPanel.add(Re);
		Re.setName("Re");
		Re.addMouseListener(this);

		JButton Mi = new JButton();
		Mi.setForeground(Color.WHITE);
		Mi.setBounds(501, 111, 57, 319);
		mainPanel.add(Mi);
		Mi.setName("Mi");
		Mi.addMouseListener(this);

		JButton Fa = new JButton();
		Fa.setForeground(Color.WHITE);
		Fa.setBounds(556, 111, 57, 319);
		mainPanel.add(Fa);
		Fa.setName("Fa");
		Fa.addMouseListener(this);

		JButton Sol = new JButton();
		Sol.setForeground(Color.WHITE);
		Sol.setBounds(611, 111, 57, 319);
		mainPanel.add(Sol);
		Sol.setName("Sol");
		Sol.addMouseListener(this);

		JButton La = new JButton();
		La.setForeground(Color.WHITE);
		La.setBounds(666, 111, 57, 319);
		mainPanel.add(La);
		La.setName("La");
		La.addMouseListener(this);

		JButton Si = new JButton();
		Si.setForeground(Color.WHITE);
		Si.setBounds(721, 111, 57, 319);
		mainPanel.add(Si);
		Si.setName("Si");
		Si.addMouseListener(this);

		JButton _Do = new JButton();
		_Do.setForeground(Color.WHITE);
		_Do.setBounds(6, 111, 57, 319);
		mainPanel.add(_Do);
		_Do.setName("_Do");
		_Do.addMouseListener(this);

		JButton _Re = new JButton();
		_Re.setForeground(Color.WHITE);
		_Re.setBounds(61, 111, 57, 319);
		mainPanel.add(_Re);
		_Re.setName("_Re");
		_Re.addMouseListener(this);

		JButton _Mi = new JButton();
		_Mi.setForeground(Color.WHITE);
		_Mi.setBounds(116, 111, 57, 319);
		mainPanel.add(_Mi);
		_Mi.setName("_Mi");
		_Mi.addMouseListener(this);

		JButton _Fa = new JButton();
		_Fa.setForeground(Color.WHITE);
		_Fa.setBounds(171, 111, 57, 319);
		mainPanel.add(_Fa);
		_Fa.setName("_Fa");
		_Fa.addMouseListener(this);

		JButton _Sol = new JButton();
		_Sol.setForeground(Color.WHITE);
		_Sol.setBounds(226, 111, 57, 319);
		mainPanel.add(_Sol);
		_Sol.setName("_Sol");
		_Sol.addMouseListener(this);

		JButton _La = new JButton();
		_La.setForeground(Color.WHITE);
		_La.setBounds(281, 111, 57, 319);
		mainPanel.add(_La);
		_La.setName("_La");
		_La.addMouseListener(this);

		JButton _Si = new JButton();
		_Si.setForeground(Color.WHITE);
		_Si.setBounds(336, 111, 57, 319);
		mainPanel.add(_Si);
		_Si.setName("_Si");
		_Si.addMouseListener(this);

		JButton Do_ = new JButton();
		Do_.setName("Do_");
		Do_.setForeground(Color.WHITE);
		Do_.setBounds(776, 111, 57, 319);
		mainPanel.add(Do_);
		Do_.addMouseListener(this);

		JButton Re_ = new JButton();
		Re_.setName("Re_");
		Re_.setForeground(Color.WHITE);
		Re_.setBounds(831, 111, 57, 319);
		mainPanel.add(Re_);
		Re_.addMouseListener(this);

		JButton Mi_ = new JButton();
		Mi_.setName("Mi_");
		Mi_.setForeground(Color.WHITE);
		Mi_.setBounds(886, 111, 57, 319);
		mainPanel.add(Mi_);
		Mi_.addMouseListener(this);

		JButton Fa_ = new JButton();
		Fa_.setName("Fa_");
		Fa_.setForeground(Color.WHITE);
		Fa_.setBounds(941, 111, 57, 319);
		mainPanel.add(Fa_);
		Fa_.addMouseListener(this);

		JButton Sol_ = new JButton();
		Sol_.setName("Sol_");
		Sol_.setForeground(Color.WHITE);
		Sol_.setBounds(996, 111, 57, 319);
		mainPanel.add(Sol_);
		Sol_.addMouseListener(this);

		JButton La_ = new JButton();
		La_.setName("La_");
		La_.setForeground(Color.WHITE);
		La_.setBounds(1051, 111, 57, 319);
		mainPanel.add(La_);
		La_.addMouseListener(this);

		JButton Si_ = new JButton();
		Si_.setName("Si_");
		Si_.setForeground(Color.WHITE);
		Si_.setBounds(1106, 111, 57, 319);
		mainPanel.add(Si_);
		Si_.addMouseListener(this);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(6, 6, 1149, 15);
		mainPanel.add(panel_1);

		btnFinish = new JButton("");
		btnFinish.setBounds(886, 64, 96, 26);
		ImageIcon finish = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Finish_IMG.png");
		Image finishImg = finish.getImage();
		Image newFinishImg = finishImg.getScaledInstance(btnFinish.getWidth(), btnFinish.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		finish = new ImageIcon(newFinishImg);
		btnFinish.setIcon(finish);
		btnFinish.setForeground(UIManager.getColor("Button.disabledText"));
		btnFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		mainPanel.add(btnFinish);

		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NoteMarkovChain chain = new NoteMarkovChain(sequence);
				copy = chain.makeMusic();

			}

		});

		JButton btnPlay = new JButton("");
		btnPlay.setBounds(1025, 64, 96, 26);
		ImageIcon play = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Play_IMG.png");
		Image playImg = play.getImage();
		Image newPlayImg = 	playImg.getScaledInstance(btnPlay.getWidth(), btnPlay.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		play = new ImageIcon(newPlayImg);
		btnPlay.setIcon(play);
		btnPlay.setForeground(UIManager.getColor("Button.disabledText"));
		
		btnPlay.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		mainPanel.add(btnPlay);

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sequence = sequence + copy;
					player.setSequence(sequence);
					player.readTrack();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		

		// Settings button to show panel
		JButton btnSettings = new JButton("");
		btnSettings.setForeground(UIManager.getColor("Button.disabledText"));
		btnSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnSettings.setBounds(39, 64, 96, 26);
		ImageIcon settings = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Settings_IMG.png");
		Image settingImg = settings.getImage();
		Image newSettingImg = settingImg.getScaledInstance(btnSettings.getWidth(), btnSettings.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		settings = new ImageIcon(newSettingImg);
		btnSettings.setIcon(settings);
		mainPanel.add(btnSettings);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsPanel.setVisible(true);
				SettingsPanel.setEnabled(true);
				for (Component c : mainPanel.getComponents())
					c.setEnabled(false);
			}
		});

		JButton btnSaveTrack = new JButton("");
		btnSaveTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MidiExporter exporter = new MidiExporter(sequence);
			}
		});
		btnSaveTrack.setForeground(Color.GRAY);
		btnSaveTrack.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnSaveTrack.setBounds(175, 64, 96, 26);
		/*ImageIcon save = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Save_IMG.png");
		Image saveImg = settings.getImage();
		Image newsaveImg = saveImg.getScaledInstance(btnSaveTrack.getWidth(), btnSaveTrack.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		save = new ImageIcon(newsaveImg);
		btnSettings.setIcon(save);*/
		ImageIcon save = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Save_IMG.png");
		Image saveImg = save.getImage();
		Image newSaveImg = saveImg.getScaledInstance(btnSaveTrack.getWidth(), btnSaveTrack.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		save = new ImageIcon(newSaveImg);
		btnSaveTrack.setIcon(save);
		mainPanel.add(btnSaveTrack);

		btnStart = new JButton("");
		btnStart.setBounds(755, 64, 96, 26);
		ImageIcon starting = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Start_IMG.png");
		Image startImg = starting.getImage();
		Image newStartImg = startImg.getScaledInstance(btnSaveTrack.getWidth(), btnSaveTrack.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		starting = new ImageIcon(newStartImg);
		btnStart.setIcon(starting);
		btnStart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnFinish.setForeground(UIManager.getColor("Button.disabledText"));
		mainPanel.add(btnStart);

		txtSettings = new JTextField();
		txtSettings.setEditable(false);
		txtSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtSettings.setForeground(Color.WHITE);
		txtSettings.setBackground(Color.BLACK);
		txtSettings.setText("  Settings");
		txtSettings.setBounds(49, 32, 78, 26);
		mainPanel.add(txtSettings);
		txtSettings.setColumns(10);

		txtSave = new JTextField();
		txtSave.setEditable(false);
		txtSave.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtSave.setText("    Save");
		txtSave.setForeground(Color.WHITE);
		txtSave.setColumns(10);
		txtSave.setBackground(Color.BLACK);
		txtSave.setBounds(182, 32, 78, 26);
		mainPanel.add(txtSave);

		txtStart = new JTextField();
		txtStart.setEditable(false);
		txtStart.setText("    Start");
		txtStart.setForeground(Color.WHITE);
		txtStart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtStart.setColumns(10);
		txtStart.setBackground(Color.BLACK);
		txtStart.setBounds(755, 32, 78, 26);
		mainPanel.add(txtStart);

		txtFinish = new JTextField();
		txtFinish.setEditable(false);
		txtFinish.setText("    Finish");
		txtFinish.setForeground(Color.WHITE);
		txtFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtFinish.setColumns(10);
		txtFinish.setBackground(Color.BLACK);
		txtFinish.setBounds(896, 32, 68, 26);
		mainPanel.add(txtFinish);

		txtPlay = new JTextField();
		txtPlay.setEditable(false);
		txtPlay.setText("    Play");
		txtPlay.setForeground(Color.WHITE);
		txtPlay.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtPlay.setColumns(10);
		txtPlay.setBackground(Color.BLACK);
		txtPlay.setBounds(1035, 32, 78, 26);
		mainPanel.add(txtPlay);
		
		JButton btnPlayGenerated = new JButton("");
		btnPlayGenerated.setForeground(Color.GRAY);
		btnPlayGenerated.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnPlayGenerated.setBounds(319, 64, 96, 26);
		ImageIcon generated = new ImageIcon("/Users/ronan/workspace/Virtual_Piano/bin/VirtualPiano/Generated_IMG.png");
		Image generatedImg = generated.getImage();
		Image newGeneratedImg = generatedImg.getScaledInstance(btnPlayGenerated.getWidth(), btnPlayGenerated.getHeight(),  java.awt.Image.SCALE_SMOOTH );
		generated = new ImageIcon(newGeneratedImg);
		btnPlayGenerated.setIcon(generated);
		mainPanel.add(btnPlayGenerated);
		
		
		VirtualPiano.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequence = "";
				start = true;
			}
		});
		
		
		btnPlayGenerated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					player.setSequence(copy);
					player.readTrack();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		
		txtPlayGenerated = new JTextField();
		txtPlayGenerated.setText("Play generated");
		txtPlayGenerated.setForeground(Color.WHITE);
		txtPlayGenerated.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtPlayGenerated.setEditable(false);
		txtPlayGenerated.setColumns(10);
		txtPlayGenerated.setBackground(Color.BLACK);
		txtPlayGenerated.setBounds(308, 32, 115, 26);
		mainPanel.add(txtPlayGenerated);


		VirtualPiano.btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * try { //database.createSample("FirstProfile",1, sequence, 1);
				 * <--- to database } catch (SQLException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace(); }
				 */
				start = false;
			}
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		tStart = System.currentTimeMillis();

		final String[] NOTE_NAMES = { "Do", "Do_Sharp", "Re", "Re_Sharp", "Mi", "Fa", "Fa_Sharp", "Sol", "Sol_Sharp",
				"La", "La_Sharp", "Si" };
		final String[] NOTE_CODES = { "1,0", "1,1", "2,0", "2,1", "3,0", "4,0", "4,1", "5,0", "5,1", "6,0", "6,1",
				"7,0" };

		key = ((Component) e.getSource()).getName();
		aud = key + ".wav";

		for (int i = 0; i < 12; i++) {
			if (("" + key).contains(NOTE_NAMES[i]))
				note_Code = NOTE_CODES[i];
		}

		if (("" + key).endsWith("_"))
			note_Octave = "4";
		else if (("" + key).startsWith("_"))
			note_Octave = "2";
		else note_Octave = "3";

		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(aud).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		duration = tDelta / 1000.0;
		duration = roundDuration(duration);

		if (start == true) {
			sequence = sequence + note_Code + "," + note_Octave + "," + duration + ";";

		}

		VirtualPiano.Display.setText("" + key + " Oct: " + note_Octave + "Time:" + duration);

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public double roundDuration(double d) {
		if ((0 <= d) && (d <= 0.3))
			d = 0.5;
		else if ((0.3 < d) && (d < 1.1))
			d = 1.0;
		else if ((1.1 <= d) && (d <= 1.5))
			d = 1.5;
		else if ((1.5 < d) && (d < 2.1))
			d = 2.0;
		else
			d = 4;
		return d;
	}
}
