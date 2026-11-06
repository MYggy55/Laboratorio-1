import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("=== BATALLA DIGIMON: CUATRO RONDAS ===");
            List<Digimon> catalogo = crearCatalogo();
            Entrenador j1 = crearEntrenador(entrada, catalogo, 1);
            Entrenador j2 = crearEntrenador(entrada, catalogo, 2);
            Batalla batalla = new Batalla(j1, j2, new Random());
            while (!batalla.haTerminado()) {
                System.out.println("\n--- Ronda " + (batalla.getRondasJugadas() + 1) + " ---");
                int i1 = pedirDigimon(entrada, j1);
                boolean h1 = leerEntero(entrada, "Acción: 1 = atacar, 2 = intentar habilidad: ", 1, 2) == 2;
                int i2 = pedirDigimon(entrada, j2);
                boolean h2 = leerEntero(entrada, "Acción: 1 = atacar, 2 = intentar habilidad: ", 1, 2) == 2;
                ResultadoRonda r = batalla.jugarRonda(i1, h1, i2, h2);
                System.out.println(j1.getNombre() + " usa " + j1.getEquipo().get(i1).getNombre()
                    + ": ataqueTotal = " + r.getTotal1() + ". " + estadoHabilidad(h1, r.isActivada1()));
                System.out.println(j2.getNombre() + " usa " + j2.getEquipo().get(i2).getNombre()
                    + ": ataqueTotal = " + r.getTotal2() + ". " + estadoHabilidad(h2, r.isActivada2()));
                System.out.println(r.getGanador() == 0 ? "Ronda empatada."
                    : "Gana la ronda: " + (r.getGanador() == 1 ? j1.getNombre() : j2.getNombre()));
                System.out.println("Marcador: " + batalla.getVictorias1() + " - " + batalla.getVictorias2());
            }
            System.out.println("\n=== RESULTADO FINAL ===");
            System.out.println(batalla.getGanador() == 0 ? "La partida terminó en empate."
                : "Entrenador ganador: " + (batalla.getGanador() == 1 ? j1.getNombre() : j2.getNombre()));
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Entrada finalizada. La partida se cerró antes de completarse.");
        }
    }

    private static List<Digimon> crearCatalogo() {
        List<Digimon> catalogo = new ArrayList<>();
        catalogo.add(new Digimon("Agumon", Tipo.FUEGO, 60, 25, new Digievolucion("Adult", TipoEfecto.ATAQUE, 15, 30)));
        catalogo.add(new Digimon("Guilmon", Tipo.FUEGO, 65, 20, new Digievolucion("Ultimate", TipoEfecto.REDUCIR_RIVAL, 10, 40)));
        catalogo.add(new Digimon("Gomamon", Tipo.AGUA, 55, 30, new Digievolucion("Mega", TipoEfecto.DEFENSA, 20, 30)));
        catalogo.add(new Digimon("Betamon", Tipo.AGUA, 58, 27, new Digievolucion("Adult", TipoEfecto.ATAQUE, 15, 35)));
        catalogo.add(new Digimon("Palmon", Tipo.PLANTA, 57, 28, new Digievolucion("Ultimate", TipoEfecto.REDUCIR_RIVAL, 10, 40)));
        catalogo.add(new Digimon("Floramon", Tipo.PLANTA, 52, 33, new Digievolucion("Mega", TipoEfecto.DEFENSA, 20, 30)));
        catalogo.add(new Digimon("Tentomon", Tipo.ELECTRICO, 62, 23, new Digievolucion("Adult", TipoEfecto.ATAQUE, 15, 30)));
        catalogo.add(new Digimon("Elecmon", Tipo.ELECTRICO, 59, 26, new Digievolucion("Mega", TipoEfecto.DEFENSA, 20, 35)));
        return catalogo;
    }

    private static Entrenador crearEntrenador(Scanner entrada, List<Digimon> catalogo, int numero) {
        System.out.println("\nNombre del entrenador " + numero + ":");
        String nombre = entrada.nextLine().trim();
        while (nombre.isEmpty()) {
            System.out.println("Escribe un nombre no vacío:");
            nombre = entrada.nextLine().trim();
        }
        System.out.println("Catálogo (tipos y estadísticas definidos para este laboratorio):");
        for (int i = 0; i < catalogo.size(); i++) {
            Digimon d = catalogo.get(i);
            Digievolucion h = d.getDigievolucion();
            System.out.println((i + 1) + ". " + d.getNombre() + " / " + d.getTipo()
                + " / ATQ " + d.getAtaque() + " / DEF " + d.getDefensa()
                + " / " + h.getNombre() + " " + h.getEfecto() + " " + h.getMagnitud()
                + " / " + h.getProbabilidad() + "%");
        }
        List<Digimon> equipo = new ArrayList<>();
        while (equipo.size() < 4) {
            int indice = leerEntero(entrada, "Elige Digimon " + (equipo.size() + 1) + " de 4: ", 1, catalogo.size()) - 1;
            if (equipo.contains(catalogo.get(indice))) {
                System.out.println("Ya elegiste ese Digimon; selecciona otro.");
            } else {
                equipo.add(catalogo.get(indice));
            }
        }
        return new Entrenador(nombre, equipo);
    }

    private static int pedirDigimon(Scanner entrada, Entrenador entrenador) {
        System.out.println("\nTurno de " + entrenador.getNombre() + ". Digimon disponibles:");
        for (int i = 0; i < entrenador.getEquipo().size(); i++) {
            if (entrenador.estaDisponible(i)) {
                Digimon d = entrenador.getEquipo().get(i);
                System.out.println((i + 1) + ". " + d.getNombre() + " (" + d.getTipo() + ")");
            }
        }
        int indice = leerEntero(entrada, "Selecciona una opción: ", 1, 4) - 1;
        while (!entrenador.estaDisponible(indice)) {
            System.out.println("Ese Digimon ya participó.");
            indice = leerEntero(entrada, "Selecciona uno disponible: ", 1, 4) - 1;
        }
        return indice;
    }

    private static int leerEntero(Scanner entrada, String mensaje, int minimo, int maximo) {
        Integer valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            String linea = entrada.nextLine();
            try {
                int candidato = Integer.parseInt(linea.trim());
                if (candidato >= minimo && candidato <= maximo) valor = candidato;
                else System.out.println("Ingresa un número entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            }
        }
        return valor;
    }

    private static String estadoHabilidad(boolean intento, boolean activada) {
        return !intento ? "Ataque normal." : (activada ? "Habilidad activada." : "La habilidad no se activó.");
    }
}
