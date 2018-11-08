import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class DiseaseSim extends JFrame implements ActionListener{
    
    String[] columnNames = {"Day", "# Susceptible", "# Infected", "# Immune", "# Dead", "Total Population"};
    Object[][] data = {};
    Vector<Double> dayArray = new Vector<>();
        
    /*Declaration of all of the elements that are in
     * the user interface
     */
    JLabel label1 = new JLabel("Initial Immunity (0-100%)");
    JTextField textImmune = new JTextField();
    
    JLabel label2 = new JLabel("Virulence (0-100%)");
    JTextField textDie = new JTextField();
    
    JLabel label3 = new JLabel("Duration of Infection (1-20)");
    JTextField textDuration = new JTextField();
    
    JLabel label4 = new JLabel("R0/Rate of Transmission (0-20)");
    JTextField textRate = new JTextField();
    
    JLabel label5 = new JLabel("Population");
    JTextField textPopulation = new JTextField();
    
    JLabel label6 = new JLabel("Number of people Infected");
    JTextField textInfected = new JTextField();
    
    JTable table1 = new JTable(new DefaultTableModel(data, columnNames));
    JScrollPane pane = new JScrollPane(table1);
    JButton button30 = new JButton("Run 30 Days");
    JButton button1 = new JButton("Step One Day");
    JButton buttonReset = new JButton("Reset");
    
    /*
     * Dimensions that allow the display to look identical in any
     * resolution that it is being used on
     */
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenHeight;
    double screenWidth;
    int distanceFromLeft = (int) ((int) screenSize.width * 0.015);
    int distanceFromTop = (int) ((int) screenSize.height * 0.05);
    int labelWidth =  (int) ((int) screenSize.width * 0.25);
    int labelHeight = (int) ((int) screenSize.height * 0.025);
    int textFieldDistanceFromLeft = (int) ((int) screenSize.width * 0.2315);
    int textFieldHeight = (int) ((int) screenSize.height * 0.025);
    int textFieldWidth = (int) ((int) screenSize.width * 0.085);
    int buttonDistanceFromTop = (int) ((int)distanceFromTop * 14.5);
    int buttonHeight = (int) ((int) screenSize.height * 0.075);
    int centerButtonX = (int) ((int) screenSize.width * 0.325 - (textFieldWidth / 2));
    int rightButtonX = (int) ((int) screenSize.width * 0.65 - textFieldWidth - screenSize.width *0.015);

    
    DiseaseSim(){
        setTitle("Disease Transmission Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(700, 700);
        screenHeight = screenSize.height * 0.85;
        screenWidth = screenSize.width * 0.65;
        setSize((int) screenWidth, (int) screenHeight);
        setLocationRelativeTo(null);
        setLayout(null);
        
        //Initial Immunity
        label1.setBounds(distanceFromLeft, distanceFromTop, labelWidth, labelHeight);
        add(label1);
        textImmune.setBounds(textFieldDistanceFromLeft, distanceFromTop, textFieldWidth, textFieldHeight);
        add(textImmune);
        
        //Virulence
        label2.setBounds(distanceFromLeft, distanceFromTop * 2, labelWidth, labelHeight);
        add(label2);
        textDie.setBounds(textFieldDistanceFromLeft, distanceFromTop * 2, textFieldWidth, textFieldHeight);
        add(textDie);
        
        //Duration of Infection
        label3.setBounds(distanceFromLeft, distanceFromTop * 3, labelWidth, labelHeight);
        add(label3);
        textDuration.setBounds(textFieldDistanceFromLeft, distanceFromTop * 3, textFieldWidth, textFieldHeight);
        add(textDuration);
        
        //Rate of Transmission
        label4.setBounds(distanceFromLeft, distanceFromTop * 4, labelWidth, labelHeight);
        add(label4);
        textRate.setBounds(textFieldDistanceFromLeft, distanceFromTop * 4, textFieldWidth, textFieldHeight);
        add(textRate);
        
        //Population
        label5.setBounds(distanceFromLeft, distanceFromTop * 5, labelWidth, labelHeight);
        add(label5);
        textPopulation.setBounds(textFieldDistanceFromLeft, distanceFromTop * 5, textFieldWidth, textFieldHeight);
        add(textPopulation);
        
      //Infected
        label6.setBounds(distanceFromLeft, distanceFromTop * 6, labelWidth, labelHeight);
        add(label6);
        textInfected.setBounds(textFieldDistanceFromLeft, distanceFromTop * 6, textFieldWidth, textFieldHeight);
        add(textInfected);
        
        //Table
        table1.setEnabled(false);
        pane.setEnabled(false);
        pane.setBounds(distanceFromLeft, distanceFromTop * 7, (int) ((int)screenSize.width * 0.6), 
                (int) ((int)screenSize.height * 0.3));
        add(pane);
        
        //30 Day Button
        button30.setBounds(distanceFromLeft, buttonDistanceFromTop , textFieldWidth, buttonHeight);
        button30.addActionListener(this);
        add(button30);
        
        //Step One Day Button
        button1.setBounds(centerButtonX, buttonDistanceFromTop, textFieldWidth, buttonHeight);
        button1.addActionListener(this);
        add(button1);
        
        buttonReset.setBounds(rightButtonX, buttonDistanceFromTop, textFieldWidth, buttonHeight);
        buttonReset.addActionListener(this);
        add(buttonReset);
        
        setVisible(true);
        
    }
    
    int days = 1;
    
    public void actionPerformed(ActionEvent e) {
    	
    	boolean emptyFlag = false;
    	boolean boundsFlag = false;
    	boolean numberFlag = false;
    	
    	if(textPopulation.getText().equals("") || textInfected.getText().equals("") || textImmune.getText().equals("") || 
    			textRate.getText().equals("") || textDuration.getText().equals("") || textDie.getText().equals("")) {
    		emptyFlag = true;
    	}
    	else if(!Formula.isNum(textPopulation.getText()) || !Formula.isNum(textInfected.getText()) || !Formula.isNum(textImmune.getText()) || 
    			!Formula.isNum(textRate.getText()) || !Formula.isNum(textDuration.getText()) || !Formula.isNum(textDie.getText())) {
    		numberFlag = true;
    	}
    	else if(Double.valueOf(textPopulation.getText()) < 0 || 
    			Double.valueOf(textImmune.getText()) < 0 || Double.valueOf(textImmune.getText()) > 100 ||
    			Double.valueOf(textInfected.getText()) < 0 || Double.valueOf(textInfected.getText()) > Double.valueOf(textPopulation.getText()) ||
    			Double.valueOf(textDie.getText()) < 0 || Double.valueOf(textDie.getText()) > 100 || 
    			Double.valueOf(textDuration.getText()) < 1 || Double.valueOf(textDuration.getText()) > 20 ||Double.valueOf(textDuration.getText()) != Math.floor(Double.valueOf(textDuration.getText())) || 
    			Double.valueOf(textRate.getText()) < 0 || Double.valueOf(textRate.getText()) > 20) {
    		boundsFlag = true;
    	}
        
        if (e.getSource() == button1) {
        	if(emptyFlag) {
        		JOptionPane.showMessageDialog(null, "Error: One or more fields are blank.\nPlease fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
        	}
        	else if(boundsFlag) {
        		JOptionPane.showMessageDialog(null, "Error: Data entry out of bounds.\nPlease only input values within the bounds described.", "Out of Bounds", JOptionPane.ERROR_MESSAGE);
        	}
        	else if(numberFlag) {
        		JOptionPane.showMessageDialog(null, "Error: Input not numeric.\nPlease only input numeric values.", "Missing Information", JOptionPane.ERROR_MESSAGE);
        	}
        	else {
        		DefaultTableModel model = (DefaultTableModel) table1.getModel();
        	
        		if (model.getRowCount() == 0) {
            		dayArray = Formula.MakeDayZero(Double.valueOf(textPopulation.getText()), Double.valueOf(textInfected.getText()), Double.valueOf(textImmune.getText()));
            		model.addRow(dayArray);
            	}
            	else {
            		dayArray = Formula.MakeNextDay(dayArray, Double.valueOf(textRate.getText()), Double.valueOf(textPopulation.getText()), Double.valueOf(textDuration.getText()),Double.valueOf(textDie.getText()));
            		model.addRow(dayArray);
            	}
        	}
        }
        
        if (e.getSource() == button30) {
        	if(emptyFlag) {
        		JOptionPane.showMessageDialog(null, "Error: One or more fields are blank.\nPlease fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
        	}
        	else if(boundsFlag) {
        		JOptionPane.showMessageDialog(null, "Error: Data entry out of bounds.", "Out of Bounds", JOptionPane.ERROR_MESSAGE);
        	}
        	else if(numberFlag) {
        		JOptionPane.showMessageDialog(null, "Error: Input not numeric.\nPlease only input numeric values.", "Missing Information", JOptionPane.ERROR_MESSAGE);
        	}
        	else{
        		DefaultTableModel model = (DefaultTableModel) table1.getModel();
        	
        		model.setRowCount(0);
            	dayArray = Formula.MakeDayZero(Double.valueOf(textPopulation.getText()), Double.valueOf(textInfected.getText()), Double.valueOf(textImmune.getText()));
        		model.addRow(dayArray);
            	for(int i=0; i<30 ;i++) {
            		dayArray = Formula.MakeNextDay(dayArray, Double.valueOf(textRate.getText()), Double.valueOf(textPopulation.getText()), Double.valueOf(textDuration.getText()),Double.valueOf(textDie.getText()));
            		model.addRow(dayArray);
            	}
        	}

        }
        
        if (e.getSource() == buttonReset) {
            
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            days = 1;
        }
            
        }
        
    public static void main(String[] args) {
        new DiseaseSim();   
    }       
}
