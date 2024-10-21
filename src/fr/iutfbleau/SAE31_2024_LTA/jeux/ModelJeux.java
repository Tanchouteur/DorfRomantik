package fr.iutfbleau.SAE31_2024_LTA.jeux;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.endGame.VueScoreScreen;

import java.awt.Point;
import java.util.*;

public class ModelJeux {
    private VueJeux vueJeux;
    private VueScoreScreen vueScoreScreen;

    private final ModelPrincipale modelPrincipale;
    private final ModelMatrice modelMatrice;
    private final ModelListePoche modelListePoche;

    private final LinkedList<ModelTuile> listTuiles;//Liste de tuile généré

    private boolean undoActivate = false;
    private boolean undo = false;
    private ModelTuile tuileUndoAble;

    private int score = 0;

    public ModelJeux(ModelPrincipale modelPrincipale, int seed) {
        this.modelPrincipale = modelPrincipale;
        this.modelListePoche = new ModelListePoche(this);
        this.modelMatrice = new ModelMatrice(this);
        listTuiles = new LinkedList<>();

        for (int i = 10; i >= 0; i--) {
            ModelTuile tuile = new ModelTuile(seed*i, false);
            listTuiles.add(tuile);
        }

        createView();

        vueJeux.updatePreviewTuileList();
        createButton();
    }

    private void createView(){
        this.vueJeux = new VueJeux(this);
        modelPrincipale.getVuePrincipale().add(vueJeux, "jeux");
    }

    public void createEndView(){
        this.vueScoreScreen = new VueScoreScreen(modelPrincipale);
    }

    public LinkedList<ModelTuile> getListTuiles() {

        return listTuiles;
    }

    public VueJeux getVueJeux() {
        return this.vueJeux;
    }

    public ModelMatrice getModelMatrice() {
        return this.modelMatrice;
    }

    public ModelPrincipale getModelPrincipale() {
        return this.modelPrincipale;
    }

    public void createButton() {
        List<Point> pointsToAdd = new ArrayList<>(); // Liste pour stocker les points à ajouter

        for (Map.Entry<Point, ModelTuile> entry : modelMatrice.getTuilesPartie().entrySet()) {
            Point point = entry.getKey();
            ModelTuile tuile = entry.getValue();

            if (tuile != null && !tuile.isButton()) {
                // Vérifie si chaque position est disponible et l'ajoute à la liste
                if (tryCreateButton(point.x - 1, point.y - 1)) {  // Nord-Ouest
                    pointsToAdd.add(new Point(point.x - 1, point.y - 1));
                }
                if (tryCreateButton(point.x - 2, point.y)) {      // Nord
                    pointsToAdd.add(new Point(point.x - 2, point.y));
                }
                if (tryCreateButton(point.x - 1, point.y + 1)) {  // Nord-Est
                    pointsToAdd.add(new Point(point.x - 1, point.y + 1));
                }
                if (tryCreateButton(point.x + 1, point.y - 1)) {  // Sud-Ouest
                    pointsToAdd.add(new Point(point.x + 1, point.y - 1));
                }
                if (tryCreateButton(point.x + 2, point.y)) {      // Sud
                    pointsToAdd.add(new Point(point.x + 2, point.y));
                }
                if (tryCreateButton(point.x + 1, point.y + 1)) {  // Sud-Est
                    pointsToAdd.add(new Point(point.x + 1, point.y + 1));
                }
            }
        }


        for (Point newPoint : pointsToAdd) {
            modelMatrice.poseeButton(newPoint.x, newPoint.y, new ModelTuile());
        }
    }

    private boolean tryCreateButton(int x, int y) {
        return !modelMatrice.isOccupied(x, y);
    }


    public void deleteButtons() {
        modelMatrice.getTuilesPartie().values().removeIf(ModelTuile::isButton);
        for (ModelTuile tuile : listTuiles) {
            if (tuile.isButton() && tuile.getVueTuile() != null) {
                vueJeux.remove(tuile.getVueTuile());
            }
        }

    }

    public void playTuileSound(int soundIndex) {
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClipsTuiles()[soundIndex], false);
    }

    public VueScoreScreen getVueScoreScreen() {
        return vueScoreScreen;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ModelListePoche getModelListePoche() {
        return modelListePoche;
    }

    public boolean isUndo() {
        return undo;
    }

    public void setUndo(boolean undo) {
        this.undo = undo;
    }

    public boolean isUndoActivate() {
        return undoActivate;
    }

    public void setUndoActivate(boolean undoActivate) {
        this.undoActivate = undoActivate;
    }

    public void setTuileUndoAble(ModelTuile tuileUndoAble) {
        this.tuileUndoAble = tuileUndoAble;
    }

    public void undoLastTuile() {
        if (!undo) {
            listTuiles.addFirst(tuileUndoAble);
            deleteButtons();
            modelMatrice.deleteTuile(tuileUndoAble);
            getVueJeux().updatePreviewTuileList();

            createButton();
            undo = true;
        }
    }
}
