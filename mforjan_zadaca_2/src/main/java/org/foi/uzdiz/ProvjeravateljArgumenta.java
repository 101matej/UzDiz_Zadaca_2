package org.foi.uzdiz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class ProvjeravateljArgumenta {
  public int brojacGresaka = 0;

  public int dohvatiBrojacGresaka() {
    return brojacGresaka;
  }

  public void provjeriArgumentVp(String datotekaVp) {
    if (datotekaVp == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR VP NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaVp = datotekaVp.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaVp);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuVp(datotekaVp);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR VP NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPv(String datotekaPv) {
    if (datotekaPv == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PV NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPv = datotekaPv.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPv);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPv(datotekaPv);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PV NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPp(String datotekaPp) {
    if (datotekaPp == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PP NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPp = datotekaPp.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPp);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPp(datotekaPp);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PP NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPo(String datotekaPo) {
    if (datotekaPo == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PO NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPo = datotekaPo.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPo);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPo(datotekaPo);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PO NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPm(String datotekaPm) {
    if (datotekaPm == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PM NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPm = datotekaPm.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPm);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPm(datotekaPm);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PM NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPu(String datotekaPu) {
    if (datotekaPu == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PU NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPu = datotekaPu.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPu);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPu(datotekaPu);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PU NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPmu(String datotekaPmu) {
    if (datotekaPmu == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PMU NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    datotekaPmu = datotekaPmu.trim();
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.csv");
    Matcher matcher = pattern.matcher(datotekaPmu);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajDatotekuPmu(datotekaPmu);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PMU NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentMt(String stringVrijednostMt) {
    if (stringVrijednostMt == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR MT NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostMt = stringVrijednostMt.trim();
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher matcher = pattern.matcher(stringVrijednostMt);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajVrijednostMt(Integer.parseInt(stringVrijednostMt));
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR MT NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentVi(String stringVrijednostVi) {
    if (stringVrijednostVi == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR VI NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostVi = stringVrijednostVi.trim();
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher matcher = pattern.matcher(stringVrijednostVi);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajVrijednostVi(Integer.parseInt(stringVrijednostVi));
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR VI NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentVs(String stringVrijednostVs) {
    if (stringVrijednostVs == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR VS NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    Pattern pattern = Pattern.compile("(3[01]|[12][0-9]|0[1-9])[.](1[0-2]|0[1-9])[.][0-9]{4}[.] "
        + "(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9])");
    Matcher matcher = pattern.matcher(stringVrijednostVs);
    if (matcher.matches()) {
      SimpleDateFormat formatDatuma = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
      try {
        Date vrijednostVs = formatDatuma.parse(stringVrijednostVs);
        TvrtkaSingleton.getInstance().ucitajVrijednostVs(vrijednostVs);
      } catch (Exception e) {
        UpraviteljGresakaSingleton.getInstance().sustavskaGreska(e);
      }
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR VS NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentMs(String stringVrijednostMs) {
    if (stringVrijednostMs == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR MS NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostMs = stringVrijednostMs.trim();
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher matcher = pattern.matcher(stringVrijednostMs);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajVrijednostMs(Integer.parseInt(stringVrijednostMs));
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR MS NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentPr(String stringVrijednostPr) {
    if (stringVrijednostPr == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR PR NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostPr = stringVrijednostPr.trim();
    Pattern pattern = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])");
    Matcher matcher = pattern.matcher(stringVrijednostPr);
    if (matcher.matches()) {
      SimpleDateFormat formatDatuma = new SimpleDateFormat("hh:mm");
      try {
        Date vrijednostPr = formatDatuma.parse(stringVrijednostPr);
        TvrtkaSingleton.getInstance().ucitajVrijednostPr(vrijednostPr);
      } catch (Exception e) {
        UpraviteljGresakaSingleton.getInstance().sustavskaGreska(e);
      }
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR PR NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentKr(String stringVrijednostKr) {
    if (stringVrijednostKr == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR KR NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostKr = stringVrijednostKr.trim();
    Pattern pattern = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])");
    Matcher matcher = pattern.matcher(stringVrijednostKr);
    if (matcher.matches()) {
      SimpleDateFormat formatDatuma = new SimpleDateFormat("hh:mm");
      try {
        Date vrijednostKr = formatDatuma.parse(stringVrijednostKr);
        TvrtkaSingleton.getInstance().ucitajVrijednostKr(vrijednostKr);
      } catch (Exception e) {
        UpraviteljGresakaSingleton.getInstance().sustavskaGreska(e);
      }
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR KR NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }

  public void provjeriArgumentGps(String stringVrijednostGps) {
    if (stringVrijednostGps == null) {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("PARAMETAR GPS NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    String cistiGps = stringVrijednostGps.replaceAll("\\s+", "");
    Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]+)?),(-?[0-9]+(\\.[0-9]+)?)");
    Matcher matcher = pattern.matcher(cistiGps);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance().ucitajVrijednostGps(cistiGps);
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR GPS NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }

  }

  public void provjeriArgumentIsporuka(String stringVrijednostIsporuka) {
    if (stringVrijednostIsporuka == null) {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR ISPORUKA NE POSTOJI!");
      brojacGresaka++;
      return;
    }
    stringVrijednostIsporuka = stringVrijednostIsporuka.trim();
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher matcher = pattern.matcher(stringVrijednostIsporuka);
    if (matcher.matches()) {
      TvrtkaSingleton.getInstance()
          .ucitajVrijednostIsporuka(Integer.parseInt(stringVrijednostIsporuka));
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("PARAMETAR ISPORUKA NIJE U ISPRAVNOM FORMATU!");
      brojacGresaka++;
    }
  }
}
