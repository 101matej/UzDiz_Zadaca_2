package org.foi.uzdiz.proxy;

public class Proxy implements SubjectProxy {
  private RealSubject realSubject;

  public Proxy() {
    this.realSubject = new RealSubject();
  }

  @Override
  public void dodajVozilo(String registracija, String opis, String kapacitetTezine,
      String kapacitetProstora, String redoslijed, String prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    realSubject.dodajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora, redoslijed,
        prosjecnaBrzina, podrucjaPoRangu, status);
  }
}
