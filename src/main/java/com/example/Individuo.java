package com.example;

public class Individuo {

  private char[] cromossomos;

  public Individuo() {
    cromossomos = new char[AlgoritmoGenetico.ALVO.length()];

    int gene;

    // gera genes aleatoriamente para cada individuo
    // genes sao lista de caracteres
    for (int i = 0; i < cromossomos.length; i++) {
      gene = (int) (Math.random() * AlgoritmoGenetico.CROMOSSOMOS.length());

      cromossomos[i] = AlgoritmoGenetico.CROMOSSOMOS.charAt(gene);
    }
  }

  // fitness e a qualidade do individuo em relacao ao alvo
  // soma de todos os caracteres iguais entre o alvo e o individuo
  public int getPontuacao() {
    int soma = 0;

    for (int i = 0; i < cromossomos.length; i++) {
      if (cromossomos[i] == AlgoritmoGenetico.ALVO.charAt(i)) {
        soma++;
      }
    }

    return soma;
  }

  public void setGene(int posicao, char gene) {
    cromossomos[posicao] = gene;
  }

  public char getGene(int posicao) {
    return cromossomos[posicao];
  }

  public char[] getCromossomos() {
    return cromossomos;
  }
}
