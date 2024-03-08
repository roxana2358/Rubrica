package model;

import view.Observer;

/**
 * Interfaccia Observable (dati) per notificare l'unico Observer (interfaccia grafica).
 */
public interface Observable {
    void setObserver(Observer observer);
    void notifyObserver();
}