/*
Alunos:
- Pedro Henrique Carvalho Pereira - 10418861
- Mateus Ribeiro Cerqueira - 10443901
*/

public class Main {

    // Executa Prim, imprime as arestas da AGM e o peso total. Retorna o peso total (-1 se desconexo).
    static int executaPrim(TGrafo g, int origem, String[] rotulos) {
        int[] ant = g.Prim(origem);
        int total = 0, arestas = 0;

        System.out.println("Prim a partir de " + rotulos[origem] + ":");
        for (int v = 0; v < g.getN(); v++) {
            if (ant[v] == -1) continue;
            int peso = g.getPeso(ant[v], v);
            System.out.println("  " + rotulos[ant[v]] + " - " + rotulos[v] + "  (peso " + peso + ")");
            total += peso;
            arestas++;
        }
        if (arestas < g.getN() - 1) {
            System.out.println("  Grafo desconexo: AGM não existe (floresta com " + arestas + " arestas, peso " + total + ").");
            return -1;
        }
        System.out.println("  Peso total da AGM: " + total);
        return total;
    }

    // Grafo 1 (material da aula): vértices 1..9 -> índices 0..8
    static TGrafo grafoAula() {
        TGrafo g = new TGrafo(9);
        int[][] arestas = {
            {1,2,4}, {1,6,5}, {2,3,7}, {2,6,3}, {3,4,5}, {3,7,6}, {4,5,3},
            {5,7,2}, {5,8,4}, {6,7,7}, {6,9,5}, {7,8,6}, {8,9,8}
        };
        for (int[] a : arestas) g.insereA(a[0] - 1, a[1] - 1, a[2]);
        return g;
    }

    // Grafo 2 (atividade anterior): a..f -> índices 0..5
    static TGrafo grafoAtividade() {
        TGrafo g = new TGrafo(6);
        int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5;
        g.insereA(a, e, 8);
        g.insereA(a, b, 6);
        g.insereA(a, c, 15);
        g.insereA(b, e, 10);
        g.insereA(b, f, 8);
        g.insereA(b, c, 20);
        g.insereA(e, f, 5);
        g.insereA(f, d, 7);
        g.insereA(c, d, 9);
        return g;
    }

    public static void main(String[] args) {
        String[] rot1 = {"1","2","3","4","5","6","7","8","9"};
        String[] rot2 = {"a","b","c","d","e","f"};

        System.out.println("=== GRAFO 1 (Material da Aula) ===");
        TGrafo g1 = grafoAula();
        g1.show();
        System.out.println();
        int t1 = executaPrim(g1, 0, rot1);
        System.out.println("  Esperado: 33 -> " + (t1 == 33 ? "OK" : "FALHOU"));
        // A AGM deve ter o mesmo peso independentemente da origem
        boolean todasIguais = true;
        for (int o = 1; o < g1.getN(); o++) {
            java.io.PrintStream out = System.out;
            System.setOut(new java.io.PrintStream(new java.io.OutputStream() { public void write(int b) {} }));
            int t = executaPrim(g1, o, rot1);
            System.setOut(out);
            if (t != 33) todasIguais = false;
        }
        System.out.println("  Peso 33 a partir de qualquer origem: " + (todasIguais ? "OK" : "FALHOU"));

        System.out.println("\n=== GRAFO 2 (Atividade Anterior) ===");
        TGrafo g2 = grafoAtividade();
        g2.show();
        System.out.println();
        int t2 = executaPrim(g2, 0, rot2);
        System.out.println("  Esperado: 35 -> " + (t2 == 35 ? "OK" : "FALHOU"));

        System.out.println("\n=== EXTRA: grafo desconexo ===");
        TGrafo g3 = new TGrafo(4);
        g3.insereA(0, 1, 1);
        g3.insereA(2, 3, 2);
        executaPrim(g3, 0, new String[]{"A","B","C","D"});
    }
}