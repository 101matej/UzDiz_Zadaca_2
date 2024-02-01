package org.foi.uzdiz.observer;

public interface SubjectObserver {
  void dodajObserver(Observer observer);

  void ukloniObserver(Observer observer);

  void obavijestiObserverZaprimljen();

  void obavijestiObserverPreuzet();
}
