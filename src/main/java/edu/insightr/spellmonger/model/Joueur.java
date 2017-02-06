package edu.insightr.spellmonger.model;

import javafx.beans.property.SimpleStringProperty;

public class Joueur implements Comparable{

    private final SimpleStringProperty Login;
    private final SimpleStringProperty NbPlay;
    private final SimpleStringProperty PercentageScore;

    public Joueur(String login, double nbPlay, double PercentageScore) {
        this.Login = new SimpleStringProperty(login);
        this.NbPlay = new SimpleStringProperty((int)nbPlay+"");
        this.PercentageScore = new SimpleStringProperty(PercentageScore+"");
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Joueur){
            Joueur oo = (Joueur) o;
            if(Double.parseDouble(this.getNbPlay()) > Double.parseDouble(oo.getNbPlay())) return 1;
            else if(Double.parseDouble(this.getNbPlay()) < Double.parseDouble(oo.getNbPlay())) return -1;
            else return 0;
        }
        return -2;
    }

    public String getLogin() {
        return Login.get();
    }

    public SimpleStringProperty loginProperty() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login.set(login);
    }

    public String getNbPlay() {
        return NbPlay.get();
    }

    public SimpleStringProperty nbPlayProperty() {
        return NbPlay;
    }

    public void setNbPlay(String nbPlay) {
        this.NbPlay.set(nbPlay);
    }

    public String getPercentageScore() {
        return PercentageScore.get();
    }

    public SimpleStringProperty PercentageScoreProperty() {
        return PercentageScore;
    }

    public void setPercentageScore(String PercentageScore) {
        this.PercentageScore.set(PercentageScore);
    }
}
