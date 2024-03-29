package br.ufpr.inf.opla.patterns.designpatterns;

import br.ufpr.inf.opla.patterns.models.DesignPattern;
import br.ufpr.inf.opla.patterns.models.Scope;

public class Mediator extends DesignPattern {

    private static volatile Mediator INSTANCE;

    private Mediator() {
        super("Mediator", "Behavioral");
    }

    public synchronized static Mediator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mediator();
        }
        return INSTANCE;
    }

    @Override
    public boolean verifyPS(Scope scope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verifyPSPLA(Scope scope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apply(Scope scope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
