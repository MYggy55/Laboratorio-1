public final class ResultadoRonda {
    private final int numero;
    private final int total1;
    private final int total2;
    private final boolean activada1;
    private final boolean activada2;

    public ResultadoRonda(int numero, int total1, int total2, boolean activada1, boolean activada2) {
        this.numero = numero;
        this.total1 = total1;
        this.total2 = total2;
        this.activada1 = activada1;
        this.activada2 = activada2;
    }

    public int getNumero() { return numero; }
    public int getTotal1() { return total1; }
    public int getTotal2() { return total2; }
    public boolean isActivada1() { return activada1; }
    public boolean isActivada2() { return activada2; }
    public int getGanador() { return total1 == total2 ? 0 : (total1 > total2 ? 1 : 2); }
}
