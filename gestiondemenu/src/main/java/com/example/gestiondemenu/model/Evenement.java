package com.example.gestiondemenu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Created by DevProsper on 10/01/2018.
 */

public class Evenement implements Serializable{

    private Date date;
    private String description;
    private String url;

    private static final String [] images = new String[]{
        "Url des images ici",
            "deuxième",
            "Troisième",
            "Quartième"
    };

    public Evenement(Date date, String description) {
        this.date = date;
        this.description = description;

        //Une url aleatoire du tableau
        int aleatoire = new Random().nextInt(images.length);
        url = images[aleatoire];
    }

    public Date getDate()  {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
