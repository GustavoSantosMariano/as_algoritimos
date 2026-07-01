/**
 * Representa um registro de rodada no histórico do jogo.
 */
public class RegistroHistorico {
    private int rodada;
    private String jogador;
    private String dados;
    private String casaDestino;
    private String efeito;

    public RegistroHistorico(int rodada, String jogador, String dados, String casaDestino, String efeito) {
        this.rodada = rodada;
        this.jogador = jogador;
        this.dados = dados;
        this.casaDestino = casaDestino;
        this.efeito = efeito;
    }

    public int getRodada() {
        return rodada;
    }

    public String getJogador() {
        return jogador;
    }

    public String getDados() {
        return dados;
    }

    public String getCasaDestino() {
        return casaDestino;
    }

    public String getEfeito() {
        return efeito;
    }

    @Override
    public String toString() {
        return String.format("Rodada %d | Jogador: %-10s | Dados: %-5s | Destino: %-25s | Efeito: %s",
            rodada, jogador, dados, casaDestino, efeito);
    }
}
