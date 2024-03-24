package razionale;

import java.math.BigInteger;

public class RazionaleBig implements Algebra{
    private BigInteger num;
    private BigInteger den;
    private boolean negativo;
    
    // costruttori
    
    /**
     * costruttore per stringa
     * 
     * @param str 
     */
    public RazionaleBig(String str) {
        str = str.trim();
        if(str==null || str.length()==0){
            throw new IllegalArgumentException("ERROR: string has to be not null and lenght >0");
        }
        String[] operandi = str.split("/");
        if(operandi.length >2){
            throw new IllegalArgumentException("ERROR: String cannot be converted into Razionali use \"long/long\"");
        }
        if(str.indexOf("/")>=0 && operandi.length==1){
            throw new IllegalArgumentException("ERROR: String cannot be converted into Razionele: use \"long/long\"");
        }
        BigInteger num = new BigInteger(operandi[0]);
        //managment of sing..
        this.num = num.compareTo(BigInteger.ZERO)<0? num.negate() : num;
        if(operandi.length == 1){
            this.den = new BigInteger("1");
            this.negativo = num.compareTo(BigInteger.ZERO)<0;
        }else{
            BigInteger den = new BigInteger(operandi[1]);
            if(den.compareTo(BigInteger.ZERO)==0){
                throw new ArithmeticException("ERROR: Division by 0");
            }
            this.negativo = (num.compareTo(BigInteger.ZERO)<0 ^ den.compareTo(BigInteger.ZERO)<0);
            this.den = den.compareTo(BigInteger.ZERO)<0? den.negate() : den;
            this.semplifica();
        }
    }
    
    
    /**
     * costruttore generale
     * 
     * @param numAbs
     * @param denAbs
     * @param negativo 
     */
    public RazionaleBig(BigInteger numAbs, BigInteger denAbs, boolean negativo) {
        if(den.compareTo(BigInteger.ZERO) == 0){
            throw new ArithmeticException("ERROR: Division by 0");
        }
        this.num = (num.compareTo(BigInteger.ZERO)<0 ? num.negate() : num);
        this.den = (den.compareTo(BigInteger.ZERO)<0 ? den.negate() : den);
        this.semplifica();
    }
    
    /**
     * costruttore 
     * 
     * @param num
     * @param den 
     */
    public RazionaleBig(BigInteger num, BigInteger den) {
        this(num.abs(), den.abs(), (num.compareTo(BigInteger.ZERO)<0 ^ den.compareTo(BigInteger.ZERO)<0));
    }
    
    public RazionaleBig(BigInteger num) {
        this(num, BigInteger.ONE);
    }
    
    /**
     * costruttore per copia
     * 
     * @param r 
     */
    public RazionaleBig(RazionaleBig r) {
        this( r.num, r.den, r.negativo);
    }
    
    public RazionaleBig(int num, int den) {
        this(Integer.toString(num)+ "/" + Integer.toString(den));
    }
    
    public RazionaleBig(int num) {
        this(Long.toString(num));
    }
    
    public RazionaleBig(double num){
        this(Double.toString(num));
    }
    
    public RazionaleBig() {
        this(0, 1);
    }

    public RazionaleBig(Algebra r) {
	this((RazionaleBig) r);
    }
    
    /**
     * Effettua l'addizione dell'elemnento corrente con l'oggeto passato come
     * parametro e fornisce in nuovo oggetto della gerarchia RazionaleBig come risultato
     * 
     * @param razionale addendo per l'operazione di addizione
     * @return nuovo oggetto risultato dell'addizione
     */
    @Override
    public Algebra addizione(Algebra razionale) {
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        RazionaleBig r = (RazionaleBig) razionale;
        BigInteger denominatore = MCM(this.den, r.den);
        
        /** 
         * per vedere l'operazione semplificata vedere l'adizione della classe Razionale
         * (consigliabile)
         */ 
        BigInteger numeratore = ((this.negativo ? this.num.negate() : this.num)
                                .multiply(denominatore.divide(this.den))
                                .add(((r.negativo ? r.num.negate() : r.num))
                                .multiply(denominatore.divide(r.den))
        ));
        return (Algebra) new RazionaleBig(numeratore, denominatore);
    }
    
    @Override
    public Algebra sottrazione(Algebra r) {
        return (Algebra) new RazionaleBig(this.addizione(r.opposto()));
    }
    
    @Override
    public Algebra prodotto(Algebra razionale) {
        if(razionale == null){
            throw new IllegalArgumentException("ERROR: argument 'razionale' has to be not null");
        }
        
        RazionaleBig r = (RazionaleBig)razionale;
        BigInteger div1 = this.den.gcd(r.num);
        BigInteger div2 = this.num.gcd(r.den);
        boolean segno = this.negativo ^ r.negativo;
        BigInteger num = this.num.divide(div2).multiply(r.num.divide(div1));
        BigInteger den = this.den.divide(div1).multiply(r.den.divide(div2));
        
        return (Algebra) new RazionaleBig( (segno? num.negate() : num), den);
    }
    
    @Override
    public Algebra divisione(Algebra razionale) {
        return (Algebra) new RazionaleBig(this.prodotto(razionale.reciproco()));
    }
    
    @Override
    public Algebra potenza(int esp) {
	BigInteger numeratore, denominatore;

	numeratore = this.num.pow(esp);
	denominatore = this.den.pow(esp);

	return (Algebra) new RazionaleBig(numeratore, denominatore, this.negativo);
    }

    @Override
    public RazionaleBig opposto() {
        return new RazionaleBig( (this.negativo? this.den.negate() : this.den), this.num);
    }

    @Override
    public RazionaleBig reciproco() {
        return new RazionaleBig( (this.negativo? this.den.negate() : this.den), this.num);
    }

    public double toDouble() {
        //String strRaz = this.toRealAsString(...);
        //return Double.valueOf(strRaz);
        return 0; // .....
    }
    
    //comparazione
    public int compareTo(Object obj) {
        if(obj.getClass() != this.getClass())
            throw new ClassCastException("type of paramenter");
        
        //....
        
        return 0; // .....
    }
    
    public BigInteger getNum() {
        return this.num;
    }
    
    public BigInteger getDen() {
        return this.den;
    }
    
    public boolean getSegno() {
        return negativo;
    }
    
    private static BigInteger MCM(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b));
    }
    
    public void semplifica(){
        BigInteger mcd = this.num.gcd(this.den);
        
        this.num = this.num.divide(mcd);
        this.den = this.den.divide(mcd);
    }

    public String toString() {
        return (this.negativo? "-" : "+") 
              + this.num 
              + "/" 
              + this.den;
    }
}
