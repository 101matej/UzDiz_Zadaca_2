package org.foi.uzdiz.proxy;

import org.foi.uzdiz.modeli.Podrucje;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class RealSubject implements SubjectProxy {

  @Override
  public void dodajVozilo(String registracija, String opis, String kapacitetTezine,
      String kapacitetProstora, String redoslijed, String prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    boolean nePostoji = provjeriPostojanje(registracija);
    if (nePostoji) {
      ucitajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora, redoslijed,
          prosjecnaBrzina, podrucjaPoRangu, status);
      System.out.println("VOZILO " + registracija + " JE USPJEŠNO DODANO!");
    }
  }

  private boolean provjeriPostojanje(String registracija) {
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (registracija.equals(vozilo.dohvatiRegistraciju())) {
        UpraviteljGresakaSingleton.getInstance()
            .greskaURadu("Vozilo s registracijskom oznakom " + registracija + " već postoji!");
        return false;
      }
    }
    return true;
  }

  private void ucitajVozilo(String registracija, String opis, String kapacitetTezine,
      String kapacitetProstora, String redoslijed, String prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    String registracijaUnos = registracija;
    String opisUnos = opis;
    Float kapacitetTezineUnos = Float.parseFloat(kapacitetTezine.replace(",", "."));
    Float kapacitetProstoraUnos = Float.parseFloat(kapacitetProstora.replace(",", "."));
    Integer redoslijedUnos = Integer.parseInt(redoslijed);
    Float prosjecnaBrzinaUnos = Float.parseFloat(prosjecnaBrzina.replace(",", "."));
    String podrucjePoRanguUnos = podrucjaPoRangu.trim();
    String konacnaPodrucjaPoRangu = provjeraPodrucjaPostojanja(podrucjePoRanguUnos);
    String statusUnos = status.trim();
    Vozilo vozilo =
        new Vozilo(registracijaUnos, opisUnos, kapacitetTezineUnos, kapacitetProstoraUnos,
            redoslijedUnos, prosjecnaBrzinaUnos, konacnaPodrucjaPoRangu, statusUnos);
    TvrtkaSingleton.getInstance().listaVozila.add(vozilo);
  }

  private String provjeraPodrucjaPostojanja(String podrucjePoRangu) {
    String konacnaPodrucja = "";
    String[] listaPodrucja = podrucjePoRangu.split(",");
    for (String podrucjeIdString : listaPodrucja) {
      boolean greska = true;
      for (Podrucje popisSvihPodrucja : TvrtkaSingleton.getInstance().listaPodrucja) {
        Integer podrucjeIdInteger = Integer.parseInt(podrucjeIdString);
        if (podrucjeIdInteger == popisSvihPodrucja.dohvatiId()) {
          konacnaPodrucja = konacnaPodrucja + podrucjeIdString + ",";
          greska = false;
        }
      }
      if (greska) {
        UpraviteljGresakaSingleton.getInstance()
            .greskaURadu("Područje čiji je ID " + podrucjeIdString + " ne postoji!");
      }
    }
    if (!konacnaPodrucja.isEmpty()) {
      konacnaPodrucja = konacnaPodrucja.substring(0, konacnaPodrucja.length() - 1);
    }
    return konacnaPodrucja;
  }
}
