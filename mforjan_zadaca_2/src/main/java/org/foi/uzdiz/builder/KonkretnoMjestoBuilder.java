package org.foi.uzdiz.builder;

import org.foi.uzdiz.modeli.Mjesto;

public class KonkretnoMjestoBuilder implements MjestoBuilder {
  private Integer id;
  private String naziv;
  private String ulica;

  @Override
  public Mjesto build() {
    return new Mjesto(id, naziv, ulica);
  }

  @Override
  public MjestoBuilder postaviId(Integer id) {
    this.id = id;
    return this;
  }

  @Override
  public MjestoBuilder postaviNaziv(String naziv) {
    this.naziv = naziv;
    return this;
  }

  @Override
  public MjestoBuilder postaviUlica(String ulica) {
    this.ulica = ulica;
    return this;
  }

}
