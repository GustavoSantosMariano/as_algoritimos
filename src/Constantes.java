/**
 * Constantes globais do jogo, incluindo cores ANSI e presets de propriedades.
 */
public class Constantes {
    // Cores ANSI para formatar o terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Estilos de texto
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";

    // Fundos de cor
    public static final String ANSI_BG_RED = "\u001B[41m";
    public static final String ANSI_BG_GREEN = "\u001B[42m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    public static final String ANSI_BG_BLUE = "\u001B[44m";
    public static final String ANSI_BG_PURPLE = "\u001B[45m";
    public static final String ANSI_BG_CYAN = "\u001B[46m";

    // Estruturas de dados pré-cadastradas para os Imóveis dos Anexos

    public static class PresetImovel {
        public String nome;
        public double precoCompra;
        public double aluguelBase;

        public PresetImovel(String nome, double precoCompra, double aluguelBase) {
            this.nome = nome;
            this.precoCompra = precoCompra;
            this.aluguelBase = aluguelBase;
        }
    }

    // Anexo A - Cidades Brasileiras
    public static final PresetImovel[] ANEXO_A = new PresetImovel[] {
        new PresetImovel("Chalé da Serra Gaúcha", 200000.0, 1000.0),
        new PresetImovel("Flat Paulista", 350000.0, 1750.0),
        new PresetImovel("Sobrado de Ouro Preto", 400000.0, 2000.0),
        new PresetImovel("Pousada do Pantanal", 500000.0, 2500.0),
        new PresetImovel("Mansão de Gramado", 600000.0, 3000.0),
        new PresetImovel("Cobertura de Florianópolis", 750000.0, 3750.0),
        new PresetImovel("Fazenda do Cerrado", 280000.0, 1400.0),
        new PresetImovel("Bangalô de Búzios", 450000.0, 2250.0),
        new PresetImovel("Penthouse de Salvador", 850000.0, 4250.0),
        new PresetImovel("Casa de Bonito", 220000.0, 1100.0),
        new PresetImovel("Palacete de Petrópolis", 1000000.0, 5000.0),
        new PresetImovel("Rancho do Vale do São Francisco", 310000.0, 1550.0)
    };

    // Anexo B - Mitologia e Fantasia
    public static final PresetImovel[] ANEXO_B = new PresetImovel[] {
        new PresetImovel("Cabana do Druida", 200000.0, 1000.0),
        new PresetImovel("Torre do Mago", 380000.0, 1900.0),
        new PresetImovel("Fortaleza do Guardião", 470000.0, 2350.0),
        new PresetImovel("Castelo do Dragão", 620000.0, 3100.0),
        new PresetImovel("Palácio da Fênix", 800000.0, 4000.0),
        new PresetImovel("Gruta do Oráculo", 250000.0, 1250.0),
        new PresetImovel("Templo de Atena", 550000.0, 2750.0),
        new PresetImovel("Mansão do Vampiro", 430000.0, 2150.0),
        new PresetImovel("Cidadela dos Elfos", 700000.0, 3500.0),
        new PresetImovel("Covil do Minotauro", 210000.0, 1050.0),
        new PresetImovel("Palácios dos Titãs", 950000.0, 4750.0),
        new PresetImovel("Santuário do Grifo", 330000.0, 1650.0)
    };

    // Anexo C - Espaço e Ficção Científica
    public static final PresetImovel[] ANEXO_C = new PresetImovel[] {
        new PresetImovel("Módulo Lunar Alfa", 200000.0, 10000.0),
        new PresetImovel("Estação Orbital Beta", 420000.0, 21000.0),
        new PresetImovel("Colônia de Marte", 500000.0, 25000.0),
        new PresetImovel("Domo de Titã", 350000.0, 17500.0),
        new PresetImovel("Nave-Mãe Andrômeda", 750000.0, 37500.0),
        new PresetImovel("Bunker Subterrâneo", 230000.0, 11500.0),
        new PresetImovel("Plataforma de Europa", 600000.0, 30000.0),
        new PresetImovel("Cúpula de Kepler-22b", 870000.0, 43500.0),
        new PresetImovel("Laboratório do Asteroid Belt", 460000.0, 23000.0),
        new PresetImovel("Refúgio de Plutão", 210000.0, 10500.0),
        new PresetImovel("Torre de Observação Solar", 1000000.0, 50000.0),
        new PresetImovel("Base Antártica Omega", 290000.0, 14500.0)
    };
}
