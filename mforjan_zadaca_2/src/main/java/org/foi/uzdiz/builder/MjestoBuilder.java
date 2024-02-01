package org.foi.uzdiz.builder;

import org.foi.uzdiz.modeli.Mjesto;

public interface MjestoBuilder {
  Mjesto build();

  MjestoBuilder postaviId(Integer id);

  MjestoBuilder postaviNaziv(String naziv);

  MjestoBuilder postaviUlica(String ulica);
}
