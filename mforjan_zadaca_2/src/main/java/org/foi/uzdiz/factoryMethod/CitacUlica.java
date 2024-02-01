package org.foi.uzdiz.factoryMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.modeli.Ulica;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class CitacUlica implements CitacDatoteka {

  @Override
  public void ucitajPodatke(String nazivDatoteke) {
    File datoteka = new File(nazivDatoteke);
    try {
      FileInputStream inputStream = new FileInputStream(datoteka);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String prvaLinija = reader.readLine();
      String zapis = "";

      if (!prvaLinija.equals("")) {
        if (prvaLinija
            .equals("id; naziv; gps_lat_1; gps_lon_1; gps_lat_2; gps_lon_2; najveći kućni broj")) {
          while ((zapis = reader.readLine()) != null) {
            if (!zapis.trim().isEmpty()) {
              String[] atributi = zapis.split(";");
              boolean ispravanRedak = provjeraRetka(atributi, zapis);
              boolean nePostoji = provjeriPostojanje(atributi[0], zapis);
              if (ispravanRedak && nePostoji) {
                ucitajUlicu(atributi);
              }
            }
          }
        } else {
          UpraviteljGresakaSingleton.getInstance().greskaSDatotekama(
              "Informativni redak u datoteci s popisom ulica nije u pravilnom formatu!");
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaSDatotekama("Datoteka s popisom ulica je prazna!");
      }
      reader.close();
    } catch (Exception e) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom ulica!");
    }
  }

  private boolean provjeraRetka(String[] atributi, String zapis) {
    if (atributi.length == 7) {
      boolean ispravanId = provjeraId(zapis, atributi[0]);
      boolean ispravanNaziv = provjeraNaziva(zapis, atributi[1]);
      boolean ispravanGpsLat1 = provjeraGpsLat1(zapis, atributi[2]);
      boolean ispravanGpsLon1 = provjeraGpsLon1(zapis, atributi[3]);
      boolean ispravanGpsLat2 = provjeraGpsLat2(zapis, atributi[4]);
      boolean ispravanGpsLon2 = provjeraGpsLon2(zapis, atributi[5]);
      boolean ispravanNajveciKucniBroj = provjeraNajveciKucniBroj(zapis, atributi[6]);

      if (ispravanId && ispravanNaziv && ispravanGpsLat1 && ispravanGpsLon1 && ispravanGpsLat2
          && ispravanGpsLon2 && ispravanNajveciKucniBroj) {
        return true;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Redak u tablici s popisom ulica ne sadrži traženi broj elemenata!");
    }
    return false;
  }

  private boolean provjeriPostojanje(String id, String zapis) {
    Integer idInteger = 0;
    try {
      idInteger = Integer.parseInt(id);
    } catch (Exception e) {
    }
    for (Ulica ulica : TvrtkaSingleton.getInstance().listaUlica) {
      if (idInteger == ulica.dohvatiId()) {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Ulica već postoji!");
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
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Id ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraNaziva(String zapis, String naziv) {
    naziv = naziv.trim();
    if (!naziv.equals("")) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Naziv ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraGpsLat1(String zapis, String gpsLat1) {
    gpsLat1 = gpsLat1.trim();
    if (!gpsLat1.equals("")) {
      Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]+)?)");
      Matcher matcher = pattern.matcher(gpsLat1);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Gps_lat_1 ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Gps_lat_1 ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraGpsLon1(String zapis, String gpsLon1) {
    gpsLon1 = gpsLon1.trim();
    if (!gpsLon1.equals("")) {
      Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]+)?)");
      Matcher matcher = pattern.matcher(gpsLon1);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Gps_lon_1 ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Gps_lon_1 ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraGpsLat2(String zapis, String gpsLat2) {
    gpsLat2 = gpsLat2.trim();
    if (!gpsLat2.equals("")) {
      Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]+)?)");
      Matcher matcher = pattern.matcher(gpsLat2);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Gps_lat_2 ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Gps_lat_2 ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraGpsLon2(String zapis, String gpsLon2) {
    gpsLon2 = gpsLon2.trim();
    if (!gpsLon2.equals("")) {
      Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]+)?)");
      Matcher matcher = pattern.matcher(gpsLon2);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Gps_lon_2 ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis, "Gps_lon_2 ulice nije unesen!");
    }
    return false;
  }

  private boolean provjeraNajveciKucniBroj(String zapis, String najveciKucniBroj) {
    najveciKucniBroj = najveciKucniBroj.trim();
    if (!najveciKucniBroj.equals("")) {
      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(najveciKucniBroj);
      if (matcher.matches()) {
        return true;
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
            "Najveći kućni broj ulice je neispravan!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURetku(zapis,
          "Najveći kućni broj ulice nije unesen!");
    }
    return false;
  }

  private void ucitajUlicu(String[] atributi) {
    Integer id = Integer.parseInt(atributi[0]);
    String naziv = atributi[1].trim();
    Double gpsLat1 = Double.parseDouble(atributi[2].replace(",", ".").trim());
    Double gpsLon1 = Double.parseDouble(atributi[3].replace(",", ".").trim());
    Double gpsLat2 = Double.parseDouble(atributi[4].replace(",", ".").trim());
    Double gpsLon2 = Double.parseDouble(atributi[5].replace(",", ".").trim());
    Integer najveciKucniBroj = Integer.parseInt(atributi[6].trim());
    Ulica ulica = new Ulica(id, naziv, gpsLat1, gpsLon1, gpsLat2, gpsLon2, najveciKucniBroj);
    TvrtkaSingleton.getInstance().listaUlica.add(ulica);
  }
}
