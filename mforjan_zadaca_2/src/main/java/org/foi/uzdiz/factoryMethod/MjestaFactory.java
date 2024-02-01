package org.foi.uzdiz.factoryMethod;

public class MjestaFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacMjesta();
    return product;
  }

}
