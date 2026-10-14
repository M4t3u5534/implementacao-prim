/*
Alunos:
- Pedro Henrique Carvalho Pereira - 10418861
- Mateus Ribeiro Cerqueira - 10443901
*/

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet; // ordem crescente automaticamente

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafo {
	// Atributos Privados
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	int adj[][]; //matriz de adjacência (INF = sem aresta; valor != 2^31^-1 (INF) = peso da aresta)
    
	private static final int INF = Integer.MAX_VALUE;

	// Métodos Públicos
	public TGrafo( int n) {  // construtor
	    this.n = n;
	    // No início dos tempos não há arestas
	    this.m = 0; 
	    // alocação da matriz do TGrafo
	    this.adj = new int [n][n];

	    // Inicia a matriz com infinito
		for(int i = 0; i< n; i++)
			for(int j = 0; j< n; j++)
				this.adj[i][j]=INF;
	}

	/*
	Insere uma aresta v<->w com peso. O peso possui a única limitação de ser diferente de 2^31^-1 (INF),
    uma vez que o algoritmo de Prim não possui restricoes quanto ao peso das arestas.
	O valor 2^31^-1 (INF) na matriz significa que não existe aresta.
	*/
	public void insereA(int v, int w, int peso) {
	    // testa se nao temos a aresta
	    if(adj[v][w] == INF && peso != INF){
	        adj[v][w] = peso;
            adj[w][v] = peso;
	        m++; // atualiza qtd arestas
	    }
	}
	
	// remove uma aresta v<->w do Grafo	
	public void removeA(int v, int w) {
	    // testa se temos a aresta
	    if(adj[v][w] != INF ){
	        adj[v][w] = INF;
            adj[w][v] = INF;
	        m--; // atualiza qtd arestas
	    }
	}
    
	// Apresenta o Grafo contendo
	// número de vértices, arestas
	// e a matriz de adjacência obtida	
	public void show() {
	    System.out.println("n: " + n );
	    System.out.println("m: " + m );
	    for( int i=0; i < n; i++){
	    	System.out.print("\n");
	        for( int w=0; w < n; w++){
                if (adj[i][w] == INF) System.out.print("Adj[" + i + "," + w + "]= - ");
                else System.out.print("Adj[" + i + "," + w + "]= " + adj[i][w] + " ");
            }
	    }
	    System.out.println("\n\nfim da impressao do grafo." );
	}

    public int getN() { return n; }
	public int getM() { return m; }
    public int getPeso(int v, int w) { return adj[v][w]; }


	// Prim


	public int[] Prim(int origem) {
        Set<Integer> conect = new TreeSet<Integer>();

        int[] ant = new int[n];
		Arrays.fill(ant, -1);

        // INICIALIZAÇÃO
		conect.add(origem);

        PrimRecursivo(conect, ant);

        return ant;
	}


    private void PrimRecursivo(Set<Integer> conect, int[] ant) {

        if (conect.size() == n) return;

        int r = -1;
        int origemR = -1;
        int p = INF;

        for (int i : conect) {

            for (int j = 0; j < n; j++) {

                if (conect.contains(j)) continue;

                if (adj[i][j] != INF && adj[i][j] < p) {
                    r = j;
                    origemR = i;
                    p = adj[i][j];
                }
            }
        }

        // sem aresta válida = grafo desconexo
        if (r == -1) return;

        ant[r] = origemR;
        conect.add(r);

        PrimRecursivo(conect, ant);
	}
}