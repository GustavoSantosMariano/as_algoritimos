/**
 * Representa uma ação financeira reversível (para o Deque de Undo).
 */
public class UndoAction {
    public enum TipoUndo {
        COMPRA_IMOVEL,
        PAGAMENTO_ALUGUEL,
        PAGAMENTO_IMPOSTO,
        RECEBIMENTO_RESTITUICAO,
        RECEBIMENTO_SALARIO,
        RECEBIMENTO_SORTE_REVES,
        PAGAMENTO_SORTE_REVES,
        HIPOTECA_IMOVEL,
        QUITACAO_HIPOTECA
    }

    private TipoUndo tipo;
    private Jogador jogador;
    private double valor;
    private Casa imovel; // Se a transação envolver um imóvel
    private Jogador receptorAluguel; // Para transferência de aluguel

    public UndoAction(TipoUndo tipo, Jogador jogador, double valor) {
        this.tipo = tipo;
        this.jogador = jogador;
        this.valor = valor;
    }

    public UndoAction(TipoUndo tipo, Jogador jogador, double valor, Casa imovel) {
        this.tipo = tipo;
        this.jogador = jogador;
        this.valor = valor;
        this.imovel = imovel;
    }

    public UndoAction(TipoUndo tipo, Jogador jogador, double valor, Jogador receptorAluguel) {
        this.tipo = tipo;
        this.jogador = jogador;
        this.valor = valor;
        this.receptorAluguel = receptorAluguel;
    }

    public TipoUndo getTipo() {
        return tipo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public double getValor() {
        return valor;
    }

    public Casa getImovel() {
        return imovel;
    }

    public Jogador getReceptorAluguel() {
        return receptorAluguel;
    }

    /**
     * Executa a reversão desta transação.
     */
    public boolean desfazer() {
        switch (tipo) {
            case COMPRA_IMOVEL:
                if (imovel != null && imovel.getProprietario() == jogador) {
                    jogador.adicionarSaldo(valor);
                    jogador.getPropriedades().remove(imovel);
                    imovel.setProprietario(null);
                    imovel.resetMultiplicador(); // Reseta multiplicador de visitas se desfeito
                    System.out.println("-> [UNDO] Compra de '" + imovel.getNome() + "' desfeita. R$ " + String.format("%.2f", valor) + " devolvidos a " + jogador.getNome() + ".");
                    return true;
                }
                break;

            case PAGAMENTO_ALUGUEL:
                if (receptorAluguel != null) {
                    jogador.adicionarSaldo(valor); // Jogador recebe o aluguel de volta
                    receptorAluguel.subtrairSaldo(valor); // Dono devolve o aluguel
                    System.out.println("-> [UNDO] Aluguel pago a " + receptorAluguel.getNome() + " desfeito. R$ " + String.format("%.2f", valor) + " transferidos de volta para " + jogador.getNome() + ".");
                    return true;
                }
                break;

            case PAGAMENTO_IMPOSTO:
                jogador.adicionarSaldo(valor);
                System.out.println("-> [UNDO] Pagamento de Imposto desfeito. R$ " + String.format("%.2f", valor) + " devolvidos a " + jogador.getNome() + ".");
                return true;

            case RECEBIMENTO_RESTITUICAO:
                jogador.subtrairSaldo(valor);
                System.out.println("-> [UNDO] Recebimento de Restituição desfeito. R$ " + String.format("%.2f", valor) + " removidos de " + jogador.getNome() + ".");
                return true;

            case RECEBIMENTO_SALARIO:
                jogador.subtrairSaldo(valor);
                jogador.setVoltasCompletas(Math.max(0, jogador.getVoltasCompletas() - 1));
                System.out.println("-> [UNDO] Recebimento de Salário desfeito. R$ " + String.format("%.2f", valor) + " removidos de " + jogador.getNome() + " e volta reduzida.");
                return true;

            case RECEBIMENTO_SORTE_REVES:
                jogador.subtrairSaldo(valor);
                System.out.println("-> [UNDO] Recebimento de Carta da Sorte desfeito. R$ " + String.format("%.2f", valor) + " removidos de " + jogador.getNome() + ".");
                return true;

            case PAGAMENTO_SORTE_REVES:
                jogador.adicionarSaldo(valor);
                System.out.println("-> [UNDO] Pagamento de Carta do Revés desfeito. R$ " + String.format("%.2f", valor) + " devolvidos a " + jogador.getNome() + ".");
                return true;

            case HIPOTECA_IMOVEL:
                if (imovel != null && imovel.isHipotecado() && imovel.getProprietario() == jogador) {
                    jogador.subtrairSaldo(valor); // Devolve o dinheiro recebido no empréstimo
                    imovel.setHipotecado(false);  // Remove hipoteca
                    System.out.println("-> [UNDO] Hipoteca de '" + imovel.getNome() + "' desfeita. R$ " + String.format("%.2f", valor) + " removidos de " + jogador.getNome() + ".");
                    return true;
                }
                break;

            case QUITACAO_HIPOTECA:
                if (imovel != null && !imovel.isHipotecado() && imovel.getProprietario() == jogador) {
                    jogador.adicionarSaldo(valor); // Recebe de volta o valor pago pela quitação
                    imovel.setHipotecado(true);   // Coloca em estado hipotecado novamente
                    System.out.println("-> [UNDO] Quitação de Hipoteca de '" + imovel.getNome() + "' desfeita. R$ " + String.format("%.2f", valor) + " devolvidos a " + jogador.getNome() + ".");
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public String toString() {
        switch (tipo) {
            case COMPRA_IMOVEL:
                return "Compra do imóvel " + imovel.getNome() + " por R$ " + String.format("%.2f", valor);
            case PAGAMENTO_ALUGUEL:
                return "Pagamento de aluguel de R$ " + String.format("%.2f", valor) + " para " + receptorAluguel.getNome();
            case PAGAMENTO_IMPOSTO:
                return "Pagamento de imposto de R$ " + String.format("%.2f", valor);
            case RECEBIMENTO_RESTITUICAO:
                return "Recebimento de restituição de R$ " + String.format("%.2f", valor);
            case RECEBIMENTO_SALARIO:
                return "Recebimento de salário de R$ " + String.format("%.2f", valor);
            case RECEBIMENTO_SORTE_REVES:
                return "Recebimento por Sorte/Revés de R$ " + String.format("%.2f", valor);
            case PAGAMENTO_SORTE_REVES:
                return "Pagamento por Sorte/Revés de R$ " + String.format("%.2f", valor);
            case HIPOTECA_IMOVEL:
                return "Hipoteca do imóvel " + imovel.getNome() + " recebendo R$ " + String.format("%.2f", valor);
            case QUITACAO_HIPOTECA:
                return "Quitação de hipoteca do imóvel " + imovel.getNome() + " pagando R$ " + String.format("%.2f", valor);
        }
        return tipo.toString();
    }
}
