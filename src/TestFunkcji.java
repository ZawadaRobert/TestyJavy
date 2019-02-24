import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private static AzButton exitButton, getPathsButton, resetButton, testButton;

	private static void createFrame() {

		mainFrame = new JFrame("Jakieœ Okienko");
		mainFrame.setSize(800, 800);
		mainFrame.setLayout(null);

		exitButton = new AzButton("Wyjœcie", 30, 60, 120, 20);
		getPathsButton = new AzButton("Œcie¿ki", 30, 90, 120, 20);
		resetButton = new AzButton("Reset", 30, 120, 120, 20);
		testButton = new AzButton("Test", 30, 150, 120, 20);

		mainFrame.add(exitButton);
		mainFrame.add(getPathsButton);
		mainFrame.add(resetButton);
		mainFrame.add(testButton);

		// ACTIVITY TABLE MODEL START
		class ActivityTableModel extends DefaultTableModel {

			private Set<CPMActivity> activitySet;
			private Set<Integer> allIdSet;

			public ActivityTableModel() {
				String[] columnNames = {"Id", "Name",
										"Time",	"PrevList",
										"NextList", "ES",
										"EF", "LS", "LF",
										"Reserve", "IsCrytical"};
				for (String l : columnNames) {
					addColumn(l);
				}
				
				activitySet = new TreeSet<CPMActivity>();
				allIdSet = new TreeSet<Integer>();
				refresh();
			}

			public void calculateAllId() {
				
				for (CPMActivity activity : activitySet) {
					allIdSet.add(activity.getId());
				}
			}
			
			public CPMActivity getActivity(int a) {
				CPMActivity activity;
				activity = activitySet
						.stream()
						.filter(p -> p.getId() == a)
						.findAny()
						.get();
				return activity;
			}
			
			
			public void refresh() {
				
				int b=activitySet.size();
				
				for (int a=0; a<b; a++) {
				
					setRowCount(0);
					allIdSet.clear();
	
					Duration totalDuration = Duration.ZERO;
	
					for (CPMActivity activity : activitySet) {
						activity.addNextActionFromList(activitySet);
						Set<Integer> prevList = activity.getPrevList();
						Duration baseES = Duration.ZERO;
	
						for (Integer i : prevList) {
							for (CPMActivity act : activitySet) {
								if (act.getEarlyFinish()!=null) {
									if ((act.getId() == i)&&(baseES.compareTo(act.getEarlyFinish()) < 0)) {
										baseES = act.getEarlyFinish();
									}
								}
							}
						}
						activity.setEarlyStart(baseES);
					}
					
					for (CPMActivity activity : activitySet) {
						if (activity.getEarlyFinish().compareTo(totalDuration) > 0) {
							totalDuration = activity.getEarlyFinish();
						}
					}
	
					for (CPMActivity activity : activitySet) {
						Set<Integer> nextList = activity.getNextList();
	
						Duration baseLF = totalDuration;
	
						for (Integer i : nextList) {
							for (CPMActivity act : activitySet) {
								if (act.getLateStart()!=null) {
									if ((act.getId() == i)&&(baseLF.compareTo(act.getLateStart()) > 0)) {
										baseLF = act.getLateStart();
									}
								}
							}
						}
						
						activity.setLateFinish(baseLF);
						activity.calculateReserve();
						calculateAllId();
					}
				}

				for (CPMActivity activity : activitySet) {
					addRow(activity.getArrayRow());
				}
			}

			public Set<CPMActivity> getActivitySet() {
				return activitySet;
			}
			
			public Vector<String> getCryticalPathsList() {
				
				Set<CPMActivity> cryticalSet = new TreeSet<CPMActivity>();
				Set<Integer> cryticalIdSet = new TreeSet<Integer>();
				Set<Integer> nonCryticalIdSet = new TreeSet<Integer>();
				Set<String> cryticalpathsSet = new TreeSet<String>();
				
				for (CPMActivity activity : activitySet) {
					if (activity.isCrytical()) {
						cryticalSet.add(activity);
						cryticalIdSet.add(activity.getId());
					}
				}
				
				nonCryticalIdSet = allIdSet;
				nonCryticalIdSet.removeAll(cryticalIdSet);
			
				for (CPMActivity activity : cryticalSet) {
					for (Integer id : nonCryticalIdSet) {
						activity.removeFromPrevList(id);
						activity.removeFromNextList(id);
					}
				}
				
				for (CPMActivity activity : cryticalSet) {
					
					if (activity.isStart()) {
						CPMActivity currentActivity = activity;
						Integer currentId = activity.getId();
						String currentPath = "START -> "+currentId+" -> ";
						
						while (!currentActivity.isEnd()) {
							
							if (currentActivity.getNextList().size()==1) {
							
								currentId = currentActivity.getNextList().stream().findFirst().get();
								currentActivity = getActivity(currentId);
								currentPath += currentId+" -> ";
							}
							
							else {
								
								System.out.println(currentActivity + " ma wiêcj ni¿ jedn¹ nastêpn¹ akcjê.");
								currentId = currentActivity.getNextList().stream().findFirst().get();
								currentActivity = getActivity(currentId);
								currentPath += currentId+" -> ";
								
							}
						}
						currentPath += "END";
						System.out.println(currentPath);
					}
				}

				return new Vector<String>();
			}
			
			public void clear() {
				activitySet.clear();
				setRowCount(0);
				refresh();
			}

		}
		ActivityTableModel activityTableModel = new ActivityTableModel();
		// ACTIVITY TABLE MODEL ENDS

		// INPUT PANE START
		class InputPane extends JPanel {

			private JTextField idTextField, nameTextField, timeTextField, prevActionTextField;
			private JButton addingActionButton;
			private JComboBox<String> methodList;

			public InputPane() {
				setLayout(new GridLayout(1, 5));
				setSize(new Dimension(500, 45));

				idTextField = new GhostTextField("Id");
				((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9]"));
				add(idTextField);

				nameTextField = new GhostTextField("Name");
				add(nameTextField);

				timeTextField = new GhostTextField("Time");
				((AbstractDocument) timeTextField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
				add(timeTextField);

				String[] comboString = { "sek", "min", "godz", "dni" };
				methodList = new JComboBox<String>(comboString);
				methodList.setSelectedIndex(2);
				add(methodList);

				prevActionTextField = new GhostTextField("Prev Actions");
				((AbstractDocument) prevActionTextField.getDocument())
						.setDocumentFilter(new CharacterFilter("[^0-9,]"));
				add(prevActionTextField);

				addingActionButton = new JButton("Dodaj");
				add(addingActionButton);

				setBorder(BorderFactory.createTitledBorder("Wprowadzanie danych"));
			}

			public JButton getAddingActionButton() {
				return addingActionButton;
			}

			public boolean haveFilledFields() {
				return !(idTextField.getText().isEmpty() || nameTextField.getText().isEmpty()
						|| timeTextField.getText().isEmpty());
			}

			public boolean haveValidActivity() {
				return CPMActivity.isValidActivity(Integer.parseInt(idTextField.getText()),
						prevActionTextField.getText());
			}

			public CPMActivity getNewActivity() {
				int t = methodList.getSelectedIndex();

				CPMActivity newActivity = new CPMActivity(Integer.parseInt(idTextField.getText()),
						nameTextField.getText());
				newActivity.addPrevActionFromString(prevActionTextField.getText());

				switch (t) {
				case 0:
					newActivity.setTime(Az.toSeconds(timeTextField.getText()));
					break;
				case 1:
					newActivity.setTime(Az.toMinutes(timeTextField.getText()));
					break;
				case 2:
					newActivity.setTime(Az.toHours(timeTextField.getText()));
					break;
				case 3:
					newActivity.setTime(Az.toDays(timeTextField.getText()));
					break;
				}
				return newActivity;
			}

		}
		InputPane inputPane = new InputPane();
		mainFrame.add(inputPane);
		// INPUT PANE END

		// MAIN MENU BAR START
		class MainMenuBar extends JMenuBar {

			private JMenu fileMenu;
			private JMenuItem menuItem, exitMenuItem;

			public MainMenuBar() {

				fileMenu = new JMenu("Plik");
				fileMenu.setMnemonic(KeyEvent.VK_P);

				menuItem = new JMenuItem("An item", KeyEvent.VK_T);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
				fileMenu.add(menuItem);

				fileMenu.addSeparator();

				exitMenuItem = new JMenuItem("Wyjœcie", KeyEvent.VK_F4);
				exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
				AzBasicEvent.performDialogExitFromButton(mainFrame, exitMenuItem);
				fileMenu.add(exitMenuItem);

				add(fileMenu);
			}
		}
		MainMenuBar mainMenuBar = new MainMenuBar();
		mainFrame.setJMenuBar(mainMenuBar);
		// MAIN MENU END

		// OUTPUT TABLE PANE START
		class TablePane extends JPanel {

			private JTable table;
			private JScrollPane scrollPane;

			public TablePane() {
				setBounds(0, 300, 750, 300);

				table = new JTable(activityTableModel);

				table.setPreferredScrollableViewportSize(new Dimension(720, 250));
				table.setFillsViewportHeight(true);
				table.setEnabled(false);

				scrollPane = new JScrollPane(table);
				add(scrollPane);

				setBorder(BorderFactory.createTitledBorder("Lista aktywnoœci"));
			}

			public void addActionFromInputPane(InputPane pane) {

				if (pane.haveFilledFields()) {

					if (pane.haveValidActivity()) {

						CPMActivity newActivity = pane.getNewActivity();

						if (activityTableModel.getActivitySet().contains(newActivity)) {
							AzBasicEvent.eventDialogError(mainFrame, "To id ju¿ istnieje");
						}

						else {
							activityTableModel.getActivitySet().add(newActivity);
							activityTableModel.refresh();
						}
					}

					else {
						AzBasicEvent.eventDialogError(mainFrame,
								"Id poprzedzaj¹cej aktywnoœci nie mo¿e byæ takie same jak id wprowadzanej aktywnosci");
					}
				}

				else {
					AzBasicEvent.eventDialogError(mainFrame, "Podaj id, nazwê i czas trwania aktywnoœci");
				}
			}

		}
		TablePane tablePane = new TablePane();
		mainFrame.add(tablePane);
		// OUTPUT TABLE PANE END
		
		// OUTPUT CRITICAL PATH START
		class PathPane extends JPanel {
			
			public PathPane() {
				
				setBounds(0, 600, 750, 100);
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				add(new JLabel("Testowa etykieta"));
				add(new JLabel("Testowa etykieta 2"));
				add(new JLabel("Testowa etykieta 3"));
				
				setBorder(BorderFactory.createTitledBorder("Lista œcie¿ek krytycznych"));
			}
		}
		PathPane pathPane = new PathPane();       
		mainFrame.add(pathPane);
		// OUTPUT CRITICAL PATH END

		inputPane.getAddingActionButton().addActionListener(e -> tablePane.addActionFromInputPane(inputPane));
		
		resetButton.addActionListener(e -> activityTableModel.clear());
		getPathsButton.addActionListener(e -> activityTableModel.getCryticalPathsList());

		AzBasicEvent.performDialogExitFromButton(mainFrame, exitButton);
		AzBasicEvent.performDialogExitFromX(mainFrame);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createFrame();
			}
		});
	}
}