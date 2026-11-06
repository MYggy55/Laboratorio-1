import java.util.Objects;
import java.util.Random;

public final class Batalla {
    public static final int RONDAS = 4;
    private final Entrenador jugador1;
    private final Entrenador jugador2;
    private final Random azar;
    private int rondasJugadas;
    private int victorias1;
    private int victorias2;

    public Batalla(Entrenador jugador1, Entrenador jugador2, Random azar) {
        this.jugador1 = Objects.requireNonNull(jugador1);
        this.jugador2 = Objects.requireNonNull(jugador2);
        this.azar = Objects.requireNonNull(azar);
        if (jugador1 == jugador2) throw new IllegalArgumentException("Se requieren dos entrenadores.");
    }

    public ResultadoRonda jugarRonda(int indice1, boolean habilidad1, int indice2, boolean habilidad2) {
        if (haTerminado()) throw new IllegalStateException("La partida ya terminó.");
        if (!jugador1.estaDisponible(indice1) || !jugador2.estaDisponible(indice2)) {
            throw new IllegalArgumentException("Seleccione Digimon disponibles.");
        }
        Digimon d1 = jugador1.elegir(indice1);
        Digimon d2 = jugador2.elegir(indice2);
        boolean activa1 = activar(jugador1, d1, habilidad1);
        boolean activa2 = activar(jugador2, d2, habilidad2);
        int total1 = calcularPoder(d1, d2, jugador1, jugador2);
        int total2 = calcularPoder(d2, d1, jugador2, jugador1);
        rondasJugadas++;
        ResultadoRonda resultado = new ResultadoRonda(rondasJugadas, total1, total2, activa1, activa2);
        if (resultado.getGanador() == 1) victorias1++;
        if (resultado.getGanador() == 2) victorias2++;
        jugador1.finalizarRonda();
        jugador2.finalizarRonda();
        return resultado;
    }

    private boolean activar(Entrenador entrenador, Digimon digimon, boolean intentar) {
        boolean activada = intentar && digimon.getDigievolucion().intentarActivar(azar);
        if (activada) entrenador.agregarEfecto(digimon.getDigievolucion());
        return activada;
    }

    private int calcularPoder(Digimon propio, Digimon rival, Entrenador aliado, Entrenador enemigo) {
        return propio.getAtaque() + propio.getTipo().modificadorContra(rival.getTipo())
            + aliado.bonificacion(TipoEfecto.ATAQUE) - rival.getDefensa()
            - enemigo.bonificacion(TipoEfecto.DEFENSA)
            - enemigo.bonificacion(TipoEfecto.REDUCIR_RIVAL);
    }

    public boolean haTerminado() { return rondasJugadas == RONDAS; }
    public int getRondasJugadas() { return rondasJugadas; }
    public int getVictorias1() { return victorias1; }
    public int getVictorias2() { return victorias2; }
    public int getGanador() {
        if (!haTerminado()) throw new IllegalStateException("Todavía hay rondas pendientes.");
        return victorias1 == victorias2 ? 0 : (victorias1 > victorias2 ? 1 : 2);
    }
}
