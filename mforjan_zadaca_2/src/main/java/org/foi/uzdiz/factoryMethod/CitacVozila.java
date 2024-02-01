package org.foi.uzdiz.factoryMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.modeli.Podrucje;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class CitacVozila implements CitacDatoteka {

  @Override
  public void ucitajPodatke(String nazivDatoteke) {
    File datoteka = new File(nazivDatoteke);
    try {
      FileInputStream inputStream = new FileInputStream(datoteka);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String prvaLinija = reader.readLine();
      String zapis = "";

      if (!prvaLinija.equals("")) {
        if (prvaLinija.equals(
            "Registracija;Opis;Kapacitet težine u kg;Kapacitet prostora u m3;Redoslijed;Prosječna brzina;Područja po rangu;Status")) {
          while ((zapis = reader.readLine()) != null) {
            if (!zapis.trim().isEmpty()) {
              String[] atributi = zapis.split(";");
              boolean ispravanRedak = provjeraRetka(atributi, zapis);
              boolean nePostoji = provjeriPostojanje(atributi[0], zapis);
              if (ispravanRedak && nePostoji) {
                ucitajVozilo(atributi, zapis);
              }
            }
          }
        } else {
          UpraviteljGresakaSingleton.getInstance().greskaSDatotekama(
              "Informativni redak u datoteci s popisom vozila nije u pravilnom formatu!");
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaSDatotekama("Datoteka s popisom vozila je prazna!");
      }
      reader.close();
    } catch (Exception e) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom vozila!");
    }
  }

  private boolean provjeraRetka(String[] atributi, String zapis) {
    if (atributi.length == 8) {
      boolean ispravnaRegistracija = provjeraRegistracije(zapis, atributi[0]);
      boolean ispravanOpis = provjeraOpisa(zapis, atributi[1]);
      boolean ispravanKapacitetTezine = provjeraKapacitetaTezine(zapis, atributi[2]);
      boolean ispravanKapacitetProstora = provjeraKapacitetaProstora(zapis, atributi[3]);
      boolean ispravanRedoslijed = provjeraRedoslijeda(zapis, atributi[4]);
      boolean ispravnaProsjecnaBrzina = provjeraProsjecneBrzine(zapis, atributi[5]);
      boolean ispravnaPodrucjaPoRangu = provjeraPodrucjaPoRangu(zapis, atributi[6]);
      boolean ispravanStatus = provjeraStatusa(zapis, atributi[7]);

      if (ispravnaRegistracija && ispravanOpis && ispravanKapacitetTezine
          && ispravanKapacitetProstora && ispravanRedoslijed && ispravnaProsjecnaBrzina
          && ispravnaPodrucjaPoRangu && ispravanStatus) {
        return true;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redak u tablici s popisom vozila ne sadrži traženi broj elemenata!");
    }
    return false;
  }

  private boolean provjeriPostojanje(String registracija, String zapis) {
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (registracija.equals(vozilo.dohvatiRegistraciju())) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Vozilo s ovom registracijskom oznakom već postoji!");
        return false;
      }
    }
    return true;
  }

  private boolean provjeraRegistracije(String zapis, String registracija) {
    if (!registracija.equals("")) {
      Pattern pattern = Pattern.compile("[A-Z0-9ŽŠČĆĐ]+");
      Matcher matcher = pattern.matcher(registracija);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Registracija vozila je neispravna!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Registracija vozila nije unesena!");
    }
    return false;
  }

  private boolean provjeraOpisa(String zapis, String opis) {
    if (!opis.equals("")) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Opis vozila nije unesen!");
    }
    return false;
  }

  private boolean provjeraKapacitetaTezine(String zapis, String kapacitetTezine) {
    if (!kapacitetTezine.equals("")) {
      Pattern pattern = Pattern.compile("^\\d+(?:[.,]\\d+)?$");
      Matcher matcher = pattern.matcher(kapacitetTezine);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Kapacitet težine vozila je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Kapacitet težine vozila nije unesen!");
    }
    return false;
  }

  private boolean provjeraKapacitetaProstora(String zapis, String kapacitetProstora) {
    if (!kapacitetProstora.equals("")) {
      Pattern pattern = Pattern.compile("^\\d+(?:[.,]\\d+)?$");
      Matcher matcher = pattern.matcher(kapacitetProstora);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Kapacitet prostora vozila je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Kapacitet prostora vozila nije unesen!");
    }
    return false;
  }

  private boolean provjeraRedoslijeda(String zapis, String redoslijed) {
    if (!redoslijed.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(redoslijed);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Redoslijed vozila je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redoslijed vozila nije unesen!");
    }
    return false;
  }

  private boolean provjeraProsjecneBrzine(String zapis, String prosjecnaBrzina) {
    if (!prosjecnaBrzina.equals("")) {
      Pattern pattern = Pattern.compile("^\\d+(?:[.,]\\d+)?$");
      Matcher matcher = pattern.matcher(prosjecnaBrzina);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Prosječna brzina vozila je neispravna!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Prosječna brzina vozila nije unesena!");
    }
    return false;
  }

  private boolean provjeraPodrucjaPoRangu(String zapis, String podrucjePoRangu) {
    if (!podrucjePoRangu.equals("")) {
      Pattern pattern = Pattern.compile("(\\d+(,\\d+)*)?");
      Matcher matcher = pattern.matcher(podrucjePoRangu);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Područje po rangu vozila je neispravno!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Područje po rangu vozila nije uneseno!");
    }
    return false;
  }

  private boolean provjeraStatusa(String zapis, String status) {
    status = status.trim();
    if (!status.equals("")) {
      Pattern pattern = Pattern.compile("A|NI|NA");
      Matcher matcher = pattern.matcher(status);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Status vozila je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Status vozila nije unesen!");
    }
    return false;
  }

  private void ucitajVozilo(String[] atributi, String zapis) {
    String registracija = atributi[0];
    String opis = atributi[1];
    Float kapacitetTezine = Float.parseFloat(atributi[2].replace(",", "."));
    Float kapacitetProstora = Float.parseFloat(atributi[3].replace(",", "."));
    Integer redoslijed = Integer.parseInt(atributi[4]);
    Float prosjecnaBrzina = Float.parseFloat(atributi[5].replace(",", "."));
    String podrucjePoRangu = atributi[6].trim();
    String konacnaPodrucjaPoRangu = provjeraPodrucjaPostojanja(podrucjePoRangu, zapis);
    String status = atributi[7].trim();
    Vozilo vozilo = new Vozilo(registracija, opis, kapacitetTezine, kapacitetProstora, redoslijed,
        prosjecnaBrzina, konacnaPodrucjaPoRangu, status);
    TvrtkaSingleton.getInstance().listaVozila.add(vozilo);
  }

  private String provjeraPodrucjaPostojanja(String podrucjePoRangu, String zapis) {
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
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Područje čiji je ID " + podrucjeIdString + " ne postoji!");
      }
    }
    if (!konacnaPodrucja.isEmpty()) {
      konacnaPodrucja = konacnaPodrucja.substring(0, konacnaPodrucja.length() - 1);
    }
    return konacnaPodrucja;
  }
}
