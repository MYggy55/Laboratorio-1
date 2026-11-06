public enum Tipo {
    FUEGO, AGUA, PLANTA, ELECTRICO;

    public int modificadorContra(Tipo rival) {
        if (venceA(rival)) return 20;
        if (rival.venceA(this)) return -10;
        return 0;
    }

    private boolean venceA(Tipo rival) {
        return (this == FUEGO && rival == PLANTA)
            || (this == PLANTA && rival == AGUA)
            || (this == AGUA && rival == FUEGO)
            || (this == ELECTRICO && rival == AGUA);
    }
}
