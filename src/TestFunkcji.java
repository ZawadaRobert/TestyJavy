import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import AzGUI.*;

public class TestFunkcji {
	private static AzButton exitButton, testButton, testButton2;
		
	private static void createFrame() {
		AzFrame NoweOkienko = new AzFrame("Jakieœ Okienko",800,1200);
		
		exitButton = new AzButton("Wyjœcie",30,60,100,20);
		testButton = new AzButton("Lista Prev",30,90,100,20);
		testButton2 = new AzButton("Lista Next",30,120,100,20);
	
		AzLabel LPole = new AzLabel("Jakiœ tekst",30,120,100,20);

		Set<Component> ButtonsSet = new HashSet<Component>();
		ButtonsSet.add(exitButton);
		ButtonsSet.add(testButton);
		ButtonsSet.add(testButton2);
		
		NoweOkienko.addAll(ButtonsSet);
		
		NoweOkienko.add(LPole);
		
		String[] TimeMethodStringList = {"sekundy","minuty","godziny","dni"};
		
		AzRadioButtonGroup ChooseTimeMethodGroup = new AzRadioButtonGroup(TimeMethodStringList,2);
		ChooseTimeMethodGroup.setBounds(200,100,100,100);
		NoweOkienko.add(ChooseTimeMethodGroup);
		

		
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
		NoweOkienko.add(inputPane);
		
		Set<CPMActivity> ActivityList = new TreeSet<CPMActivity>();
		
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
       
        inputPane.getAddingActionButton().addActionListener(e -> {
			if (inputPane.isValidActivity()) {
		    	CPMActivity newActivity = inputPane.getNewActivity();
				
		    	ActivityList.add(newActivity);
		    	model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
		    					
				for (CPMActivity activity : ActivityList) {
						model.addRow(activity.getArrayRow());
		        }
			}
		});
        
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane TablePane = new JScrollPane(table);
        TablePane.setBounds(30,300,500,70);
        table.setEnabled(false);
        
        NoweOkienko.add(TablePane);
	
		new AzBasicEvent().performDialogExitFromButton(exitButton,NoweOkienko);
		new AzBasicEvent().performDialogExitFromX(NoweOkienko);
		
		NoweOkienko.setLocationRelativeTo(null);
		NoweOkienko.setVisible(true);
		
		NoweOkienko.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
            }
        });
    }
}