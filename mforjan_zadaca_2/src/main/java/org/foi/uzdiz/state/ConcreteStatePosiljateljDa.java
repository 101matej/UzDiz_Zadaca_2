package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Paket;

public class ConcreteStatePosiljateljDa implements PosiljateljState {

  @Override
  public void promijeniStatus(Paket paket) {
    System.out.println("POŠILJATELJ " + paket.dohvatiPosiljatelja()
        + " ŽELI PRIMATI OBAVIJESTI ZA PAKET " + paket.dohvatiOznaku() + "!");
  }

  @Override
  public String dohvatiOznakuStatusa() {
    return "D";
  }

}
