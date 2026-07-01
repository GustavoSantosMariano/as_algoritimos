/**
 * Representa um jogador da partida.
 */
public class Jogador {
    public enum Personagem {
        ESPECULADOR, // Recebe +20% no salário ao completar volta, paga +10% de imposto
        NEGOCIANTE,  // Paga 10% a menos de aluguel para outros jogadores
        ADVOGADO,    // Pode sair da prisão de graça uma vez por jogo
        CONSTRUTOR   // Imóveis comprados por ele têm o aluguel base aumentado em 15%
    }

    private String nome;
    private double saldo;
    private Casa posicaoAtual;
    private Casa posicaoAnterior;
    private ListaEncadeada<Casa> propriedades;
    private Personagem personagem;
    private int voltasCompletas;

    // Prisão
    private boolean preso;
    private int tentativasPrisao;
    private boolean isencaoFiancaUsada;

    // Deque para Desfazer (Undo) - armazena as últimas 3 transações financeiras
    private Deque<UndoAction> historicoTransacoes;
    private int undosRealizadosNestaRodada;

    public Jogador(String nome, double saldoInicial, Personagem personagem) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.personagem = personagem;
        this.propriedades = new ListaEncadeada<>();
        this.posicaoAtual = null;
        this.posicaoAnterior = null;
        this.voltasCompletas = 0;
        this.preso = false;
        this.tentativasPrisao = 0;
        this.isencaoFiancaUsada = false;
        this.historicoTransacoes = new Deque<>();
        this.undosRealizadosNestaRodada = 0;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionarSaldo(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        }
    }

    public void subtrairSaldo(double valor) {
        if (valor > 0) {
            this.saldo -= valor;
        }
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Casa getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Casa posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Casa getPosicaoAnterior() {
        return posicaoAnterior;
    }

    public void setPosicaoAnterior(Casa posicaoAnterior) {
        this.posicaoAnterior = posicaoAnterior;
    }

    public ListaEncadeada<Casa> getPropriedades() {
        return propriedades;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public int getVoltasCompletas() {
        return voltasCompletas;
    }

    public void setVoltasCompletas(int voltasCompletas) {
        this.voltasCompletas = voltasCompletas;
    }

    public void incrementarVoltas() {
        this.voltasCompletas++;
    }

    public boolean isPreso() {
        return preso;
    }

    public void setPreso(boolean preso) {
        this.preso = preso;
        if (!preso) {
            this.tentativasPrisao = 0;
        }
    }

    public int getTentativasPrisao() {
        return tentativasPrisao;
    }

    public void incrementarTentativasPrisao() {
        this.tentativasPrisao++;
    }

    public boolean isIsencaoFiancaUsada() {
        return isencaoFiancaUsada;
    }

    public void setIsencaoFiancaUsada(boolean isencaoFiancaUsada) {
        this.isencaoFiancaUsada = isencaoFiancaUsada;
    }

    public int getUndosRealizadosNestaRodada() {
        return undosRealizadosNestaRodada;
    }

    public void resetUndosRodada() {
        this.undosRealizadosNestaRodada = 0;
    }

    public void incrementarUndosRodada() {
        this.undosRealizadosNestaRodada++;
    }

    /**
     * Calcula o patrimônio total do jogador (Saldo + Valor de compra dos imóveis).
     */
    public double calcularPatrimonioTotal() {
        double patrimonio = saldo;
        ListaEncadeada.Node<Casa> current = propriedades.getHead();
        while (current != null) {
            patrimonio += current.data.getPrecoCompra();
            current = current.next;
        }
        return patrimonio;
    }

    /**
     * Adiciona uma transação financeira ao histórico de Undo, mantendo apenas as 3 últimas.
     */
    public void registrarTransacao(UndoAction transacao) {
        historicoTransacoes.addLast(transacao);
        if (historicoTransacoes.size() > 3) {
            historicoTransacoes.removeFirst(); // Descarta a mais antiga (primeira inserida)
        }
    }

    /**
     * Tenta desfazer a última transação financeira.
     */
    public boolean desfazerUltimaTransacao() {
        if (undosRealizadosNestaRodada >= 1) {
            System.out.println("Erro: Você já realizou uma ação de desfazer (Undo) nesta rodada!");
            return false;
        }
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Erro: Nenhuma transação recente para desfazer!");
            return false;
        }
        UndoAction ultima = historicoTransacoes.removeLast();
        boolean sucesso = ultima.desfazer();
        if (sucesso) {
            undosRealizadosNestaRodada++;
        } else {
            // Se falhou por algum motivo (ex: o imóvel foi repassado), podemos retornar ao deque ou limpar
            System.out.println("Erro ao tentar reverter transação.");
        }
        return sucesso;
    }

    public Deque<UndoAction> getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" [").append(personagem).append("]\n");
        sb.append("  Saldo: R$ ").append(String.format("%.2f", saldo)).append("\n");
        sb.append("  Patrimônio Total: R$ ").append(String.format("%.2f", calcularPatrimonioTotal())).append("\n");
        sb.append("  Posição: ").append(posicaoAtual != null ? posicaoAtual.getNome() : "Sem Posição").append("\n");
        sb.append("  Voltas Completas: ").append(voltasCompletas).append("\n");
        sb.append("  Estado: ").append(preso ? "Preso (Tentativa " + tentativasPrisao + "/3)" : "Livre").append("\n");
        sb.append("  Propriedades (").append(propriedades.size()).append("): ");
        if (propriedades.isEmpty()) {
            sb.append("Nenhuma");
        } else {
            ListaEncadeada.Node<Casa> current = propriedades.getHead();
            while (current != null) {
                sb.append(current.data.getNome());
                if (current.data.isHipotecado()) {
                    sb.append(" [H]");
                }
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
        }
        return sb.toString();
    }
}
