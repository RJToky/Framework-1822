# Framework-1822

Framework-1822 est un framework Java conçu pour simplifier le processus de développement d'applications web en utilisant le modèle de conception MVC.

## Installation

Avant d'installer le framework, assurez-vous d'avoir les éléments suivants :

- Java 17 ou une version supérieure installée sur votre système.
- Servlet 3.0 ou une version ultérieure.
- Tomcat 10 ou une version ultérieure pour le déploiement de votre application web.

Suivez les étapes ci-dessous pour installer le framework :

1. Assurez-vous d'avoir les dépendances requises : Java 17, Servlet 3.0 et Tomcat 10.
2. Téléchargez le framework-1822 depuis le dépôt GitHub.
3. Importez le framework-1822 dans votre projet Web Java.
4. Configurez votre projet pour utiliser les bibliothèques Servlet 3.0 et Tomcat 10.
5. Compilez votre projet pour vous assurer que toutes les dépendances sont satisfaites.

## Fonctionnalités principales

- **Class ModelView** : Fournit une classe `ModelView` qui permet de définir la vue et les données à rendre. Cette classe facilite la communication entre le contrôleur et la vue en regroupant les données nécessaires pour le rendu.

- **Annotation Url** : Utilisez l'annotation `@Url` pour annoter les fonctions de contrôleur. Cela permet de définir les URL correspondantes aux actions du contrôleur, simplifiant ainsi la configuration des routes.

- **Formulaire** : Le framework simplifie la création de formulaires en fournissant des outils intégrés. Il offre des méthodes et des classes pour générer automatiquement les éléments de formulaire et gérer les validations.

- **Class FileUpload** : Si vous souhaitez permettre aux utilisateurs de télécharger des fichiers, le framework propose une classe `FileUpload` pour faciliter la gestion de ces fichiers. Elle fournit des méthodes pour manipuler les fichiers téléchargés et les sauvegarder sur le serveur.

- **Facilité de prise des paramètres de requête** : Le framework simplifie la récupération des paramètres de requête en fournissant des méthodes pour extraire facilement les paramètres GET et POST. Il offre également des fonctionnalités pour valider et convertir automatiquement les valeurs des paramètres.

- **Annotation Scope** : L'annotation `@Scope` permet d'optimiser l'instanciation des classes en évitant de créer une nouvelle instance à chaque appel. Vous pouvez définir la portée des instances de classe en utilisant des annotations, telles que `@Scope("singleton")` pour une instance unique.

## Exemple

- **Class ModelView et Annotation Url :**
```java
@Url("/liste.do")
public ModelView liste() {
    ModelView modelView = new ModelView();

    Emp[] allEmp = new Emp[2];
    allEmp[0] = new Emp("Toky", 19);
    allEmp[1] = new Emp("Judichael", 18);

    modelView.setView("liste.jsp");
    modelView.addItem("allEmp", allEmp);
    return modelView;
}
```
```jsp
<% Emp[] allEmp = (Emp[]) request.getAttribute("allEmp"); %>
```

- **Formulaire :**
```html
<form action="./add.do" method="get">
  <input type="text" name="nom" placeholder="nom" />
  <input type="number" name="age" placeholder="age" />
  <input type="submit" value="valider" />
</form>
```
```java
@Url("/add.do")
public ModelView add() {
    System.out.println(this.nom + " " + this.age);
}
```

- **Class FileUpload :**
```html
<form action="./upload.do" method="post" enctype="multipart/form-data">
  <input type="file" name="cv" />
  <input type="submit" value="Valider" />
</form>
```
```java
@Url("/upload.do")
public ModelView upload() {
    System.out.println(this.cv.getName() + " " + this.cv.getFile());
}
```

- **Prise des paramètres de requête :**
```html
<a href="./detail.do?id=1">Voir</a>
```
```java
@Url("/detail.do")
public ModelView detail(int id) {
  System.out.println(id);
}
```

- **Annotation Scope :**
```java
@Scope("singleton")
public class Emp {
    int id;
    String nom;
    int age;
    FileUpload cv;
}
```

## Auteur

Framework-1822 a été créé par Judichaël RAKOTOARIVONY.

## Remerciements

Je souhaite exprimer mes sincères remerciements aux personnes suivantes pour leur précieuse contribution à ce projet :

- **Mr Naina** : Chef de projet et vecteur principal du Framework, dont l'expertise et la vision ont permis de réaliser cette réalisation.

- **Les élèves de la Promotion 15 de l'IT University** : Leur dévouement, leurs idées et leur collaboration ont grandement contribué au développement et à l'amélioration continue du Framework.

Je tiens également à remercier tous ceux qui ont participé de près ou de loin à ce projet et ont apporté leur soutien. Votre contribution a été précieuse et a joué un rôle important dans la réussite de ce projet.

