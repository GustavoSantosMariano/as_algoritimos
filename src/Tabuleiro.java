/**
 * Implementação manual da Lista Duplamente Ligada Circular para o tabuleiro.
 */
public class Tabuleiro {
    private Casa head;
    private Casa tail;
    private int size;

    public Tabuleiro() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adiciona uma casa ao tabuleiro, ligando-a de forma circular e duplamente encadeada.
     */
    public void adicionarCasa(Casa novaCasa) {
        if (novaCasa == null) return;

        if (head == null) {
            head = novaCasa;
            tail = novaCasa;
            novaCasa.next = novaCasa;
            novaCasa.prev = novaCasa;
        } else {
            tail.next = novaCasa;
            novaCasa.prev = tail;
            novaCasa.next = head;
            head.prev = novaCasa;
            tail = novaCasa;
        }
        size++;
    }

    public Casa getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    /**
     * Remove uma casa do tabuleiro por nome (útil para o CRUD de imóveis).
     * Nota: O Início, Prisão, Leilão, etc. não devem ser removíveis, apenas imóveis.
     */
    public boolean removerCasa(String nome) {
        if (head == null) return false;

        Casa current = head;
        do {
            if (current.getNome().equalsIgnoreCase(nome)) {
                if (current.getTipo() != Casa.TipoCasa.IMOVEL) {
                    // Apenas imóveis cadastrados podem ser removidos
                    return false;
                }

                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    if (current == head) {
                        head = current.next;
                    }
                    if (current == tail) {
                        tail = current.prev;
                    }
                }
                size--;
                return true;
            }
            current = current.next;
        } while (current != head);

        return false;
    }

    /**
     * Busca uma casa pelo nome.
     */
    public Casa buscarCasa(String nome) {
        if (head == null) return null;

        Casa current = head;
        do {
            if (current.getNome().equalsIgnoreCase(nome)) {
                return current;
            }
            current = current.next;
        } while (current != head);

        return null;
    }

    /**
     * Exibe o tabuleiro de forma circular, mostrando todos os nós e a ligação do último com o primeiro.
     */
    public void exibirTabuleiro() {
        if (head == null) {
            System.out.println("Tabuleiro vazio.");
            return;
        }

        System.out.println("\n=== CASAS DO TABULEIRO (Estrutura Circular) ===");
        Casa current = head;
        int index = 1;
        do {
            String linkStr = " <-> ";
            System.out.println(String.format("[%02d] ", index) + current.toString());
            current = current.next;
            index++;
        } while (current != head);
        System.out.println("----------------------------------------------");
        System.out.println("Visualização Circular: [Última Casa: " + tail.getNome() + "] -> Conecta com [Primeira Casa: " + head.getNome() + "]");
        System.out.println("==============================================\n");
    }
}
