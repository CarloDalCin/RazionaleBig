package ApplicazioneRazionale;

//import java.math.BigInteger;

import razionale.Razionale;
import razionale.RazionaleBig;


public class AppRazionale {   
    
    public static void main(String[] args) {
        System.out.println("Inizio main..\n................\n");
        RazionaleBig r1 = new RazionaleBig("25/6");
	RazionaleBig r2 = new RazionaleBig("5/6");

	System.out.println(r1 + " " + r2);

	System.out.println(r1.addizione(r2));
        System.out.println("\n................\nFine main..");
    }
}
