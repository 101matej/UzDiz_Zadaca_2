package org.foi.uzdiz.composite;

import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class Leaf implements Component {
  private String naziv;

  public Leaf(String naziv) {
    this.naziv = naziv;
  }

  @Override
  public void ispisiStrukturu() {
    System.out.println(naziv);
  }

  @Override
  public void dodajDio(Component dio) {
    UpraviteljGresakaSingleton.getInstance().greskaURadu("List nema dijelove!");
  }

}
