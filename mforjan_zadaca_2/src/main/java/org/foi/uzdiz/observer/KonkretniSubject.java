package org.foi.uzdiz.observer;

import java.util.ArrayList;
import java.util.List;

public class KonkretniSubject implements SubjectObserver {
  private List<Observer> listaObservera = new ArrayList<>();

  @Override
  public void dodajObserver(Observer observer) {
    listaObservera.add(observer);
  }

  @Override
  public void ukloniObserver(Observer observer) {
    listaObservera.remove(observer);
  }

  @Override
  public void obavijestiObserverPreuzet() {
    for (Observer observer : listaObservera) {
      observer.azurirajPreuzet();
    }
  }

  @Override
  public void obavijestiObserverZaprimljen() {
    for (Observer observer : listaObservera) {
      observer.azurirajZaprimljen();
    }
  }
}
