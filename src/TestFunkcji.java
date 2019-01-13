import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AzGUI.*;

public class TestFunkcji {
	static AzButton BPodajDate, BExit, BTest, BTest2;
		
	public static void main(String[] args) {
		AzFrame NoweOkienko = new AzFrame("Jakieœ Okienko",400,800);
		
		BPodajDate = new AzButton("Podaj datê",30,30,100,20);
		BExit = new AzButton("Wyjœcie",30,60,100,20);
		BTest = new AzButton("Lista Prev",30,90,100,20);
		BTest2 = new AzButton("Lista Next",30,120,100,20);
	
		AzLabel LPole = new AzLabel("Jakiœ tekst",30,120,100,20);
		
		AzTextField PoleTekstowe = new AzTextField("",150,30,100,20);
		
		AzCheckBox ChZaznaczenie = new AzCheckBox ("Nazwa",200,60,150,20);
		
		String[] ListaNumerowana = new String[3];
		ListaNumerowana[0] = "Dom";
		ListaNumerowana[1] = "Okno";
		ListaNumerowana[2] = "Piwo";

		Set<Component> ButtonsSet = new HashSet<Component>();
		ButtonsSet.add(BPodajDate);
		ButtonsSet.add(BExit);
		ButtonsSet.add(BTest);
		ButtonsSet.add(BTest2);
		
		NoweOkienko.addAll(ButtonsSet);
		
		NoweOkienko.add(LPole);
		NoweOkienko.add(PoleTekstowe);
		NoweOkienko.add(ChZaznaczenie);
		
		AzRadioButtonGroup Grupa1 = new AzRadioButtonGroup(ListaNumerowana,2);
		NoweOkienko.add(Grupa1);
		
		Integer PunktID = 5;
		String PunktNazwa = "NowyElement";
		Duration PunktCzas = Duration.ofHours(1);
		String PunktLista ="1,2,3,4";
		Set<Integer> WymaganeLista = new HashSet<Integer>();
		WymaganeLista.add(3);
		WymaganeLista.add(4);
		WymaganeLista.add(5);
		CPMActivity TestowaAkcja = new CPMActivity(PunktID, PunktNazwa, PunktCzas);
		
		TestowaAkcja.addPrevActionFromString(PunktLista);
		TestowaAkcja.addPrevAction(5);
		TestowaAkcja.removePrevAction(1);
		TestowaAkcja.addNextActionFromIndex(2,WymaganeLista);
		
		BTest.addActionListener(e -> {
			System.out.println(TestowaAkcja.getPrevList());
		});
		
		BTest2.addActionListener(e -> {
			System.out.println(TestowaAkcja.getNextList());
		});
	
			BPodajDate.addActionListener(e -> {
			LPole.setText("Klikniêto przycisk");
		});
			
		ChZaznaczenie.addActionListener(e -> {
			if (ChZaznaczenie.isSelected()==true) {
				LPole.setText("Zaznaczono");
			}
			else {
				LPole.setText("Odznaczono");
			}			
		});
		
		Grupa1.getRadioButton(0).addActionListener(e -> System.out.println("Pierwsze dzia³a"));
		Grupa1.getRadioButton(1).addActionListener(e -> System.out.println("Drugie dzia³a"));
		Grupa1.getRadioButton(2).addActionListener(e -> System.out.println("Trzecie dzia³a"));
	
		new AzBasicEvent().performDialogExitFromButton(BExit,NoweOkienko);
		new AzBasicEvent().performDialogExitFromX(NoweOkienko);

		NoweOkienko.setLocationRelativeTo(null);
		NoweOkienko.setVisible(true);
		
		NoweOkienko.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}	
}