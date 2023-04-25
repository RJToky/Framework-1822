package model;

import etu1822.framework.ModelView;
import etu1822.framework.annotation.Url;

public class Emp {
    int id;
    String nom;
    int age;

    public Emp() {
    }

    public Emp(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Emp(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }

    @Url("/liste")
    public ModelView liste() {
        ModelView modelView = new ModelView();

        Emp[] allEmp = new Emp[3];
        allEmp[0] = new Emp(1, "Toky");
        allEmp[1] = new Emp(2, "Niaina");
        allEmp[2] = new Emp(3, "Judichael");

        modelView.setView("liste.jsp");
        modelView.addItem("allEmp", allEmp);
        return modelView;
    }

    @Url("/formulaire")
    public ModelView formulaire() {
        ModelView modelView = new ModelView();

        modelView.setView("formulaire.jsp");
        return modelView;
    }

    @Url("/ajouter")
    public ModelView ajouter() {
        ModelView modelView = new ModelView();

        modelView.setView("liste.jsp");
        return modelView;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
