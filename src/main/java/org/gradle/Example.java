/**
 * 
 */
package org.gradle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.gradle.core.flexi.Builder;
import org.gradle.core.flexi.Factory;

/**
 * @author stefano
 *
 */
public class Example {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Example window = new Example();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Example() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

		StyleContext context = new StyleContext();
		// build a style
		Style style = context.addStyle("term", null);
		// set some style properties
		StyleConstants.setForeground(style, Color.CYAN);
		StyleConstants.setUnderline(style, true);

		DefaultStyledDocument document = new DefaultStyledDocument();
		final JTextPane textPane = new JTextPane(document);
		textPane.setText("Term: pilot.\nTerm: planes.\nFact type: pilot can fly plane.\nFact type: pilot is experienced.\nRule: it is obligatory that each pilot can fly at least 1 plane. \nRule: it is obligatory that each pilot which is experienced can fly at least 3 planes.");
		frame.getContentPane().add(textPane, BorderLayout.CENTER);

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText(textPane.getText());
				Builder builder = Factory.getBuilder(Locale.ENGLISH);
				builder.load(textPane.getText());
				if (builder.hasErrors()) {
					for (String error : builder.getErrors())
						System.err.println(error);
					// System.exit(-1);
					// throw new RuntimeException("Unable to load text.");
				}
				
				
				// add some data to the document
				// document.insertString(0, "", style);
				
			}
		});
		toolBar.add(btnNewButton);
	}

}
