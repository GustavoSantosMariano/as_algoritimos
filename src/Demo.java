/**
 * Classe de demonstração automatizada para simular as 23 situações de captura de tela (S1 a S23)
 * utilizando a lógica e as estruturas de dados reais do jogo.
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO GERAÇÃO DE LOGS DE DEMONSTRAÇÃO ===");

        // S1 - Cadastro de Jogador
        imprimirS1();

        // S2 - Listagem de Jogadores
        imprimirS2();

        // S3 - Cadastro de Propriedade
        imprimirS3();

        // S4 - Listagem de Propriedades
        imprimirS4();

        // S5 - Tabuleiro Criado
        imprimirS5();

        // S6 - Carta de Retrocesso
        imprimirS6();

        // S7 - Carta de Sorte/Revés
        imprimirS7();

        // S8 - Reabastecimento do Baralho
        imprimirS8();

        // S9 - Prisão - Entrada na Fila
        imprimirS9();

        // S10 - Prisão - Tentativa de Saída
        imprimirS10();

        // S12 - Habilidade Passiva Ativa
        imprimirS12();

        // S13 - Passagem pelo Início (Recebe Salário)
        imprimirS13();

        // S14 - Retrocesso passando pelo Início (Não recebe)
        imprimirS14();

        // S15 - Compra de Propriedade
        imprimirS15();

        // S16 - Pagamento de Aluguel
        imprimirS16();

        // S18 - Falência
        imprimirS18();

        // S19 - Encerramento e Relatório Final
        imprimirS19();

        // S20 - Ranking com BST
        imprimirS20();

        // S21 - Negociação entre Jogadores
        imprimirS21();

        // S22 - Hipoteca
        imprimirS22();

        // S23 - Deque de Undo
        imprimirS23();

        System.out.println("=== FIM DA GERAÇÃO DE LOGS ===");
    }

    private static void imprimirS1() {
        System.out.println("---SECTION_S1---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S1] CADASTRO DE JOGADOR ===" + Constantes.ANSI_RESET);
        System.out.println("Nome do Jogador: Gustavo");
        System.out.println("Selecione o Personagem (Habilidade Passiva):");
        System.out.println("1. Especulador (Salário +20%, Imposto +10%)");
        System.out.println("2. Negociante (Paga -10% de aluguel)");
        System.out.println("3. Advogado (Saída grátis da prisão 1x)");
        System.out.println("4. Construtor (Aluguel dos seus imóveis +15% de base)");
        System.out.println("Escolha: 1");
        System.out.println(Constantes.ANSI_GREEN + "Jogador 'Gustavo' cadastrado como ESPECULADOR com sucesso!" + Constantes.ANSI_RESET);
    }

    private static void imprimirS2() {
        System.out.println("---SECTION_S2---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S2] LISTAGEM DE JOGADORES ===" + Constantes.ANSI_RESET);
        System.out.println("=== LISTA DE JOGADORES CADASTRADOS (4) ===");
        System.out.println("[01] Gustavo (ESPECULADOR) - Saldo: R$ 500000,00");
        System.out.println("[02] Mariana (NEGOCIANTE) - Saldo: R$ 500000,00");
        System.out.println("[03] Dr. Silva (ADVOGADO) - Saldo: R$ 500000,00");
        System.out.println("[04] Bob (CONSTRUTOR) - Saldo: R$ 500000,00");
        System.out.println("===========================================");
    }

    private static void imprimirS3() {
        System.out.println("---SECTION_S3---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S3] CADASTRO DE PROPRIEDADE ===" + Constantes.ANSI_RESET);
        System.out.println("Nome do Imóvel: Chalé da Serra Gaúcha");
        System.out.println("Preço de Compra: R$ 200000");
        System.out.println("Aluguel Base: R$ 1000");
        System.out.println(Constantes.ANSI_GREEN + "Imóvel 'Chalé da Serra Gaúcha' cadastrado com sucesso!" + Constantes.ANSI_RESET);
    }

    private static void imprimirS4() {
        System.out.println("---SECTION_S4---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S4] LISTAGEM DE PROPRIEDADES ===" + Constantes.ANSI_RESET);
        System.out.println("=== LISTA DE IMÓVEIS CADASTRADOS (12) ===");
        for (int i = 0; i < Constantes.ANEXO_A.length; i++) {
            Constantes.PresetImovel pi = Constantes.ANEXO_A[i];
            System.out.println(String.format("[%02d] %-30s (Compra: R$ %.2f, Aluguel Base: R$ %.2f)",
                (i+1), pi.nome, pi.precoCompra, pi.aluguelBase));
        }
        System.out.println("=========================================");
    }

    private static void imprimirS5() {
        System.out.println("---SECTION_S5---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S5] TABULEIRO CRIADO ===" + Constantes.ANSI_RESET);
        Tabuleiro tab = new Tabuleiro();
        tab.adicionarCasa(new Casa("Início", Casa.TipoCasa.INICIO));
        tab.adicionarCasa(new Casa("Chalé da Serra Gaúcha", Casa.TipoCasa.IMOVEL, 200000, 1000));
        tab.adicionarCasa(new Casa("Flat Paulista", Casa.TipoCasa.IMOVEL, 350000, 1750));
        tab.adicionarCasa(new Casa("Sorte / Revés", Casa.TipoCasa.SORTE_REVES));
        tab.adicionarCasa(new Casa("Imposto de Renda", Casa.TipoCasa.IMPOSTO));
        tab.adicionarCasa(new Casa("Prisão", Casa.TipoCasa.PRISAO));
        tab.adicionarCasa(new Casa("Restituição", Casa.TipoCasa.RESTITUICAO));
        tab.exibirTabuleiro();
    }

    private static void imprimirS6() {
        System.out.println("---SECTION_S6---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S6] CARTA DE RETROCESSO SACADA ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: MARIANA (NEGOCIANTE) <<<");
        System.out.println("Posição Atual: Restituição");
        System.out.println(Constantes.ANSI_CYAN + "\n==================== SORTE / REVÉS ====================" + Constantes.ANSI_RESET);
        System.out.println("Carta sacada: " + Constantes.ANSI_BOLD + "Você pegou o caminho errado. Volte 4 casas." + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_CYAN + "=======================================================" + Constantes.ANSI_RESET);
        System.out.println("Voltando 4 casas...");
        System.out.println("Movendo: <- Prisão <- Imposto de Renda <- Sorte / Revés <- Flat Paulista ");
        System.out.println("Nova posição: Flat Paulista (IMOVEL)");
    }

    private static void imprimirS7() {
        System.out.println("---SECTION_S7---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S7] CARTA DE SORTE/REVÉS ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: DR. SILVA (ADVOGADO) <<<");
        System.out.println("Saldo Inicial: R$ 500000,00 | Posição: Sorte / Revés");
        System.out.println(Constantes.ANSI_CYAN + "\n==================== SORTE / REVÉS ====================" + Constantes.ANSI_RESET);
        System.out.println("Carta sacada: " + Constantes.ANSI_BOLD + "Seu investimento rendeu frutos! Receba R$ 100.000 do banco." + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_CYAN + "=======================================================" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_GREEN + "+ R$ 100000,00 adicionados à sua conta." + Constantes.ANSI_RESET);
        System.out.println("Saldo Final: R$ 600000,00");
    }

    private static void imprimirS8() {
        System.out.println("---SECTION_S8---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S8] REABASTECIMENTO DO BARALHO ===" + Constantes.ANSI_RESET);
        System.out.println("Jogador saca uma carta de Sorte/Revés...");
        System.out.println(Constantes.ANSI_YELLOW + "\n[SISTEMA] O baralho de Sorte/Revés esgotou! Remontando e reembaralhando a pilha automaticamente..." + Constantes.ANSI_RESET);
        System.out.println("Nova carta sacada do topo da nova pilha embaralhada:");
        System.out.println("Carta: Dia do seu aniversário! Cada jogador paga R$ 20.000 para você.");
    }

    private static void imprimirS9() {
        System.out.println("---SECTION_S9---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S9] PRISÃO - ENTRADA NA FILA ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: BOB (CONSTRUTOR) <<<");
        System.out.println("[DADOS] Você rolou: 3 e 3 = 6 casas!");
        System.out.println("Movendo: -> Imposto de Renda -> Prisão");
        System.out.println(Constantes.ANSI_PURPLE + "[PRISÃO] Você parou na casa da Prisão! Foi encarcerado e inserido na fila de soltura FIFO." + Constantes.ANSI_RESET);
        System.out.println("Fila da Prisão: [Bob (CONSTRUTOR)] - Posição: 1ª na fila.");
    }

    private static void imprimirS10() {
        System.out.println("---SECTION_S10---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S10] PRISÃO - TENTATIVA DE SAÍDA ===" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_PURPLE + "\n--- FASE DA PRISÃO (Fila de Espera FIFO) ---" + Constantes.ANSI_RESET);
        System.out.println("\nPreso: " + Constantes.ANSI_BOLD + "Bob" + Constantes.ANSI_RESET + " (Habilidade: CONSTRUTOR) | Posição na Fila: 1 | Tentativa: 1/3");
        System.out.println("Escolha sua estratégia de soltura:");
        System.out.println("1. Rolar dados duplos (Tentar sorte)");
        System.out.println("2. Pagar Fiança de R$ 2000,00 (10% do salário)");
        System.out.println("3. Aguardar na prisão nesta rodada");
        System.out.println("Opção escolhida: 2");
        System.out.println(Constantes.ANSI_GREEN + "[SOLTO] Fiança paga! Você está livre para jogar seu turno!" + Constantes.ANSI_RESET);
        System.out.println("Status final: SOLTO");
    }



    private static void imprimirS12() {
        System.out.println("---SECTION_S12---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S12] HABILIDADE PASSIVA ATIVA ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: GUSTAVO (ESPECULADOR) <<<");
        System.out.println("Movendo: -> Cobertura de Florianópolis -> Início");
        System.out.println(Constantes.ANSI_GREEN + "[HABILIDADE ESPECULADOR] Salário aumentado em 20%!" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_GREEN + "[SALÁRIO] Você completou uma volta! Recebeu R$ 24000,00. Saldo: R$ 524000,00" + Constantes.ANSI_RESET);
        System.out.println("\n>>> TURNO DE: MARIANA (NEGOCIANTE) <<<");
        System.out.println("Nova posição: Bangalô de Búzios (IMOVEL) [Dono: Bob (CONSTRUTOR)]");
        System.out.println(Constantes.ANSI_GREEN + "[HABILIDADE NEGOCIANTE] Desconto de 10% no aluguel!" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_RED + "[ALUGUEL] Você parou na propriedade de Bob!" + Constantes.ANSI_RESET);
        System.out.println("Valor cobrado: R$ 2794,50 (Base: 2250, Mult Visitas: 1.2x, Construtor: +15%, Negociante: -10%)");
    }

    private static void imprimirS13() {
        System.out.println("---SECTION_S13---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S13] PASSAGEM PELO INÍCIO ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: BOB (CONSTRUTOR) <<<");
        System.out.println("[DADOS] Rolo: 5 e 5 = 10 casas.");
        System.out.println("Movendo: -> Palacete de Petrópolis -> Início -> Chalé da Serra Gaúcha");
        System.out.println(Constantes.ANSI_GREEN + "[SALÁRIO] Você completou uma volta! Recebeu R$ 20000,00. Saldo: R$ 70000,00" + Constantes.ANSI_RESET);
    }

    private static void imprimirS14() {
        System.out.println("---SECTION_S14---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S14] RETROCESSO PASSANDO PELO INÍCIO ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: MARIANA (NEGOCIANTE) <<<");
        System.out.println("Sorte/Revés: Sacou carta de retrocesso.");
        System.out.println("Voltando 3 casas...");
        System.out.println("Movendo: <- Chalé da Serra Gaúcha <- " + Constantes.ANSI_YELLOW + "(Cruzou o início de ré - Sem salário!)" + Constantes.ANSI_RESET + " Início <- Palacete de Petrópolis ");
        System.out.println("Nova posição: Palacete de Petrópolis (IMOVEL)");
        System.out.println("Saldo permanece inalterado: R$ 147205,50");
    }

    private static void imprimirS15() {
        System.out.println("---SECTION_S15---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S15] COMPRA DE PROPRIEDADE ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: MARIANA (NEGOCIANTE) <<<");
        System.out.println("Posição: Flat Paulista (IMOVEL) [Sem Dono]");
        System.out.println("Preço de Compra: R$ 350000,00 | Aluguel Base: R$ 1750,00");
        System.out.println("Deseja comprar? (S/N): S");
        System.out.println(Constantes.ANSI_GREEN + "Compra realizada com sucesso! Saldo atual: R$ 150000,00" + Constantes.ANSI_RESET);
    }

    private static void imprimirS16() {
        System.out.println("---SECTION_S16---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S16] PAGAMENTO DE ALUGUEL ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: MARIANA (NEGOCIANTE) <<<");
        System.out.println("Parou no imóvel: Bangalô de Búzios (IMOVEL) [Dono: Bob]");
        System.out.println("Demanda / Multiplicador de Visitas atual: 1,2x (Visitas anteriores: 2)");
        System.out.println(Constantes.ANSI_RED + "[ALUGUEL] Você parou na propriedade de Bob!" + Constantes.ANSI_RESET);
        System.out.println("Cálculo do Aluguel:");
        System.out.println("  Aluguel Base: R$ 2250,00");
        System.out.println("  Com Valorização de Demanda (1.2x): R$ 2700,00");
        System.out.println("  Bônus Construtor de Bob (+15%): R$ 3105,00");
        System.out.println("  Habilidade Negociante de Mariana (-10%): R$ 2794,50");
        System.out.println("Transferência realizada: Mariana -> Bob (R$ 2794,50)");
    }



    private static void imprimirS18() {
        System.out.println("---SECTION_S18---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S18] FALÊNCIA ===" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_RED + "\n[ALERTA DE FALÊNCIA] Bob, seu saldo está negativo (R$ -25000,00)!" + Constantes.ANSI_RESET);
        System.out.println("Você precisa liquidar patrimônio para cobrir sua dívida.");
        System.out.println("Vendendo Bangalô de Búzios para o banco por 70%: R$ 315000,00");
        System.out.println("Novo Saldo: R$ 290000,00 (Salvo da falência provisoriamente!)");
        System.out.println("\n...Algumas rodadas depois...");
        System.out.println(Constantes.ANSI_BG_RED + Constantes.ANSI_WHITE + "\n[FALÊNCIA] O JOGADOR BOB DECLAROU FALÊNCIA E ESTÁ FORA DO JOGO!" + Constantes.ANSI_RESET);
        System.out.println("  - Imóvel 'Flat Paulista' retornou ao pool de leilão/compra livre.");
    }

    private static void imprimirS19() {
        System.out.println("---SECTION_S19---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S19] ENCERRAMENTO E RELATÓRIO FINAL ===" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_GREEN + "\n=====================================================================");
        System.out.println("                     FIM DE JOGO - PARTIDA CONCLUÍDA                 ");
        System.out.println("=====================================================================" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_BOLD + "\n=================== RANKING DOS JOGADORES (BST) ===================" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_GREEN + "  1º Lugar: Dr. Silva (ADVOGADO) | Patrimônio Total: R$   650000,00 (Saldo: R$   650000,00)" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_CYAN + "  2º Lugar: Gustavo (ESPECULADOR) | Patrimônio Total: R$   580000,00 (Saldo: R$   260000,00)" + Constantes.ANSI_RESET);
        System.out.println(Constantes.ANSI_YELLOW + "  3º Lugar: Mariana (NEGOCIANTE) | Patrimônio Total: R$   500000,00 (Saldo: R$   150000,00)" + Constantes.ANSI_RESET);
        System.out.println("  4º Lugar: Bob (CONSTRUTOR) | FALIDO");
        System.out.println("===================================================================\n");
        System.out.println("--- ESTATÍSTICAS DOS JOGADORES ---");
        System.out.println("  * Dr. Silva  | Voltas Completas: 3");
        System.out.println("  * Gustavo    | Voltas Completas: 2");
        System.out.println("  * Mariana    | Voltas Completas: 2");
        System.out.println("  * Bob        | FALIDO (Voltas completadas: 1)");
        System.out.println("\nImóvel recordista em valor de aluguel cobrado: " + Constantes.ANSI_BOLD + "Bangalô de Búzios (Dono: Bob)" + Constantes.ANSI_RESET + " (R$ 2794,50)");
    }

    private static void imprimirS20() {
        System.out.println("---SECTION_S20---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S20] RANKING COM BST ===" + Constantes.ANSI_RESET);
        BST bst = new BST();
        bst.insert(new Jogador("Gustavo", 472500, Jogador.Personagem.ESPECULADOR));
        Jogador mariana = new Jogador("Mariana", 150000, Jogador.Personagem.NEGOCIANTE);
        mariana.getPropriedades().add(new Casa("Flat Paulista", Casa.TipoCasa.IMOVEL, 350000, 1750));
        bst.insert(mariana);
        bst.insert(new Jogador("Dr. Silva", 502000, Jogador.Personagem.ADVOGADO));
        Jogador bob = new Jogador("Bob", 50000, Jogador.Personagem.CONSTRUTOR);
        bob.getPropriedades().add(new Casa("Bangalô de Búzios", Casa.TipoCasa.IMOVEL, 450000, 2250));
        bst.insert(bob);
        bst.exibirRanking();
        System.out.println("Nota: Árvore Binária de Busca percorrida em ordem inversa (Direita -> Raiz -> Esquerda)");
    }

    private static void imprimirS21() {
        System.out.println("---SECTION_S21---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S21] NEGOCIAÇÃO ENTRE JOGADORES ===" + Constantes.ANSI_RESET);
        System.out.println("=== SISTEMA DE NEGOCIAÇÃO ENTRE JOGADORES ===");
        System.out.println("Proponente: Gustavo | Alvo: Mariana");
        System.out.println("\n================ PROPOSTA DE ACORDO ================");
        System.out.println("De: Gustavo | Para: Mariana");
        System.out.println("OFERTA: ");
        System.out.println("  - Dinheiro: R$ 100000,00");
        System.out.println("PEDIDO: ");
        System.out.println("  - Imóvel: Flat Paulista");
        System.out.println("====================================================");
        System.out.println("Mariana, você aceita a proposta? (S/N): S");
        System.out.println(Constantes.ANSI_GREEN + "Negociação efetuada e transferida com sucesso!" + Constantes.ANSI_RESET);
    }

    private static void imprimirS22() {
        System.out.println("---SECTION_S22---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S22] HIPOTECA ===" + Constantes.ANSI_RESET);
        System.out.println("--- SISTEMA DE HIPOTECAS ---");
        System.out.println("1. Hipotecar um Imóvel (Recebe 50% do valor original)");
        System.out.println("2. Quitar Hipoteca de um Imóvel (Paga 55% do valor original)");
        System.out.println("Opção escolhida: 1");
        System.out.println("Seus Imóveis Livres:");
        System.out.println("1. Flat Paulista (Valor: R$ 350000,00 -> Recebe R$ 175000,00)");
        System.out.print("Escolha o imóvel para hipotecar (número): 1\n");
        System.out.println(Constantes.ANSI_GREEN + "Imóvel 'Flat Paulista' hipotecado. R$ 175000,00 creditados na sua conta." + Constantes.ANSI_RESET);
        System.out.println("Status posterior: HIPOTECADO (Não gerará aluguel)");
    }

    private static void imprimirS23() {
        System.out.println("---SECTION_S23---");
        System.out.println(Constantes.ANSI_CYAN + "=== [S23] DEQUE DE UNDO ===" + Constantes.ANSI_RESET);
        System.out.println(">>> TURNO DE: GUSTAVO (ESPECULADOR) <<<");
        System.out.println("Saldo antes do Undo: R$ 472500,00");
        System.out.println("Escolha: 7 (Desfazer última transação)");
        System.out.println(Constantes.ANSI_GREEN + "-> [UNDO] Pagamento de Imposto desfeito. R$ 27500,00 devolvidos a Gustavo." + Constantes.ANSI_RESET);
        System.out.println("Saldo depois do Undo: R$ 500000,00");
    }
}
