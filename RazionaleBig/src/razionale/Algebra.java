package razionale;

public interface Algebra {

    // operazioni matematiche..
    /**
     * Effettua l'addizione dell'elemnento corrente con l'oggeto passato come
     * parametro e fornisce in nuovo oggetto della gerarchia Razionale come risultato
     *
     * @param razionale addendo per l'operazione di addizione
     * @return nuovo oggetto risultato dell'addizione
     */
    Algebra addizione(Algebra razionale);

    /**
     * genera un nuovo Razionale che sara la divisione tra:
     * "questo Razionale" e "il Razionale passato"
     * @param razionale
     * @return nuovo razzionale frutto della divisione
     */
    Algebra divisione(Algebra razionale);

    /**
     * @return un nuovo razionale con lo stesso valore del razionale
     *         da cui è stato chiamato il metodo con il segno invertito
     */
    Algebra opposto();

    /**
     * effettua ripetute volte la moltiplicazione tra: "questo"
     * per n volte quante esp
     *
     * @param esp
     * @return nuovo Razionale frutto della potenza
     */
    Algebra potenza(int esp);

    /**
     *  genera un nuovo Razionale che sara la moltiplicazione tra:
     *  "questo" e "quello passato"
     * @param razionale
     * @return nuovo Razionale frutto della moltiplicazione
     */
    Algebra prodotto(Algebra razionale);

    /**
     * @return un nuovo razionale con numeratore e denominatore invertiti
     *         rispetto a quiello da cui è stato chiamato
     */
    Algebra reciproco();

    /**
     * Effettua la sottrazione tra i razionali: "questo" e "quello passato per parametro"
     *
     * @param razionale
     * @return un nuovo Razionale risultante dalla sottrazione
     */
    Algebra sottrazione(Algebra razionale);
    
}
