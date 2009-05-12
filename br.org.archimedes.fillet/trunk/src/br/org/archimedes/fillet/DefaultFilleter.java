package br.org.archimedes.fillet;

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;


public class DefaultFilleter implements Filleter {

    public MacroCommand fillet (Element e1, Point click, Element e2, Point click2) {

        // TODO Auto-generated method stub
        System.out.println("Called DefaultFilleter.fillet");
        return null;
    }

}
