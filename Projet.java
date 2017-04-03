package Pakage;

//import java.util.function.*;
import java.lang.Math;
//import java.util.Scanner;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public class cul {

	static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final static int SW = g.getWidth();
	final static int SH = g.getHeight();
	final static int DELAY = 2000;
	final static int TITLE_DELAY = 1000;

	static void displayTacho(NXTRegulatedMotor m)
	{
		g.clear();
		while(m.isMoving())
		{
			g.clear();
			g.drawString("Position: " + m.getTachoCount(), SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);            
			g.refresh();
		}
		Button.waitForAnyPress(DELAY);
		g.clear();
	}

	static void motors()
	{
		g.setFont(Font.getLargeFont());
		g.drawString("Motors", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
		Button.waitForAnyPress(TITLE_DELAY);
		g.clear();
		g.setFont(Font.getDefaultFont());
		NXTRegulatedMotor ma = Motor.A;
		NXTRegulatedMotor mb = Motor.B;

		int l1 = 9; //longueur des bras
		int l2 = 7;

		//System.out.println("X, Y ?");

		/*Scanner sc = new Scanner(System.in);
    int x, y;

    	x = sc.nextInt();
    	y = sc.nextInt();	*/
		
		//position en (X,Y) du bras, rentrable aussi avec un scanner
		int x=10;
		int y = 6;
		int gain = 1; //facteur multiplicateur des vitesses angulaire

		double alpha = Math.atan2(y, x); //calcul preliminaire pour les angles


		//angles des deux moteurs pour atteindre la position en (X,Y)
		double teta1 = alpha - Math.acos((x^2+y^2+l1^2+l2^2)/(2*l1*Math.sqrt(x^2+y^2)));
		double teta2 = Math.PI - Math.acos((l1^2+l2^2-x^2-y^2)/(2*l1*l2));

		ma.resetTachoCount();
		mb.resetTachoCount();
		
		//boucle de déplacement 
		do{
			int k1 = (int)teta1-ma.getTachoCount();
			int k2 = (int)teta2-mb.getTachoCount();
			ma.setSpeed(Math.abs(k1*gain));
			mb.setSpeed(Math.abs(k2*gain));
			//test d'orientation
			if (k1 >0)
			{ma.forward();}
			else {ma.backward();}
			if (k2 >0)
			{mb.forward();}
			else {mb.backward();}

		} while ( Math.abs(ma.getTachoCount()-teta1) != 0 && Math.abs(mb.getTachoCount()-teta2) != 0 && Button.ENTER.isUp());


// TP's précédents
		/* ma.resetTachoCount();
    ma.setAcceleration(600);
    ma.setSpeed(500);
    mb.resetTachoCount();
    mb.setAcceleration(600);
    mb.setSpeed(500);
    g.drawString("Forward 720", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
    Button.waitForAnyPress(DELAY);
    ma.rotate(720, true);
    mb.rotate(650, true);
    displayTacho(ma);
    displayTacho(mb);
    g.drawString("Backward 720", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
    Button.waitForAnyPress(DELAY);
    ma.rotate(-720, true);
    displayTacho(ma);
    ma.setSpeed(80);
    g.drawString("Slow Forward 360", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
    Button.waitForAnyPress(DELAY);
    ma.rotate(360, true);
    displayTacho(ma);
    ma.setSpeed(800);
    g.drawString("Fast Backward 360", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
    Button.waitForAnyPress(DELAY);
    ma.rotate(-360, true);
    displayTacho(ma);        */
	}



	public static void main(String[] options)
	{

		cul.motors();

	}
}
