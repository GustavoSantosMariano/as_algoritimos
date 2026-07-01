import java.util.Random;

/**
 * Gerencia o baralho de Sorte/Revés usando uma Pilha manual.
 */
public class Baralho {
    private Pilha<Carta> pilha;
    private Carta[] todasCartas;
    private Random random;

    public Baralho() {
        this.pilha = new Pilha<>();
        this.random = new Random();
        inicializarCartas();
        reabastecerEEmbaralhar();
    }

    /**
     * Define as cartas fixas do jogo (no mínimo 12 cartas: 6 ganho/avanço, 6 perda/retrocesso/prisão).
     */
    private void inicializarCartas() {
        todasCartas = new Carta[] {
            // Cartas de ganho/avanço (6)
            new Carta("Você achou R$ 50.000 em uma carteira perdida! Receba do banco.", Carta.TipoEfeito.RECEBER_BANCO, 50000.0),
            new Carta("Seu investimento rendeu frutos! Receba R$ 100.000 do banco.", Carta.TipoEfeito.RECEBER_BANCO, 100000.0),
            new Carta("Avance 3 casas no tabuleiro.", Carta.TipoEfeito.AVANCAR_CASAS, 3.0),
            new Carta("Avance 5 casas no tabuleiro.", Carta.TipoEfeito.AVANCAR_CASAS, 5.0),
            new Carta("Vá direto para o Início para coletar seu salário!", Carta.TipoEfeito.AVANCAR_INICIO, 0.0),
            new Carta("Dia do seu aniversário! Cada jogador paga R$ 20.000 para você.", Carta.TipoEfeito.RECEBER_OUTROS, 20000.0),

            // Cartas de perda/penalidade/retrocesso (6)
            new Carta("Sua empresa foi multada por sonegação. Pague R$ 80.000 ao banco.", Carta.TipoEfeito.PAGAR_BANCO, 80000.0),
            new Carta("Manutenção de emergência! Pague R$ 40.000 ao banco.", Carta.TipoEfeito.PAGAR_BANCO, 40000.0),
            new Carta("Almoço de negócios caro. Pague R$ 15.000 para cada um dos outros jogadores.", Carta.TipoEfeito.PAGAR_OUTROS, 15000.0),
            new Carta("Infração grave de trânsito. Vá diretamente para a Prisão!", Carta.TipoEfeito.IR_PRISAO, 0.0),
            new Carta("Você pegou o caminho errado. Volte 4 casas.", Carta.TipoEfeito.VOLTAR_CASAS, 4.0),
            new Carta("Tornado financeiro! Volte para a última casa em que estava.", Carta.TipoEfeito.VOLTAR_CASA_ANTERIOR, 0.0)
        };
    }

    /**
     * Reabastece o baralho com as cartas iniciais e realiza o embaralhamento (Fisher-Yates).
     */
    public void reabastecerEEmbaralhar() {
        // Criar uma cópia para embaralhar
        Carta[] temp = new Carta[todasCartas.length];
        System.arraycopy(todasCartas, 0, temp, 0, todasCartas.length);

        // Algoritmo de Fisher-Yates
        for (int i = temp.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta swap = temp[i];
            temp[i] = temp[j];
            temp[j] = swap;
        }

        // Limpar e empilhar no baralho
        pilha.clear();
        for (Carta carta : temp) {
            pilha.push(carta);
        }
    }

    /**
     * Saca a carta do topo. Se o baralho estiver vazio, reabastece e avisa o usuário.
     */
    public Carta sacarCarta() {
        if (pilha.isEmpty()) {
            System.out.println(Constantes.ANSI_YELLOW + "\n[SISTEMA] O baralho de Sorte/Revés esgotou! Remontando e reembaralhando a pilha automaticamente..." + Constantes.ANSI_RESET);
            reabastecerEEmbaralhar();
        }
        return pilha.pop();
    }

    public int tamanho() {
        return pilha.size();
    }
}
