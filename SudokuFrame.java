package net.gjo.sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
 
public class SudokuFrame
{
	  private static JFrame frame = new JFrame("Sudoku Solver");
	  private static final Dimension FIELD_SIZE = new Dimension(30, 30);
	  private static final int BORDER_WIDTH = 3;
	  private static final float FONT_POINTS = 15;
	  private JPanel mainPanel = new JPanel();
	  private JPanel[][] subPanels = new JPanel[3][3];
	  private static JTextField[][] fields = new JTextField[9][9];
	  private static int[] []  inputSudoku=new int [9][9];
	  private static int[] []  outputSudoku=new int [9][9];
	  public static boolean issolutionfound=true;
 
  public SudokuFrame()
  {
    mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
    mainPanel.setLayout(new GridLayout(3, 3));

    // create 3x3 grid of sub-panels,
    // add a line border to each sub-panel
    // and add to main panel
    for (int i = 0; i < subPanels.length; i++)
    {
      for (int j = 0; j < subPanels[i].length; j++)
      {
        subPanels[i][j] = new JPanel(new GridLayout(3, 3));
        subPanels[i][j].setBorder(
          BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
        mainPanel.add(subPanels[i][j]);
      }
    }
     
    // create all JTextFields
    // set fields preferred size, font, center text
    // and add to sub-panels
    for (int i = 0; i < fields.length; i++)
    {
      for (int j = 0; j < fields[i].length; j++)
      {
        fields[i][j] = new JTextField();
        fields[i][j].setPreferredSize(FIELD_SIZE);
        Font font = fields[i][j].getFont().deriveFont(Font.BOLD, FONT_POINTS);
        fields[i][j].setFont(font);
        fields[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        subPanels[i/3][j/3].add(fields[i][j]);
      }
    }
  }
 
public JComponent getPanel()
  {
    return mainPanel;
  }
 
  private static void createAndShowGUI()
  {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new SudokuFrame().getPanel());
    buildMenu();
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
 
  private static void buildMenu() {
      JMenuBar bar = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      JMenuItem New  = new JMenuItem("New");
      JMenuItem solve  = new JMenuItem("Solve");
      JMenuItem about = new JMenuItem("About");
      

     // fileMenu.add(open);
      fileMenu.addSeparator();
      fileMenu.add(New);
      fileMenu.addSeparator();
      fileMenu.add(solve);
      fileMenu.addSeparator();
      fileMenu.add(about);

      bar.add(fileMenu);

      New.addActionListener((ActionEvent e) -> {
          frame.getContentPane().removeAll();
          frame.getContentPane().add(new SudokuFrame().getPanel());
          frame.pack();
      });
      
      solve.addActionListener((ActionEvent e) -> {
    	  
    	  for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (fields[i][j].getText().equals("")) {
					inputSudoku[i][j]=0;
				} else {
					inputSudoku[i][j]= Integer.parseInt(fields[i][j].getText());
				}
			}
		}
    	  issolutionfound=SudokuSolver.solver(inputSudoku,issolutionfound);
    	  if(issolutionfound==true){
    	  //display values
    	  for (int i = 0; i < 9; i++) {
  			for (int j = 0; j < 9; j++) {
  				fields[i][j].setText(inputSudoku[i][j]+"");
  				//inputSudoku[i][j]= Integer.parseInt(fields[i][j].getText());
  			}
  		}
    	}
    	  else{
    		  JOptionPane.showMessageDialog(
                      null,
                      "This Sudoku could'nt be solved.Please re-check your digits", 
                      "About",
                      JOptionPane.INFORMATION_MESSAGE);
    	  }
    	  
    	  
      });

      about.addActionListener((ActionEvent e) -> {
          JOptionPane.showMessageDialog(
                  null,
                  "Developed By Goutham Joshi - April 2017", 
                  "About",
                  JOptionPane.INFORMATION_MESSAGE);
      });

      frame.setJMenuBar(bar);
  }

public static void main(String[] args)
  {
    try {
    	javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            createAndShowGUI();
          }
        });
		
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null, 
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
  }
}