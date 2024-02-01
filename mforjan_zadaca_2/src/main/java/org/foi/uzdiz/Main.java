package org.foi.uzdiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.ispis.Ispis;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class Main {

  static int brojSatiRada = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean izlazIzPrograma = false;

    if (args.length == 1) {
      if (provjeraDatotekeParametara(args[0])) {
        if (Files.exists(Paths.get(args[0]))) {
          Properties postavke = new Properties();
          Set<String> vidjeniKljucevi = new HashSet<>();
          try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String linija;

            while ((linija = reader.readLine()) != null) {
              String[] kljucVrijednost = linija.split("=", 2);
              if (kljucVrijednost.length == 2) {
                String kljuc = kljucVrijednost[0].trim();
                String vrijednost = kljucVrijednost[1].trim();

                if (vidjeniKljucevi.contains(kljuc)) {
                  UpraviteljGresakaSingleton.getInstance()
                      .greskaUlazneKomande("DATOTEKA PARAMETARA SADRŽI DUPLIKAT KLJUČA!");
                  return;
                }
                vidjeniKljucevi.add(kljuc);
                postavke.setProperty(kljuc, vrijednost);
              }
            }

            if (postavke.size() == 15) {
              ProvjeravateljArgumenta provjeravateljArgumenta = new ProvjeravateljArgumenta();
              ProvjeravateljParametara provjeravateljParametara =
                  new ProvjeravateljParametara(provjeravateljArgumenta);
              provjeravateljParametara.provjeriParametre(postavke);

              if (provjeravateljArgumenta.dohvatiBrojacGresaka() == 0) {
                TvrtkaSingleton.getInstance().ucitajDatoteke();
                VirtualnoVrijemeSingleton.getInstance()
                    .ucitajPocetnoVrijemeVs(TvrtkaSingleton.getInstance().dohvatiVrijednostVs());
              } else {
                scanner.close();
                return;
              }
            } else {
              UpraviteljGresakaSingleton.getInstance()
                  .greskaUlazneKomande("DATOTEKA PARAMETARA NE SADRŽI 15 PARAMETARA!");
              scanner.close();
              return;
            }
          } catch (IOException e) {
            UpraviteljGresakaSingleton.getInstance().sustavskaGreska(e);
          }

        } else {
          UpraviteljGresakaSingleton.getInstance()
              .greskaUlazneKomande("DATOTEKA PARAMETARA NE POSTOJI!");
          scanner.close();
          return;
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaUlazneKomande("NAZIV DATOTEKE PARAMETARA JE U KRIVOM FORMATU!");
        scanner.close();
        return;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande(
          "POTREBNO JE UNIJETI JEDAN ULAZNI ARGUMENT NAZIVA DATOTEKE PARAMETARA!");
      scanner.close();
      return;
    }

    unesiKomandu(izlazIzPrograma, scanner);
    scanner.close();
  }

  private static boolean provjeraDatotekeParametara(String datotekaParametara) {
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.txt");
    Matcher matcher = pattern.matcher(datotekaParametara);
    if (matcher.matches()) {
      return true;
    } else {
      return false;
    }
  }

  private static void unesiKomandu(Boolean izlazIzPrograma, Scanner scanner) {
    do {
      System.out.print("\nUnesite komandu tražene aktivnosti: \n");
      String spojenaKomanda = scanner.nextLine();
      switch (spojenaKomanda.split(" ")[0]) {
        case "IP":
          provjeraKomandeIp(spojenaKomanda);
          break;
        case "VR":
          provjeraKomandeVr(spojenaKomanda);
          break;
        case "SV":
          provjeraKomandeSv(spojenaKomanda);
          break;
        case "PP":
          provjeraKomandePp(spojenaKomanda);
          break;
        case "PS":
          provjeraKomandePs(spojenaKomanda);
          break;
        case "PO":
          provjeraKomandePo(spojenaKomanda);
          break;
        case "DV":
          provjeraKomandeDv(scanner);
          break;
        case "Q":
          System.out.println("IZLAZ IZ PROGRAMA!");
          return;
        default:
          UpraviteljGresakaSingleton.getInstance()
              .greskaUlazneKomande("Ulazna komanda je neispravna!");
      }
    } while (!izlazIzPrograma);
  }

  private static void provjeraKomandeVr(String spojenaKomanda) {
    if (spojenaKomanda.split(" ").length == 2) {
      Pattern patternVr = Pattern.compile("[0-9]|0[0-9]|1[0-9]|2[0-4]");
      Matcher matcherVr = patternVr.matcher(spojenaKomanda.split(" ")[1]);
      if (matcherVr.matches()) {
        brojSatiRada = Integer.parseInt(spojenaKomanda.split(" ")[1]);
        inicijalizacijaVirtualnogSata();
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaURadu("Neispravno definiran broj sati rada!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURadu("Neispravna komanda VR hh!");
    }
  }

  private static void inicijalizacijaVirtualnogSata() {
    VirtualnoVrijemeSingleton.getInstance().ucitajBrojSatiRada(brojSatiRada);
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajVrijednostMs(TvrtkaSingleton.getInstance().dohvatiVrijednostMs());
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajPocetakRadaTvrtke(TvrtkaSingleton.getInstance().dohvatiVrijednostPr());
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajKrajRadaTvrtke(TvrtkaSingleton.getInstance().dohvatiVrijednostKr());
    VirtualnoVrijemeSingleton.getInstance().upravljajVirtualnimVremenom(
        TvrtkaSingleton.getInstance().dohvatiVrijednostVs(),
        TvrtkaSingleton.getInstance().dohvatiVrijednostPr(),
        TvrtkaSingleton.getInstance().dohvatiVrijednostKr());
  }

  private static void provjeraKomandeIp(String spojenaKomanda) {
    Pattern patternIp = Pattern.compile("IP");
    Matcher matcherIp = patternIp.matcher(spojenaKomanda);
    if (matcherIp.matches()) {
      LocalDateTime virtualnoVrijeme =
          VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;
      Ispis ispisPaketa = new Ispis();
      ispisPaketa.ispisPrimljenihIDostavljenihPaketa(virtualnoVrijeme);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA IP NIJE ISPRAVNA!");
    }
  }

  private static void provjeraKomandePs(String spojenaKomanda) {
    if (spojenaKomanda.split(" ").length == 3) {
      Pattern patternPs = Pattern.compile(".*\\S$");
      Matcher matcherPs = patternPs.matcher(spojenaKomanda);
      if (matcherPs.matches()) {
        LocalDateTime virtualnoVrijemePs =
            VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;
        UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
        upraviteljKomande.izmjeniStatusVozila(spojenaKomanda.split(" ")[1],
            spojenaKomanda.split(" ")[2], virtualnoVrijemePs);
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PS NIJE ISPRAVNA!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("KOMANDA PS MORA IMATI 3 PARAMETRA!");
    }
  }

  private static void provjeraKomandePp(String spojenaKomanda) {
    Pattern patternPp = Pattern.compile("PP");
    Matcher matcherPp = patternPp.matcher(spojenaKomanda);
    if (matcherPp.matches()) {
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.ispisiPodrucjaMjestaUlice();
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PP NIJE ISPRAVNA!");
    }
  }

  private static void provjeraKomandePo(String spojenaKomanda) {
    String regularniIzraz = "PO '([^']+)' ([A-Za-z0-9ČĆŠĐŽčćšđž]+) ([ND])";
    Pattern patternPo = Pattern.compile(regularniIzraz);
    Matcher matcherPo = patternPo.matcher(spojenaKomanda);

    if (matcherPo.matches()) {
      String osoba = matcherPo.group(1).trim();
      String oznakaPaketa = matcherPo.group(2);
      String status = matcherPo.group(3);
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.izmjeniStatusSlanjaObavijesti(osoba, oznakaPaketa, status);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PO NIJE ISPRAVNA!");
    }
  }

  private static void provjeraKomandeDv(Scanner scanner) {
    System.out.println(
        "Unesite novo vozilo u sljedećem formatu: registracija;opis;kapacitetTezine;kapacitetProstora;redoslijed;prosjecnaBrzina;podrucjaPoRangu;status");
    String ulaz = scanner.nextLine();
    String regularniIzraz =
        "[A-Za-z0-9ČĆŠĐŽ]+;[A-Za-z0-9ČĆŠĐŽčćšđž\\-\\: ]+;\\d+(?:[.,]\\d+)?;\\d+(?:[.,]\\d+)?;[0-9]+;\\d+(?:[.,]\\d+)?;(\\d+(,\\d+)*);(A|NI|NA)";
    Pattern patternDv = Pattern.compile(regularniIzraz);
    Matcher matcherDv = patternDv.matcher(ulaz);

    if (matcherDv.matches()) {
      String[] komandaDv = ulaz.split(";");
      String registracija = komandaDv[0].trim();
      String opis = komandaDv[1].trim();
      String kapacitetTezine = komandaDv[2].trim();
      String kapacitetProstora = komandaDv[3].trim();
      String redoslijed = komandaDv[4].trim();
      String prosjecnaBrzina = komandaDv[5].trim();
      String podrucjaPoRangu = komandaDv[6].trim();
      String status = komandaDv[7].trim();
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.dodajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora,
          redoslijed, prosjecnaBrzina, podrucjaPoRangu, status);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA DV NIJE ISPRAVNA!");
    }
  }

  private static void provjeraKomandeSv(String spojenaKomanda) {
    if (spojenaKomanda.equals("SV")) {
      LocalDateTime virtualnoVrijemeSv =
          VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.pregledajPodatkeVozila(virtualnoVrijemeSv);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA SV NIJE ISPRAVNA!");
    }
  }
}
