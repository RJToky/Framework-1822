package model;

import etu1822.framework.ModelView;
import etu1822.framework.annotation.Url;
import etu1822.framework.annotation.Scope;
import etu1822.framework.utility.FileUpload;

@Scope("singleton")
public class Emp {
    int id;
    String nom;
    int age;
    FileUpload cv;

    public Emp() {
    }

    public Emp(int id, String nom, int age) {
        this.setId(id);
        this.setNom(nom);
        this.setAge(age);
    }

    @Url("/liste.do")
    public ModelView liste() {
        ModelView modelView = new ModelView();

        Emp[] allEmp = new Emp[3];
        allEmp[0] = new Emp(1, "Toky", 19);
        allEmp[1] = new Emp(2, "Niaina", 17);
        allEmp[2] = new Emp(3, "Judichael", 18);

        modelView.setView("liste.jsp");
        modelView.addItem("allEmp", allEmp);
        return modelView;
    }

    @Url("/form-add.do")
    public ModelView formAdd() {
        ModelView modelView = new ModelView();

        modelView.setView("formulaire.jsp");
        return modelView;
    }

    @Url("/add.do")
    public ModelView add() {
        ModelView modelView = new ModelView();
        Emp[] allEmp = new Emp[1];
        allEmp[0] = new Emp(1, this.nom, this.age);

        modelView.setView("liste.jsp");
        modelView.addItem("allEmp", allEmp);
        return modelView;
    }

    @Url("/form-search.do")
    public ModelView formSearch() {
        ModelView modelView = new ModelView();

        modelView.setView("recherche.jsp");
        return modelView;
    }

    @Url("/search.do")
    public ModelView findById(int id) {
        ModelView modelView = new ModelView();

        Emp[] allEmp = new Emp[3];
        allEmp[0] = new Emp(1, "Toky", 19);
        allEmp[1] = new Emp(2, "Niaina", 17);
        allEmp[2] = new Emp(3, "Judichael", 18);

        for (Emp emp : allEmp) {
            if (emp.getId() == id) {
                Emp[] temp = new Emp[1];
                temp[0] = emp;
                modelView.addItem("allEmp", temp);
                break;
            }
        }
        modelView.setView("liste.jsp");
        return modelView;
    }

    @Url("/form-upload.do")
    public ModelView formUpload() {
        return new ModelView("upload.jsp");
    }

    @Url("/upload.do")
    public ModelView upload() {
        System.out.println(this.getCv().getName() + " " + this.getCv().getFile());
        return formUpload();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FileUpload getCv() {
        return cv;
    }

    public void setCv(FileUpload cv) {
        this.cv = cv;
    }

}
