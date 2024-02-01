package org.foi.uzdiz.uredi;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.foi.uzdiz.modeli.Osoba;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.modeli.VrstaPaketa;
import org.foi.uzdiz.observer.KonkretniSubject;
import org.foi.uzdiz.observer.PosiljateljObserver;
import org.foi.uzdiz.observer.PrimateljObserver;
import org.foi.uzdiz.observer.SubjectObserver;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class UredZaDostavuSingleton {
  public static UredZaDostavuSingleton uredZaDostavu;

  public List<Paket> listaIsporucenihPaketa = new ArrayList<Paket>();
  public List<Paket> listaDostavljenihPaketa = new ArrayList<Paket>();
  public ArrayList<String> oznakeIVremenaPreuzimanja = new ArrayList<>();
  public List<Paket> listaPaketaZaDostavuPremaHitnosti = new ArrayList<Paket>();
  public List<Paket> listaSvihUkrcanihPaketa = new ArrayList<Paket>();
  private Map<String, Boolean> primateljMapa = new HashMap<>();

  private UredZaDostavuSingleton() {

  }

  public static UredZaDostavuSingleton getInstance() {
    if (uredZaDostavu == null) {
      uredZaDostavu = new UredZaDostavuSingleton();
    }
    return uredZaDostavu;
  }

  public void ukrcajPakete(LocalDateTime virtualnoVrijeme) {
    String virtualnoVrijemeString = pretvoriVirtualnoVrijemeUString(virtualnoVrijeme);
    sortirajVozilaPremaRedoslijedu(TvrtkaSingleton.getInstance().listaVozila);
    sortirajPakete();
    provjeraNakonSatVremenaImaLiHitniPaket(virtualnoVrijeme);
    voziloUDostavi(virtualnoVrijeme);
    for (Paket primljeniPaket : listaPaketaZaDostavuPremaHitnosti) {
      boolean postojiPrimatelj = provjeriPrimatelja(primljeniPaket);
      if (!postojiPrimatelj) {
        continue;
      }
      Float volumen = 0.0f;
      Float tezina = 0.0f;
      for (Vozilo vozilo : sortirajVozilaPremaRedoslijedu(
          TvrtkaSingleton.getInstance().listaVozila)) {
        if (vozilo.dohvatiTrenutniStatus().equals("A")) {
          if (primljeniPaket.dohvatiVrstuPaketa().equals("X")) {
            volumen = izracunajVolumenPaketaTipaX(primljeniPaket, volumen);
          } else {
            volumen = izracunajVolumenTipskihPaketa(primljeniPaket, volumen);
          }
          tezina = primljeniPaket.dohvatiTezinu();

          if (provjeriPostojanje(primljeniPaket, vozilo) == true) {
            break;
          }

          if (vozilo.dostavlja == false) {
            if ((vozilo.trenutniProstor + volumen) <= vozilo.dohvatikapacitetProstora()
                && (vozilo.trenutnaTezina + tezina) <= vozilo.dohvatikapacitetTezine()
                && provjeraPoloviceKapaciteta(vozilo, virtualnoVrijeme, volumen, tezina) == true
                && provjeriPostojanjeUkrcanihPaketa(primljeniPaket) == false) {
              vozilo.trenutniProstor = vozilo.trenutniProstor + volumen;
              vozilo.trenutnaTezina = vozilo.trenutnaTezina + tezina;
              vozilo.listaUkrcanihPaketa.add(primljeniPaket);
              listaSvihUkrcanihPaketa.add(primljeniPaket);

              if (vozilo.vrijemeUkrcavanjaPrvogPaketa == null) {
                vozilo.vrijemeUkrcavanjaPrvogPaketa = virtualnoVrijeme;
              }

              System.out
                  .println("VIRTUALNO VRIJEME: " + virtualnoVrijemeString + " PAKET S OZNAKOM "
                      + primljeniPaket.dohvatiOznaku() + "JE UKRCAN U VOZILO REGISTARSKE OZNAKE "
                      + vozilo.dohvatiRegistraciju() + "!\n");
              break;

            } else {
              if (!vozilo.listaUkrcanihPaketa.isEmpty()) {
                vozilo.dostavlja = true;
                System.out.println("VIRTUALNO VRIJEME: " + virtualnoVrijemeString
                    + " VOZILO S REGISTARSKOM OZNAKOM " + vozilo.dohvatiRegistraciju()
                    + " JE KRENULO S DOSTAVOM PAKETA!\n");
                isporuciPakete(vozilo, virtualnoVrijeme);
                naplatiDostavu(vozilo);
              }
            }
          }
        }
      }
    }
  }

  private boolean provjeriPrimatelja(Paket primljeniPaket) {
    String primateljPaketa = primljeniPaket.dohvatiPrimatelja().trim();
    boolean postojiPrimatelj = false;

    if (primateljMapa.containsKey(primljeniPaket.dohvatiOznaku())) {
      return false;
    }

    for (Osoba osoba : TvrtkaSingleton.getInstance().listaOsoba) {
      if (osoba.dohvatiOsobu().trim().equals(primateljPaketa)) {
        postojiPrimatelj = true;
      }
    }

    if (postojiPrimatelj == true) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURadu("PAKET S OZNAKOM "
          + primljeniPaket.dohvatiOznaku()
          + " NEMA DEFINIRANE PODATKE ZA ADRESU PRIMATELJA STOGA GA NIJE MOGUĆE UKRCATI U VOZILO!");
      primateljMapa.put(primljeniPaket.dohvatiOznaku(), true);
      return false;
    }
  }

  private void provjeraNakonSatVremenaImaLiHitniPaket(LocalDateTime virtualnoVrijeme) {
    for (Vozilo vozilo : sortirajVozilaPremaRedoslijedu(
        TvrtkaSingleton.getInstance().listaVozila)) {
      if (vozilo.dohvatiTrenutniStatus().equals("A")) {
        if (vozilo.dostavlja == false) {

          if (vozilo.vrijemeUkrcavanjaPrvogPaketa == null) {
            return;
          }

          LocalDateTime vrijemeNakonJednogSata = vozilo.vrijemeUkrcavanjaPrvogPaketa.plusHours(1);

          if (virtualnoVrijeme.isAfter(vrijemeNakonJednogSata)
              || virtualnoVrijeme.isEqual(vrijemeNakonJednogSata)) {
            for (Paket ukrcaniPaket : vozilo.listaUkrcanihPaketa) {
              if (ukrcaniPaket.dohvatiUsluguDostave().equals("H")) {
                String virtualnoVrijemeString = pretvoriVirtualnoVrijemeUString(virtualnoVrijeme);
                vozilo.dostavlja = true;
                System.out.println("VIRTUALNO VRIJEME: " + virtualnoVrijemeString
                    + " VOZILO S REGISTARSKOM OZNAKOM " + vozilo.dohvatiRegistraciju()
                    + " JE KRENULO S DOSTAVOM PAKETA!\n");
                isporuciPakete(vozilo, virtualnoVrijeme);
                naplatiDostavu(vozilo);
                return;
              }
            }
          }
        }
      }
    }
  }

  private boolean provjeraPoloviceKapaciteta(Vozilo vozilo, LocalDateTime virtualnoVrijeme,
      Float volumen, Float tezina) {

    if (vozilo.vrijemeUkrcavanjaPrvogPaketa == null) {
      vozilo.vrijemeUkrcavanjaPrvogPaketa = virtualnoVrijeme;
    }

    LocalDateTime vrijemeNakonJednogSata = vozilo.vrijemeUkrcavanjaPrvogPaketa.plusHours(1);

    if (virtualnoVrijeme.isAfter(vrijemeNakonJednogSata)
        || virtualnoVrijeme.isEqual(vrijemeNakonJednogSata)) {
      if ((vozilo.trenutniProstor + volumen) < vozilo.dohvatikapacitetProstora() / 2
          && (vozilo.trenutnaTezina + tezina) < vozilo.dohvatikapacitetTezine() / 2) {
        return true;
      } else {
        return false;
      }
    }
    return true;
  }

  private void sortirajPakete() {
    for (Paket primljeniPaket : UredZaPrijemSingleton.getInstance().listaPrimljenihPaketa) {
      if (primljeniPaket.dohvatiUsluguDostave().equals("H")) {
        listaPaketaZaDostavuPremaHitnosti.add(0, primljeniPaket);
      } else {
        listaPaketaZaDostavuPremaHitnosti.add(primljeniPaket);
      }
    }
  }

  private void isporuciPakete(Vozilo vozilo, LocalDateTime virtualnoVrijeme) {
    int vrijemeIsporuke = TvrtkaSingleton.getInstance().dohvatiVrijednostVi();
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      Duration intervalIsporuke = Duration.ofMinutes(vrijemeIsporuke);
      virtualnoVrijeme = virtualnoVrijeme.plus(intervalIsporuke);
      String virtualnoVrijemeString = pretvoriVirtualnoVrijemeUString(virtualnoVrijeme);
      oznakeIVremenaPreuzimanja.add(paket.dohvatiOznaku());
      oznakeIVremenaPreuzimanja.add(virtualnoVrijemeString);
    }
  }

  private void naplatiDostavu(Vozilo vozilo) {
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      if (paket.dohvatiUsluguDostave().equals("P")) {
        vozilo.prikupljeniNovac = vozilo.prikupljeniNovac + paket.dohvatiIznosPouzeca();
      }
    }
  }

  private boolean provjeriPostojanje(Paket primljeniPaket, Vozilo vozilo) {
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      if (primljeniPaket.dohvatiOznaku().equals(paket.dohvatiOznaku())) {
        return true;
      }
    }
    return false;
  }

  private void voziloUDostavi(LocalDateTime virtualnoVrijeme) {
    String virtualnoVrijemeString = pretvoriVirtualnoVrijemeUString(virtualnoVrijeme);
    for (Vozilo vozilo : sortirajVozilaPremaRedoslijedu(
        TvrtkaSingleton.getInstance().listaVozila)) {
      if (vozilo.dohvatiTrenutniStatus().equals("A")) {
        if (vozilo.dostavlja == true) {
          String vrijemePreuzimanja = "";
          String oznaka = "";
          for (Paket paket : vozilo.listaUkrcanihPaketa) {
            for (int i = 0; i < oznakeIVremenaPreuzimanja.size(); i++) {
              oznaka = oznakeIVremenaPreuzimanja.get(i);

              if (paket.dohvatiOznaku().equals(oznaka)) {
                if (i < oznakeIVremenaPreuzimanja.size() - 1) {
                  vrijemePreuzimanja = oznakeIVremenaPreuzimanja.get(i + 1);
                }
              }
            }
            if (provjeriPostojanjeDostavljenihPaketa(paket) == true) {
              continue;
            }
            LocalDateTime vrijemePreuzimanjaLocalDateTime =
                vrijemePreuzimanjaULocalDateTime(vrijemePreuzimanja);
            if (vrijemePreuzimanjaLocalDateTime.isBefore(virtualnoVrijeme)
                || vrijemePreuzimanjaLocalDateTime.equals(virtualnoVrijeme)) {
              provjeraOznakeIVremenaPreuzimanja(paket, vrijemePreuzimanja);

              String vrijemePrijemaString = vrijemePrijemaUString(paket.dohvatiVrijemePrijema());
              SubjectObserver subjectObserver = new KonkretniSubject();
              if (paket.dohvatiTrenutniStatusPosiljatelj().equals("D")) {
                new PosiljateljObserver(subjectObserver, paket.dohvatiPosiljatelja(),
                    paket.dohvatiOznaku(), vrijemePrijemaString, vrijemePreuzimanja);
              }
              if (paket.dohvatiTrenutniStatusPrimatelj().equals("D")) {
                new PrimateljObserver(subjectObserver, paket.dohvatiPrimatelja(),
                    paket.dohvatiOznaku(), vrijemePrijemaString, vrijemePreuzimanja);
              }
              subjectObserver.obavijestiObserverPreuzet();

              System.out.println("VIRTUALNO VRIJEME: " + virtualnoVrijemeString
                  + " PAKET S OZNAKOM " + paket.dohvatiOznaku() + " JE DOSTAVLJEN PRIMATELJU "
                  + paket.dohvatiPrimatelja() + " VRIJEME PREUZIMANJA " + vrijemePreuzimanja
                  + "!\n");
              listaDostavljenihPaketa.add(paket);
              paket.dostavljen = true;
            }
          }
        }
      }
    }
  }

  private String vrijemePrijemaUString(Date vrijemePrijemaDate) {
    SimpleDateFormat formatDatuma = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    String vrijemePrijemaString = formatDatuma.format(vrijemePrijemaDate);
    return vrijemePrijemaString;
  }

  private void provjeraOznakeIVremenaPreuzimanja(Paket paket, String vrijemePreuzimanja) {
    for (int i = 0; i < oznakeIVremenaPreuzimanja.size(); i++) {
      String oznaka = oznakeIVremenaPreuzimanja.get(i);

      if (oznaka.equals(paket.dohvatiOznaku())) {
        if (i < oznakeIVremenaPreuzimanja.size() - 1) {
          oznakeIVremenaPreuzimanja.set(i + 1, vrijemePreuzimanja);
        }
      }
    }
  }

  private boolean provjeriPostojanjeDostavljenihPaketa(Paket dostavljeniPaket) {
    for (Paket paket : listaDostavljenihPaketa) {
      if (paket.dohvatiOznaku().equals(dostavljeniPaket.dohvatiOznaku())) {
        return true;
      }
    }
    return false;
  }

  private boolean provjeriPostojanjeUkrcanihPaketa(Paket ukrcaniPaket) {
    for (Paket paket : listaSvihUkrcanihPaketa) {
      if (paket.dohvatiOznaku().equals(ukrcaniPaket.dohvatiOznaku())) {
        return true;
      }
    }
    return false;
  }

  private Float izracunajVolumenTipskihPaketa(Paket primljeniPaket, Float volumen) {
    for (VrstaPaketa vrstaPaketa : TvrtkaSingleton.getInstance().listaVrstePaketa) {
      if (vrstaPaketa.dohvatiOznaku().equals(primljeniPaket.dohvatiVrstuPaketa())) {
        volumen =
            vrstaPaketa.dohvatiVisinu() * vrstaPaketa.dohvatiSirinu() * vrstaPaketa.dohvatiDuzinu();
      }
    }
    return volumen;
  }

  private Float izracunajVolumenPaketaTipaX(Paket primljeniPaket, Float volumen) {
    volumen = primljeniPaket.dohvatiVisinu() * primljeniPaket.dohvatiSirinu()
        * primljeniPaket.dohvatiDuzinu();
    return volumen;
  }

  public List<Vozilo> sortirajVozilaPremaRedoslijedu(List<Vozilo> listaVozila) {
    int n = listaVozila.size();
    boolean zamijenjeno;
    do {
      zamijenjeno = false;
      for (int i = 1; i < n; i++) {
        if (listaVozila.get(i - 1).dohvatiRedoslijed() > listaVozila.get(i).dohvatiRedoslijed()) {
          Vozilo temp = listaVozila.get(i - 1);
          listaVozila.set(i - 1, listaVozila.get(i));
          listaVozila.set(i, temp);
          zamijenjeno = true;
        }
      }
    } while (zamijenjeno);

    return listaVozila;
  }

  private String pretvoriVirtualnoVrijemeUString(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    return virtualnoVrijeme.format(formatter);
  }

  private LocalDateTime vrijemePreuzimanjaULocalDateTime(String vrijemePreuzimanja) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime vrijemePreuzimanjaLocalDateTime =
        LocalDateTime.parse(vrijemePreuzimanja, formatter);
    return vrijemePreuzimanjaLocalDateTime;
  }
}
