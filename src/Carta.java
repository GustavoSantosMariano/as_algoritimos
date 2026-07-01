/**
 * Representa uma carta do baralho de Sorte/Revés.
 */
public class Carta {
    public enum TipoEfeito {
        RECEBER_BANCO,          // Recebe R$ X do banco
        AVANCAR_CASAS,          // Avança N casas
        AVANCAR_INICIO,         // Avança diretamente para o Início
        RECEBER_OUTROS,         // Todos os outros jogadores pagam R$ X para você
        PAGAR_BANCO,            // Paga R$ X ao banco
        PAGAR_OUTROS,           // Paga R$ X para cada um dos outros jogadores
        IR_PRISAO,              // Vai diretamente para a prisão
        VOLTAR_CASAS,           // Volta N casas
        VOLTAR_CASA_ANTERIOR    // Volta para a última casa em que estava
    }

    private String descricao;
    private TipoEfeito efeito;
    private double valor; // Pode ser dinheiro ou número de casas

    public Carta(String descricao, TipoEfeito efeito, double valor) {
        this.descricao = descricao;
        this.efeito = efeito;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoEfeito getEfeito() {
        return efeito;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Carta: " + descricao;
    }
}
