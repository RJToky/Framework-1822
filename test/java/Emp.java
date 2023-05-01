package model;

import etu1822.framework.ModelView;
import etu1822.framework.annotation.Url;

public class Emp {
    String nom;
    int age;

    public Emp() {
    }

    public Emp(String nom, int age) {
        this.setNom(nom);
        this.setAge(age);
    }

    @Url("/liste")
    public ModelView liste() {
        ModelView modelView = new ModelView();

        Emp[] allEmp = new Emp[3];
        allEmp[0] = new Emp("Toky", 19);
        allEmp[1] = new Emp("Niaina", 17);
        allEmp[2] = new Emp("Judichael", 18);

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
        Emp[] allEmp = new Emp[1];
        allEmp[0] = new Emp(this.nom, this.age);

        modelView.setView("liste.jsp");
        modelView.addItem("allEmp", allEmp);
        return modelView;
    }

    public String getNom() {
        return nom;
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
