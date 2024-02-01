package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Paket;

public class ConcreteStatePrimateljDa implements PrimateljState {

  @Override
  public void promijeniStatus(Paket paket) {
    System.out.println("PRIMATELJ " + paket.dohvatiPrimatelja()
        + " ŽELI PRIMATI OBAVIJESTI ZA PAKET " + paket.dohvatiOznaku() + "!");
  }

  @Override
  public String dohvatiOznakuStatusa() {
    return "D";
  }

}
