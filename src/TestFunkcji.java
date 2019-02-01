import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import AzGUI.AzBasicEvent;
import AzGUI.AzButton;
import AzGUI.AzRadioButtonGroup;

public class TestFunkcji {
	private static JFrame mainFrame;
	private static AzButton exitButton, saveButton, resetButton;
	private static Set<CPMActivity> activityList;
	
	private static void createFrame() {
				
		mainFrame = new JFrame("Jakieœ Okienko");
		mainFrame.setSize(550,800);
		mainFrame.setLayout(null);
		activityList = new TreeSet<CPMActivity>();
	
		exitButton = new AzButton("Wyjœcie",30,60,120,20);
		saveButton = new AzButton("Zapisz",30,90,120,20);
		resetButton = new AzButton("Reset",30,120,120,20);

		mainFrame.add(exitButton);
		mainFrame.add(saveButton);
		mainFrame.add(resetButton);
		
		saveButton.addActionListener(e -> {

		});
		
		resetButton.addActionListener(e -> {

		});	
		
		String[] timeMethodStringList = {"sekundy","minuty","godziny","dni"};
		
		AzRadioButtonGroup chooseTimeMethodGroup = new AzRadioButtonGroup(timeMethodStringList,2);
		chooseTimeMethodGroup.setBounds(200,100,100,100);
		mainFrame.add(chooseTimeMethodGroup);
		
		//ACTIVITY TABLE MODEL START
		class ActivityTableModel extends DefaultTableModel {
	        	
	      	public void refresh(Set<CPMActivity> activitySet) {
	     		this.setRowCount(0);
	     		
				for (CPMActivity activity : activitySet) {				
					this.addRow(activity.getArrayRow());
				}
	       	}
	    }	
		//ACTIVITY TABLE MODEL ENDS	
		
		//INPUT PANE START
		class InputPane extends JPanel {
		
			
			private JTextField idTextField, nameTextField, timeTextField, prevActionTextField;
			private JButton addingActionButton;
			private JComboBox<String> methodList;
						

			public InputPane() {				
				setLayout(new GridLayout(1,5));
				setSize(new Dimension(500,45));
		
				idTextField = new GhostTextField("Id");
				((AbstractDocument)idTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9]"));
				add(idTextField);
		
				nameTextField = new GhostTextField("Name");
				add(nameTextField);
		
				timeTextField = new GhostTextField("Time");
				((AbstractDocument)timeTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
				add(timeTextField);
				
				String[] comboString = { "sek", "min", "godz", "dni"};
				methodList = new JComboBox<String>(comboString);
				methodList.setSelectedIndex(2);
				add(methodList);
		
				prevActionTextField = new GhostTextField("Prev Actions");
				((AbstractDocument)prevActionTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9,]"));
				add(prevActionTextField);
		
				addingActionButton = new JButton("Dodaj");
				add(addingActionButton);
				
				setBorder(BorderFactory.createTitledBorder("Wprowadzanie danych"));
			}
			
			public JButton getAddingActionButton() {
				return addingActionButton;
			}
			
			public boolean haveFilledFields() {
				return !(idTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || timeTextField.getText().isEmpty());
			}
			
			public boolean haveValidActivity() {
				return CPMActivity.isValidActivity(Integer.parseInt(idTextField.getText()),prevActionTextField.getText());
			}

			public CPMActivity getNewActivity() {
				int t = methodList.getSelectedIndex();

				CPMActivity newActivity = new CPMActivity(Integer.parseInt(idTextField.getText()),nameTextField.getText());
				newActivity.addPrevActionFromString(prevActionTextField.getText());
				
				switch (t) {
					case 0: newActivity.setTime(Duration.ofSeconds(Integer.parseInt(timeTextField.getText())));
							break;
					case 1: newActivity.setTime(Duration.ofMinutes(Integer.parseInt(timeTextField.getText())));
							break;
					case 2: newActivity.setTime(Duration.ofHours(Integer.parseInt(timeTextField.getText())));
							break;
					case 3: newActivity.setTime(Duration.ofDays(Integer.parseInt(timeTextField.getText())));
							break;
				}
				return newActivity;
			}
				
		}
		InputPane inputPane = new InputPane();
		mainFrame.add(inputPane);
		//INPUT PANE END

		//MAIN MENU BAR START
		class MainMenuBar extends JMenuBar {
	
			private JMenu fileMenu;
			private JMenuItem menuItem, exitMenuItem;
			
			public MainMenuBar() {
				
				fileMenu = new JMenu("Plik");
				fileMenu.setMnemonic(KeyEvent.VK_P);
	
				menuItem = new JMenuItem("An item",KeyEvent.VK_T);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
				fileMenu.add(menuItem);
	
				fileMenu.addSeparator();
	
				exitMenuItem = new JMenuItem("Wyjœcie",KeyEvent.VK_F4);
				exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
				AzBasicEvent.performDialogExitFromButton(mainFrame,exitMenuItem);
				fileMenu.add(exitMenuItem);
				
				add(fileMenu);
			}	
		}
		MainMenuBar mainMenuBar = new MainMenuBar();
		mainFrame.setJMenuBar(mainMenuBar);
		//MAIN MENU END
		
		//OUTPUT TABLE PANE START
		class TablePane extends JPanel {
			
			private JTable table;
			private ActivityTableModel model;
			private JScrollPane scrollPane;
			
			public TablePane() {

		        setBounds(0,300,500,140);
		        
		        String[] columnNames = {"Id","Name","Time","PrevList","NextList","Reserve","IsCrytical"};
		        model = new ActivityTableModel();
		        
		        for (String l : columnNames) {
		        	model.addColumn(l);
		        }
		        
		        table = new JTable(model);

				table.setPreferredScrollableViewportSize(new Dimension(490, 90));
		        table.setFillsViewportHeight(true);
		        table.setEnabled(false);
		        
				scrollPane = new JScrollPane(table);
		        add(scrollPane);
		        
				setBorder(BorderFactory.createTitledBorder("Lista aktywnoœci"));
			}
			
			public ActivityTableModel getModel() {
				return model;
			}
			
		}
		TablePane tablePane = new TablePane();
		mainFrame.add(tablePane);		
		//OUTPUT TABLE PANE END			

		inputPane.getAddingActionButton().addActionListener(e -> {

			if (inputPane.haveFilledFields()) {
				
				if (inputPane.haveValidActivity()) {
				
					CPMActivity newActivity = inputPane.getNewActivity();
					
					if (activityList.contains(newActivity)) {
						AzBasicEvent.eventDialogError(mainFrame, "To id ju¿ istnieje");
					}
					
					else {
						activityList.add(newActivity);		
						tablePane.getModel().refresh(activityList);
					}
				}
				
				else {
					AzBasicEvent.eventDialogError(mainFrame, "Id poprzedzaj¹cej aktywnoœci nie mo¿e byæ takie same jak id wprowadzanej aktywnosci");
				}
			}
				
			else {
				AzBasicEvent.eventDialogError(mainFrame, "Podaj id, nazwê i czas trwania aktywnoœci");
			}
		});
       
		resetButton.addActionListener(e -> tablePane.getModel().setRowCount(0));

	
		AzBasicEvent.performDialogExitFromButton(mainFrame,exitButton);
		AzBasicEvent.performDialogExitFromX(mainFrame);
		
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
            }
        });
    }
}