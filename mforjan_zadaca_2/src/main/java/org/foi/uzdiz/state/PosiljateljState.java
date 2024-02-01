package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Paket;

public interface PosiljateljState {
  void promijeniStatus(Paket paket);

  String dohvatiOznakuStatusa();
}
