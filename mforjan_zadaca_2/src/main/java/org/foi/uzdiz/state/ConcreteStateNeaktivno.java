package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Vozilo;

public class ConcreteStateNeaktivno implements VoziloState {

  @Override
  public void promijeniStatus(Vozilo vozilo, String virtualnoVrijemeString) {
    System.out.println("Vozilu " + vozilo.dohvatiRegistraciju()
        + " postavlja se status na 'nije aktivno' u trenutku virtualnog vremena "
        + virtualnoVrijemeString);
  }

  @Override
  public String dohvatiOznakuStatusa() {
    return "NA";
  }
}
