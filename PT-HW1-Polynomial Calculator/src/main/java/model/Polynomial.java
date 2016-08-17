package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gliga on 3/1/2015.
 */


/**
 * Base class representing a polynomial.
 */
public class Polynomial {

    /**|
     * Every polynomial has a list of Terms, each term representing a monome
     */
    private List<Term> termList;

    public Polynomial() {
        this(new ArrayList<Term>());
    }

    public Polynomial(Term term) {
        this(new ArrayList<Term>());
        termList.add(term);
    }

    public Polynomial(List<Term> termList) {
        this.termList = termList;
        Collections.sort(termList);
    }

    public Polynomial(int i) {
        this.termList = new ArrayList<Term>();
        termList.add(new Term(i,0));
    }

    public List<Term> getTermList() {
        return termList;
    }

    public void setTermList(List<Term> termList) {
        this.termList = termList;
        Collections.sort(termList);
    }

    public int getSize() {
        return termList.size();
    }

    @Override
    public boolean equals(Object obj) {

        Polynomial otherPolynomial = (Polynomial) obj;

        return otherPolynomial.getTermList().equals(((Polynomial) obj).getTermList());

    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < termList.size(); i++) {
            Term term = termList.get(i);

            if (term.getCoefficient() < 0) {
                buffer.append(" - ");
            } else if (i != 0) {
                buffer.append(" + ");
            }

            if (term.getDegree()!=0 || (termList.size()==1 && term.getDegree()!=0) ) {
                buffer.append(term.toString());
            } else if (term.getCoefficient() % 1 == 0) {
                buffer.append(Math.abs((int) term.getCoefficient()));
            }else{
                buffer.append(Math.abs(term.getNormalizedCoefficient()));
            }

        }


        return buffer.toString();
    }

}
