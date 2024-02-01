package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Paket;

public interface PrimateljState {
  void promijeniStatus(Paket paket);

  String dohvatiOznakuStatusa();
}
