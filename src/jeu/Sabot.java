package jeu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import cartes.Carte;

public class Sabot implements Iterable<Carte> {
    private Carte[] cartes;
    private int nbCartes;
    private int nbOperations = 0;

    public Sabot(Carte[] cartes) {
        this.cartes = cartes;
        this.nbCartes = cartes.length;
    }

    public boolean estVide() {
        return nbCartes == 0;
    }

    public void ajouterCarte(Carte carte) {
        if (nbCartes >= cartes.length) {
            throw new IllegalStateException("Capacité du sabot dépassée");
        }
        cartes[nbCartes] = carte;
        nbCartes++;
        nbOperations++;
    }

    public Carte piocher() {
        Iterator<Carte> iterateur = iterator();
        Carte carte = iterateur.next();
        iterateur.remove();
        return carte;
    }

    @Override
    public Iterator<Carte> iterator() {
        return new Iterateur();
    }

    private class Iterateur implements Iterator<Carte> {
        private int indiceIterateur = 0;
        private int nbOperationsReference = nbOperations;
        private boolean nextEffectue = false;

        @Override
        public boolean hasNext() {
            return indiceIterateur < nbCartes;
        }

        @Override
        public Carte next() {
            verifierConcurrence();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Carte carte = cartes[indiceIterateur];
            indiceIterateur++;
            nextEffectue = true;
            return carte;
        }

        @Override
        public void remove() {
            verifierConcurrence();
            if (!nextEffectue) {
                throw new IllegalStateException();
            }
            for (int i = indiceIterateur - 1; i < nbCartes - 1; i++) {
                cartes[i] = cartes[i + 1];
            }
            nbCartes--;
            indiceIterateur--;
            nextEffectue = false;
            nbOperations++;
            nbOperationsReference++;
        }

        private void verifierConcurrence() {
            if (nbOperations != nbOperationsReference) {
                throw new ConcurrentModificationException();
            }
        }
    }
}