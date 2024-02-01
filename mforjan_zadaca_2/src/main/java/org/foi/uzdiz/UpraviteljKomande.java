package org.foi.uzdiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.composite.Component;
import org.foi.uzdiz.composite.Composite;
import org.foi.uzdiz.composite.Leaf;
import org.foi.uzdiz.modeli.Mjesto;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.modeli.Podrucje;
import org.foi.uzdiz.modeli.Ulica;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.proxy.Proxy;
import org.foi.uzdiz.proxy.SubjectProxy;
import org.foi.uzdiz.state.ConcreteStateAktivno;
import org.foi.uzdiz.state.ConcreteStateNeaktivno;
import org.foi.uzdiz.state.ConcreteStateNeispravno;
import org.foi.uzdiz.state.ConcreteStatePosiljateljDa;
import org.foi.uzdiz.state.ConcreteStatePosiljateljNe;
import org.foi.uzdiz.state.ConcreteStatePrimateljDa;
import org.foi.uzdiz.state.ConcreteStatePrimateljNe;
import org.foi.uzdiz.state.PosiljateljState;
import org.foi.uzdiz.state.PrimateljState;
import org.foi.uzdiz.state.VoziloState;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.visitor.IspisVisitor;
import org.foi.uzdiz.visitor.VoziloElement;
import org.foi.uzdiz.visitor.VoziloVisitor;

public class UpraviteljKomande {
  public UpraviteljKomande() {

  }

