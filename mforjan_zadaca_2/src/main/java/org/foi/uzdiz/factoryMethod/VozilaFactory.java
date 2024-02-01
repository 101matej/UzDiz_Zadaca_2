package org.foi.uzdiz.factoryMethod;

public class VozilaFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacVozila();
    return product;
  }

}
