package org.foi.uzdiz.factoryMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.builder.KonkretnoMjestoBuilder;
import org.foi.uzdiz.builder.MjestoBuilder;
import org.foi.uzdiz.builder.MjestoDirector;
import org.foi.uzdiz.modeli.Mjesto;
import org.foi.uzdiz.modeli.Ulica;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class CitacMjesta implements CitacDatoteka {

  @Override
  public void ucitajPodatke(String nazivDatoteke) {
    File datoteka = new File(nazivDatoteke);
    try {
      FileInputStream inputStream = new FileInputStream(datoteka);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String prvaLinija = reader.readLine();
      String zapis = "";

      if (!prvaLinija.equals("")) {
        if (prvaLinija.equals("id; naziv; ulica,ulica,ulica,...")) {
          while ((zapis = reader.readLine()) != null) {
            if (!zapis.trim().isEmpty()) {
              String[] atributi = zapis.split(";");
              boolean ispravanRedak = provjeraRetka(atributi, zapis);
              boolean nePostoji = provjeriPostojanje(atributi[0], zapis);
              if (ispravanRedak && nePostoji) {
                ucitajMjesto(atributi, zapis);
              }
            }
          }
        } else {
          UpraviteljGresakaSingleton.getInstance().greskaSDatotekama(
              "Informativni redak u datoteci s popisom mjesta nije u pravilnom formatu!");
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaSDatotekama("Datoteka s popisom mjesta je prazna!");
      }
      reader.close();
    } catch (Exception e) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom mjesta!");
    }
  }

  private boolean provjeraRetka(String[] atributi, String zapis) {
    if (atributi.length == 3) {
      boolean ispravanId = provjeraId(zapis, atributi[0]);
      boolean ispravanNaziv = provjeraNaziva(zapis, atributi[1]);
      boolean ispravnaUlica = provjeraUlice(zapis, atributi[2]);

      if (ispravanId && ispravanNaziv && ispravnaUlica) {
        return true;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redak u tablici s popisom mjesta ne sadrži traženi broj elemenata!");
    }
    return false;
  }

  private boolean provjeriPostojanje(String id, String zapis) {
    Integer idInteger = 0;
    try {
      idInteger = Integer.parseInt(id);
    } catch (Exception e) {
    }
    for (Mjesto mjesto : TvrtkaSingleton.getInstance().listaMjesta) {
      if (idInteger == mjesto.dohvatiId()) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Mjesto već postoji!");
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
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id mjesta je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id mjesta nije unesen!");
    }
    return false;
  }

  private boolean provjeraNaziva(String zapis, String naziv) {
    naziv = naziv.trim();
    if (!naziv.equals("")) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Naziv mjesta nije unesen!");
    }
    return false;
  }

  private boolean provjeraUlice(String zapis, String ulica) {
    ulica = ulica.trim();
    if (!ulica.equals("")) {
      Pattern pattern = Pattern.compile("(\\d+(,\\d+)*)?");
      Matcher matcher = pattern.matcher(ulica);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ulica mjesta je neispravna!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ulica mjesta nije unesena!");
    }
    return false;
  }

  private void ucitajMjesto(String[] atributi, String zapis) {
    Integer id = Integer.parseInt(atributi[0]);
    String naziv = atributi[1].trim();
    String ulica = atributi[2].trim();
    String konacneUlice = provjeraUlicePostojanja(ulica, zapis);

    MjestoBuilder builder = new KonkretnoMjestoBuilder();
    MjestoDirector director = new MjestoDirector(builder);

    Mjesto mjesto = director.kreiraj(id, naziv, konacneUlice);
    TvrtkaSingleton.getInstance().listaMjesta.add(mjesto);
  }

  private String provjeraUlicePostojanja(String ulica, String zapis) {
    String konacneUlice = "";
    String[] listaUlica = ulica.split(",");
    for (String ulicaIdString : listaUlica) {
      boolean greska = true;
      for (Ulica popisSvihUlica : TvrtkaSingleton.getInstance().listaUlica) {
        Integer ulicaIdInteger = Integer.parseInt(ulicaIdString);
        if (ulicaIdInteger == popisSvihUlica.dohvatiId()) {
          konacneUlice = konacneUlice + ulicaIdString + ",";
          greska = false;
        }
      }
      if (greska) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Ulica čiji je ID " + ulicaIdString + " ne postoji!");
      }
    }
    if (!konacneUlice.isEmpty()) {
      konacneUlice = konacneUlice.substring(0, konacneUlice.length() - 1);
    }
    return konacneUlice;
  }
}
