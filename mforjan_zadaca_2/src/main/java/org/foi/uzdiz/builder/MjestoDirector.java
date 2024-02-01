package org.foi.uzdiz.builder;

import org.foi.uzdiz.modeli.Mjesto;

public class MjestoDirector {
  private MjestoBuilder builder;

  public MjestoDirector(MjestoBuilder builder) {
    this.builder = builder;
  }

  public Mjesto kreiraj(Integer id, String naziv, String ulica) {
    return builder.postaviId(id).postaviNaziv(naziv).postaviUlica(ulica).build();
  }
}
