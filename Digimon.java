import java.util.Objects;

public final class Digimon {
    private final String nombre;
    private final Tipo tipo;
    private final int ataque;
    private final int defensa;
    private final Digievolucion digievolucion;

    public Digimon(String nombre, Tipo tipo, int ataque, int defensa, Digievolucion digievolucion) {
        if (nombre == null || nombre.trim().isEmpty() || ataque < 0 || defensa < 0) {
            throw new IllegalArgumentException("Digimon inválido.");
        }
        this.nombre = nombre.trim();
        this.tipo = Objects.requireNonNull(tipo);
        this.ataque = ataque;
        this.defensa = defensa;
        this.digievolucion = Objects.requireNonNull(digievolucion);
    }

    public String getNombre() { return nombre; }
    public Tipo getTipo() { return tipo; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public Digievolucion getDigievolucion() { return digievolucion; }
}
