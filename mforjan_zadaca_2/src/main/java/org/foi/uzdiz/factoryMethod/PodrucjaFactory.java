package org.foi.uzdiz.factoryMethod;

public class PodrucjaFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacPodrucja();
    return product;
  }
}
