package ApplicazioneRazionale;

//import java.math.BigInteger;

import razionale.Razionale;
import razionale.RazionaleBig;


public class AppRazionale {   
    
    public static void main(String[] args) {
        System.out.println("Inizio main..\n................\n");
        
        Razionale[] v = new Razionale[4];
        RazionaleBig[] b = new RazionaleBig[4];
        
        for(int i=0; i<3; ++i) {
            v[i] = new Razionale(i, 5);
            b[i] = new RazionaleBig(Integer.toString(i) + "/5");
        }
        
        
        
        Razionale risultato = v[1].prodotto(v[1]);
        System.out.println(risultato);
        RazionaleBig bigRisultato = b[1].prodotto(b[1]);
        System.out.println(bigRisultato);
        
        System.out.println("\n................\nFine main..");
    }
}