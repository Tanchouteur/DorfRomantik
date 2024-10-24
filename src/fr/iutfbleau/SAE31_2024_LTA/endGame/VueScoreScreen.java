package fr.iutfbleau.SAE31_2024_LTA.endGame;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.menu.ControllerMenuCard;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.*;

public class VueScoreScreen extends JPanel {
    private JPanel sidebarPanel;
    private final ModelPrincipale modelPrincipale;
    private final int widthSidebar, heightSidebar;
    private JButton saveBddButton;

    public VueScoreScreen(ModelPrincipale modelPrincipale) {
        widthSidebar = 450;
        heightSidebar = 700;
        setLayout(new BorderLayout());
        this.modelPrincipale = modelPrincipale;
        initSidebarComponent();
        add(sidebarPanel, BorderLayout.CENTER);
    }

    private void initSidebarComponent() {
        int buttonFontSize = 30;

        sidebarPanel = new JPanel(new GridBagLayout());
        sidebarPanel.setBackground(getPanelColor());
        sidebarPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.gridx = 0;

        sidebarPanel.add(setStyleImageTitre(), gbc);

        JLabel globalScoreLabel;
        if (modelPrincipale.getBdd().getBestScoreSeed(modelPrincipale.getSelectedSeed()) != null){
             globalScoreLabel = new JLabel("Global best : "+modelPrincipale.getBdd().getBestScoreSeed(modelPrincipale.getSelectedSeed()) +" Points");
        }else {
             globalScoreLabel = new JLabel("Indisponible");
        }

        gbc.gridy = 1;
        sidebarPanel.add(setStyleLabelScore(globalScoreLabel, 30), gbc);

        JLabel scoreLabel = new JLabel("Score : "+modelPrincipale.getModelJeux().getScore() +" Points");
        gbc.gridy = 2;
        sidebarPanel.add(setStyleLabelScore(scoreLabel, 32), gbc);

        saveBddButton = new JButton();
        if (modelPrincipale.getBdd().updateBdd()) {
            saveBddButton.setText("Sauvegarde en cours...");
        }else {
            saveBddButton.setText("Hors Ligne...");
        }
        saveBddButton.setEnabled(false);
        new ControllerSaveGame(modelPrincipale, this);

        gbc.gridy = 3;
        sidebarPanel.add(setStyleButton(saveBddButton,buttonFontSize), gbc);

        JButton menuButton = new JButton("Menu");

        menuButton.addActionListener(new ControllerMenuCard(modelPrincipale));
        gbc.gridy = 4;
        sidebarPanel.add(setStyleButton(menuButton,buttonFontSize), gbc);

        JButton settingsButton = new JButton("Paramètres");

        settingsButton.addActionListener(modelPrincipale.getControllerInputMap());

        gbc.gridy = 5;
        sidebarPanel.add(setStyleButton(settingsButton,buttonFontSize), gbc);

        JButton quitButton = new JButton("Quitter");

        quitButton.addActionListener(e -> System.exit(0));

        gbc.gridy = 6;
        sidebarPanel.add(setStyleButton(quitButton,buttonFontSize), gbc);
    }

    public int getWidthSidebar() {
        return widthSidebar;
    }

    public int getHeightSidebar() {
        return heightSidebar;
    }

    public JButton getSaveBddButton(){
        return this.saveBddButton;
    }
}
