package fr.iutfbleau.SAE31_2024_LTA.Model;

import fr.iutfbleau.SAE31_2024_LTA.Model.Bdd.ModelBDD;
import fr.iutfbleau.SAE31_2024_LTA.Vue.VuePrincipale;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ModelPrincipale {
    private final MediaPlayerManager mediaPlayerManager;
    private final ModelBDD bdd;

    private final ModelPartieJouer modelPartieJouer;
    private final ModelMenu modelMenu;
    private ModelJeux modelJeux;

    private final VuePrincipale vuePrincipale;

    private Clip menuMusicClip;
    private Clip clicAudioClip;

    private String playerName;
    private int selectedSeed;

    private Image logo;


    public ModelPrincipale() {
        bdd = new ModelBDD();

        mediaPlayerManager = new MediaPlayerManager(menuMusicClip, clicAudioClip);
        loadMedia();
        vuePrincipale = createView();

        modelMenu = new ModelMenu(this);
        modelPartieJouer = new ModelPartieJouer(this);

        vuePrincipale.setVisible(true);
    }

    private void loadMedia() {
        try {
            URL logoUrl = getClass().getResource("/Images/logo.png");
            if (logoUrl != null) {
                Image logo = ImageIO.read(logoUrl);

            } else {
                System.err.println("Logo non trouvé : /Images/Logo.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream menuMusicStream = getClass().getResourceAsStream("/Audio/MenuSoundTrack.wav");
            if (menuMusicStream == null) {
                throw new IllegalArgumentException("Le fichier audio MenuSoundTrack.wav est introuvable.");
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = menuMusicStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.toByteArray());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            menuMusicClip = AudioSystem.getClip();
            menuMusicClip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la musique du menu : " + e.getMessage());
            System.exit(1);
        }

        try {
            InputStream clicAudioStream = getClass().getResourceAsStream("/Audio/buttonClic.wav");
            if (clicAudioStream == null) {
                throw new IllegalArgumentException("Le fichier audio buttonClic.wav est introuvable.");
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = clicAudioStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.toByteArray());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            clicAudioClip = AudioSystem.getClip();
            clicAudioClip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du son de clic : " + e.getMessage());
            System.exit(1);
        }
    }

    private VuePrincipale createView(){
        return new VuePrincipale(this);
    }

    public VuePrincipale getVuePrincipale() {
        return vuePrincipale;
    }

    public MediaPlayerManager getMediaPlayerManager() {
        return mediaPlayerManager;
    }

    public ModelBDD getBdd() {
        return this.bdd;
    }

    public ModelMenu getModelMenu() {
        return modelMenu;
    }

    public int getSelectedSeed() {
        return selectedSeed;
    }

    public void setSelectedSeed(int seed) {
        selectedSeed = seed;
        this.createJeux();
    }

    private void createJeux(){
        this.modelJeux = new ModelJeux(this, selectedSeed);
    }

    public ModelJeux getModelJeux() {
        return modelJeux;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Image getLogo() {
        return logo;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ModelPartieJouer getModelPartieJouer() {
        return modelPartieJouer;
    }
}
