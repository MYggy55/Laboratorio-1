import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Entrenador {
    private final String nombre;
    private final List<Digimon> equipo;
    private final Set<Integer> usados;
    private final List<EfectoActivo> efectos;

    public Entrenador(String nombre, List<Digimon> equipo) {
        if (nombre == null || nombre.trim().isEmpty() || equipo == null || equipo.size() != 4) {
            throw new IllegalArgumentException("Se requiere nombre y cuatro Digimon.");
        }
        Set<String> nombres = new HashSet<>();
        for (Digimon d : equipo) {
            if (d == null || !nombres.add(d.getNombre().toLowerCase(java.util.Locale.ROOT))) {
                throw new IllegalArgumentException("Los cuatro Digimon deben ser distintos.");
            }
        }
        this.nombre = nombre.trim();
        this.equipo = Collections.unmodifiableList(new ArrayList<>(equipo));
        this.usados = new HashSet<>();
        this.efectos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public List<Digimon> getEquipo() { return equipo; }

    public boolean estaDisponible(int indice) {
        return indice >= 0 && indice < equipo.size() && !usados.contains(indice);
    }

    public Digimon elegir(int indice) {
        if (!estaDisponible(indice)) throw new IllegalArgumentException("Selección no disponible.");
        usados.add(indice);
        return equipo.get(indice);
    }

    public void agregarEfecto(Digievolucion habilidad) {
        efectos.add(new EfectoActivo(habilidad));
    }

    public int bonificacion(TipoEfecto tipo) {
        int total = 0;
        for (EfectoActivo efecto : efectos) total += efecto.aporte(tipo);
        return total;
    }

    public void finalizarRonda() {
        for (EfectoActivo efecto : efectos) efecto.consumirRonda();
        efectos.removeIf(EfectoActivo::haExpirado);
    }
}
