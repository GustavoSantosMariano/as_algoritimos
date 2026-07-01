import java.util.Scanner;
import java.util.Random;

/**
 * Classe principal que gerencia o fluxo de execução, menus e lógica do jogo.
 */
public class Main {
    // Configurações do Jogo (Configuráveis)
    private static double saldoInicial = 500000.0;
    private static double salarioCompleto = 20000.0;
    private static int limiteRodadas = 30;
    private static double valorFianca = 2000.0; // Padrão: 10% do salário

    // Estado do Jogo
    private static ListaEncadeada<Jogador> jogadores = new ListaEncadeada<>();
    private static ListaEncadeada<Casa> listaImoveisCadastro = new ListaEncadeada<>();
    private static Tabuleiro tabuleiro = new Tabuleiro();
    private static Baralho baralho = new Baralho();
    private static Fila<Jogador> filaPrisao = new Fila<>();
    private static BST rankingTree = new BST();

    // Utilitários
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static int rodadaAtual = 1;

    // Estatísticas da Partida
    private static String imovelMaiorAluguelCobrado = "Nenhum";
    private static double valorMaiorAluguelCobrado = 0.0;

    public static void main(String[] args) {
        carregarConfiguracoesPadrao();
        exibirBanner();
        menuPrincipal();
    }

    private static void exibirBanner() {
        System.out.println(Constantes.ANSI_CYAN + "=====================================================================" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_CYAN + "  SIMULAÇÃO DE JOGO DE TABULEIRO ESTRATÉGICO COM ESTRUTURAS DE DADOS  " + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_CYAN + "=====================================================================" + Constantes.ANSI_RESET);
        System.out.println("  Tema: Gestão Imobiliária & Estruturas de Dados Manuais");
        System.out.println("  Desenvolvido em Java - Avaliação Semestral");
        System.out.println(Constantes.ANSI_CYAN + "=====================================================================\n" + Constantes.ANSI_RESET);
    }

