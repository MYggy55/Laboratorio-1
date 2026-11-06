public final class EfectoActivo {
    private final Digievolucion habilidad;
    private int rondasRestantes;

    public EfectoActivo(Digievolucion habilidad) {
        this.habilidad = java.util.Objects.requireNonNull(habilidad);
        this.rondasRestantes = 2;
    }

    public int aporte(TipoEfecto tipo) {
        return rondasRestantes > 0 && habilidad.getEfecto() == tipo ? habilidad.getMagnitud() : 0;
    }

    public void consumirRonda() {
        if (rondasRestantes > 0) rondasRestantes--;
    }

    public boolean haExpirado() { return rondasRestantes == 0; }
}
