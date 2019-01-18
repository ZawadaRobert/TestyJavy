import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import AzGUI.*;

public class TestFunkcji {
	private static AzButton BExit, BTest, BTest2;
		
	private static void createFrame() {
		AzFrame NoweOkienko = new AzFrame("Jakieœ Okienko",800,1200);
		
		BExit = new AzButton("Wyjœcie",30,60,100,20);
		BTest = new AzButton("Lista Prev",30,90,100,20);
		BTest2 = new AzButton("Lista Next",30,120,100,20);
	
		AzLabel LPole = new AzLabel("Jakiœ tekst",30,120,100,20);

		Set<Component> ButtonsSet = new HashSet<Component>();
		ButtonsSet.add(BExit);
		ButtonsSet.add(BTest);
		ButtonsSet.add(BTest2);
		
		NoweOkienko.addAll(ButtonsSet);
		
		NoweOkienko.add(LPole);
		
		String[] TimeMethodStringList = {"sekundy","minuty","godziny","dni"};
		
		AzRadioButtonGroup ChooseTimeMethodGroup = new AzRadioButtonGroup(TimeMethodStringList,2);
		ChooseTimeMethodGroup.setBounds(200,100,100,100);
		NoweOkienko.add(ChooseTimeMethodGroup);
		
		class InputPane extends JPanel {
		
			private JTextField IdTextField, NameTextField, TimeTextField, PrevActionTextField;
			private JButton AddingActionButton;
			
			public InputPane() {
				setLayout(new GridLayout(1,5));
				setSize(400,20);
		
				IdTextField = new JTextField("Id");
				IdTextField.setSize(40,20);
				add(IdTextField);
		
				NameTextField = new JTextField("Name");
				NameTextField.setSize(120,20);
				add(NameTextField);
		
				TimeTextField = new JTextField("Time");
				TimeTextField.setSize(120,20);
				add(TimeTextField);
		
				PrevActionTextField = new JTextField("PrevAction");
				PrevActionTextField.setSize(120,20);
				add(PrevActionTextField);
		
				AddingActionButton = new JButton("Dodaj");
				AddingActionButton.setSize(120,20);
				add(AddingActionButton);			
			}
			
			public JButton getAddingActionButton() {
				return AddingActionButton;
			}
			
			public boolean isValidActivity() {
				boolean check = CPMActivity.isValidActivity(Integer.parseInt(IdTextField.getText()),PrevActionTextField.getText());
				return check;
			}
					
			public CPMActivity getNewActivity() {
				CPMActivity newActivity = new CPMActivity(Integer.parseInt(IdTextField.getText()),NameTextField.getText());
				newActivity.setTime(Duration.ofSeconds(Integer.parseInt(TimeTextField.getText())));
				newActivity.addPrevActionFromString(PrevActionTextField.getText());
				return newActivity;
			}
				
		}
		
		InputPane inputPane = new InputPane();
		NoweOkienko.add(inputPane);
		
		Set<CPMActivity> ActivityList = new HashSet<CPMActivity>();
		
		//
		
		BTest.addActionListener(e -> {
			;
		});
		
		BTest2.addActionListener(e -> {
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
	
		new AzBasicEvent().performDialogExitFromButton(BExit,NoweOkienko);
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