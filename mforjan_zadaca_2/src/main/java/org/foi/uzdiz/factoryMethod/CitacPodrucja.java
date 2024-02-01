package org.foi.uzdiz.factoryMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.modeli.Podrucje;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class CitacPodrucja implements CitacDatoteka {

  @Override
  public void ucitajPodatke(String nazivDatoteke) {
    File datoteka = new File(nazivDatoteke);
    try {
      FileInputStream inputStream = new FileInputStream(datoteka);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String prvaLinija = reader.readLine();
      String zapis = "";

      if (!prvaLinija.equals("")) {
        if (prvaLinija.equals("id;grad:ulica,grad:ulica,grad:*,...")) {
          while ((zapis = reader.readLine()) != null) {
            if (!zapis.trim().isEmpty()) {
              String[] atributi = zapis.split(";");
              boolean ispravanRedak = provjeraRetka(atributi, zapis);
              boolean nePostoji = provjeriPostojanje(atributi[0], zapis);
              if (ispravanRedak && nePostoji) {
                ucitajPodrucje(atributi);
              }
            }
          }
        } else {
          UpraviteljGresakaSingleton.getInstance().greskaSDatotekama(
              "Informativni redak u datoteci s popisom područja nije u pravilnom formatu!");
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaSDatotekama("Datoteka s popisom područja je prazna!");
      }
      reader.close();
    } catch (Exception e) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom područja!");
    }
  }

  private boolean provjeraRetka(String[] atributi, String zapis) {
    if (atributi.length == 2) {
      boolean ispravanId = provjeraId(zapis, atributi[0]);
      boolean ispravanGradUlica = provjeraGradUlica(zapis, atributi[1]);

      if (ispravanId && ispravanGradUlica) {
        return true;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redak u tablici s popisom područja ne sadrži traženi broj elemenata!");
    }
    return false;
  }

  private boolean provjeriPostojanje(String id, String zapis) {
    Integer idInteger = 0;
    try {
      idInteger = Integer.parseInt(id);
    } catch (Exception e) {
    }
    for (Podrucje podrucje : TvrtkaSingleton.getInstance().listaPodrucja) {
      if (idInteger == podrucje.dohvatiId()) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Područje već postoji!");
        return false;
      }
    }
    return true;
  }

  private boolean provjeraId(String zapis, String id) {
    if (!id.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(id);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id područja je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id područja nije unesen!");
    }
    return false;
  }

  private boolean provjeraGradUlica(String zapis, String gradUlica) {
    gradUlica = gradUlica.trim();
    if (!gradUlica.equals("")) {
      Pattern pattern = Pattern.compile("(\\d+:\\*|\\d+:\\d+)(?:,\\d+:\\*|,\\d+:\\d+)*");
      Matcher matcher = pattern.matcher(gradUlica);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Grad:ulica područja je neispravna!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Grad:ulica nije unesena!");
    }
    return false;
  }

  private void ucitajPodrucje(String[] atributi) {
    Integer id = Integer.parseInt(atributi[0]);
    String gradUlica = atributi[1].trim();
    Podrucje podrucje = new Podrucje(id, gradUlica);
    TvrtkaSingleton.getInstance().listaPodrucja.add(podrucje);
  }
}
