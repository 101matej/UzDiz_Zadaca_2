package org.foi.uzdiz.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
  private String naziv;
  private List<Component> dijelovi = new ArrayList<>();

  public Composite(String naziv) {
    this.naziv = naziv;
  }

  @Override
  public void dodajDio(Component dio) {
    dijelovi.add(dio);
  }

  @Override
  public void ispisiStrukturu() {
    System.out.println(naziv);
    for (Component dio : dijelovi) {
      dio.ispisiStrukturu();
    }
  }

}
