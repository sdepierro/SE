import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Formula {
	
	public static Vector<Double> MakeDayZero(double vInitPop, double vNumInfectious, double vPercentImm){
		Vector<Double> initDay = new Vector<>();
		double susceptible = vInitPop-vNumInfectious-vInitPop*vPercentImm/100;
		double immune = vInitPop*vPercentImm/100;
		
		initDay.add(new Double(0));
		initDay.add(new Double(Math.round(susceptible)));
		initDay.add(new Double(vNumInfectious));
		initDay.add(new Double(Math.round(immune)));
		initDay.add(new Double(0));
		initDay.add(new Double(vInitPop));
		
		return initDay;        
		}
    
    public static Vector<Double> MakeNextDay(Vector<Double> before, double vRateTrans, double vInitPop, double vLengthInf, double vPercentDie){
    	Vector<Double> nextDay = new Vector<>();
    	
    	double Sb = before.get(1);
		double Ib = before.get(2);
		double Imb = before.get(3);
		double Deb = before.get(4);
		
		nextDay.add(before.get(0) + 1); //day number
		
		double sNext = Sb-(vRateTrans*Sb*Ib/vInitPop);
		if (sNext > 0) {
			nextDay.add(new Double(Math.round(sNext))); //susceptible  
		}
		else {
			nextDay.add(0.0);
		}
		
		double iNext = Ib+(vRateTrans*Sb*Ib/vInitPop - Ib/vLengthInf - Ib*vPercentDie/(100*vLengthInf));
		if (iNext > 0) {
			nextDay.add(new Double(Math.round(iNext))); //infected
		}
		else {
			nextDay.add(0.0);
		}
		
		double imNext = Imb+(Ib/vLengthInf);
		if (imNext > 0) {
			nextDay.add(new Double(Math.round(imNext))); //immune
		}
		else {
			nextDay.add(0.0);
		}
		
		double dNext = vPercentDie*Ib/(100*vLengthInf)+Deb;
		if (dNext > 0) {
			nextDay.add(new Double(Math.round(dNext))); //dead
		}
		else {
			nextDay.add(0.0);
		}
		
		double pop = nextDay.get(1) + nextDay.get(2) + nextDay.get(3);
		if (pop > 0) {
			nextDay.add(pop);
		}
		else {
			nextDay.add(0.0);
		}
//		System.out.println("Sb: " + Sb);
//		System.out.println("Ib: " + Ib);
//		System.out.println("Imb: " + Imb);
//		System.out.println("Deb: " + Deb);
//		System.out.println("vRateTrans: " + vRateTrans);
//		System.out.println("vInitPop: " + vInitPop);
//		System.out.println("vLengthInf: " + vLengthInf);
//		System.out.println("vPercentDie: " + vPercentDie);

    	return nextDay;
    }
    
    public static Vector<Double> getRowAt(DefaultTableModel model, int num){
    	Vector<Double> row = new Vector<>();
    	
    	for(int i = 0; i < model.getColumnCount(); i++) {
    		row.add((Double) model.getValueAt(num, i));
    	}
    	
    	
    	return row;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        }
        catch(NumberFormatException e) {
        	return false;
        }
        return true;
    }
    
    public static boolean isFloat(String s) {
    	try { 
            Float.parseFloat(s); 
        }
        catch(NumberFormatException e) {
        	return false;
        }
        return true;
    }
    
    public static boolean isNum(String s) {
    	if(Formula.isFloat(s) || Formula.isInteger(s)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static void main(String[] args) {
        
        DefaultTableModel model = new DefaultTableModel();
        String[] columnNames = {"Day", "# Susceptible", "# Infected",
        		"# Immune", "# Dead", "Total Population"};
        model.setColumnIdentifiers(columnNames);
        
        Vector<Double> row1 = new Vector<>();
        row1.add(0.0); row1.add(94995.0); row1.add(5.0); row1.add(5000.0); 
        row1.add(0.0); row1.add(100000.0);
        Vector<Double> row2 = new Vector<>();
        row2.add(1.0); row2.add(94996.0); row2.add(6.0); row2.add(6000.0); row2.add(1.0); row2.add(100006.0);
        
        model.addRow(row1);
        model.addRow(row2);
        
        
        //System.out.println(model.getValueAt(1, 1));
        System.out.println(getRowAt(model, 1));
        //System.out.println(row1);
        //System.out.println(MakeNextDay(row1, 5.0, 1000000.0, 5.0, 5.0));
    }

}
