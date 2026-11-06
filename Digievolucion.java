import java.util.Objects;
import java.util.Random;

public final class Digievolucion {
    private final String nombre;
    private final TipoEfecto efecto;
    private final int magnitud;
    private final int probabilidad;

    public Digievolucion(String nombre, TipoEfecto efecto, int magnitud, int probabilidad) {
        if (nombre == null || nombre.trim().isEmpty() || magnitud <= 0
                || probabilidad < 0 || probabilidad > 100) {
            throw new IllegalArgumentException("Habilidad inválida.");
        }
        this.nombre = nombre.trim();
        this.efecto = Objects.requireNonNull(efecto);
        this.magnitud = magnitud;
        this.probabilidad = probabilidad;
    }

    public boolean intentarActivar(Random azar) {
        return azar.nextInt(100) < probabilidad;
    }

    public String getNombre() { return nombre; }
    public TipoEfecto getEfecto() { return efecto; }
    public int getMagnitud() { return magnitud; }
    public int getProbabilidad() { return probabilidad; }
}