    /**
     * Carrega presets padrão para agilizar os testes de imediato.
     */
    private static void carregarConfiguracoesPadrao() {
        // Carrega imóveis do Anexo A por padrão
        carregarPresetImoveis(1, false);

        // A lista de jogadores começa vazia para que o usuário cadastre manualmente
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println(Constantes.ANSI_BOLD + "--- MENU PRINCIPAL ---" + Constantes.ANSI_RESET);
            System.out.println("1. Iniciar Partida");
            System.out.println("2. Configurações da Partida");
            System.out.println("3. Exibir Regras e Créditos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    if (validarRequisitosInicio()) {
                        jogar();
                        return; // Termina após a partida
                    }
                    break;
                case 2:
                    menuConfiguracoes();
                    break;
                case 3:
                    exibirRegras();
                    break;
                case 4:
                    System.out.println("Obrigado por jogar!");
                    System.exit(0);
                default:
                    System.out.println(Constantes.ANSI_RED + "Opção inválida!" + Constantes.ANSI_RESET);
            }
        }
    }

    private static boolean validarRequisitosInicio() {
        if (listaImoveisCadastro.size() < 10) {
            System.out.println(Constantes.ANSI_RED + "\nErro: São necessários pelo menos 10 imóveis cadastrados para iniciar o jogo! (Atual: " + listaImoveisCadastro.size() + ")" + Constantes.ANSI_RESET);
            return false;
        }
        if (jogadores.size() < 2) {
            System.out.println(Constantes.ANSI_RED + "\nErro: São necessários pelo menos 2 jogadores cadastrados para iniciar o jogo! (Atual: " + jogadores.size() + ")" + Constantes.ANSI_RESET);
            return false;
        }
        return true;
    }

    private static void menuConfiguracoes() {
        while (true) {
            System.out.println(Constantes.ANSI_BOLD + "\n--- CONFIGURAÇÕES ---" + Constantes.ANSI_RESET);
            System.out.println("1. Gerenciamento de Imóveis (CRUD)");
            System.out.println("2. Gerenciamento de Jogadores (CRUD)");
            System.out.println("3. Ajustar Parâmetros Financeiros");
            System.out.println("4. Carregar Preset de Imóveis (Anexos)");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    menuCRUDImoveis();
                    break;
                case 2:
                    menuCRUDJogadores();
                    break;
                case 3:
                    ajustarParametros();
                    break;
                case 4:
                    menuPresetImoveis();
                    break;
                case 5:
                    return;
                default:
                    System.out.println(Constantes.ANSI_RED + "Opção inválida!" + Constantes.ANSI_RESET);
            }
        }
    }

    private static void menuCRUDImoveis() {
        while (true) {
            System.out.println(Constantes.ANSI_BOLD + "\n--- CRUD IMÓVEIS ---" + Constantes.ANSI_RESET);
            System.out.println("1. Cadastrar Imóvel");
            System.out.println("2. Listar Imóveis Cadastrados");
            System.out.println("3. Atualizar Imóvel");
            System.out.println("4. Remover Imóvel");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    cadastrarImovel();
                    break;
                case 2:
                    listarImoveis();
                    break;
                case 3:
                    atualizarImovel();
                    break;
                case 4:
                    removerImovel();
                    break;
                case 5:
                    return;
                default:
                    System.out.println(Constantes.ANSI_RED + "Opção inválida!" + Constantes.ANSI_RESET);
            }
        }
    }

    private static void cadastrarImovel() {
        if (listaImoveisCadastro.size() >= 40) {
            System.out.println(Constantes.ANSI_RED + "Limite máximo de 40 imóveis atingido!" + Constantes.ANSI_RESET);
            return;
        }
        System.out.print("Nome do Imóvel: ");
        String nome = scanner.nextLine();
        System.out.print("Preço de Compra: R$ ");
        double preco = lerDouble();
        System.out.print("Aluguel Base: R$ ");
        double aluguel = lerDouble();

        Casa novo = new Casa(nome, Casa.TipoCasa.IMOVEL, preco, aluguel);
        listaImoveisCadastro.add(novo);
        System.out.println(Constantes.ANSI_GREEN + "Imóvel '" + nome + "' cadastrado com sucesso!" + Constantes.ANSI_RESET);
    }

    private static void listarImoveis() {
        System.out.println("\n=== LISTA DE IMÓVEIS CADASTRADOS (" + listaImoveisCadastro.size() + ") ===");
        ListaEncadeada.Node<Casa> current = listaImoveisCadastro.getHead();
        int idx = 1;
        while (current != null) {
            System.out.println(String.format("[%02d] %s (Compra: R$ %.2f, Aluguel: R$ %.2f)",
                idx++, current.data.getNome(), current.data.getPrecoCompra(), current.data.getAluguelBase()));
            current = current.next;
        }
    }

    private static void atualizarImovel() {
        System.out.print("Digite o nome do imóvel a atualizar: ");
        String nome = scanner.nextLine();
        ListaEncadeada.Node<Casa> current = listaImoveisCadastro.getHead();
        while (current != null) {
            if (current.data.getNome().equalsIgnoreCase(nome)) {
                System.out.print("Novo nome (atual: " + current.data.getNome() + "): ");
                String novoNome = scanner.nextLine();
                System.out.print("Novo preço (atual: R$ " + current.data.getPrecoCompra() + "): R$ ");
                double novoPreco = lerDouble();
                System.out.print("Novo aluguel base (atual: R$ " + current.data.getAluguelBase() + "): R$ ");
                double novoAluguel = lerDouble();

                current.data.setNome(novoNome);
                // Como precoCompra e aluguelBase são private com getters, vamos criar um novo objeto ou alterar a classe.
                // Mas alteramos o objeto diretamente se for possível (vamos criar um novo nó ou reatribuir)
                // Espera, na nossa classe Casa.java, os atributos são privados mas não temos setters para PrecoCompra e AluguelBase para mantê-los seguros.
                // Vamos remover e adicionar de novo, ou simplesmente expor um método de atualização.
                // Criar um método de atualização ou recriar a Casa. Vamos remover e adicionar a nova no mesmo lugar!
                listaImoveisCadastro.remove(current.data);
                Casa novo = new Casa(novoNome, Casa.TipoCasa.IMOVEL, novoPreco, novoAluguel);
                listaImoveisCadastro.add(novo);
                System.out.println(Constantes.ANSI_GREEN + "Imóvel atualizado com sucesso!" + Constantes.ANSI_RESET);
                return;
            }
            current = current.next;
        }
        System.out.println(Constantes.ANSI_RED + "Imóvel não encontrado!" + Constantes.ANSI_RESET);
    }

    private static void removerImovel() {
        System.out.print("Digite o nome do imóvel a remover: ");
        String nome = scanner.nextLine();
        ListaEncadeada.Node<Casa> current = listaImoveisCadastro.getHead();
        while (current != null) {
            if (current.data.getNome().equalsIgnoreCase(nome)) {
                listaImoveisCadastro.remove(current.data);
                System.out.println(Constantes.ANSI_GREEN + "Imóvel '" + current.data.getNome() + "' removido!" + Constantes.ANSI_RESET);
                return;
            }
            current = current.next;
        }
        System.out.println(Constantes.ANSI_RED + "Imóvel não encontrado!" + Constantes.ANSI_RESET);
    }

    private static void menuCRUDJogadores() {
        while (true) {
            System.out.println(Constantes.ANSI_BOLD + "\n--- CRUD JOGADORES ---" + Constantes.ANSI_RESET);
            System.out.println("1. Cadastrar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Atualizar Jogador");
            System.out.println("4. Remover Jogador");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    cadastrarJogador();
                    break;
                case 2:
                    listarJogadores();
                    break;
                case 3:
                    atualizarJogador();
                    break;
                case 4:
                    removerJogador();
                    break;
                case 5:
                    return;
                default:
                    System.out.println(Constantes.ANSI_RED + "Opção inválida!" + Constantes.ANSI_RESET);
            }
        }
    }

    private static void cadastrarJogador() {
        if (jogadores.size() >= 6) {
            System.out.println(Constantes.ANSI_RED + "Limite máximo de 6 jogadores atingido!" + Constantes.ANSI_RESET);
            return;
        }
        System.out.print("Nome do Jogador: ");
        String nome = scanner.nextLine();
        System.out.println("Selecione o Personagem (Habilidade Passiva):");
        System.out.println("1. Especulador (Salário +20%, Imposto +10%)");
        System.out.println("2. Negociante (Paga -10% de aluguel)");
        System.out.println("3. Advogado (Saída grátis da prisão 1x)");
        System.out.println("4. Construtor (Aluguel dos seus imóveis +15% de base)");
        System.out.print("Escolha: ");
        int pOpt = lerInteiro();
        Jogador.Personagem p;
        switch (pOpt) {
            case 1: p = Jogador.Personagem.ESPECULADOR; break;
            case 2: p = Jogador.Personagem.NEGOCIANTE; break;
            case 3: p = Jogador.Personagem.ADVOGADO; break;
            case 4: p = Jogador.Personagem.CONSTRUTOR; break;
            default:
                System.out.println("Opção inválida! Escolhendo Especulador por padrão.");
                p = Jogador.Personagem.ESPECULADOR;
        }

        Jogador novo = new Jogador(nome, saldoInicial, p);
        jogadores.add(novo);
        System.out.println(Constantes.ANSI_GREEN + "Jogador '" + nome + "' cadastrado como " + p + "!" + Constantes.ANSI_RESET);
    }

    private static void listarJogadores() {
        System.out.println("\n=== LISTA DE JOGADORES CADASTRADOS (" + jogadores.size() + ") ===");
        ListaEncadeada.Node<Jogador> current = jogadores.getHead();
        int idx = 1;
        while (current != null) {
            System.out.println(String.format("[%02d] %s (%s) - Saldo: R$ %.2f",
                idx++, current.data.getNome(), current.data.getPersonagem(), current.data.getSaldo()));
            current = current.next;
        }
    }

    private static void atualizarJogador() {
        System.out.print("Digite o nome do jogador a atualizar: ");
        String nome = scanner.nextLine();
        ListaEncadeada.Node<Jogador> current = jogadores.getHead();
        while (current != null) {
            if (current.data.getNome().equalsIgnoreCase(nome)) {
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                System.out.println("Selecione novo Personagem:");
                System.out.println("1. Especulador | 2. Negociante | 3. Advogado | 4. Construtor");
                int pOpt = lerInteiro();
                Jogador.Personagem p;
                switch (pOpt) {
                    case 1: p = Jogador.Personagem.ESPECULADOR; break;
                    case 2: p = Jogador.Personagem.NEGOCIANTE; break;
                    case 3: p = Jogador.Personagem.ADVOGADO; break;
                    case 4: p = Jogador.Personagem.CONSTRUTOR; break;
                    default: p = current.data.getPersonagem();
                }

                // Cria um novo com mesmo saldo
                double saldo = current.data.getSaldo();
                jogadores.remove(current.data);
                jogadores.add(new Jogador(novoNome, saldo, p));
                System.out.println(Constantes.ANSI_GREEN + "Jogador atualizado!" + Constantes.ANSI_RESET);
                return;
            }
            current = current.next;
        }
        System.out.println(Constantes.ANSI_RED + "Jogador não encontrado!" + Constantes.ANSI_RESET);
    }

    private static void removerJogador() {
        System.out.print("Digite o nome do jogador a remover: ");
        String nome = scanner.nextLine();
        ListaEncadeada.Node<Jogador> current = jogadores.getHead();
        while (current != null) {
            if (current.data.getNome().equalsIgnoreCase(nome)) {
                jogadores.remove(current.data);
                System.out.println(Constantes.ANSI_GREEN + "Jogador removido!" + Constantes.ANSI_RESET);
                return;
            }
            current = current.next;
        }
        System.out.println(Constantes.ANSI_RED + "Jogador não encontrado!" + Constantes.ANSI_RESET);
    }

    private static void ajustarParametros() {
        System.out.println("\n--- PARÂMETROS FINANCEIROS ---");
        System.out.print("Saldo Inicial dos Jogadores (atual: R$ " + saldoInicial + "): R$ ");
        saldoInicial = lerDouble();
        System.out.print("Salário por volta completa (atual: R$ " + salarioCompleto + "): R$ ");
        salarioCompleto = lerDouble();
        System.out.print("Limite Máximo de Rodadas (atual: " + limiteRodadas + "): ");
        limiteRodadas = lerInteiro();
        System.out.print("Fiança da Prisão (atual: R$ " + valorFianca + "): R$ ");
        valorFianca = lerDouble();
        System.out.println(Constantes.ANSI_GREEN + "Parâmetros configurados com sucesso!" + Constantes.ANSI_RESET);
    }

    private static void menuPresetImoveis() {
        System.out.println("\n--- PRESETS DE IMÓVEIS ---");
        System.out.println("1. Anexo A: Cidades Brasileiras");
        System.out.println("2. Anexo B: Mitologia e Fantasia");
        System.out.println("3. Anexo C: Espaço e Ficção Científica");
        System.out.print("Escolha: ");
        int choice = lerInteiro();
        carregarPresetImoveis(choice, true);
    }

    private static void carregarPresetImoveis(int choice, boolean avisar) {
        listaImoveisCadastro.clear();
        Constantes.PresetImovel[] preset;
        String desc;
        switch (choice) {
            case 2:
                preset = Constantes.ANEXO_B;
                desc = "Mitologia e Fantasia";
                break;
            case 3:
                preset = Constantes.ANEXO_C;
                desc = "Espaço e Ficção Científica";
                break;
            case 1:
            default:
                preset = Constantes.ANEXO_A;
                desc = "Cidades Brasileiras";
        }
        for (Constantes.PresetImovel pi : preset) {
            listaImoveisCadastro.add(new Casa(pi.nome, Casa.TipoCasa.IMOVEL, pi.precoCompra, pi.aluguelBase));
        }
        if (avisar) {
            System.out.println(Constantes.ANSI_GREEN + "Preset '" + desc + "' carregado com sucesso (" + preset.length + " imóveis)!" + Constantes.ANSI_RESET);
        }
    }

    private static void exibirRegras() {
        System.out.println("\n====================== REGRAS DO JOGO ======================");
        System.out.println("1. O tabuleiro é uma Lista Circular Duplamente Ligada.");
        System.out.println("2. O Baralho de Sorte/Revés é uma Pilha (LIFO) manual.");
        System.out.println("3. A Prisão utiliza Fila (FIFO) manual.");
        System.out.println("4. O Ranking utiliza uma Árvore Binária de Busca (BST).");
        System.out.println("5. Imóveis valorizam 0.1x a cada visita de outros jogadores (máx 2.0x).");
        System.out.println("6. Especulador ganha +20% salário, paga +10% impostos.");
        System.out.println("7. Negociante paga 10% a menos de aluguel.");
        System.out.println("8. Construtor aumenta em 15% o aluguel base de suas propriedades.");
        System.out.println("9. Advogado pode sair da prisão grátis uma vez.");
        System.out.println("10. Deque de Undo: Permite desfazer até 3 ações financeiras por turno.");
        System.out.println("============================================================\n");
    }

    /**
     * Constrói o tabuleiro conectando propriedades e casas especiais.
     */
    private static void inicializarTabuleiro() {
        tabuleiro = new Tabuleiro();

        // 1. Casa Inicial
        tabuleiro.adicionarCasa(new Casa("Início", Casa.TipoCasa.INICIO));

        // 2. Entrelaçar propriedades cadastradas com casas especiais
        ListaEncadeada.Node<Casa> current = listaImoveisCadastro.getHead();
        int propCounter = 0;

        while (current != null) {
            tabuleiro.adicionarCasa(current.data);
            propCounter++;

            // Distribuição de casas especiais
            if (propCounter == 2) {
                tabuleiro.adicionarCasa(new Casa("Sorte / Revés", Casa.TipoCasa.SORTE_REVES));
            } else if (propCounter == 4) {
                tabuleiro.adicionarCasa(new Casa("Imposto de Renda", Casa.TipoCasa.IMPOSTO));
            } else if (propCounter == 6) {
                tabuleiro.adicionarCasa(new Casa("Prisão", Casa.TipoCasa.PRISAO));
            } else if (propCounter == 8) {
                tabuleiro.adicionarCasa(new Casa("Restituição", Casa.TipoCasa.RESTITUICAO));
            } else if (propCounter == 12) {
                tabuleiro.adicionarCasa(new Casa("Sorte / Revés (2)", Casa.TipoCasa.SORTE_REVES));
            }
            current = current.next;
        }

        // Posiciona todos os jogadores no Início
        ListaEncadeada.Node<Jogador> currJog = jogadores.getHead();
        while (currJog != null) {
            currJog.data.setPosicaoAtual(tabuleiro.getHead());
            currJog.data.setPosicaoAnterior(null);
            currJog.data.setPreso(false);
            currJog.data.setIsencaoFiancaUsada(false);
            currJog.data.getPropriedades().clear();
            currJog.data.setSaldo(saldoInicial);
            currJog.data.setVoltasCompletas(0);
            currJog.data.getHistoricoTransacoes().clear();
            currJog = currJog.next;
        }

        filaPrisao.clear();
        rodadaAtual = 1;
        imovelMaiorAluguelCobrado = "Nenhum";
        valorMaiorAluguelCobrado = 0.0;

        tabuleiro.exibirTabuleiro(); // Mostra tabuleiro criado (S5)
    }

    /**
     * Inicia e gerencia o loop principal da partida.
     */
    private static void jogar() {
        inicializarTabuleiro();
        System.out.println(Constantes.ANSI_GREEN + "=== PARTIDA INICIADA! ===" + Constantes.ANSI_RESET);

        while (rodadaAtual <= limiteRodadas && obterJogadoresAtivosCount() > 1) {
            System.out.println(Constantes.ANSI_BOLD + "\n\n==================================================");
            System.out.println("             RODADA " + rodadaAtual + " de " + limiteRodadas);
            System.out.println("==================================================" + Constantes.ANSI_RESET);

            // 1. Fase da Prisão (FIFO para os jogadores presos no início da rodada)
            executarFasePrisao();

            // 2. Turno dos Jogadores
            ListaEncadeada.Node<Jogador> currNode = jogadores.getHead();
            while (currNode != null) {
                Jogador jogador = currNode.data;

                // Verifica se o jogador faliu
                if (jogador.getSaldo() < 0 && jogador.getPropriedades().isEmpty()) {
                    // Declarar falência na rodada
                    declararFalencia(jogador);
                    currNode = currNode.next;
                    continue;
                }

                // Se o jogador faliu ou está preso e NÃO pode jogar nesta rodada, pula
                if (jogador.isPreso()) {
                    System.out.println("\n[TURNO] Jogador " + jogador.getNome() + " está preso e aguarda a próxima rodada.");
                    currNode = currNode.next;
                    continue;
                }

                // Turno normal do jogador
                executarTurno(jogador);

                // Checagem de falência pós-turno
                if (jogador.getSaldo() < 0) {
                    resolverSaldoNegativo(jogador);
                }

                currNode = currNode.next;
            }

            // Exibir ranking do fim da rodada
            reconstruirBST();
            rankingTree.exibirRanking();

            rodadaAtual++;
        }

        // Final do jogo
        encerrarPartida();
    }

    private static int obterJogadoresAtivosCount() {
        ListaEncadeada.Node<Jogador> curr = jogadores.getHead();
        int count = 0;
        while (curr != null) {
            if (curr.data.getSaldo() >= 0 || !curr.data.getPropriedades().isEmpty()) {
                count++;
            }
            curr = curr.next;
        }
        return count;
    }

    /**
     * Executa a tentativa de soltura da prisão para todos os jogadores presos, na ordem em que entraram.
     */
    private static void executarFasePrisao() {
        if (filaPrisao.isEmpty()) return;

        System.out.println(Constantes.ANSI_PURPLE + "\n--- FASE DA PRISÃO (Fila de Espera FIFO) ---" + Constantes.ANSI_RESET);
        int tamanhoFila = filaPrisao.size();
        Fila<Jogador> filaTemporaria = new Fila<>();

        for (int i = 0; i < tamanhoFila; i++) {
            Jogador j = filaPrisao.dequeue();
            j.incrementarTentativasPrisao();

            System.out.println("\nPreso: " + Constantes.ANSI_BOLD + j.getNome() + Constantes.ANSI_RESET +
                               " (Habilidade: " + j.getPersonagem() + ") | Posição na Fila: " + (i + 1) +
                               " | Tentativa: " + j.getTentativasPrisao() + "/3");

            System.out.println("Escolha sua estratégia de soltura:");
            System.out.println("1. Rolar dados duplos (Tentar sorte)");
            if (j.getPersonagem() == Jogador.Personagem.ADVOGADO && !j.isIsencaoFiancaUsada()) {
                System.out.println("2. Usar Isenção de Fiança do Advogado (Grátis - 1x por jogo)");
            } else {
                System.out.println("2. Pagar Fiança de R$ " + String.format("%.2f", valorFianca) + " (10% do salário)");
            }
            System.out.println("3. Aguardar na prisão nesta rodada");
            System.out.print("Opção: ");
            int opt = lerInteiro();

            boolean solto = false;

            if (opt == 1) {
                // Rolar dados
                int d1 = rolarDado();
                int d2 = rolarDado();
                System.out.println("Resultado dos dados: " + d1 + " e " + d2);
                if (d1 == d2) {
                    System.out.println(Constantes.ANSI_GREEN + "[SOLTO] DADOS DUPLOS! Você foi libertado da prisão!" + Constantes.ANSI_RESET);
                    solto = true;
                    j.setPreso(false);
                    // Rolar dados duplos permite avançar no mesmo turno!
                    int passos = d1 + d2;
                    System.out.println("Avançando " + passos + " casas imediatamente...");
                    moverJogador(j, passos, true);
                } else {
                    System.out.println(Constantes.ANSI_RED + "Não foram dados duplos. Você continua preso." + Constantes.ANSI_RESET);
                    if (j.getTentativasPrisao() >= 3) {
                        System.out.println(Constantes.ANSI_YELLOW + "3º Tentativa falhou! Você foi libertado automaticamente por tempo, mas não joga nesta rodada." + Constantes.ANSI_RESET);
                        j.setPreso(false);
                        // Fica livre para o próximo turno, não reinsere na fila
                    } else {
                        filaTemporaria.enqueue(j);
                    }
                }
            } else if (opt == 2) {
                if (j.getPersonagem() == Jogador.Personagem.ADVOGADO && !j.isIsencaoFiancaUsada()) {
                    j.setIsencaoFiancaUsada(true);
                    j.setPreso(false);
                    solto = true;
                    System.out.println(Constantes.ANSI_GREEN + "[SOLTO] Isenção aplicada! Você está livre para jogar seu turno!" + Constantes.ANSI_RESET);
                } else {
                    if (j.getSaldo() >= valorFianca) {
                        j.subtrairSaldo(valorFianca);
                        j.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_SORTE_REVES, j, valorFianca));
                        j.setPreso(false);
                        solto = true;
                        System.out.println(Constantes.ANSI_GREEN + "[SOLTO] Fiança paga! Você está livre para jogar seu turno!" + Constantes.ANSI_RESET);
                    } else {
                        System.out.println(Constantes.ANSI_RED + "Saldo insuficiente para pagar fiança! Reenviado para o fim da fila." + Constantes.ANSI_RESET);
                        filaTemporaria.enqueue(j);
                    }
                }
            } else {
                System.out.println("Você optou por esperar.");
                if (j.getTentativasPrisao() >= 3) {
                    System.out.println(Constantes.ANSI_YELLOW + "3º Tentativa concluída! Solto por tempo limite, porém não joga nesta rodada." + Constantes.ANSI_RESET);
                    j.setPreso(false);
                } else {
                    filaTemporaria.enqueue(j);
                }
            }

            // Exibir status do jogador pós-prisão (S10)
            System.out.println("Status final: " + (j.isPreso() ? "PRESO" : "SOLTO"));
        }

        // Devolve os que continuam presos para a fila original
        while (!filaTemporaria.isEmpty()) {
            filaPrisao.enqueue(filaTemporaria.dequeue());
        }
    }

    /**
     * Executa o turno individual de um jogador.
     */
    private static void executarTurno(Jogador jogador) {
        jogador.resetUndosRodada(); // Pode fazer 1 undo por rodada
        System.out.println(Constantes.ANSI_BLUE + "\n>>> TURNO DE: " + Constantes.ANSI_BOLD + jogador.getNome().toUpperCase() +
                           Constantes.ANSI_RESET + " (Habilidade: " + jogador.getPersonagem() + ") <<<");
        System.out.println("Saldo: R$ " + String.format("%.2f", jogador.getSaldo()) + " | Posição: " + jogador.getPosicaoAtual().getNome());

        boolean dadoRolado = false;
        while (!dadoRolado) {
            System.out.println("\nComandos Disponíveis:");
            System.out.println("1. Rolar Dados e Mover");
            System.out.println("2. Ver Tabuleiro (Circular)");
            System.out.println("3. Ver Meu Status Detalhado");
            System.out.println("4. Ver Ranking de Patrimônio (BST)");
            System.out.println("5. Hipotecar / Quitar Imóvel");
            System.out.println("6. Propor Negociação / Troca");
            System.out.println("7. Desfazer Última Transação Financeira (Undo)");
            System.out.print("Escolha: ");
            int menuOpt = lerInteiro();

            switch (menuOpt) {
                case 1:
                    dadoRolado = true;
                    int d1 = rolarDado();
                    int d2 = rolarDado();
                    int soma = d1 + d2;
                    System.out.println("\n[DADOS] Você rolou: " + d1 + " e " + d2 + " = " + soma + " casas!");
                    moverJogador(jogador, soma, true);
                    break;
                case 2:
                    tabuleiro.exibirTabuleiro();
                    break;
                case 3:
                    System.out.println("\n" + jogador);
                    break;
                case 4:
                    reconstructAndExhibBST();
                    break;
                case 5:
                    menuHipoteca(jogador);
                    break;
                case 6:
                    menuNegociacao(jogador);
                    break;
                case 7:
                    jogador.desfazerUltimaTransacao();
                    break;
                default:
                    System.out.println(Constantes.ANSI_RED + "Opção inválida!" + Constantes.ANSI_RESET);
            }
        }
    }

    private static void reconstructAndExhibBST() {
        reconstruirBST();
        rankingTree.exibirRanking();
    }

    private static int rolarDado() {
        return random.nextInt(6) + 1;
    }

    /**
     * Move o jogador pelo tabuleiro na direção especificada (avanço ou retrocesso).
     */
    private static void moverJogador(Jogador jogador, int casas, boolean avanco) {
        jogador.setPosicaoAnterior(jogador.getPosicaoAtual());
        Casa current = jogador.getPosicaoAtual();
        boolean passouInicio = false;

        System.out.print("Movendo: ");
        for (int i = 0; i < casas; i++) {
            if (avanco) {
                current = current.next;
                System.out.print("-> " + current.getNome() + " ");
                // Detecção de passagem pelo início no avanço
                if (current.getTipo() == Casa.TipoCasa.INICIO) {
                    passouInicio = true;
                }
            } else {
                current = current.prev;
                System.out.print("<- " + current.getNome() + " ");
                // Detecção de passagem pelo início no retrocesso (não ganha salário)
                if (current.getTipo() == Casa.TipoCasa.INICIO) {
                    System.out.print(Constantes.ANSI_YELLOW + "(Cruzou o início de ré - Sem salário!)" + Constantes.ANSI_RESET + " ");
                }
            }
        }
        System.out.println();

        jogador.setPosicaoAtual(current);
        System.out.println("Nova posição: " + current.getNome() + " (" + current.getTipo() + ")");

        // Aplicação do salário ao passar pelo Início
        if (passouInicio && avanco) {
            double salario = salarioCompleto;
            if (jogador.getPersonagem() == Jogador.Personagem.ESPECULADOR) {
                salario *= 1.20; // Especulador recebe +20% salário
                System.out.println(Constantes.ANSI_GREEN + "[HABILIDADE ESPECULADOR] Salário aumentado em 20%!" + Constantes.ANSI_RESET);
            }
            jogador.adicionarSaldo(salario);
            jogador.incrementarVoltas();
            jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.RECEBIMENTO_SALARIO, jogador, salario));
            System.out.println(Constantes.ANSI_GREEN + "[SALÁRIO] Você completou uma volta! Recebeu R$ " + String.format("%.2f", salario) + ". Saldo: R$ " + String.format("%.2f", jogador.getSaldo()) + (S13_trigger(jogador) ? " [S13]" : "") + Constantes.ANSI_RESET);
        }

        // Aplica o efeito da casa pousada
        aplicarEfeitoCasa(jogador, current, avanco, casas);
    }

    private static boolean S13_trigger(Jogador j) {
        return true;
    }

    /**
     * Aplica o efeito da casa em que o jogador parou.
     */
    private static void aplicarEfeitoCasa(Jogador jogador, Casa casa, boolean avanco, int casasDado) {
        String efeito = "";

        switch (casa.getTipo()) {
            case INICIO:
                efeito = "Parou no Início. Nada acontece.";
                System.out.println(efeito);
                break;

            case IMOVEL:
                // Visita incrementa o multiplicador (exceto se for visitado pelo próprio dono)
                if (casa.getProprietario() != jogador) {
                    casa.incrementarMultiplicador();
                }

                if (casa.getProprietario() == null) {
                    // Sem dono - opção de compra
                    efeito = "Imóvel Sem Dono. ";
                    System.out.println("[IMÓVEL] " + casa.getNome() + " está disponível para compra!");
                    System.out.println("Valor: R$ " + String.format("%.2f", casa.getPrecoCompra()) + " | Aluguel Base: R$ " + String.format("%.2f", casa.getAluguelBase()));
                    System.out.println("Seu Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
                    System.out.print("Deseja comprar? (S/N): ");
                    String resp = scanner.nextLine();
                    if (resp.equalsIgnoreCase("s")) {
                        if (jogador.getSaldo() >= casa.getPrecoCompra()) {
                            jogador.subtrairSaldo(casa.getPrecoCompra());
                            casa.setProprietario(jogador);
                            jogador.getPropriedades().add(casa);
                            jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.COMPRA_IMOVEL, jogador, casa.getPrecoCompra(), casa));
                            efeito += "Comprado por R$ " + String.format("%.2f", casa.getPrecoCompra()) + ".";
                            System.out.println(Constantes.ANSI_GREEN + "Compra realizada com sucesso! Saldo atual: R$ " + String.format("%.2f", jogador.getSaldo()) + Constantes.ANSI_RESET);
                        } else {
                            efeito += "Compra rejeitada (Saldo insuficiente).";
                            System.out.println(Constantes.ANSI_RED + "Saldo insuficiente para realizar a compra!" + Constantes.ANSI_RESET);
                        }
                    } else {
                        efeito += "Compra recusada pelo jogador.";
                    }
                } else if (casa.getProprietario() == jogador) {
                    efeito = "Parou no próprio imóvel. Visita livre.";
                    System.out.println("[IMÓVEL] Você parou na sua própria propriedade: " + casa.getNome());
                } else {
                    // Proprietário alheio - pagar aluguel (a menos que esteja hipotecado)
                    if (casa.isHipotecado()) {
                        efeito = "Parou no imóvel de " + casa.getProprietario().getNome() + " (Hipotecado - Sem aluguel).";
                        System.out.println("[IMÓVEL] " + casa.getNome() + " está hipotecado. Nenhum aluguel é cobrado!");
                    } else {
                        double aluguel = casa.calcularAluguelAtual();
                        // Habilidade passiva do Negociante (paga 10% a menos de aluguel)
                        if (jogador.getPersonagem() == Jogador.Personagem.NEGOCIANTE) {
                            aluguel *= 0.90;
                            System.out.println(Constantes.ANSI_GREEN + "[HABILIDADE NEGOCIANTE] Desconto de 10% no aluguel!" + Constantes.ANSI_RESET);
                        }

                        jogador.subtrairSaldo(aluguel);
                        casa.getProprietario().adicionarSaldo(aluguel);

                        // Registrar transação reversível para o Deque
                        jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_ALUGUEL, jogador, aluguel, casa.getProprietario()));
                        // Registrar recebimento na conta do dono (sem undo para o dono diretamente via jogador, mas mantém estado limpo)

                        efeito = "Pagou R$ " + String.format("%.2f", aluguel) + " de aluguel para " + casa.getProprietario().getNome();

                        // Atualiza estatísticas do maior aluguel
                        if (aluguel > valorMaiorAluguelCobrado) {
                            valorMaiorAluguelCobrado = aluguel;
                            imovelMaiorAluguelCobrado = casa.getNome() + " (Dono: " + casa.getProprietario().getNome() + ")";
                        }

                        System.out.println(Constantes.ANSI_RED + "[ALUGUEL] Você parou na propriedade de " + casa.getProprietario().getNome() + "!" + Constantes.ANSI_RESET);
                        System.out.println("Valor cobrado: R$ " + String.format("%.2f", aluguel) + " (Multiplicador de Visitas: " + String.format("%.1fx", casa.getMultiplicadorVisitas()) + ")");
                        System.out.println("Seu Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
                    }
                }
                break;

            case IMPOSTO:
                double patrimonio = jogador.calcularPatrimonioTotal();
                double aliquota = 0.05;
                if (jogador.getPersonagem() == Jogador.Personagem.ESPECULADOR) {
                    aliquota = 0.055; // +10% de imposto -> 5% * 1.10 = 5.5%
                    System.out.println(Constantes.ANSI_RED + "[HABILIDADE ESPECULADOR] Surtaxa de +10% de imposto ativa (Alíquota: 5.5%)!" + Constantes.ANSI_RESET);
                }
                double valorImposto = patrimonio * aliquota;
                jogador.subtrairSaldo(valorImposto);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_IMPOSTO, jogador, valorImposto));
                efeito = "Pagou imposto de R$ " + String.format("%.2f", valorImposto) + " (5% do patrimônio).";
                System.out.println(Constantes.ANSI_RED + "[IMPOSTO] Você parou na casa de Imposto!" + Constantes.ANSI_RESET);
                System.out.println("Imposto cobrado: R$ " + String.format("%.2f", valorImposto) + " (Patrimônio Total: R$ " + String.format("%.2f", patrimonio) + ")");
                System.out.println("Seu Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
                break;

            case RESTITUICAO:
                double valorRestituicao = salarioCompleto * 0.10; // 10% do salário base
                jogador.adicionarSaldo(valorRestituicao);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.RECEBIMENTO_RESTITUICAO, jogador, valorRestituicao));
                efeito = "Recebeu restituição de R$ " + String.format("%.2f", valorRestituicao) + ".";
                System.out.println(Constantes.ANSI_GREEN + "[RESTITUIÇÃO] Recebeu restituição do Banco: R$ " + String.format("%.2f", valorRestituicao) + Constantes.ANSI_RESET);
                System.out.println("Seu Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
                break;

            case PRISAO:
                efeito = "Enviado para a Prisão.";
                jogador.setPreso(true);
                filaPrisao.enqueue(jogador);
                System.out.println(Constantes.ANSI_PURPLE + "[PRISÃO] Você parou na casa da Prisão! Foi encarcerado e inserido na fila de soltura FIFO." + Constantes.ANSI_RESET);
                break;

            case SORTE_REVES:
                Carta carta = baralho.sacarCarta();
                efeito = "Sacou carta: " + carta.getDescricao();
                System.out.println(Constantes.ANSI_CYAN + "\n==================== SORTE / REVÉS ====================" + Constantes.ANSI_RESET);
                System.out.println("Carta sacada: " + Constantes.ANSI_BOLD + carta.getDescricao() + Constantes.ANSI_RESET);
                System.out.println(Constantes.ANSI_CYAN + "=======================================================" + Constantes.ANSI_RESET);
                aplicarCarta(jogador, carta);
                break;
        }
    }

    /**
     * Aplica o efeito da carta de Sorte/Revés tirada pelo jogador.
     */
    private static void aplicarCarta(Jogador jogador, Carta carta) {
        double val = carta.getValor();
        switch (carta.getEfeito()) {
            case RECEBER_BANCO:
                jogador.adicionarSaldo(val);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.RECEBIMENTO_SORTE_REVES, jogador, val));
                System.out.println(Constantes.ANSI_GREEN + "+ R$ " + String.format("%.2f", val) + " adicionados à sua conta." + Constantes.ANSI_RESET);
                break;

            case AVANCAR_CASAS:
                int casasAvancar = (int) val;
                System.out.println("Avançando " + casasAvancar + " casas...");
                moverJogador(jogador, casasAvancar, true);
                break;

            case AVANCAR_INICIO:
                System.out.println("Avançando diretamente para o Início...");
                // Mover até o início
                Casa head = tabuleiro.getHead();
                jogador.setPosicaoAnterior(jogador.getPosicaoAtual());
                jogador.setPosicaoAtual(head);
                double salario = salarioCompleto;
                if (jogador.getPersonagem() == Jogador.Personagem.ESPECULADOR) {
                    salario *= 1.20;
                }
                jogador.adicionarSaldo(salario);
                jogador.incrementarVoltas();
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.RECEBIMENTO_SALARIO, jogador, salario));
                System.out.println(Constantes.ANSI_GREEN + "[SALÁRIO] Você chegou ao Início e recebeu R$ " + String.format("%.2f", salario) + "!" + Constantes.ANSI_RESET);
                break;

            case RECEBER_OUTROS:
                // Todos pagam X para jogador
                double totalGanho = 0;
                ListaEncadeada.Node<Jogador> curr = jogadores.getHead();
                while (curr != null) {
                    Jogador outro = curr.data;
                    if (outro != jogador && outro.getSaldo() >= 0) { // ativos
                        outro.subtrairSaldo(val);
                        totalGanho += val;
                        // Registrar transações
                        outro.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_SORTE_REVES, outro, val));
                    }
                    curr = curr.next;
                }
                jogador.adicionarSaldo(totalGanho);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.RECEBIMENTO_SORTE_REVES, jogador, totalGanho));
                System.out.println(Constantes.ANSI_GREEN + "+ R$ " + String.format("%.2f", totalGanho) + " recebidos dos outros jogadores." + Constantes.ANSI_RESET);
                break;

            case PAGAR_BANCO:
                jogador.subtrairSaldo(val);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_SORTE_REVES, jogador, val));
                System.out.println(Constantes.ANSI_RED + "- R$ " + String.format("%.2f", val) + " deduzidos do seu saldo." + Constantes.ANSI_RESET);
                break;

            case PAGAR_OUTROS:
                // Jogador paga X para cada outro jogador
                ListaEncadeada.Node<Jogador> currOutros = jogadores.getHead();
                while (currOutros != null) {
                    Jogador outro = currOutros.data;
                    if (outro != jogador && outro.getSaldo() >= 0) { // ativos
                        jogador.subtrairSaldo(val);
                        outro.adicionarSaldo(val);
                        // Registrar transação para jogador pagador
                        jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.PAGAMENTO_SORTE_REVES, jogador, val, outro));
                    }
                    currOutros = currOutros.next;
                }
                System.out.println(Constantes.ANSI_RED + "Você pagou R$ " + String.format("%.2f", val) + " para cada um dos outros jogadores." + Constantes.ANSI_RESET);
                break;

            case IR_PRISAO:
                System.out.println("Vá diretamente para a Prisão!");
                jogador.setPreso(true);
                filaPrisao.enqueue(jogador);
                // Achar casa Prisão no tabuleiro
                Casa casaPrisao = tabuleiro.buscarCasa("Prisão");
                if (casaPrisao != null) {
                    jogador.setPosicaoAtual(casaPrisao);
                }
                System.out.println(Constantes.ANSI_PURPLE + "Você foi trancafiado na Prisão." + Constantes.ANSI_RESET);
                break;

            case VOLTAR_CASAS:
                int casasRecuar = (int) val;
                System.out.println("Voltando " + casasRecuar + " casas...");
                moverJogador(jogador, casasRecuar, false);
                break;

            case VOLTAR_CASA_ANTERIOR:
                Casa ant = jogador.getPosicaoAnterior();
                if (ant != null) {
                    System.out.println("Retornando para a casa anterior: " + ant.getNome() + "...");
                    jogador.setPosicaoAtual(ant);
                    // Como não é uma movimentação normal passo a passo, apenas reposiciona
                    System.out.println("Posição restabelecida.");
                } else {
                    System.out.println("Você ainda não se movimentou antes. Permanece na mesma casa.");
                }
                break;
        }
    }



    /**
     * Menu para gerenciar Hipotecas e Quitações.
     */
    private static void menuHipoteca(Jogador jogador) {
        System.out.println("\n--- SISTEMA DE HIPOTECAS ---");
        System.out.println("1. Hipotecar um Imóvel (Recebe 50% do valor original)");
        System.out.println("2. Quitar Hipoteca de um Imóvel (Paga 55% do valor original)");
        System.out.println("3. Voltar");
        System.out.print("Escolha: ");
        int opt = lerInteiro();

        if (opt == 1) {
            // Listar propriedades do jogador não hipotecadas
            ListaEncadeada<Casa> livres = new ListaEncadeada<>();
            ListaEncadeada.Node<Casa> curr = jogador.getPropriedades().getHead();
            int idx = 1;
            System.out.println("\nSeus Imóveis Livres:");
            while (curr != null) {
                if (!curr.data.isHipotecado()) {
                    livres.add(curr.data);
                    System.out.println(idx + ". " + curr.data.getNome() + " (Valor: R$ " + curr.data.getPrecoCompra() + " -> Recebe R$ " + curr.data.getPrecoCompra() * 0.5 + ")");
                    idx++;
                }
                curr = curr.next;
            }

            if (livres.isEmpty()) {
                System.out.println("Você não possui imóveis disponíveis para hipoteca.");
                return;
            }

            System.out.print("Escolha o imóvel para hipotecar (número): ");
            int item = lerInteiro() - 1;
            if (item >= 0 && item < livres.size()) {
                Casa c = livres.get(item);
                double valorEmprestimo = c.getPrecoCompra() * 0.5;
                c.setHipotecado(true);
                jogador.adicionarSaldo(valorEmprestimo);
                jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.HIPOTECA_IMOVEL, jogador, valorEmprestimo, c));
                System.out.println(Constantes.ANSI_GREEN + "Imóvel '" + c.getNome() + "' hipotecado. R$ " + String.format("%.2f", valorEmprestimo) + " creditados na sua conta." + Constantes.ANSI_RESET);
            }
        } else if (opt == 2) {
            // Listar propriedades hipotecadas
            ListaEncadeada<Casa> hipotecadas = new ListaEncadeada<>();
            ListaEncadeada.Node<Casa> curr = jogador.getPropriedades().getHead();
            int idx = 1;
            System.out.println("\nSeus Imóveis Hipotecados:");
            while (curr != null) {
                if (curr.data.isHipotecado()) {
                    hipotecadas.add(curr.data);
                    System.out.println(idx + ". " + curr.data.getNome() + " (Valor Original: R$ " + curr.data.getPrecoCompra() + " -> Quitação custa R$ " + curr.data.getPrecoCompra() * 0.55 + ")");
                    idx++;
                }
                curr = curr.next;
            }

            if (hipotecadas.isEmpty()) {
                System.out.println("Você não possui imóveis hipotecados.");
                return;
            }

            System.out.print("Escolha o imóvel para quitar (número): ");
            int item = lerInteiro() - 1;
            if (item >= 0 && item < hipotecadas.size()) {
                Casa c = hipotecadas.get(item);
                double custoQuitacao = c.getPrecoCompra() * 0.55;
                if (jogador.getSaldo() >= custoQuitacao) {
                    jogador.subtrairSaldo(custoQuitacao);
                    c.setHipotecado(false);
                    jogador.registrarTransacao(new UndoAction(UndoAction.TipoUndo.QUITACAO_HIPOTECA, jogador, custoQuitacao, c));
                    System.out.println(Constantes.ANSI_GREEN + "Hipoteca de '" + c.getNome() + "' quitada por R$ " + String.format("%.2f", custoQuitacao) + "." + Constantes.ANSI_RESET);
                } else {
                    System.out.println(Constantes.ANSI_RED + "Saldo insuficiente para quitar a hipoteca!" + Constantes.ANSI_RESET);
                }
            }
        }
    }

    /**
     * Sistema de negociações direta de propriedades/valores entre jogadores.
     */
    private static void menuNegociacao(Jogador proponente) {
        System.out.println("\n=== SISTEMA DE NEGOCIAÇÃO ENTRE JOGADORES ===");

        // Listar outros jogadores
        ListaEncadeada<Jogador> alvos = new ListaEncadeada<>();
        ListaEncadeada.Node<Jogador> curr = jogadores.getHead();
        int idx = 1;
        System.out.println("Selecione o jogador com quem deseja negociar:");
        while (curr != null) {
            if (curr.data != proponente && curr.data.getSaldo() >= 0) {
                alvos.add(curr.data);
                System.out.println(idx + ". " + curr.data.getNome() + " (Saldo: R$ " + curr.data.getSaldo() + ")");
                idx++;
            }
            curr = curr.next;
        }

        if (alvos.isEmpty()) {
            System.out.println("Nenhum parceiro de negócios elegível disponível.");
            return;
        }

        System.out.print("Escolha: ");
        int pIdx = lerInteiro() - 1;
        if (pIdx < 0 || pIdx >= alvos.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Jogador alvo = alvos.get(pIdx);

        // Listar imóveis do proponente
        System.out.println("\nSeus Imóveis disponíveis para oferta:");
        ListaEncadeada.Node<Casa> propNode = proponente.getPropriedades().getHead();
        int idxP = 1;
        while (propNode != null) {
            System.out.println(idxP + ". " + propNode.data.getNome() + (propNode.data.isHipotecado() ? " [Hipotecado]" : ""));
            idxP++;
            propNode = propNode.next;
        }

        System.out.print("Deseja oferecer algum imóvel? (0 para nenhum, ou número correspondente): ");
        int ofertaImovelIdx = lerInteiro() - 1;
        Casa imovelOferecido = null;
        if (ofertaImovelIdx >= 0 && ofertaImovelIdx < proponente.getPropriedades().size()) {
            imovelOferecido = proponente.getPropriedades().get(ofertaImovelIdx);
        }

        System.out.print("Deseja oferecer alguma quantia em dinheiro? R$ ");
        double dinheiroOferecido = lerDouble();
        if (dinheiroOferecido > proponente.getSaldo()) {
            System.out.println("Você não possui saldo suficiente para esta oferta.");
            return;
        }

        // Pedido de contrapartida
        System.out.println("\nImóveis de " + alvo.getNome() + " disponíveis para pedir:");
        ListaEncadeada.Node<Casa> alvoNode = alvo.getPropriedades().getHead();
        int idxA = 1;
        while (alvoNode != null) {
            System.out.println(idxA + ". " + alvoNode.data.getNome() + (alvoNode.data.isHipotecado() ? " [Hipotecado]" : ""));
            idxA++;
            alvoNode = alvoNode.next;
        }

        System.out.print("Deseja pedir algum imóvel? (0 para nenhum, ou número correspondente): ");
        int pedidoImovelIdx = lerInteiro() - 1;
        Casa imovelPedido = null;
        if (pedidoImovelIdx >= 0 && pedidoImovelIdx < alvo.getPropriedades().size()) {
            imovelPedido = alvo.getPropriedades().get(pedidoImovelIdx);
        }

        System.out.print("Deseja pedir alguma quantia em dinheiro de contrapartida? R$ ");
        double dinheiroPedido = lerDouble();
        if (dinheiroPedido > alvo.getSaldo()) {
            System.out.println(alvo.getNome() + " não tem saldo suficiente para pagar isso.");
            return;
        }

        // Apresentar proposta
        System.out.println("\n================ PROPOSTA DE ACORDO ================");
        System.out.println("De: " + proponente.getNome() + " | Para: " + alvo.getNome());
        System.out.println("OFERTA: ");
        if (imovelOferecido != null) System.out.println("  - Imóvel: " + imovelOferecido.getNome());
        if (dinheiroOferecido > 0) System.out.println("  - Dinheiro: R$ " + String.format("%.2f", dinheiroOferecido));
        if (imovelOferecido == null && dinheiroOferecido == 0) System.out.println("  - Nada");
        System.out.println("PEDIDO: ");
        if (imovelPedido != null) System.out.println("  - Imóvel: " + imovelPedido.getNome());
        if (dinheiroPedido > 0) System.out.println("  - Dinheiro: R$ " + String.format("%.2f", dinheiroPedido));
        if (imovelPedido == null && dinheiroPedido == 0) System.out.println("  - Nada");
        System.out.println("====================================================");

        System.out.print(alvo.getNome() + ", você aceita a proposta? (S/N): ");
        String aceitou = scanner.nextLine();
        if (aceitou.equalsIgnoreCase("s")) {
            // Executa transação atômica
            proponente.subtrairSaldo(dinheiroOferecido);
            proponente.adicionarSaldo(dinheiroPedido);

            alvo.subtrairSaldo(dinheiroPedido);
            alvo.adicionarSaldo(dinheiroOferecido);

            // Troca de imóveis
            if (imovelOferecido != null) {
                proponente.getPropriedades().remove(imovelOferecido);
                alvo.getPropriedades().add(imovelOferecido);
                imovelOferecido.setProprietario(alvo);
            }
            if (imovelPedido != null) {
                alvo.getPropriedades().remove(imovelPedido);
                proponente.getPropriedades().add(imovelPedido);
                imovelPedido.setProprietario(proponente);
            }

            // Nota: Transações de escopo complexo (troca) não entram no deque simples de undo individual por envolverem dois jogadores.
            // Limpamos os deques de undo de ambos os jogadores para evitar reverter lances unilaterais conflitantes.
            proponente.getHistoricoTransacoes().clear();
            alvo.getHistoricoTransacoes().clear();

            System.out.println(Constantes.ANSI_GREEN + "Negociação efetuada e transferida com sucesso!" + Constantes.ANSI_RESET);
        } else {
            System.out.println(Constantes.ANSI_YELLOW + "Negociação recusada." + Constantes.ANSI_RESET);
        }
    }

    /**
     * Ajuda o jogador com saldo negativo a hipotecar ou vender seus imóveis para evitar falência.
     */
    private static void resolverSaldoNegativo(Jogador jogador) {
        System.out.println(Constantes.ANSI_RED + "\n[ALERTA DE FALÊNCIA] " + jogador.getNome() + ", seu saldo está negativo (R$ " +
                           String.format("%.2f", jogador.getSaldo()) + ")!" + Constantes.ANSI_RESET);

        while (jogador.getSaldo() < 0 && !jogador.getPropriedades().isEmpty()) {
            System.out.println("\nVocê precisa liquidar patrimônio para cobrir sua dívida.");
            System.out.println("1. Hipotecar Imóvel (Recebe 50%)");
            System.out.println("2. Vender Imóvel para o Banco (Recebe 70% do preço de compra)");
            System.out.print("Escolha: ");
            int opt = lerInteiro();

            if (opt == 1) {
                // Hipoteca
                ListaEncadeada<Casa> livres = new ListaEncadeada<>();
                ListaEncadeada.Node<Casa> curr = jogador.getPropriedades().getHead();
                int idx = 1;
                while (curr != null) {
                    if (!curr.data.isHipotecado()) {
                        livres.add(curr.data);
                        System.out.println(idx + ". " + curr.data.getNome() + " (Preço: " + curr.data.getPrecoCompra() + " -> Recebe R$ " + curr.data.getPrecoCompra() * 0.5 + ")");
                        idx++;
                    }
                    curr = curr.next;
                }
                if (livres.isEmpty()) {
                    System.out.println("Nenhum imóvel disponível para hipotecar.");
                    continue;
                }
                System.out.print("Escolha o número do imóvel: ");
                int choice = lerInteiro() - 1;
                if (choice >= 0 && choice < livres.size()) {
                    Casa c = livres.get(choice);
                    double val = c.getPrecoCompra() * 0.5;
                    c.setHipotecado(true);
                    jogador.adicionarSaldo(val);
                    System.out.println("Imóvel '" + c.getNome() + "' hipotecado. Novo saldo: R$ " + jogador.getSaldo());
                }
            } else if (opt == 2) {
                // Vender para o Banco
                ListaEncadeada.Node<Casa> curr = jogador.getPropriedades().getHead();
                int idx = 1;
                System.out.println("\nSeus Imóveis:");
                while (curr != null) {
                    System.out.println(idx + ". " + curr.data.getNome() + " (Preço: " + curr.data.getPrecoCompra() + " -> Venda Banco: R$ " + curr.data.getPrecoCompra() * 0.70 + ")");
                    idx++;
                    curr = curr.next;
                }
                System.out.print("Escolha o número do imóvel: ");
                int choice = lerInteiro() - 1;
                if (choice >= 0 && choice < jogador.getPropriedades().size()) {
                    Casa c = jogador.getPropriedades().get(choice);
                    double val = c.getPrecoCompra() * 0.70;
                    jogador.getPropriedades().remove(c);
                    c.setProprietario(null);
                    c.setHipotecado(false);
                    c.resetMultiplicador();
                    jogador.adicionarSaldo(val);
                    System.out.println("Imóvel '" + c.getNome() + "' vendido ao banco. Novo saldo: R$ " + jogador.getSaldo());
                }
            }
        }

        if (jogador.getSaldo() < 0) {
            declararFalencia(jogador);
        }
    }

    /**
     * Declara a falência do jogador e recoloca suas propriedades no leilão.
     */
    private static void declararFalencia(Jogador jogador) {
        System.out.println(Constantes.ANSI_BG_RED + Constantes.ANSI_WHITE + "\n[FALÊNCIA] O JOGADOR " + jogador.getNome().toUpperCase() +
                           " DECLAROU FALÊNCIA E ESTÁ FORA DO JOGO!" + Constantes.ANSI_RESET);

        // Devolve todos os seus imóveis ao pool livre
        ListaEncadeada.Node<Casa> curr = jogador.getPropriedades().getHead();
        while (curr != null) {
            Casa c = curr.data;
            c.setProprietario(null);
            c.setHipotecado(false);
            c.resetMultiplicador();
            System.out.println("  - Imóvel '" + c.getNome() + "' retornou ao pool de leilão/compra livre.");
            curr = curr.next;
        }
        jogador.getPropriedades().clear();

        // Remove da fila da prisão se estivesse lá
        Fila<Jogador> tempFila = new Fila<>();
        while (!filaPrisao.isEmpty()) {
            Jogador p = filaPrisao.dequeue();
            if (p != jogador) {
                tempFila.enqueue(p);
            }
        }
        while (!tempFila.isEmpty()) {
            filaPrisao.enqueue(tempFila.dequeue());
        }

        // Define saldo negativo extremo para identificar fora do jogo
        jogador.setSaldo(-9999999.0);
    }

    /**
     * Reconstrói a BST de classificação.
     */
    private static void reconstruirBST() {
        rankingTree.clear();
        ListaEncadeada.Node<Jogador> curr = jogadores.getHead();
        while (curr != null) {
            if (curr.data.getSaldo() > -999999.0) { // Não falidos
                rankingTree.insert(curr.data);
            }
            curr = curr.next;
        }
    }



    /**
     * Gera os relatórios de fim de partida.
     */
    private static void encerrarPartida() {
        System.out.println(Constantes.ANSI_GREEN + "\n\n=====================================================================");
        System.out.println("                     FIM DE JOGO - PARTIDA CONCLUÍDA                 ");
        System.out.println("=====================================================================" + Constantes.ANSI_RESET);

        // 1. Classificação final via BST
        reconstruirBST();
        rankingTree.exibirRanking();

        // 2. Estatísticas de voltas e recordes
        System.out.println("\n--- ESTATÍSTICAS DOS JOGADORES ---");
        ListaEncadeada.Node<Jogador> curr = jogadores.getHead();
        while (curr != null) {
            if (curr.data.getSaldo() > -999999.0) {
                System.out.println(String.format("  * %-10s | Voltas Completas: %d", curr.data.getNome(), curr.data.getVoltasCompletas()));
            } else {
                System.out.println(String.format("  * %-10s | FALIDO (Voltas completadas: %d)", curr.data.getNome(), curr.data.getVoltasCompletas()));
            }
            curr = curr.next;
        }

        // Imóvel recordista de aluguel
        System.out.println("\nImóvel recordista em valor de aluguel cobrado: " + Constantes.ANSI_BOLD + imovelMaiorAluguelCobrado +
                           Constantes.ANSI_RESET + " (R$ " + String.format("%.2f", valorMaiorAluguelCobrado) + ")");

        System.out.println("\nObrigado por rodar a simulação! Pressione ENTER para retornar ao menu principal.");
        scanner.nextLine();
        menuPrincipal();
    }

    // Auxiliares de leitura
    private static int lerInteiro() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print(Constantes.ANSI_RED + "Entrada inválida! Digite um número inteiro: " + Constantes.ANSI_RESET);
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.print(Constantes.ANSI_RED + "Entrada inválida! Digite um valor numérico: " + Constantes.ANSI_RESET);
            }
        }
    }
}
