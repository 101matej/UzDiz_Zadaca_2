package org.foi.uzdiz.modeli;

public class Mjesto {
  private Integer id;
  private String naziv;
  private String ulica;

  public Mjesto(Integer id, String naziv, String ulica) {
    this.id = id;
    this.naziv = naziv;
    this.ulica = ulica;
  }

  public Integer dohvatiId() {
    return id;
  }

  public String dohvatiNaziv() {
    return naziv;
  }

  public String dohvatiUlicu() {
    return ulica;
  }
}
