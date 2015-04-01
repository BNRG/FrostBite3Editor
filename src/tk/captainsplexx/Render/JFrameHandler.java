package tk.captainsplexx.Render;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import tk.captainsplexx.Game.Main;





import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JFrameHandler {
	public JFrame frame = new JFrame();
	public JComboBox comboBox; 
	private JTextField textField;
	
	public JFrameHandler(){
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(301, 553);
		frame.setLocation(Display.getDisplayMode().getWidth()-frame.getWidth(), Display.getDisplayMode().getHeight()/4); // ??!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(34, 21, 216, 50);
		frame.getContentPane().add(comboBox);
		
		JButton button = new JButton("Toggle Visibility");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).toggleVisibility();
			}
		});
		button.setBounds(34, 104, 216, 50);
		frame.getContentPane().add(button);
		
		textField = new JTextField();
		textField.setText("1.0");
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				JTextField textfield = (JTextField) arg0.getSource();
				float value = Float.valueOf(textfield.getText());
				Main.getGame().getEntityHandler().getEntities().get(comboBox.getSelectedIndex()).setScaling(new Vector3f(value, value, value));
			}
		});
		textField.setBounds(34, 199, 216, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		frame.validate();
		frame.repaint();
	}
	
	public JComboBox getComboBox(){
		return comboBox;
	}
}
