/**
 * Implementação manual de uma Árvore Binária de Busca (BST)
 * usada para ordenar e classificar os jogadores por patrimônio.
 */
public class BST {
    private static class NodeBST {
        Jogador jogador;
        double patrimonio;
        NodeBST left;
        NodeBST right;

        NodeBST(Jogador jogador) {
            this.jogador = jokerNode(jogador);
            this.patrimonio = jogador.calcularPatrimonioTotal();
            this.left = null;
            this.right = null;
        }

        private Jogador jokerNode(Jogador j) {
            return j;
        }
    }

    private NodeBST root;
    private int rankCounter;

    public BST() {
        this.root = null;
    }

    public void clear() {
        this.root = null;
    }

    /**
     * Insere um jogador na BST. Ordena pelo patrimônio total do jogador.
     */
    public void insert(Jogador jogador) {
        root = insertRec(root, jogador);
    }

    private NodeBST insertRec(NodeBST root, Jogador jogador) {
        if (root == null) {
            return new NodeBST(jogador);
        }

        double val = jogador.calcularPatrimonioTotal();
        // Se for menor ou igual, vai para a esquerda, se for maior vai para a direita.
        // Isso garante ordenação correta.
        if (val < root.patrimonio) {
            root.left = insertRec(root.left, jogador);
        } else {
            // Permite duplicatas indo para a direita
            root.right = insertRec(root.right, jogador);
        }

        return root;
    }

    /**
     * Exibe os jogadores do maior para o menor patrimônio.
     * Percurso: Direita -> Raiz -> Esquerda.
     */
    public void exibirRanking() {
        if (root == null) {
            System.out.println("Nenhum jogador para listar no ranking.");
            return;
        }
        rankCounter = 1;
        System.out.println(Constantes.ANSI_BOLD + "\n=================== RANKING DOS JOGADORES (BST) ===================" + Constantes.ANSI_RESET);
        exibirRankingRec(root);
        System.out.println(Constantes.ANSI_BOLD + "===================================================================" + Constantes.ANSI_RESET);
    }

    private void exibirRankingRec(NodeBST node) {
        if (node != null) {
            // Visita subárvore direita primeiro (maiores valores)
            exibirRankingRec(node.right);

            // Visita a raiz
            String color = getRankColor(rankCounter);
            System.out.println(String.format("  %s%dº Lugar: %-15s | Patrimônio Total: R$ %11.2f (Saldo: R$ %11.2f)%s",
                color,
                rankCounter,
                node.jogador.getNome() + " (" + node.jogador.getPersonagem() + ")",
                node.patrimonio,
                node.jogador.getSaldo(),
                Constantes.ANSI_RESET
            ));
            rankCounter++;

            // Visita subárvore esquerda (menores valores)
            exibirRankingRec(node.left);
        }
    }

    private String getRankColor(int rank) {
        switch (rank) {
            case 1: return Constantes.ANSI_GREEN + Constantes.ANSI_BOLD;
            case 2: return Constantes.ANSI_CYAN + Constantes.ANSI_BOLD;
            case 3: return Constantes.ANSI_YELLOW + Constantes.ANSI_BOLD;
            default: return Constantes.ANSI_RESET;
        }
    }

    /**
     * Retorna a lista encadeada ordenada de jogadores para uso nos relatórios finais.
     */
    public ListaEncadeada<Jogador> obterListaOrdenada() {
        ListaEncadeada<Jogador> lista = new ListaEncadeada<>();
        obterListaOrdenadaRec(root, lista);
        return lista;
    }

    private void obterListaOrdenadaRec(NodeBST node, ListaEncadeada<Jogador> lista) {
        if (node != null) {
            // Para obter do maior para o menor, percorre direita -> raiz -> esquerda
            obterListaOrdenadaRec(node.right, lista);
            lista.add(node.jogador);
            obterListaOrdenadaRec(node.left, lista);
        }
    }
}
