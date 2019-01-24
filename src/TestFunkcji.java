import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import AzGUI.*;

public class TestFunkcji {
	private JFrame MainWindow;
	private static AzButton exitButton, testButton, testButton2;
		
	private static void createFrame() {
		JFrame MainWindow = new JFrame("Jakieœ Okienko");
		MainWindow.setSize(800,800);
		MainWindow.setLayout(null);
		
		exitButton = new AzButton("Wyjœcie",30,60,100,20);
		testButton = new AzButton("Lista Prev",30,90,100,20);
		testButton2 = new AzButton("Lista Next",30,120,100,20);

		MainWindow.add(exitButton);
		MainWindow.add(testButton);
		MainWindow.add(testButton2);

		
		String[] TimeMethodStringList = {"sekundy","minuty","godziny","dni"};
		
		AzRadioButtonGroup ChooseTimeMethodGroup = new AzRadioButtonGroup(TimeMethodStringList,2);
		ChooseTimeMethodGroup.setBounds(200,100,100,100);
		MainWindow.add(ChooseTimeMethodGroup);
		
		
		class InputPane extends JPanel {
		
			private JTextField idTextField, nameTextField, timeTextField, prevActionTextField;

			private JButton addingActionButton;
						
			public InputPane() {
				setLayout(new GridLayout(1,5));
				setSize(400,20);
		
				idTextField = new GhostTextField("Id");
				((AbstractDocument)idTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9]"));
				add(idTextField);
		
				nameTextField = new GhostTextField("Name");
				add(nameTextField);
		
				timeTextField = new GhostTextField("Time");
				((AbstractDocument)timeTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
				add(timeTextField);
		
				prevActionTextField = new GhostTextField("Prev Actions");
				((AbstractDocument)prevActionTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9,]"));
				add(prevActionTextField);
		
				addingActionButton = new JButton("Dodaj");
				add(addingActionButton);			
			}
			
			public JButton getAddingActionButton() {
				return addingActionButton;
			}
			
			public boolean isValidActivity() {
				boolean check = CPMActivity.isValidActivity(Integer.parseInt(idTextField.getText()),prevActionTextField.getText());
				return check;
			}
					
			public CPMActivity getNewActivity() {
				CPMActivity newActivity = new CPMActivity(Integer.parseInt(idTextField.getText()),nameTextField.getText());
				newActivity.setTime(Duration.ofSeconds(Integer.parseInt(timeTextField.getText())));
				newActivity.addPrevActionFromString(prevActionTextField.getText());
				return newActivity;
			}
				
		}
		
		InputPane inputPane = new InputPane();
		MainWindow.add(inputPane);
						
		//
		
		testButton.addActionListener(e -> {
			;
		});
		
		testButton2.addActionListener(e -> {
			;
		});
		
		//Tablica do wyœwietlania
		
        String[] columnNames = {"Id","Name","Time","PrevList","NextList","Reserve","IsCrytical"};
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        
        for (String l : columnNames) {
            model.addColumn(l);
        }
       

        
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane TablePane = new JScrollPane(table);
        TablePane.setBounds(30,300,500,140);
        table.setEnabled(false);
        
        MainWindow.add(TablePane);
	
		new AzBasicEvent().performDialogExitFromButton(exitButton,MainWindow);
		new AzBasicEvent().performDialogExitFromX(MainWindow);
		
		class MainMenuBar extends JMenuBar {

		private JMenu menu, submenu;
		private JMenuItem menuItem, menuItem2, menuItem3;

		public MainMenuBar() {
			
			menu = new JMenu("Plik");
			menu.setMnemonic(KeyEvent.VK_P);

			menuItem = new JMenuItem("An item",KeyEvent.VK_T);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
			menu.add(menuItem);

			menu.addSeparator();
			
			submenu = new JMenu("A submenu");
			submenu.setMnemonic(KeyEvent.VK_S);
	
			menuItem2 = new JMenuItem("An item in the submenu");
			menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,ActionEvent.ALT_MASK));
			submenu.add(menuItem2);
	
			menuItem3 = new JMenuItem("Another item");
			submenu.add(menuItem3);
			menu.add(submenu);
						
			add(menu);
			
			}
		}
		
		MainMenuBar mainMenuBar = new MainMenuBar();
		MainWindow.setJMenuBar(mainMenuBar);

		MainWindow.setLocationRelativeTo(null);
		MainWindow.setVisible(true);
		
		MainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
            }
        });
    }
}