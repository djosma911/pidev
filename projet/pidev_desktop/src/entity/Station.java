package entity;

public class Station {
    private int id_station;
    private String lang_alt;
    private int ligne_id; // Nouvelle propriété pour représenter la relation avec Ligne

    public Station(int id_station, String lang_alt) {
        this.id_station = id_station;
        this.lang_alt = lang_alt;
    }

    public Station(int id_station, String lang_alt, int ligne_id) {
        this.id_station = id_station;
        this.lang_alt = lang_alt;
        this.ligne_id = ligne_id;
    }

    public Station(String lang_alt, int ligne_id) {
    this.lang_alt = lang_alt;
    this.ligne_id = ligne_id;
}


    public Station(String lang_alt) {
        this.lang_alt = lang_alt;
    }

    public int getId_station() {
        return id_station;
    }

    public String getLang_alt() {
        return lang_alt;
    }

    public int getLigne_id() { // Nouvelle méthode getter pour ligne_id
        return ligne_id;
    }

    public void setId_station(int id_station) {
        this.id_station = id_station;
    }

    public void setLang_alt(String lang_alt) {
        this.lang_alt = lang_alt;
    }

    public void setLigne_id(int ligne_id) { // Nouvelle méthode setter pour ligne_id
        this.ligne_id = ligne_id;
    }

    @Override
    public String toString() {
        return "Station{" + "id_station=" + id_station + ", lang_alt=" + lang_alt + ", ligne_id=" + ligne_id + '}';
    }
}
