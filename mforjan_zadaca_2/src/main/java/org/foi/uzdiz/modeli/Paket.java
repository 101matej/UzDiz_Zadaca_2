package org.foi.uzdiz.modeli;

import java.util.Date;
import org.foi.uzdiz.state.ConcreteStatePosiljateljDa;
import org.foi.uzdiz.state.ConcreteStatePosiljateljNe;
import org.foi.uzdiz.state.ConcreteStatePrimateljDa;
import org.foi.uzdiz.state.ConcreteStatePrimateljNe;
import org.foi.uzdiz.state.PosiljateljState;
import org.foi.uzdiz.state.PrimateljState;

public class Paket {
  private String oznaka;
  private Date vrijemePrijema;
  private String posiljatelj;
  private String primatelj;
  private String vrstaPaketa;
  private Float visina;
  private Float sirina;
  private Float duzina;
  private Float tezina;
  private String uslugaDostave;
  private Float iznosPouzeca;
  public PosiljateljState trenutniStatusPosiljatelj;
  public PrimateljState trenutniStatusPrimatelj;
  public Boolean dostavljen;

  public Paket(String oznaka, Date vrijemePrijema, String posiljatelj, String primatelj,
      String vrstaPaketa, Float visina, Float sirina, Float duzina, Float tezina,
      String uslugaDostave, Float iznosPouzeca, String posiljateljObavijest,
      String primateljObavijest) {
    this.oznaka = oznaka;
    this.vrijemePrijema = vrijemePrijema;
    this.posiljatelj = posiljatelj;
    this.primatelj = primatelj;
    this.vrstaPaketa = vrstaPaketa;
    this.visina = visina;
    this.sirina = sirina;
    this.duzina = duzina;
    this.tezina = tezina;
    this.uslugaDostave = uslugaDostave;
    this.iznosPouzeca = iznosPouzeca;
    postaviPocetnoStanjePosiljatelj(posiljateljObavijest);
    postaviPocetnoStanjePrimatelj(primateljObavijest);
    dostavljen = false;
  }

  private void postaviPocetnoStanjePosiljatelj(String posiljateljObavijest) {
    switch (posiljateljObavijest) {
      case "D":
        postaviStatusPosiljatelj(new ConcreteStatePosiljateljDa());
        break;
      case "N":
        postaviStatusPosiljatelj(new ConcreteStatePosiljateljNe());
        break;
      default:
        throw new IllegalArgumentException(
            "Stanje nije ispravno definirano: " + posiljateljObavijest);
    }
  }

  public void postaviStatusPosiljatelj(PosiljateljState posiljateljStatus) {
    trenutniStatusPosiljatelj = posiljateljStatus;
  }

  public void promijeniStatusPosiljatelj() {
    trenutniStatusPosiljatelj.promijeniStatus(this);
  }

  public String dohvatiTrenutniStatusPosiljatelj() {
    return trenutniStatusPosiljatelj.dohvatiOznakuStatusa();
  }

  private void postaviPocetnoStanjePrimatelj(String primateljObavijest) {
    switch (primateljObavijest) {
      case "D":
        postaviStatusPrimatelj(new ConcreteStatePrimateljDa());
        break;
      case "N":
        postaviStatusPrimatelj(new ConcreteStatePrimateljNe());
        break;
      default:
        throw new IllegalArgumentException(
            "Stanje nije ispravno definirano: " + primateljObavijest);
    }
  }

  public void postaviStatusPrimatelj(PrimateljState primateljStatus) {
    trenutniStatusPrimatelj = primateljStatus;
  }

  public void promijeniStatusPrimatelj() {
    trenutniStatusPrimatelj.promijeniStatus(this);
  }

  public String dohvatiTrenutniStatusPrimatelj() {
    return trenutniStatusPrimatelj.dohvatiOznakuStatusa();
  }

  public String dohvatiOznaku() {
    return oznaka;
  }

  public Date dohvatiVrijemePrijema() {
    return vrijemePrijema;
  }

  public String dohvatiPosiljatelja() {
    return posiljatelj;
  }

  public String dohvatiPrimatelja() {
    return primatelj;
  }

  public String dohvatiVrstuPaketa() {
    return vrstaPaketa;
  }

  public Float dohvatiVisinu() {
    return visina;
  }

  public Float dohvatiSirinu() {
    return sirina;
  }

  public Float dohvatiDuzinu() {
    return duzina;
  }

  public Float dohvatiTezinu() {
    return tezina;
  }

  public String dohvatiUsluguDostave() {
    return uslugaDostave;
  }

  public Float dohvatiIznosPouzeca() {
    return iznosPouzeca;
  }

  public boolean dohvatiDostavljen() {
    return dostavljen;
  }
}
