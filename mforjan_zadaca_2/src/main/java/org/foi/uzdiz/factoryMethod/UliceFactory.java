package org.foi.uzdiz.factoryMethod;

public class UliceFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacUlica();
    return product;
  }

}
