package br.ufpr.inf.opla.patterns.util;

import arquitetura.exceptions.ConcernNotFoundException;
import arquitetura.exceptions.PackageNotFound;
import arquitetura.helpers.UtilResources;
import arquitetura.representation.Architecture;
import arquitetura.representation.Concern;
import arquitetura.representation.Element;
import arquitetura.representation.Interface;
import arquitetura.representation.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceUtil {

    private InterfaceUtil() {
    }

    public static Interface createInterfaceForSetOfElements(String interfaceName, List<Element> participants) {
        Interface anInterface = null;
        if (participants != null && !participants.isEmpty()) {
            try {

                arquitetura.representation.Package aPackage = null;
                Architecture architecture = participants.get(0).getArchitecture();

                List<Element> tempElements;

                String namespace = ElementUtil.getNameSpace(participants);
                String packageName = UtilResources.extractPackageName(namespace);

                boolean naArquitetura = packageName.equalsIgnoreCase("model");
                if (naArquitetura) {
                    anInterface = architecture.createInterface(interfaceName);
                    architecture.removeInterface(anInterface);

                    tempElements = Collections.unmodifiableList(new ArrayList<>(architecture.getElements()));
                } else {
                    aPackage = architecture.findPackageByName(UtilResources.extractPackageName(namespace));

                    anInterface = aPackage.createInterface(interfaceName);
                    aPackage.removeInterface(anInterface);

                    tempElements = Collections.unmodifiableList(new ArrayList<>(aPackage.getElements()));
                }
                List<Method> methodsFromSetOfElements = MethodUtil.createMethodsFromSetOfElements(participants);
                for (Method method : methodsFromSetOfElements) {
                    anInterface.addExternalOperation(method);
                }

                for (Concern concern : ElementUtil.getOwnAndMethodsConcerns(participants)) {
                    if (!anInterface.containsConcern(concern)) {
                        try {
                            anInterface.addConcern(concern.getName());
                        } catch (ConcernNotFoundException ex) {
                            Logger.getLogger(InterfaceUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                anInterface.setNamespace(namespace);

                int count = 1;
                String name = anInterface.getName();
                while (tempElements.contains(anInterface)) {
                    count++;
                    anInterface.setName(name + Integer.toString(count));
                }

                if (naArquitetura) {
                    architecture.addExternalInterface(anInterface);
                } else if (aPackage != null) {
                    aPackage.addExternalInterface(anInterface);
                }
            } catch (PackageNotFound ex) {
                Logger.getLogger(InterfaceUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return anInterface;
    }
}
