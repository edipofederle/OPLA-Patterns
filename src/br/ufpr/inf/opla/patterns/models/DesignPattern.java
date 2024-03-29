package br.ufpr.inf.opla.patterns.models;

import br.ufpr.inf.opla.patterns.designpatterns.Bridge;
import br.ufpr.inf.opla.patterns.designpatterns.Facade;
import br.ufpr.inf.opla.patterns.designpatterns.Mediator;
import br.ufpr.inf.opla.patterns.designpatterns.Strategy;
import java.util.Objects;
import java.util.Random;

public abstract class DesignPattern {

    public static final DesignPattern[] FEASIBLE = new DesignPattern[]{
        Strategy.getInstance(),
        Bridge.getInstance(),
        Facade.getInstance(),
        Mediator.getInstance()
    };
    
    public static final DesignPattern[] IMPLEMENTED = new DesignPattern[]{
        Strategy.getInstance(),
        Bridge.getInstance()
    };
    
    private final String name;
    private final String category;
    private final Random random;

    public DesignPattern(String name, String category) {
        this.random = new Random();
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean randomlyVerifyAsPSOrPSPLA(Scope scope) {
        double PLAProbability = random.nextDouble();
        if (random.nextDouble() < PLAProbability) {
            return verifyPSPLA(scope);
        } else {
            return verifyPS(scope);
        }
    }

    public abstract boolean verifyPS(Scope scope);

    public abstract boolean verifyPSPLA(Scope scope);

    public abstract boolean apply(Scope scope);

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DesignPattern other = (DesignPattern) obj;
        return Objects.equals(this.name, other.name);
    }

}
