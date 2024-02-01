package org.foi.uzdiz.modeli;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.state.ConcreteStateAktivno;
import org.foi.uzdiz.state.ConcreteStateNeaktivno;
import org.foi.uzdiz.state.ConcreteStateNeispravno;
import org.foi.uzdiz.state.VoziloState;
import org.foi.uzdiz.visitor.VoziloElement;
import org.foi.uzdiz.visitor.VoziloVisitor;

public class Vozilo implements VoziloElement {
  private String registracija;
  private String opis;
  private Float kapacitetTezine;
  private Float kapacitetProstora;
  private Integer redoslijed;
  private Float prosjecnaBrzina;
  private String podrucjaPoRangu;
  public VoziloState trenutniStatus;
  public boolean dostavlja;
  public Float trenutnaTezina;
  public Float trenutniProstor;
  public List<Paket> listaUkrcanihPaketa;
  public LocalDateTime vrijemeUkrcavanjaPrvogPaketa;
  public Float prikupljeniNovac;

  public Vozilo(String registracija, String opis, Float kapacitetTezine, Float kapacitetProstora,
      Integer redoslijed, Float prosjecnaBrzina, String podrucjaPoRangu, String pocetnoStanje) {
    this.registracija = registracija;
    this.opis = opis;
    this.kapacitetTezine = kapacitetTezine;
    this.kapacitetProstora = kapacitetProstora;
    this.redoslijed = redoslijed;
    this.prosjecnaBrzina = prosjecnaBrzina;
    this.podrucjaPoRangu = podrucjaPoRangu;
    postaviPocetnoStanje(pocetnoStanje);
    dostavlja = false;
    trenutniProstor = 0.0f;
    trenutnaTezina = 0.0f;
    listaUkrcanihPaketa = new ArrayList<>();
    vrijemeUkrcavanjaPrvogPaketa = null;
    prikupljeniNovac = 0.0f;
  }

  private void postaviPocetnoStanje(String pocetniStatus) {
    switch (pocetniStatus) {
      case "A":
        postaviStatus(new ConcreteStateAktivno());
        break;
      case "NI":
        postaviStatus(new ConcreteStateNeispravno());
        break;
      case "NA":
        postaviStatus(new ConcreteStateNeaktivno());
        break;
      default:
        throw new IllegalArgumentException("Stanje nije ispravno definirano: " + pocetniStatus);
    }
  }

  public void postaviStatus(VoziloState status) {
    trenutniStatus = status;
  }

  public void promijeniStatus(String virtualnoVrijemeString) {
    trenutniStatus.promijeniStatus(this, virtualnoVrijemeString);
  }

  public String dohvatiTrenutniStatus() {
    return trenutniStatus.dohvatiOznakuStatusa();
  }

  public String dohvatiRegistraciju() {
    return registracija;
  }

  public String dohvatiOpis() {
    return opis;
  }

  public Float dohvatikapacitetTezine() {
    return kapacitetTezine;
  }

  public Float dohvatikapacitetProstora() {
    return kapacitetProstora;
  }

  public Integer dohvatiRedoslijed() {
    return redoslijed;
  }

  public Float dohvatiProsjecnuBrzinu() {
    return prosjecnaBrzina;
  }

  public String dohvatiPodrucjaPoRangu() {
    return podrucjaPoRangu;
  }

  public Boolean dohvatiDostavlja() {
    return dostavlja;
  }

  public Float dohvatiTrenutnuTezinu() {
    return trenutnaTezina;
  }

  public Float dohvatiTrenutniProstor() {
    return trenutniProstor;
  }

  public List<Paket> dohvatiListuUkrcanihPaketa() {
    return listaUkrcanihPaketa;
  }

  @Override
  public void accept(VoziloVisitor visitor) {
    visitor.visit(this);
  }
}
