package fr.iutfbleau.SAE31_2024_LTA.jeux;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerPoseTuile implements MouseListener {

    private final ModelJeux modelJeux;
    private final ModelTuile buttonTuile;

    private ModelTuile modeleTuilePreviewed;

    private boolean clicked = false;

    ControllerPoseTuile(ModelJeux modelJeux, ModelTuile buttonTuile) {
        this.modelJeux = modelJeux;
        this.buttonTuile = buttonTuile;

    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (clicked) {
            Object source = e.getSource();

            if (source instanceof VueTuile) {
                VueTuile btnHovered = (VueTuile) source;

                if (e.getButton() == MouseEvent.BUTTON1 && !modelJeux.getListTuiles().isEmpty()) {
                    modelJeux.playTuileSound(modelJeux.getListTuiles().getFirst().getSoundIndex());
                    modelJeux.getModelMatrice().deleteTuile(btnHovered.getModelTuile());
                    modelJeux.getModelMatrice().poseTuile(btnHovered.getX(), btnHovered.getY());
                    modelJeux.getVueJeux().updatePreviewTuileList();
                }

                if (e.getButton() == MouseEvent.BUTTON3 && modelJeux.isUndoActivate()) {
                    modelJeux.undoLastTuile();
                }
            }

            clicked = false;
            modelJeux.getVueJeux().unsetPreviewOnButton(modeleTuilePreviewed);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        clicked = true;

        Object source = e.getSource();
        if (source instanceof VueTuile btnHovered) {
            modeleTuilePreviewed = modelJeux.getVueJeux().setPreviewOnButton(btnHovered);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        clicked = false;
        modelJeux.getVueJeux().unsetPreviewOnButton(modeleTuilePreviewed);
    }
}
