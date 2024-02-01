package org.foi.uzdiz.uredi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.modeli.VrstaPaketa;
import org.foi.uzdiz.observer.KonkretniSubject;
import org.foi.uzdiz.observer.PosiljateljObserver;
import org.foi.uzdiz.observer.PrimateljObserver;
import org.foi.uzdiz.observer.SubjectObserver;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;

public class UredZaPrijemSingleton {
  public static UredZaPrijemSingleton uredZaPrijem;

  public Float saldo = 0.0f;

  public List<Paket> listaPrimljenihPaketa = new ArrayList<Paket>();

  private UredZaPrijemSingleton() {

  }

  public static UredZaPrijemSingleton getInstance() {
    if (uredZaPrijem == null) {
      uredZaPrijem = new UredZaPrijemSingleton();
    }
    return uredZaPrijem;
  }

  public void primiPaket(LocalDateTime trenutnoVirtualnoVrijeme) {
    for (Paket paket : TvrtkaSingleton.getInstance().listaPaketa) {
      LocalDateTime localDateTimeVrijemePrijema =
          vrijemePrijemaPaketa(paket.dohvatiVrijemePrijema());

      if (localDateTimeVrijemePrijema.isBefore(trenutnoVirtualnoVrijeme)) {
        boolean postojiPaket = false;

        for (Paket primljeniPaket : listaPrimljenihPaketa) {
          if (paket.dohvatiOznaku().equals(primljeniPaket.dohvatiOznaku())) {
            postojiPaket = true;
            break;
          }
        }

        if (!postojiPaket) {
          String vrijemePrijemaString = vrijemePrijemaUString(paket.dohvatiVrijemePrijema());
          String vrijemePreuzimanja = "";
          SubjectObserver subjectObserver = new KonkretniSubject();
          if (paket.dohvatiTrenutniStatusPosiljatelj().equals("D")) {
            new PosiljateljObserver(subjectObserver, paket.dohvatiPosiljatelja(),
                paket.dohvatiOznaku(), vrijemePrijemaString, vrijemePreuzimanja);
          }
          if (paket.dohvatiTrenutniStatusPrimatelj().equals("D")) {
            new PrimateljObserver(subjectObserver, paket.dohvatiPrimatelja(), paket.dohvatiOznaku(),
                vrijemePrijemaString, vrijemePreuzimanja);
          }
          subjectObserver.obavijestiObserverZaprimljen();
          listaPrimljenihPaketa.add(paket);
          Float cijenaDostave = 0.0f;
          cijenaDostave = definirajCijenuDostave(paket, cijenaDostave);
          naplatiIznosDostave(cijenaDostave);
        }
      }
    }
  }

  private LocalDateTime vrijemePrijemaPaketa(Date vrijemePrijema) {
    Instant instantVrijemePrijema = vrijemePrijema.toInstant();
    LocalDateTime localDateTimeVrijemePrijema =
        instantVrijemePrijema.atZone(ZoneId.systemDefault()).toLocalDateTime();
    return localDateTimeVrijemePrijema;
  }

  private String vrijemePrijemaUString(Date vrijemePrijemaDate) {
    SimpleDateFormat formatDatuma = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    String vrijemePrijemaString = formatDatuma.format(vrijemePrijemaDate);
    return vrijemePrijemaString;
  }

  public Float definirajCijenuDostave(Paket paket, Float cijenaDostave) {
    for (VrstaPaketa vrstaPaketa : TvrtkaSingleton.getInstance().listaVrstePaketa) {
      if (paket.dohvatiVrstuPaketa().equals(vrstaPaketa.dohvatiOznaku())) {
        if (paket.dohvatiVrstuPaketa().equals("X")) {
          if (paket.dohvatiUsluguDostave().equals("H")) {
            Float volumen = izracunajVolumen(paket.dohvatiVisinu(), paket.dohvatiSirinu(),
                paket.dohvatiDuzinu());
            Float umnozakCijenaPIVolumen = vrstaPaketa.dohvatiCijenuP() * volumen;
            Float umnozakCijenaTITezina = vrstaPaketa.dohvatiCijenuT() * paket.dohvatiTezinu();
            cijenaDostave =
                vrstaPaketa.dohvatiCijenuHitno() + umnozakCijenaPIVolumen + umnozakCijenaTITezina;
          } else if (paket.dohvatiUsluguDostave().equals("S")
              || paket.dohvatiUsluguDostave().equals("H")
              || paket.dohvatiUsluguDostave().equals("R")) {
            Float volumen = izracunajVolumen(paket.dohvatiVisinu(), paket.dohvatiSirinu(),
                paket.dohvatiDuzinu());
            Float umnozakCijenaPIVolumen = vrstaPaketa.dohvatiCijenuP() * volumen;
            Float umnozakCijenaTITezina = vrstaPaketa.dohvatiCijenuT() * paket.dohvatiTezinu();
            cijenaDostave =
                vrstaPaketa.dohvatiCijenu() + umnozakCijenaPIVolumen + umnozakCijenaTITezina;
          } else {
            cijenaDostave = 0.0f;
          }
        } else {
          if (paket.dohvatiUsluguDostave().equals("H")) {
            cijenaDostave = vrstaPaketa.dohvatiCijenuHitno();
          } else if (paket.dohvatiUsluguDostave().equals("S")
              || paket.dohvatiUsluguDostave().equals("H")
              || paket.dohvatiUsluguDostave().equals("R")) {
            cijenaDostave = vrstaPaketa.dohvatiCijenu();
          } else {
            cijenaDostave = 0.0f;
          }
        }
      }
    }
    return cijenaDostave;
  }

  private Float izracunajVolumen(Float visina, Float sirina, Float duzina) {
    return visina * sirina * duzina;
  }

  public void naplatiIznosDostave(Float iznosDostave) {
    saldo = saldo + iznosDostave;
  }
}
