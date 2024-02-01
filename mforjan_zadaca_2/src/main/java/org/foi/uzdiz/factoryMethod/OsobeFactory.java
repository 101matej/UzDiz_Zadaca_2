package org.foi.uzdiz.factoryMethod;

public class OsobeFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacOsoba();
    return product;
  }

}
