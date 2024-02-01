package org.foi.uzdiz.modeli;

public class Podrucje {
  private Integer id;
  private String gradUlica;

  public Podrucje(Integer id, String gradUlica) {
    this.id = id;
    this.gradUlica = gradUlica;
  }

  public Integer dohvatiId() {
    return id;
  }

  public String dohvatiGradUlicu() {
    return gradUlica;
  }
}
