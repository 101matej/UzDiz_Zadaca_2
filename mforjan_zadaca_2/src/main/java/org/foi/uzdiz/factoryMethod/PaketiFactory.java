package org.foi.uzdiz.factoryMethod;

public class PaketiFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacPaketa();
    return product;
  }

}
