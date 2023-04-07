package model;

import etu1822.framework.ModelView;
import etu1822.framework.annotation.Url;

public class Emp {
    int id;
    String nom;

    public Emp() {
    }

    public Emp(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Url("/accueil")
    public ModelView getIndex() {
        ModelView modelView = new ModelView();

        Emp[] allEmp = new Emp[3];
        allEmp[0] = new Emp(1, "Toky");
        allEmp[1] = new Emp(2, "Niaina");
        allEmp[2] = new Emp(3, "Judichael");

        modelView.setView("accueil.jsp");
        modelView.addItem("allEmp", allEmp);

        return modelView;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

}
