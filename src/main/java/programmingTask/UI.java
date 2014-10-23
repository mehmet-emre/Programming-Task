package programmingTask;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.SwingConstants.RIGHT;
import static programmingTask.Constants.SC_FILE_NAME;
import static programmingTask.Constants.loadProperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class UI extends JFrame implements ActionListener{
	
	private SoundCloudAPI soundCloudAPI = new SoundCloudAPI();
	private AWSJavaAPI	awsJavaAPI = new AWSJavaAPI();
		
	private JPanel contentPane;
	private JTextField txtAccessKey;
	private JTextField txtSecretAccessKey;
	private JTextField txtUsername;
	private JPanel pnlTracks; 
	private JLabel lblNotification;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			loadProperties();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame: Auto-generated code by WindowBuilder editor
	 */
	public UI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("AWS Client - Access Key");
		lblNewLabel.setHorizontalAlignment(RIGHT);
		lblNewLabel.setBounds(10, 5, 207, 14);
		contentPane.add(lblNewLabel);
		
		txtAccessKey = new JTextField();
		txtAccessKey.setBounds(227, 2, 293, 20);
		contentPane.add(txtAccessKey);
		txtAccessKey.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("AWS Client - Secret Access Key");
		lblNewLabel_1.setHorizontalAlignment(RIGHT);
		lblNewLabel_1.setBounds(10, 36, 207, 14);
		contentPane.add(lblNewLabel_1);
		
		txtSecretAccessKey = new JTextField();
		txtSecretAccessKey.setBounds(227, 33, 293, 20);
		contentPane.add(txtSecretAccessKey);
		txtSecretAccessKey.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("SoundCloud Username");
		lblNewLabel_2.setHorizontalAlignment(RIGHT);
		lblNewLabel_2.setBounds(10, 98, 207, 14);
		contentPane.add(lblNewLabel_2);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(227, 95, 293, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		pnlTracks = new JPanel();
		pnlTracks.setBounds(10, 153, 510, 188);
		pnlTracks.setLayout(null);
		contentPane.add(pnlTracks);
		
		lblNotification = new JLabel("");
		lblNotification.setBounds(227, 352, 293, 14);
		contentPane.add(lblNotification);
		
		JButton btnGetTheTrack = new JButton("Get the Track List");
		btnGetTheTrack.setBounds(227, 119, 145, 23);
		btnGetTheTrack.addActionListener(this);
		contentPane.add(btnGetTheTrack);
		
		JButton btnNewButton = new JButton("Check the Credentials");
		btnNewButton.setBounds(227, 64, 145, 23);
		btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {		
			if(e.getActionCommand().equals("Copy to S3")){
				int index = Integer.parseInt(((JButton)e.getSource()).getToolTipText());	
				JSONArray trackList = soundCloudAPI.getTrackList();
				JSONObject jsonObj = trackList.getJSONObject(index);
				String downloadUrl = jsonObj.getString("download_url");
				String keyName = jsonObj.getString("id");
				soundCloudAPI.downloadTrack(downloadUrl);
				awsJavaAPI.checkCredentials(txtAccessKey.getText(), txtSecretAccessKey.getText());
				awsJavaAPI.uploadFile(keyName);
				lblNotification.setText("Track file is successfully copied to S3");
				File file = new File(SC_FILE_NAME);
				file.delete();
				lblNotification.setText("Track has been copied to S3");
			}
			else if (e.getActionCommand().equals("Check the Credentials")){
				awsJavaAPI.checkCredentials(txtAccessKey.getText(), txtSecretAccessKey.getText());
				lblNotification.setText("Credentials are correct");
			}
			else if (e.getActionCommand().equals("Get the Track List")){
				soundCloudAPI.getTheTrackList(txtUsername.getText());
				publishResults();	
				lblNotification.setText("Track List has been retrieved");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			lblNotification.setText(ex.getMessage());
		}
	}
	
	
	public void publishResults() throws JSONException{
		pnlTracks.removeAll();
		JSONArray trackList = soundCloudAPI.getTrackList();
		for(int i=0; i<trackList.length(); i++){
			JSONObject jsonObj = trackList.getJSONObject(i);
			JLabel label = new JLabel(jsonObj.getString("title"));
			label.setBounds(5, 25*i + 5, 400, 25);
			pnlTracks.add(label);
			JButton btnDownload = new JButton("Copy to S3");
			btnDownload.setToolTipText(String.valueOf(i));
			btnDownload.addActionListener(this);
			btnDownload.setBounds(405, 25*i + 5, 100, 25);
			btnDownload.setEnabled(jsonObj.getBoolean("downloadable")?true:false);
			pnlTracks.add(btnDownload);
		}
		pnlTracks.setSize(605, trackList.length()*25 + 30);
		pnlTracks.repaint();
	}

	

}
