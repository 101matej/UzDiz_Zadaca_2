package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Vozilo;

public class ConcreteStateAktivno implements VoziloState {

  @Override
  public void promijeniStatus(Vozilo vozilo, String virtualnoVrijemeString) {
    System.out.println("Vozilu " + vozilo.dohvatiRegistraciju()
        + " postavlja se status na 'aktivno' u trenutku virtualnog vremena "
        + virtualnoVrijemeString);
  }

  @Override
  public String dohvatiOznakuStatusa() {
    return "A";
  }
}
