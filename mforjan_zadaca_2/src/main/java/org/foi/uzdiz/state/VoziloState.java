package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Vozilo;

public interface VoziloState {
  void promijeniStatus(Vozilo vozilo, String virtualnoVrijemeString);

  String dohvatiOznakuStatusa();
}
