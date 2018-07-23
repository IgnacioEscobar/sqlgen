package sqlgen;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private static Repositorio ourInstance = new Repositorio();

    public static Repositorio getInstance() {
        return ourInstance;
    }

    private List<Articulo> articulos;

    private Repositorio() {
        articulos = new ArrayList<Articulo>();
    }

    public void agregarArticulo(Articulo articulo){
        articulos.add(articulo);
    }

    public List<Articulo> articulos(){
        return articulos;
    }
}
