package org.foi.uzdiz.tvrtka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.factoryMethod.CitacDatoteka;
import org.foi.uzdiz.factoryMethod.MjestaFactory;
import org.foi.uzdiz.factoryMethod.OsobeFactory;
import org.foi.uzdiz.factoryMethod.PaketiFactory;
import org.foi.uzdiz.factoryMethod.PodrucjaFactory;
import org.foi.uzdiz.factoryMethod.UliceFactory;
import org.foi.uzdiz.factoryMethod.VozilaFactory;
import org.foi.uzdiz.factoryMethod.VrstePaketaFactory;
import org.foi.uzdiz.modeli.Mjesto;
import org.foi.uzdiz.modeli.Osoba;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.modeli.Podrucje;
import org.foi.uzdiz.modeli.Ulica;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.modeli.VrstaPaketa;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class TvrtkaSingleton {
  public static TvrtkaSingleton tvrtka;

  private String datotekaVp = "";
  private String datotekaPv = "";
  private String datotekaPp = "";
  private String datotekaPo = "";
  private String datotekaPm = "";
  private String datotekaPu = "";
  private String datotekaPmu = "";
  private int vrijednostMt = 0;
  private int vrijednostVi = 0;
  private Date vrijednostVs = null;
  private int vrijednostMs = 0;
  private Date vrijednostPr = null;
  private Date vrijednostKr = null;
  private String gps = "";
  private int vrijednostIsporuka = 0;
  public List<Vozilo> listaVozila = new ArrayList<Vozilo>();
  public List<VrstaPaketa> listaVrstePaketa = new ArrayList<VrstaPaketa>();
  public List<Paket> listaPaketa = new ArrayList<Paket>();
  public List<Osoba> listaOsoba = new ArrayList<Osoba>();
  public List<Mjesto> listaMjesta = new ArrayList<Mjesto>();
  public List<Podrucje> listaPodrucja = new ArrayList<Podrucje>();
  public List<Ulica> listaUlica = new ArrayList<Ulica>();

  private TvrtkaSingleton() {

  }

  public static TvrtkaSingleton getInstance() {
    if (tvrtka == null) {
      tvrtka = new TvrtkaSingleton();
    }
    return tvrtka;
  }

  public void ucitajDatoteke() {
    ucitajPodrucja();
    ucitajVozila();
    ucitajVrstePaketa();
    ucitajPakete();
    ucitajOsobe();
    ucitajUlice();
    ucitajMjesta();
  }

  private void ucitajVozila() {
    CitacDatoteka datotekaVozila = new VozilaFactory().ucitajDatoteku(datotekaPv);
    if (datotekaVozila != null) {
      datotekaVozila.ucitajPodatke(datotekaPv);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s popisom vozila!");
    }
  }

  private void ucitajVrstePaketa() {
    CitacDatoteka datotekaVrstaPaketa = new VrstePaketaFactory().ucitajDatoteku(datotekaVp);
    if (datotekaVrstaPaketa != null) {
      datotekaVrstaPaketa.ucitajPodatke(datotekaVp);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s vrstama paketa!");
    }
  }

  private void ucitajPakete() {
    CitacDatoteka datotekaPaketa = new PaketiFactory().ucitajDatoteku(datotekaPp);
    if (datotekaPaketa != null) {
      datotekaPaketa.ucitajPodatke(datotekaPp);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s paketima!");
    }
  }

  private void ucitajOsobe() {
    CitacDatoteka datotekaOsoba = new OsobeFactory().ucitajDatoteku(datotekaPo);
    if (datotekaOsoba != null) {
      datotekaOsoba.ucitajPodatke(datotekaPo);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s osobama!");
    }
  }

  private void ucitajMjesta() {
    CitacDatoteka datotekaMjesta = new MjestaFactory().ucitajDatoteku(datotekaPm);
    if (datotekaMjesta != null) {
      datotekaMjesta.ucitajPodatke(datotekaPm);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s mjestima!");
    }
  }

  private void ucitajPodrucja() {
    CitacDatoteka datotekaPodrucja = new PodrucjaFactory().ucitajDatoteku(datotekaPmu);
    if (datotekaPodrucja != null) {
      datotekaPodrucja.ucitajPodatke(datotekaPmu);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s područjima!");
    }
  }

  private void ucitajUlice() {
    CitacDatoteka datotekaUlica = new UliceFactory().ucitajDatoteku(datotekaPu);
    if (datotekaUlica != null) {
      datotekaUlica.ucitajPodatke(datotekaPu);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaSDatotekama("Neuspješno učitavanje datoteke s ulicama!");
    }
  }

  public void ucitajDatotekuVp(String ucitanaDatotekaVp) {
    datotekaVp = ucitanaDatotekaVp;
  }

  public String dohvatiDatotekuVp() {
    return datotekaVp;
  }

  public void ucitajDatotekuPv(String ucitanaDatotekaPv) {
    datotekaPv = ucitanaDatotekaPv;
  }

  public String dohvatiDatotekuPv() {
    return datotekaPv;
  }

  public void ucitajDatotekuPp(String ucitanaDatotekaPp) {
    datotekaPp = ucitanaDatotekaPp;
  }

  public String dohvatiDatotekuPp() {
    return datotekaPp;
  }

  public void ucitajDatotekuPo(String ucitanaDatotekaPo) {
    datotekaPo = ucitanaDatotekaPo;
  }

  public String dohvatiDatotekuPo() {
    return datotekaPo;
  }

  public void ucitajDatotekuPm(String ucitanaDatotekaPm) {
    datotekaPm = ucitanaDatotekaPm;
  }

  public String dohvatiDatotekuPm() {
    return datotekaPm;
  }

  public void ucitajDatotekuPu(String ucitanaDatotekaPu) {
    datotekaPu = ucitanaDatotekaPu;
  }

  public String dohvatiDatotekuPu() {
    return datotekaPu;
  }

  public void ucitajDatotekuPmu(String ucitanaDatotekaPmu) {
    datotekaPmu = ucitanaDatotekaPmu;
  }

  public String dohvatiDatotekuPmu() {
    return datotekaPmu;
  }

  public void ucitajVrijednostMt(int ucitanaVrijednostMt) {
    vrijednostMt = ucitanaVrijednostMt;
  }

  public int dohvatiVrijednostMt() {
    return vrijednostMt;
  }

  public void ucitajVrijednostVi(int ucitanaVrijednostVi) {
    vrijednostVi = ucitanaVrijednostVi;
  }

  public int dohvatiVrijednostVi() {
    return vrijednostVi;
  }

  public void ucitajVrijednostVs(Date ucitanaVrijednostVs) {
    vrijednostVs = ucitanaVrijednostVs;
  }

  public Date dohvatiVrijednostVs() {
    return vrijednostVs;
  }

  public void ucitajVrijednostMs(int ucitanaVrijednostMs) {
    vrijednostMs = ucitanaVrijednostMs;
  }

  public int dohvatiVrijednostMs() {
    return vrijednostMs;
  }

  public void ucitajVrijednostPr(Date ucitanaVrijednostPr) {
    vrijednostPr = ucitanaVrijednostPr;
  }

  public Date dohvatiVrijednostPr() {
    return vrijednostPr;
  }

  public void ucitajVrijednostKr(Date ucitanaVrijednostKr) {
    vrijednostKr = ucitanaVrijednostKr;
  }

  public Date dohvatiVrijednostKr() {
    return vrijednostKr;
  }

  public void ucitajVrijednostGps(String ucitanaVrijednostGps) {
    gps = ucitanaVrijednostGps;
  }

  public String dohvatiVrijednostGps() {
    return gps;
  }

  public void ucitajVrijednostIsporuka(int ucitanaVrijednostIsporuka) {
    vrijednostIsporuka = ucitanaVrijednostIsporuka;
  }

  public int dohvatiVrijednostIsporuka() {
    return vrijednostIsporuka;
  }
}
