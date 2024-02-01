package org.foi.uzdiz.visitor;

import java.text.DecimalFormat;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;

public class IspisVisitor implements VoziloVisitor {

  @Override
  public void visit(Vozilo vozilo) {
    Integer brojHitnihPaketa = dohvatiBrojHitnihPaketa(vozilo);
    Integer brojObicnihPaketa = dohvatiBrojObicnihPaketa(vozilo);
    Integer brojIsporucenihPaketa = dohvatiBrojIsporucenihPaketa(vozilo);
    Double trenutniPostotakZauzecaProstora = dohvatiTrenutniPostotakZauzecaProstora(vozilo);
    String trenutniPostotakZauzecaProstoraString =
        formatirajNaDvijeDecimale(trenutniPostotakZauzecaProstora);
    Double trenutniPostotakZauzecaTezine = dohvatiTrenutniPostotakZauzecaTezine(vozilo);
    String trenutniPostotakZauzecaTezineString =
        formatirajNaDvijeDecimale(trenutniPostotakZauzecaTezine);

    System.out.printf("| %-19s | %-14s | %-27s | %-28s | %-23s | %-27s | %-25s |\n",
        vozilo.dohvatiRegistraciju(), vozilo.dohvatiTrenutniStatus(), brojHitnihPaketa,
        brojObicnihPaketa, brojIsporucenihPaketa, trenutniPostotakZauzecaProstoraString + "%",
        trenutniPostotakZauzecaTezineString + "%");
  }

  private Integer dohvatiBrojHitnihPaketa(Vozilo vozilo) {
    Integer brojacPaketa = 0;
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      if (paket.dohvatiUsluguDostave().equals("H")) {
        brojacPaketa++;
      }
    }
    return brojacPaketa;
  }

  private Integer dohvatiBrojObicnihPaketa(Vozilo vozilo) {
    Integer brojacPaketa = 0;
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      if (!paket.dohvatiUsluguDostave().equals("H")) {
        brojacPaketa++;
      }
    }
    return brojacPaketa;
  }

  private Integer dohvatiBrojIsporucenihPaketa(Vozilo vozilo) {
    Integer brojacPaketa = 0;
    for (Paket paket : vozilo.listaUkrcanihPaketa) {
      if (paket.dostavljen) {
        brojacPaketa++;
      }
    }
    return brojacPaketa;
  }

  private double dohvatiTrenutniPostotakZauzecaProstora(Vozilo trazenoVozilo) {
    Double trenutniPostotak = 0.0;
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (trazenoVozilo.dohvatiRegistraciju().equals(vozilo.dohvatiRegistraciju())) {
        trenutniPostotak =
            izracunajPostotakProstora(vozilo.trenutniProstor, vozilo.dohvatikapacitetProstora());
      }
    }
    return trenutniPostotak;
  }

  private Double izracunajPostotakProstora(Float trenutniProstor, Float kapacitetProstora) {
    return (double) (trenutniProstor / kapacitetProstora) * 100;
  }

  private double dohvatiTrenutniPostotakZauzecaTezine(Vozilo trazenoVozilo) {
    Double trenutniPostotak = 0.0;
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (trazenoVozilo.dohvatiRegistraciju().equals(vozilo.dohvatiRegistraciju())) {
        trenutniPostotak =
            izracunajPostotakTezine(vozilo.trenutnaTezina, vozilo.dohvatikapacitetTezine());
      }
    }
    return trenutniPostotak;
  }

  private Double izracunajPostotakTezine(Float trenutnaTezina, Float kapacitetTezine) {
    return (double) (trenutnaTezina / kapacitetTezine) * 100;
  }

  private String formatirajNaDvijeDecimale(Double broj) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(broj);
  }
}
