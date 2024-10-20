package fr.iutfbleau.SAE31_2024_LTA.jeux;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ControllerMouseWheelDecalage implements MouseWheelListener {
    ModelPrincipale modelPrincipale;
    ControllerMouseWheelDecalage(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {//WheelListener
        int wheelRotation = e.getWheelRotation();
        modelPrincipale.getModelJeux().getListTuiles().getFirst().decalage(wheelRotation,modelPrincipale.getModelJeux().getVueJeux());
    }
}