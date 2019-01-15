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

import AzGUI.*;

public class TestFunkcji {
	private static AzButton BExit, BTest, BTest2;
		
	private static void createFrame() {
		AzFrame NoweOkienko = new AzFrame("Jakieœ Okienko",800,1200);
		
		BExit = new AzButton("Wyjœcie",30,60,100,20);
		BTest = new AzButton("Lista Prev",30,90,100,20);
		BTest2 = new AzButton("Lista Next",30,120,100,20);
	
		AzLabel LPole = new AzLabel("Jakiœ tekst",30,120,100,20);
		
		AzTextField PoleTekstowe = new AzTextField("",150,30,100,20);

		Set<Component> ButtonsSet = new HashSet<Component>();
		ButtonsSet.add(BExit);
		ButtonsSet.add(BTest);
		ButtonsSet.add(BTest2);
		
		NoweOkienko.addAll(ButtonsSet);
		
		NoweOkienko.add(LPole);
		NoweOkienko.add(PoleTekstowe);
		
		Integer PunktID = 5;
		String PunktNazwa = "NowyElement";
		Duration PunktCzas = Duration.ofHours(1);
		String PunktLista ="1,2,3,4";
		Set<Integer> WymaganeLista = new HashSet<Integer>();
		WymaganeLista.add(3);
		WymaganeLista.add(4);
		WymaganeLista.add(5);
		CPMActivity TestowaAkcja = new CPMActivity(PunktID, PunktNazwa);
		
		TestowaAkcja.addPrevActionFromString(PunktLista);
		TestowaAkcja.removePrevAction(1);
		TestowaAkcja.addNextActionFromIndex(2,WymaganeLista);
		
		String[] TimeMethodStringList = {"sekundy","minuty","godziny","dni"};
		
		AzRadioButtonGroup ChooseTimeMethodGroup = new AzRadioButtonGroup(TimeMethodStringList,2);
		ChooseTimeMethodGroup.setBounds(200,100,100,100);
		NoweOkienko.add(ChooseTimeMethodGroup);
		
		//Konfiguracja GIU wpisywania wartoœci
		JPanel InputPane = new JPanel();
		InputPane.setLayout(new GridLayout(1,5));
		InputPane.setSize(400,20);
		
		JTextField IdTextField = new JTextField("Id");
		IdTextField.setSize(40,20);
		InputPane.add(IdTextField);
		
		JTextField NameTextField = new JTextField("Name");
		NameTextField.setSize(120,20);
		InputPane.add(NameTextField);
		
		JTextField TimeTextField = new JTextField("Time");
		TimeTextField.setSize(120,20);
		InputPane.add(TimeTextField);
		
		JTextField PrevActionTextField = new JTextField("PrevAction");
		PrevActionTextField.setSize(120,20);
		InputPane.add(PrevActionTextField);
		
		JButton AddingActionButton = new JButton("Dodaj");
		AddingActionButton.setSize(120,20);
		InputPane.add(AddingActionButton);
		
		NoweOkienko.add(InputPane);
		
		Set<CPMActivity> ActionList = new HashSet<CPMActivity>();
			
		AddingActionButton.addActionListener(e -> {
			CPMActivity NewActivity = new CPMActivity(Integer.parseInt(IdTextField.getText()),NameTextField.getText());
			
			if (ChooseTimeMethodGroup.isButtonSelected(0)) {
				NewActivity.setTime(Duration.ofSeconds(Integer.parseInt(TimeTextField.getText())));
			}

			ActionList.add(NewActivity);
			System.out.println(ActionList);
		});
		
		
		//
		
		BTest.addActionListener(e -> {
			System.out.println(TestowaAkcja.getPrevList());
		});
		
		BTest2.addActionListener(e -> {
			System.out.println(TestowaAkcja.getNextList());
		});
		
		//Tablica do wyœwietlania
		
        String[] columnNames = {"Id","Name","Time","PrevList","NextList","Reserve","IsCrytical"};
        
        Object[][] data = {
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
	    {TestowaAkcja.getId(), TestowaAkcja.getName(),TestowaAkcja.getTime().getSeconds(),TestowaAkcja.getPrevList(),TestowaAkcja.getNextList(),TestowaAkcja.getReserve(),TestowaAkcja.isCrytical()},
        };
		
        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(30,300,500,70);
        
        NoweOkienko.add(tablePane);
	
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