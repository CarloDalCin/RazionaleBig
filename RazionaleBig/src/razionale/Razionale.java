package razionale;

import java.util.Objects;


/**
 * la classe Razionale è una classe immutabile ecc..
*/

public class Razionale implements Algebra {
    private long num; 
    private long den; 
    private boolean negativo; // true=(-), false=(+)
    
    // costruttori..
    
    /** (costruttore generale)
     * trova il segno del Razionale, e assegna num e a den i corrispondeti valori positivi,
     * per poi semplificare la frazione
     * 
     * @param num
     * @param den 
     */
    public Razionale(long num, long den) {
        //escludere eventuali ecezioni dello 0;
        if(den==0){
            throw new ArithmeticException("ERROR: Division by 0");
        }
        
        this.negativo = (num<0 ^ den<0);
        this.num = (num<0 ? -num : num);
        this.den = (den<0 ? -den : den);
        this.semplifica();
    }
    
    /**
     * genera un numero Razionale con il valore di num che sara il numeratore
     * e il denominatore sara 1
     * 
     * @param num 
     */
    public Razionale(long num) {
        this(num,1);
    }
    
    /**
     * costruttore per copia, fa la copia di un gia esistente Razionale che gli viene indicato
     * 
     * @param razionale
     */
    public Razionale(Razionale razionale) {
        this((razionale.getSegno()? -1 : 1)*razionale.getNum(), razionale.getDen());
    }
    
    /**
     * costruttore di default,
     * numeratore   = 0
     * denominatore = 1
     */
    public Razionale(){
        this(0, 1);
    }
    
    /**
     * Generazione di un istanza di Razionale fornita una stringa col seguente formato:
     * "+/-num / +/-den"   oppure: "+/-num".
     * 
     * @param elemRaz 
     */ 
    public Razionale(String elemRaz) {
        elemRaz = elemRaz.trim();
        if(elemRaz==null || elemRaz.length()==0){
            throw new IllegalArgumentException("ERROR: string has to be not null and lenght >0");
        }
        String[] operandi = elemRaz.split("/");
        if(operandi.length >2){
            throw new IllegalArgumentException("ERROR: String cannot be converted into Razionali use \"long/long\"");
        }
        if(elemRaz.indexOf("/")>=0 && operandi.length==1){
            throw new IllegalArgumentException("ERROR: String cannot be converted into Razionele: use \"long/long\"");
        }
        long num = Long.parseLong(operandi[0]);
        //managment of sing..
        this.num = num<0? -num : num;
        if(operandi.length == 1){
            this.den = 1;
            this.negativo = num<0;
        }else{
            long den = Long.parseLong(operandi[1]);
            if(den==0){
                throw new ArithmeticException("ERROR: Division by 0");
            }
            this.negativo = (num<0 ^ den<0);
            this.den = den<0? -den : den;
            this.semplifica();
        }
    }
    
    // metodi..
    
    // query..
    
    /**
     * @return il numeratore
     */
    public long getNum(){
        return this.num;
    }
    
    /**
     * @return il denominatore
     */
    public long getDen(){
        return this.den;
    }
    
    /**
     * restituisce il valore del segno del razionale in booleano
     * 
     * @return ritorna true se il segno è '-'
     *         ritorna false se il segno è '+'
     */
    public boolean getSegno() {
        return negativo;
    }
    
    // operazioni matematiche..
    
    /**
     * Effettua l'addizione dell'elemnento corrente con l'oggeto passato come
     * parametro e fornisce in nuovo oggetto della gerarchia Razionale come risultato
     * 
     * @param razionale addendo per l'operazione di addizione
     * @return nuovo oggetto risultato dell'addizione
     */
    @Override
    public Razionale addizione(Algebra razionale){
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        Razionale r = (Razionale) razionale;
        long  denominatore = MCM(this.den, r.den);
        long numeratore = (this.negativo ? -1 : 1) * this.num * (denominatore / this.den) + (r.negativo ? -1 : 1) * r.num * (denominatore / r.den);
        return new Razionale(numeratore, denominatore);
    }
    
    /**
     * Effettua la sottrazione tra i razionali: "questo" e "quello passato per parametro"
     * 
     * @param razionale
     * @return un nuovo Razionale risultante dalla sottrazione
     */
    @Override
    public Razionale sottrazione(Algebra razionale) {
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        return this.addizione(razionale.opposto());
    }
    
