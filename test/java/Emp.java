import etu1822.framework.ModelView;
import etu1822.framework.annotation.Url;

public class Emp {

    @Url("accueil")
    public ModelView getIndex() {
        return new ModelView("accueil.jsp");
    }
}
