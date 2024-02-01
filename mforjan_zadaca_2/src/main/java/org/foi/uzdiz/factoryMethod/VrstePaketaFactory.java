package org.foi.uzdiz.factoryMethod;

public class VrstePaketaFactory extends DatotekaFactory {

  @Override
  public CitacDatoteka ucitajDatoteku(String nazivDatoteke) {
    CitacDatoteka product = new CitacVrstePaketa();
    return product;
  }

}