    /**
     *  genera un nuovo Razionale che sara la moltiplicazione tra:
     *  "questo" e "quello passato"
     * @param razionale
     * @return nuovo Razionale frutto della moltiplicazione
     */
    @Override
    public Razionale prodotto(Algebra razionale){
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        Razionale r = (Razionale) razionale;
        long div1 = MCD(this.getDen(), r.getNum());
        long div2 = MCD(this.getNum(), r.getDen());
        boolean segno = this.getSegno() ^ r.getSegno();
        long num = (this.getNum()/div2) * (r.getNum()/div1);
        long den = (this.getDen()/div1) * (r.getDen()/div2);
        
        return new Razionale( (segno? -1 : 1)* num, den);
    }
    
    /**
     * genera un nuovo Razionale che sara la divisione tra:
     * "questo Razionale" e "il Razionale passato"
     * @param razionale
     * @return nuovo razzionale frutto della divisione
     */
    @Override
    public Razionale divisione(Algebra razionale){
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        return this.prodotto(razionale.reciproco());
    }
    
    /**
     * effettua ripetute volte la moltiplicazione tra: "questo"
     * per n volte quante esp
     * 
     * @param esp
     * @return nuovo Razionale frutto della potenza 
     */
    @Override
    public Razionale potenza(int esp) {
        /*
        avrei usato queto ma mi ci vuole più di un ora e mezza
        
        Razionale buffer = new Razionale(this);
        if(esp == 0)
            return new Razionale(1);
        else{
            for(int i=1; i< (esp<0? -esp:esp); ++i)
                buffer = new Razionale(buffer.prodotto(this));
            
            if(esp<0)
                buffer = buffer.reciproco();
        }
        
        return buffer;
        */        
        long numeratore = getNum();
        long denominatore = getDen();

        if(getSegno()){
            numeratore = -numeratore;
        }
        
        numeratore = (long)Math.pow(numeratore, esp);

        denominatore = (long)Math.pow(denominatore, esp);

        return new Razionale(numeratore, denominatore);
    }
    
    /**
     * @return un nuovo razionale con lo stesso valore del razionale 
     *         da cui è stato chiamato il metodo con il segno invertito
     */
    @Override
    public Razionale opposto(){
        return new Razionale( (this.getSegno()? 1 : -1)*this.getNum(), this.getDen());
    }
    
    /**
     * @return un nuovo razionale con numeratore e denominatore invertiti 
     *         rispetto a quiello da cui è stato chiamato
     */
    @Override
    public Razionale reciproco(){
        return new Razionale( (this.getSegno()? -1 : 1)*this.getDen(), this.getNum());
    }
    
    // operazioni
    
    /**
     * modifica i valori di "num" e "den" semplificandoli tra loro se possibile 
     */
    public void semplifica(){
        long mcd = MCD(this.getNum(), this.getDen());
        
        this.num /= mcd;
        this.den /= mcd;
    }
    
    /**
     * 
     * 
     * @param a
     * @param b
     * @return il Minimo Comune Multiplo
     */
    private static long MCM(long a, long b) {
        return (a * b) / MCD(a, b);
    }
    
    /**
     * algoritmo di Euclide
     * 
     * @param a
     * @param b
     * @return il Massimo Comune Divisore
     */
    private static long MCD(long a, long b){
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    /**
     * 
     * @return il valore del razionale espressa non più sotto forma di frazione ma in (double)
     */
    public double toDouble() {
        return (this.getSegno()? -1 : 1) * ( (double)this.getNum() / (double)this.getDen() ); // fuziona solo con questo casting (esplicito)
    }    
    
    // comparatori..
    
    /**
     * calcola l'hasCode tramite gli attributi: num, den, negativo
     * @return hasCode
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.num, this.den, this.negativo);
    }
    
    /**
     * compara i valori reali e determina quale è più grande, più piccolo
     * o se sono uguali
     * 
     * @param razionale
     * @return 1 se "questo" > "razionale"
     *         0 se "questo" == "razionale"
     *        -1 se "questo" < "razionale"
     */
    public int compareTo(Razionale razionale){
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        int comp;
        
        if(this == razionale || this.hashCode()== razionale.hashCode()){
            comp = 0;
        }else if(this.toDouble() > razionale.toDouble()){
            comp = 1;
        }else{
            comp = -1;
        }
        return comp;
    }

    /**
     * confronta due oggettiverifica  se sono la stessa istanza oppure se hanno
     * gli attributi uguali tramite l'hashCode
     * 
     * @param obj
     * @return vero se sono la stessa istanza oppure se hanno gli stessi attributi
     *         falso se obj non esiste o se sono di classi differenti
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) 
            return true;
        else if(obj == null || getClass() != obj.getClass()) 
            return false;
        
        final Razionale other = (Razionale) obj;
        
        return this.hashCode() == other.hashCode();
    }
    
    /**
     * @return la stringa di formato: "segno num/den"
     */
    @Override
    public String toString() {
        return  (negativo ? '-' : '+') 
                + Long.toString(num) 
                + '/' 
                + Long.toString(den);
    }
};
