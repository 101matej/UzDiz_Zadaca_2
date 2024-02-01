package org.foi.uzdiz.factoryMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.modeli.Osoba;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class CitacOsoba implements CitacDatoteka {

  @Override
  public void ucitajPodatke(String nazivDatoteke) {
    File datoteka = new File(nazivDatoteke);
    try {
      FileInputStream inputStream = new FileInputStream(datoteka);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String prvaLinija = reader.readLine();
      String zapis = "";

      if (!prvaLinija.equals("")) {
        if (prvaLinija.equals("osoba; grad; ulica; kbr")) {
          while ((zapis = reader.readLine()) != null) {
            if (!zapis.trim().isEmpty()) {
              String[] atributi = zapis.split(";");
              boolean ispravanRedak = provjeraRetka(atributi, zapis);
              boolean nePostoji = provjeriPostojanje(atributi[0], zapis);
              if (ispravanRedak && nePostoji) {
                ucitajOsobu(atributi);
              }
            }
          }
        } else {
          UpraviteljGresakaSingleton.getInstance().greskaSDatotekama(
              "Informativni redak u datoteci s popisom osoba nije u pravilnom formatu!");
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaSDatotekama("Datoteka s popisom osoba je prazna!");
      }
      reader.close();
    } catch (Exception e) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom osoba!");
    }
  }

  private boolean provjeraRetka(String[] atributi, String zapis) {
    if (atributi.length == 4) {
      boolean ispravnaOsoba = provjeraOsobe(zapis, atributi[0]);
      boolean ispravanGrad = provjeraGrada(zapis, atributi[1]);
      boolean ispravnaUlica = provjeraUlice(zapis, atributi[2]);
      boolean ispravanKbr = provjeraKbr(zapis, atributi[3]);

      if (ispravnaOsoba && ispravanGrad && ispravnaUlica && ispravanKbr) {
        return true;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redak u tablici s popisom osoba ne sadrži traženi broj elemenata!");
    }
    return false;
  }

  private boolean provjeriPostojanje(String osobaIme, String zapis) {
    for (Osoba osoba : TvrtkaSingleton.getInstance().listaOsoba) {
      if (osobaIme.equals(osoba.dohvatiOsobu())) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Osoba već postoji!");
        return false;
      }
    }
    return true;
  }

  private boolean provjeraOsobe(String zapis, String osoba) {
    if (!osoba.equals("")) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ime osobe nije uneseno!");
    }
    return false;
  }

  private boolean provjeraGrada(String zapis, String grad) {
    if (!grad.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(grad);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Grad osobe je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Grad osobe nije unesen!");
    }
    return false;
  }

  private boolean provjeraUlice(String zapis, String ulica) {
    if (!ulica.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(ulica);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ulica osobe je neispravna!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ulica osobe nije unesena!");
    }
    return false;
  }

  private boolean provjeraKbr(String zapis, String kbr) {
    kbr = kbr.trim();
    if (!kbr.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(kbr);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Kbr osobe je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Kbr osobe nije unesen!");
    }
    return false;
  }

  private void ucitajOsobu(String[] atributi) {
    String osobaIme = atributi[0];
    Integer grad = Integer.parseInt(atributi[1]);
    Integer ulica = Integer.parseInt(atributi[2]);
    Integer kbr = Integer.parseInt(atributi[3].trim());
    Osoba osoba = new Osoba(osobaIme, grad, ulica, kbr);
    TvrtkaSingleton.getInstance().listaOsoba.add(osoba);
  }
}
