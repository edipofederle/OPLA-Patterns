package br.ufpr.inf.opla.patterns.designpatterns;

import arquitetura.representation.Class;
import arquitetura.representation.Concern;
import arquitetura.representation.Element;
import arquitetura.representation.Interface;
import br.ufpr.inf.opla.patterns.models.Scope;
import br.ufpr.inf.opla.patterns.models.ps.impl.PSMediator;
import br.ufpr.inf.opla.patterns.util.ElementUtil;
import br.ufpr.inf.opla.patterns.util.MediatorUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.CollectionUtils;

public class Mediator extends DesignPattern {

    private static volatile Mediator INSTANCE;

    public static synchronized Mediator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mediator();
        }
        return INSTANCE;
    }

    private Mediator() {
        super("Mediator", "Behavioral");
    }

    @Override
    public boolean verifyPS(Scope scope) {
        boolean isPs = false;

        List<Element> elements = scope.getElements();
        HashMap<Concern, List<Element>> groupedElements = ElementUtil.groupElementsByConcern(elements);
        for (Map.Entry<Concern, List<Element>> entry : groupedElements.entrySet()) {
            Concern concern = entry.getKey();
            List<Element> list = entry.getValue();
            if (concern != null) {
                List<Element> chainOfElements = ElementUtil.getChainOfRelatedElementsWithSameConcern(list, concern);
                if (chainOfElements.size() > 2) {
                    PSMediator psMediator = new PSMediator(list, concern);
                    if (!scope.getPSs().contains(psMediator)) {
                        scope.addPS(psMediator);
                    } else {
                        psMediator = (PSMediator) scope.getPSs().get(scope.getPSs().indexOf(psMediator));
                        psMediator.setParticipants(new ArrayList<>(CollectionUtils.union(list, psMediator.getParticipants())));
                    }
                    isPs = true;
                }
            }
        }

        return isPs;
    }

    @Override
    public boolean verifyPSPLA(Scope scope) {
        return verifyPS(scope);
    }

    @Override
    public boolean apply(Scope scope) {
        boolean applied = false;
        if (!scope.getPSs(this).isEmpty()) {
            try {
                PSMediator psMediator = (PSMediator) scope.getPSs(this).get(0);

                // Identificar ou criar classe EventOfInterest
                Class eventOfInterest = MediatorUtil.getOrCreateEventOfInterestClass();

                // "" interface Mediator
                Interface mediatorInterface = MediatorUtil.getOrCreateMediatorInterface(psMediator.getParticipants(), psMediator.getConcern());
                psMediator.getParticipants().remove(mediatorInterface);

                // "" classe Mediator
                // "" interfaces colleagues
                // Implementar Mediator
                // Criar método para Mediator
                // Implementar Colleague
                // Criar método para Colleagues
                // Usar interface Mediator
                // Usar colleagues
                // Aplicar Estereótipo
            } catch (Exception ex) {
                Logger.getLogger(Mediator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return applied;
    }

}