  public void izmjeniStatusVozila(String registracijaVozila, String status,
      LocalDateTime trenutnoVirtualnoVrijeme) {
    boolean ispravnaRegistracija = provjeriRegistracijuVozila(registracijaVozila);
    boolean ispravanStatus = provjeriStatusVozila(status);
    if (ispravnaRegistracija && ispravanStatus) {
      String virtualnoVrijemeString = virtualnoVrijemeUString(trenutnoVirtualnoVrijeme);
      for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
        if (registracijaVozila.equals(vozilo.dohvatiRegistraciju())) {
          if ("NI".equals(vozilo.dohvatiTrenutniStatus())) {
            UpraviteljGresakaSingleton.getInstance().greskaURadu("Vozilu " + registracijaVozila
                + " ne možete promijeniti status jer je neispravno!");
            return;
          }
          VoziloState noviStatus = odaberiStatus(status);
          vozilo.postaviStatus(noviStatus);
          vozilo.promijeniStatus(virtualnoVrijemeString);
        }
      }
    }
  }

  private VoziloState odaberiStatus(String status) {
    switch (status) {
      case "A":
        return new ConcreteStateAktivno();
      case "NI":
        return new ConcreteStateNeispravno();
      case "NA":
        return new ConcreteStateNeaktivno();
      default:
        throw new IllegalArgumentException("Nepodržan status: " + status);
    }
  }

  private boolean provjeriRegistracijuVozila(String registracijaVozila) {
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (registracijaVozila.equals(vozilo.dohvatiRegistraciju())) {
        return true;
      }
    }
    UpraviteljGresakaSingleton.getInstance()
        .greskaURadu("VOZILO S NAVEDENOM REGISTRACIJSKOM OZNAKOM NE POSTOJI!");
    return false;
  }

  private boolean provjeriStatusVozila(String status) {
    Pattern pattern = Pattern.compile("A|NI|NA");
    Matcher matcher = pattern.matcher(status);
    if (matcher.matches()) {
      return true;
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("UNESENI STATUS VOZILA NIJE ISPRAVAN!");
      return false;
    }
  }

  private String virtualnoVrijemeUString(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    String virtualnoVrijemeString = virtualnoVrijeme.format(formatter);
    return virtualnoVrijemeString;
  }

  public void ispisiPodrucjaMjestaUlice() {
    for (Podrucje podrucje : TvrtkaSingleton.getInstance().listaPodrucja) {
      System.out.println("==============================================");
      Component compositePodrucje = new Composite("ID PODRUČJA: " + podrucje.dohvatiId());
      String[] listaGradUlica = podrucje.dohvatiGradUlicu().split(",");
      Set<Integer> postojeciGradovi = new HashSet<>();
      Set<Integer> postojeceUlice = new HashSet<>();

      for (String gradUlica : listaGradUlica) {
        String[] listaGradovaIUlica = gradUlica.split(":");
        Integer gradId = Integer.valueOf(listaGradovaIUlica[0]);

        if (!postojeciGradovi.contains(gradId)) {
          postojeciGradovi.add(gradId);

          for (Mjesto mjesto : TvrtkaSingleton.getInstance().listaMjesta) {
            if (gradId == mjesto.dohvatiId()) {
              Component compositeGradUlica =
                  new Composite("    NAZIV GRADA: " + mjesto.dohvatiNaziv());
              compositePodrucje.dodajDio(compositeGradUlica);
              if (!listaGradovaIUlica[1].equals("*")) {
                for (Ulica pronadenaUlica : TvrtkaSingleton.getInstance().listaUlica) {
                  String[] listaGradUlicaPodrucja = podrucje.dohvatiGradUlicu().split(",");
                  for (String pronadenaUlicaPodrucja : listaGradUlicaPodrucja) {
                    String[] listaGradovaIUlicaPodrucja = pronadenaUlicaPodrucja.split(":");
                    if (!listaGradovaIUlicaPodrucja[1].equals("*")) {
                      Integer idGrada = Integer.valueOf(listaGradovaIUlicaPodrucja[0]);
                      Integer ulicaId = Integer.valueOf(listaGradovaIUlicaPodrucja[1]);
                      if (ulicaId == pronadenaUlica.dohvatiId() && idGrada == mjesto.dohvatiId()) {
                        if (!postojeceUlice.contains(ulicaId)) {
                          postojeceUlice.add(ulicaId);
                          compositeGradUlica.dodajDio(
                              new Leaf("        NAZIV ULICE: " + pronadenaUlica.dohvatiNaziv()));
                          break;
                        }
                      }
                    }
                  }
                }
              } else {
                for (String ulicaMjesta : mjesto.dohvatiUlicu().split(",")) {
                  Integer ulicaMjestaInteger = Integer.valueOf(ulicaMjesta);
                  for (Ulica pronadenaUlica : TvrtkaSingleton.getInstance().listaUlica) {
                    if (ulicaMjestaInteger == pronadenaUlica.dohvatiId()) {
                      compositeGradUlica.dodajDio(
                          new Leaf("        NAZIV ULICE: " + pronadenaUlica.dohvatiNaziv()));
                    }
                  }
                }
              }
            }
          }
        }
      }
      compositePodrucje.ispisiStrukturu();
    }
    System.out.println("==============================================");
  }

  public void izmjeniStatusSlanjaObavijesti(String osoba, String oznakaPaketa, String status) {
    boolean postojiPaket = provjeriPostojanjePaketa(oznakaPaketa);
    if (postojiPaket) {
      boolean ispravnaOsoba = provjeriOsobu(oznakaPaketa, osoba);
      for (Paket paket : TvrtkaSingleton.getInstance().listaPaketa) {
        if (oznakaPaketa.equals(paket.dohvatiOznaku()) && ispravnaOsoba) {
          if (osoba.equals(paket.dohvatiPosiljatelja())) {
            PosiljateljState noviStatusPosiljatelj = odaberiStatusPosiljatelja(status);
            paket.postaviStatusPosiljatelj(noviStatusPosiljatelj);
            paket.promijeniStatusPosiljatelj();
          } else {
            PrimateljState noviStatusPrimateljj = odaberiStatusPrimatelja(status);
            paket.postaviStatusPrimatelj(noviStatusPrimateljj);
            paket.promijeniStatusPrimatelj();
          }
        }
      }
    }
  }

  private boolean provjeriPostojanjePaketa(String oznakaPaketa) {
    for (Paket paket : TvrtkaSingleton.getInstance().listaPaketa) {
      if (oznakaPaketa.equals(paket.dohvatiOznaku())) {
        return true;
      }
    }
    UpraviteljGresakaSingleton.getInstance().greskaURadu("OZNAKA PAKETA NE POSTOJI!");
    return false;
  }

  private boolean provjeriOsobu(String oznakaPaketa, String osoba) {
    for (Paket paket : TvrtkaSingleton.getInstance().listaPaketa) {
      if (oznakaPaketa.equals(paket.dohvatiOznaku()) && (osoba.equals(paket.dohvatiPosiljatelja())
          || osoba.equals(paket.dohvatiPrimatelja()))) {
        return true;
      }
    }
    UpraviteljGresakaSingleton.getInstance()
        .greskaURadu("OSOBA " + osoba + " NIJE NI POŠILJATELJ NI PRIMATELJ ZA DEFINIRANI PAKET!");
    return false;
  }

  private PosiljateljState odaberiStatusPosiljatelja(String status) {
    switch (status) {
      case "D":
        return new ConcreteStatePosiljateljDa();
      case "N":
        return new ConcreteStatePosiljateljNe();
      default:
        throw new IllegalArgumentException("Nepodržan status: " + status);
    }
  }

  private PrimateljState odaberiStatusPrimatelja(String status) {
    switch (status) {
      case "D":
        return new ConcreteStatePrimateljDa();
      case "N":
        return new ConcreteStatePrimateljNe();
      default:
        throw new IllegalArgumentException("Nepodržan status: " + status);
    }
  }

  public void dodajVozilo(String registracija, String opis, String kapacitetTezine,
      String kapacitetProstora, String redoslijed, String prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    SubjectProxy subjectProxy = new Proxy();
    subjectProxy.dodajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora, redoslijed,
        prosjecnaBrzina, podrucjaPoRangu, status);
  }

  public void pregledajPodatkeVozila(LocalDateTime trenutnoVirtualnoVrijeme) {
    String virtualnoVrijemeString = virtualnoVrijemeUString(trenutnoVirtualnoVrijeme);
    VoziloVisitor ispisVisitor = new IspisVisitor();
    System.out.println("\nTRENUTNO VIRTUALNO VRIJEME: " + virtualnoVrijemeString);
    ispisiZaglavlje();
    for (VoziloElement vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      vozilo.accept(ispisVisitor);
    }
    System.out.println(
        "----------------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------");
  }

  private void ispisiZaglavlje() {
    System.out.println(
        "----------------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------");
    System.out.println(
        "| Registracija vozila |     Status     | Broj hitnih paketa u vozilu | Broj običnih paketa u vozilu "
            + "| Broj isporučenih paketa | Trenutni % zauzeća prostora | Trenutni % zauzeća težine |");
    System.out.println(
        "----------------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------");
  }
}
