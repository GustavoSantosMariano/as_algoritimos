/**
 * Representa uma casa do tabuleiro. Funciona como um nó da Lista Circular Duplamente Ligada.
 */
public class Casa {
    public enum TipoCasa {
        INICIO,
        IMOVEL,
        IMPOSTO,
        RESTITUICAO,
        PRISAO,
        SORTE_REVES
    }

    private String nome;
    private TipoCasa tipo;
    private double precoCompra;
    private double aluguelBase;
    private double multiplicadorVisitas;
    private Jogador proprietario;
    private boolean hipotecado;

    // Ponteiros da lista duplamente ligada circular
    public Casa next;
    public Casa prev;

    public Casa(String nome, TipoCasa tipo) {
        this(nome, tipo, 0, 0);
    }

    public Casa(String nome, TipoCasa tipo, double precoCompra, double aluguelBase) {
        this.nome = nome;
        this.tipo = tipo;
        this.precoCompra = precoCompra;
        this.aluguelBase = aluguelBase;
        this.multiplicadorVisitas = 1.0;
        this.proprietario = null;
        this.hipotecado = false;
        this.next = null;
        this.prev = null;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCasa getTipo() {
        return tipo;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public double getMultiplicadorVisitas() {
        return multiplicadorVisitas;
    }

    public void incrementarMultiplicador() {
        if (tipo == TipoCasa.IMOVEL && multiplicadorVisitas < 2.0) {
            multiplicadorVisitas = Math.min(2.0, multiplicadorVisitas + 0.1);
        }
    }

    public void resetMultiplicador() {
        this.multiplicadorVisitas = 1.0;
    }

    public Jogador getProprietario() {
        return proprietario;
    }

    public void setProprietario(Jogador proprietario) {
        this.proprietario = proprietario;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    /**
     * Calcula o aluguel atual do imóvel, considerando o multiplicador de visitas,
     * se o proprietário for do tipo CONSTRUTOR (+15%) e se o imóvel está hipotecado (aluguel = 0).
     */
    public double calcularAluguelAtual() {
        if (tipo != TipoCasa.IMOVEL || hipotecado) {
            return 0;
        }
        double aluguel = aluguelBase * multiplicadorVisitas;
        if (proprietario != null && proprietario.getPersonagem() == Jogador.Personagem.CONSTRUTOR) {
            aluguel *= 1.15; // Construtor aumenta em 15% o aluguel base de seus imóveis
        }
        return aluguel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" (").append(tipo).append(")");
        if (tipo == TipoCasa.IMOVEL) {
            sb.append(" - Preço: R$ ").append(String.format("%.2f", precoCompra));
            sb.append(" - Aluguel Base: R$ ").append(String.format("%.2f", aluguelBase));
            if (proprietario != null) {
                sb.append(" - Dono: ").append(proprietario.getNome());
                if (hipotecado) {
                    sb.append(" [HIPOTECADO]");
                } else {
                    sb.append(" - Aluguel Atual: R$ ").append(String.format("%.2f", calcularAluguelAtual()));
                    sb.append(" (Mult: ").append(String.format("%.1fx", multiplicadorVisitas)).append(")");
                }
            } else {
                sb.append(" [Sem Dono] (Mult: ").append(String.format("%.1fx", multiplicadorVisitas)).append(")");
            }
        }
        return sb.toString();
    }
}
