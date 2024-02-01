package org.foi.uzdiz;

import java.util.Properties;

public class ProvjeravateljParametara {
  private ProvjeravateljArgumenta provjeravateljArgumenta;

  public ProvjeravateljParametara(ProvjeravateljArgumenta provjeravateljArgumenta) {
    this.provjeravateljArgumenta = provjeravateljArgumenta;
  }

  public void provjeriParametre(Properties postavke) {
    provjeravateljArgumenta.provjeriArgumentVp(postavke.getProperty("vp"));
    provjeravateljArgumenta.provjeriArgumentPv(postavke.getProperty("pv"));
    provjeravateljArgumenta.provjeriArgumentPp(postavke.getProperty("pp"));
    provjeravateljArgumenta.provjeriArgumentPo(postavke.getProperty("po"));
    provjeravateljArgumenta.provjeriArgumentPm(postavke.getProperty("pm"));
    provjeravateljArgumenta.provjeriArgumentPu(postavke.getProperty("pu"));
    provjeravateljArgumenta.provjeriArgumentPmu(postavke.getProperty("pmu"));
    provjeravateljArgumenta.provjeriArgumentMt(postavke.getProperty("mt"));
    provjeravateljArgumenta.provjeriArgumentVi(postavke.getProperty("vi"));
    provjeravateljArgumenta.provjeriArgumentVs(postavke.getProperty("vs"));
    provjeravateljArgumenta.provjeriArgumentMs(postavke.getProperty("ms"));
    provjeravateljArgumenta.provjeriArgumentPr(postavke.getProperty("pr"));
    provjeravateljArgumenta.provjeriArgumentKr(postavke.getProperty("kr"));
    provjeravateljArgumenta.provjeriArgumentGps(postavke.getProperty("gps"));
    provjeravateljArgumenta.provjeriArgumentIsporuka(postavke.getProperty("isporuka"));
  }
}
