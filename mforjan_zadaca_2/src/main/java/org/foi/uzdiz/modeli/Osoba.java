package org.foi.uzdiz.modeli;

public class Osoba {
  private String osoba;
  private Integer grad;
  private Integer ulica;
  private Integer kbr;

  public Osoba(String osoba, Integer grad, Integer ulica, Integer kbr) {
    this.osoba = osoba;
    this.grad = grad;
    this.ulica = ulica;
    this.kbr = kbr;
  }

  public String dohvatiOsobu() {
    return osoba;
  }

  public int dohvatiGrad() {
    return grad;
  }

  public int dohvatiUlicu() {
    return ulica;
  }

  public int dohvatiKbr() {
    return kbr;
  }
}
