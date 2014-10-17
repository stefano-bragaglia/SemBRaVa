/**
 * 
 */
package org.gradle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.drools.lang.dsl.DSLMapParser.pattern_return;
import org.gradle.core.flexi.Chunk;
import org.gradle.core.flexi.Document;
import org.gradle.core.flexi.Factory;
import org.gradle.core.flexi.Sentence;
import org.gradle.core.flexi.Token;

import com.jtechdev.macwidgets.LabeledComponentGroup;
import com.jtechdev.macwidgets.MacUtils;
import com.jtechdev.macwidgets.UnifiedToolBar;

/**
 * @author stefano
 *
 */
public class Test {

	/**
	 * Launch the application.
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("SBVR Editor");
		frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JRootPane root = frame.getRootPane();
		// root.putClientProperty( "Window.documentFile", new
		// File("pg1661.txt"));

		MacUtils.makeWindowLeopardStyle(frame.getRootPane());

		StyleContext context = new StyleContext();
		// build a style

		final Style normalStyle = context.addStyle("normal", null);
		StyleConstants.setBackground(normalStyle, Color.WHITE);
		StyleConstants.setForeground(normalStyle, Color.BLACK);
		StyleConstants.setBold(normalStyle, false);
		StyleConstants.setItalic(normalStyle, false);
		StyleConstants.setUnderline(normalStyle, false);

		final Style termLbl = context.addStyle("term label", null);
		StyleConstants.setForeground(termLbl, new Color(128, 0, 0));
		StyleConstants.setBold(termLbl, true);
		StyleConstants.setItalic(termLbl, false);
		StyleConstants.setUnderline(termLbl, false);

		final Style verbLbl = context.addStyle("verb label", null);
		StyleConstants.setForeground(verbLbl, new Color(128, 128, 0));
		StyleConstants.setBold(verbLbl, true);
		StyleConstants.setItalic(verbLbl, false);
		StyleConstants.setUnderline(verbLbl, false);

		final Style ruleLbl = context.addStyle("rule label", null);
		StyleConstants.setForeground(ruleLbl, new Color(0, 128, 128));
		StyleConstants.setBold(ruleLbl, true);
		StyleConstants.setItalic(ruleLbl, false);
		StyleConstants.setUnderline(ruleLbl, false);

		final Style termStyle = context.addStyle("term", null);
		StyleConstants.setBackground(termStyle, new Color(232, 255, 255));
		StyleConstants.setForeground(termStyle, new Color(0, 102, 102));
		StyleConstants.setBold(termStyle, false);
		StyleConstants.setItalic(termStyle, false);
		StyleConstants.setUnderline(termStyle, false);

		final Style verbStyle = context.addStyle("verb", null);
		StyleConstants.setBackground(verbStyle, new Color(232, 232, 255));
		StyleConstants.setForeground(verbStyle, new Color(0, 0, 102));
		StyleConstants.setBold(verbStyle, false);
		StyleConstants.setItalic(verbStyle, true);
		StyleConstants.setUnderline(verbStyle, false);

		final Style glueStyle = context.addStyle("glue", null);
		StyleConstants.setForeground(glueStyle, Color.ORANGE);
		StyleConstants.setBold(glueStyle, false);
		StyleConstants.setItalic(glueStyle, false);
		StyleConstants.setUnderline(glueStyle, false);

		final Style numberStyle = context.addStyle("number", null);
		StyleConstants.setForeground(numberStyle, new Color(128, 0, 128));
		StyleConstants.setBold(numberStyle, false);
		StyleConstants.setItalic(numberStyle, false);
		StyleConstants.setUnderline(numberStyle, false);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

		// JToggleButton leftButton = new JToggleButton(new
		// ImageIcon(this.getClass().getResource("sourceViewNormal.png")));
		// leftButton.setSelectedIcon(new
		// ImageIcon(this.getClass().getResource("sourceViewNormalSelected.png")));
		// leftButton.putClientProperty("JButton.buttonType",
		// "segmentedTextured");
		// leftButton.putClientProperty("JButton.segmentPosition", "first");
		// leftButton.setFocusable(false);
		//
		// JToggleButton rightButton = new JToggleButton(new
		// ImageIcon(this.getClass().getResource("ColumnViewTemplate.png")));
		// rightButton.putClientProperty("JButton.buttonType",
		// "segmentedTextured");
		// rightButton.putClientProperty("JButton.segmentPosition", "last");
		// rightButton.setFocusable(false);
		//
		// ButtonGroup group = new ButtonGroup();
		// group.add(leftButton);
		// group.add(rightButton);
		//
		// LabeledComponentGroup viewButtons = new LabeledComponentGroup("View",
		// leftButton, rightButton);

		// Icon blueGlobeIcon = new
		// ImageIcon(this.getClass().getResource("DotMac.png"));
		// Icon greyGlobeIcon = new
		// ImageIcon(this.getClass().getResource("Network.png"));
		// Icon gear = new
		// ImageIcon(this.getClass().getResource("Advanced.png"));

		// AbstractButton greyGlobeButton =
		// MacButtonFactory.makeUnifiedToolBarButton(new JButton("Network",
		// greyGlobeIcon));
		// greyGlobeButton.setEnabled(false);

		final JToggleButton aButton = new JToggleButton("Parse");
		// aButton.setSelectedIcon(new
		// ImageIcon(this.getClass().getResource("sourceViewNormalSelected.png")));
		aButton.putClientProperty("JButton.buttonType", "segmentedTextured");
		aButton.putClientProperty("JButton.segmentPosition", "only");
		aButton.setFocusable(false);

		// ButtonGroup aGroup = new ButtonGroup();
		// aGroup.add(aButton);
		LabeledComponentGroup aButtons = new LabeledComponentGroup("", aButton);

		DefaultStyledDocument document = new DefaultStyledDocument() {
			private static final long serialVersionUID = 1L;

			public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
				super.insertString(offset, str, a);

				if (aButton.isSelected()) {

					String text = getText(0, getLength());
					Document document = Factory.getDocument(Locale.ENGLISH, text);
					for (Sentence sentence : document)
						for (Chunk chunk : sentence)
							if (chunk.index() == 0) {
								// if
								// (chunk.getText().toLowerCase().startsWith("term"))
								// setCharacterAttributes(chunk.offset(),
								// chunk.length(), termLbl.copyAttributes(),
								// false);
								// else if
								// (chunk.getText().toLowerCase().startsWith("fact"))
								// setCharacterAttributes(chunk.offset(),
								// chunk.length(), verbLbl.copyAttributes(),
								// false);
								// else if
								// (chunk.getText().toLowerCase().startsWith("rule"))
								// setCharacterAttributes(chunk.offset(),
								// chunk.length(), ruleLbl.copyAttributes(),
								// false);
								// else
								// setCharacterAttributes(chunk.offset(),
								// chunk.length(), normalStyle.copyAttributes(),
								// false);
							} else if (chunk.index() > 0) {
								switch (chunk.getTag()) {
									case VP:
										setCharacterAttributes(chunk.offset(), chunk.length(), verbStyle.copyAttributes(), false);
										break;
									case NP:
										setCharacterAttributes(chunk.offset(), chunk.length(), termStyle.copyAttributes(), false);
										break;
									// case ADJP:
									// case PP:
									// setCharacterAttributes(chunk.offset(),
									// chunk.length(),
									// glueStyle.copyAttributes(),
									// false);
									// break;
									// case SBAR: // case PRT: // case LST:
									// //case
									// INTJ:
									// case DP: // case CONJP: // case ADVP:
									default:
										setCharacterAttributes(chunk.offset(), chunk.length(), normalStyle.copyAttributes(), false);
								}
								for (Token token : chunk) {
									// if (token.getText().equals("obligatory"))
									// lblNewLabel.setText(token.getTag().getSymbol());
									switch (token.getTag()) {
										case DT:
											// case JJ: case JJR: case JJS:
											// case IN: case WDT:
											setCharacterAttributes(token.offset(), token.length(), glueStyle.copyAttributes(), false);
											break;
										case CD:
											setCharacterAttributes(token.offset(), token.length(), numberStyle.copyAttributes(), false);
											break;
										default:
									}
								}
							}
					/*
					 * int before = findLastNonWordChar(text, offset); if
					 * (before < 0) before = 0; int after =
					 * findFirstNonWordChar(text, offset + str.length()); int
					 * wordL = before; int wordR = before;
					 * 
					 * while (wordR <= after) { if (wordR == after ||
					 * String.valueOf(text.charAt(wordR)).matches("\\W")) { if
					 * (text.substring(wordL,
					 * wordR).matches("(\\W)*(private|public|protected)"))
					 * setCharacterAttributes(wordL, wordR - wordL,
					 * termStyle.copyAttributes(), false); else
					 * setCharacterAttributes(wordL, wordR - wordL,
					 * normalStyle.copyAttributes(), false); wordL = wordR; }
					 * wordR++; }
					 */
				}
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);

				if (aButton.isSelected()) {
					String text = getText(0, getLength());
					Document document = Factory.getDocument(Locale.ENGLISH, text);
					int start, end;
					try {
						start = document.get(offs).index();
					} catch (NullPointerException e) {
						start = 0;
					}
					try {
						end = document.get(offs + len).index();
					} catch (NullPointerException e) {
						end = 0;
						for (Sentence sentence : document)
							end = sentence.index();
					}

					for (Sentence sentence : document)
						if (start <= sentence.index() && sentence.index() <= end) {
							setCharacterAttributes(sentence.offset(), sentence.length(), termLbl.copyAttributes(), false);
						} else
							setCharacterAttributes(sentence.offset(), sentence.length(), normalStyle.copyAttributes(), false);

					// String text = getText(0, getLength());
					// int before = findLastNonWordChar(text, offs);
					// if (before < 0)
					// before = 0;
					// int after = findFirstNonWordChar(text, offs);
					//
					// if (text.substring(before,
					// after).matches("(\\W)*(private|public|protected)")) {
					// setCharacterAttributes(before, after - before,
					// termStyle.copyAttributes(), false);
					// } else {
					// setCharacterAttributes(before, after - before,
					// normalStyle.copyAttributes(), false);
					// }
				}
			}
		};

		final JTextPane textPane = new JTextPane(document);
		textPane.setText("Term: pilot.\nTerm: planes.\nFact type: pilot can fly plane.\nFact type: pilot is experienced.\nRule: it is obligatory that each pilot can fly at least 1 plane. \nRule: it is obligatory that each pilot which is experienced can fly at least 3 planes.");
		// textPane.setText(String.join("\n", System.getenv().values()));
		// textPane.setText("public class Person {\n\t// This is a private String.\n\tprivate String name;\n}\n");
		JScrollPane scrollPane = new JScrollPane(textPane);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		aButton.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textPane.setText(textPane.getText());
			}
		});

		final JTextField textField = new JTextField(10);
		textField.putClientProperty("JTextField.variant", "search");
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case 10:
						if (!pattern.equals(textField.getText())) {
							pos = 0;
							pattern = textField.getText();
						}
						if (pos == -1) {
							textPane.setCaretPosition(0);
							textPane.moveCaretPosition(0);
							textPane.update(textPane.getGraphics());
							pos = 0;
						} else if (pattern != null && !pattern.isEmpty()) {
							int result = textPane.getText().indexOf(pattern, pos);
							if (result != -1) {
								textPane.setCaretPosition(result);
								textPane.moveCaretPosition(result + pattern.length());
								textPane.update(textPane.getGraphics());
								pos = result + pattern.length();
							} else
								pos = result;
						}
						break;
				}
			}
		});

		UnifiedToolBar toolBar = new UnifiedToolBar();
		// toolBar.addComponentToLeft(viewButtons.getComponent());

		toolBar.addComponentToLeft(aButtons.getComponent());
		// toolBar.addComponentToCenter(MacButtonFactory.makeUnifiedToolBarButton(new
		// JButton("MobileMe", blueGlobeIcon)));
		// toolBar.addComponentToCenter(greyGlobeButton);
		// toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(
		// new JButton("Network", greyGlobeIcon)));
		// toolBar.addComponentToCenter(MacButtonFactory.makeUnifiedToolBarButton(new
		// JButton("Advanced", gear)));
		toolBar.addComponentToRight(textField);
		// toolBar.addComponentToRight(new LabeledComponentGroup("Search",
		// textField).getComponent());
		toolBar.installWindowDraggerOnWindow(frame);

		// JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar.getComponent(), BorderLayout.NORTH);

		textPane.requestFocusInWindow();

		// JButton btnNewButton = new JButton("New button");
		// btnNewButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });
		// toolBar.add(btnNewButton);
	}

	private static String pattern = "";
	private static int pos = 0;

}
