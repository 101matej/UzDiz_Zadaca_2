package org.foi.uzdiz.observer;

public class PosiljateljObserver implements Observer {
  private String posiljatelj;
  private String oznakaPaketa;
  private String vrijemePrijema;
  private String vrijemePreuzimanja;

  @Override
  public void azurirajZaprimljen() {
    System.out.println("OBAVIJEST POŠILJATELJU " + posiljatelj + ": VAŠ PAKET " + oznakaPaketa
        + " JE ZAPRIMLJEN U " + vrijemePrijema + "!\n");
  }

  @Override
  public void azurirajPreuzet() {
    System.out.println("OBAVIJEST POŠILJATELJU " + posiljatelj + ": VAŠ PAKET " + oznakaPaketa
        + " JE PREUZET U " + vrijemePreuzimanja + "!\n");
  }

  public PosiljateljObserver(SubjectObserver subjectObserver, String posiljatelj,
      String oznakaPaketa, String vrijemePrijema, String vrijemePreuzimanja) {
    this.posiljatelj = posiljatelj;
    this.oznakaPaketa = oznakaPaketa;
    this.vrijemePrijema = vrijemePrijema;
    this.vrijemePreuzimanja = vrijemePreuzimanja;
    subjectObserver.dodajObserver(this);
  }
}
