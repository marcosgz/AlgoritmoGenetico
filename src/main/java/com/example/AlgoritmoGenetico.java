package com.example;

public class AlgoritmoGenetico {
  // public static final int TAM_POPULACAO = 100;
  // public static final int GERACOES = 9000;
  // public static final int NUM_CRUZAMENTOS = 20;
  // public static final double PROB_CRUZAMENTO = 0.8;
  // public static final double PROB_MUTACAO = 0.1;
  // public static final String CROMOSSOMOS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  // public static final String ALVO = "Manfred Heil";

  public static final int TAM_POPULACAO = 100;
  public static final int GERACOES = 50;
  public static final int NUM_CRUZAMENTOS = 20;
  public static final double PROB_CRUZAMENTO = 0.8;
  public static final double PROB_MUTACAO = 0.1;
  public static final String CROMOSSOMOS = "01";
  public static final String ALVO = "11111111111111111111";

  public static void main(String[] args) {
    Individuo[] populacao = new Individuo[TAM_POPULACAO];

    System.out.println("Alvo: " + ALVO);
    iniciaPopulacao(populacao);
    System.out.println("------------------------");

    Individuo melhorIndividuo = getMelhorIndividuo(populacao);
    exibeMelhorIndividuo(0, melhorIndividuo);

    for (int i = 0; i < GERACOES; i++) {
      for (int j = 0; j < NUM_CRUZAMENTOS; j++) {
        //int probabilidade = (int) Math.random();
        double  probabilidade = Math.random();

        if (probabilidade < PROB_CRUZAMENTO) {
          int pai = (int) (Math.random() * TAM_POPULACAO);
          int mae = pai;
          // faz loop ate encontrar individuo diferente para ser cruzado
          while (pai == mae) {
            mae = (int) (Math.random() * TAM_POPULACAO);
          }

          Individuo filho = cruzarIndividuo(populacao[pai], populacao[mae]);

          if (probabilidade < PROB_MUTACAO) {
            mutarIndividuo(filho);
          }

          // fitness dos individuos.
          int pontuacaoPai = populacao[pai].getPontuacao();
          int pontuacaoMae = populacao[mae].getPontuacao();
          int pontuacaoFilho = filho.getPontuacao();

          // se o filho for melhor que o pai ou mae, entao substitui.
          if (pontuacaoFilho > pontuacaoPai) {
            populacao[pai] = filho;
          } else if (pontuacaoFilho > pontuacaoMae) {
            populacao[mae] = filho;
          }
        }
      }

      Individuo melhorIndividuoAtual = getMelhorIndividuo(populacao);
      if (melhorIndividuoAtual.getPontuacao() > melhorIndividuo.getPontuacao()) {
        melhorIndividuo = melhorIndividuoAtual;
        exibeMelhorIndividuo(i+1, melhorIndividuo);
      }
      if (melhorIndividuo.getPontuacao() == ALVO.length()) {
        break;
      }
    }
  }

  public static void exibeMelhorIndividuo(int interacao, Individuo melhorIndividuo) {
    System.out.printf("[%d] fitness %d: ", interacao, melhorIndividuo.getPontuacao());
    for (int i = 0; i < ALVO.length(); i++) {
      System.out.printf("%c", melhorIndividuo.getGene(i));
    }
    System.out.println();
  }

  public static void iniciaPopulacao(Individuo[] populacao) {
    for (int i = 0; i < TAM_POPULACAO; i++) {
      populacao[i] = new Individuo();
    }
    // for (int i = 0; i < 10; i++) {
    //   for (char gene : ALVO.toCharArray()) {
    //     int individuoIndex = (int) (Math.random() * TAM_POPULACAO);
    //     int geneIndex = (int) (Math.random() * ALVO.length());
    //     populacao[individuoIndex].setGene(geneIndex, gene);
    //   }
    // }
  }

  // Crossover
  // Cruzamento uniponto com ponto de corte randomico:
  // --> Mergeia estes 2 cromossomos em um novo, sendo de 0 a ponto de corte, o cromossomo pai;
  // --> sendo do ponto de corte ao final do trajto, o cromossomo mae;
  // Exemplo:
  // pai: abcdefghijklmnopqrstuvwxyz
  // mae: ABCDEFGHIJKLMNOPQRSTUVWXYZ
  // ponto de corte: 10
  // (pai:abcdefghij | mae:KLMNOPQRSTUVWXYZ) => filho: abcdefghijKLMNOPQRSTUVWXYZ
  public static Individuo cruzarIndividuo(Individuo pai, Individuo mae) {
    Individuo filho = new Individuo();

    int pontoCorte = (int) (Math.random() * pai.getCromossomos().length);

    for (int i = 0; i < pontoCorte; i++) {
      filho.setGene(i, pai.getGene(i));
    }

    for (int i = pontoCorte; i < mae.getCromossomos().length; i++) {
      filho.setGene(i, mae.getGene(i));
    }

    return filho;
  }

  // Mutacao
  // Troca aleatoriamente um gene do cromossomo
  public static void mutarIndividuo(Individuo individuo) {
    int r1 = (int) (Math.random() * individuo.getCromossomos().length);
    int r2 = (int) (Math.random() * individuo.getCromossomos().length);
    char aux;

    aux = individuo.getGene(r1);
    individuo.setGene(r1, individuo.getGene(r2));
    individuo.setGene(r2, aux);
  }

  public static Individuo getMelhorIndividuo(Individuo[] pop) {
    int posicao = 0;
    int melhorPontuacao = pop[posicao].getPontuacao();

    for (int i = 1; i < TAM_POPULACAO; i++) {
      if (pop[i].getPontuacao() > melhorPontuacao) {
        melhorPontuacao = pop[i].getPontuacao();
        posicao = i;
      }
    }

    return pop[posicao];
  }
}