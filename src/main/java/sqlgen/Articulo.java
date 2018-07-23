package sqlgen;

public class Articulo {
    private String codigo;
    private String descripcion;
    private String unidadMagentica;
    private Double precio;
    private Boolean importado;

    public Articulo(String codigo, String descripcion, String unidadMagentica, Double precio, Boolean importado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadMagentica = unidadMagentica;
        this.precio = precio;
        this.importado = importado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidadMagentica() {
        return unidadMagentica;
    }

    public Double getPrecio() {
        return precio;
    }

    public Boolean getImportado() {
        return importado;
    }
}
