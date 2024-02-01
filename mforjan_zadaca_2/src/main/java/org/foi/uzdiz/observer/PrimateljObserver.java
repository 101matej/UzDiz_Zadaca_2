package org.foi.uzdiz.observer;

public class PrimateljObserver implements Observer {
  private String primatelj;
  private String oznakaPaketa;
  private String vrijemePrijema;
  private String vrijemePreuzimanja;

  @Override
  public void azurirajZaprimljen() {
    System.out.println("OBAVIJEST PRIMATELJU " + primatelj + ": VAŠ PAKET " + oznakaPaketa
        + " JE ZAPRIMLJEN U " + vrijemePrijema + "!\n");
  }

  @Override
  public void azurirajPreuzet() {
    System.out.println("OBAVIJEST PRIMATELJU " + primatelj + ": VAŠ PAKET " + oznakaPaketa
        + " JE PREUZET U " + vrijemePreuzimanja + "!\n");
  }

  public PrimateljObserver(SubjectObserver subjectObserver, String primatelj, String oznakaPaketa,
      String vrijemePrijema, String vrijemePreuzimanja) {
    this.primatelj = primatelj;
    this.oznakaPaketa = oznakaPaketa;
    this.vrijemePrijema = vrijemePrijema;
    this.vrijemePreuzimanja = vrijemePreuzimanja;
    subjectObserver.dodajObserver(this);
  }
}
