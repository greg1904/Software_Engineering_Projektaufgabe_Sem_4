package team16.configuration;

import team16.storage.packet.Package;

public enum SearchAlgorithm implements ISearchAlgorithm {
    BOYERMOORE(new BoyerMooreSearchAlgorithm()), RABINKARP(new RabinKarpSearchAlgorithm());

    private final ISearchAlgorithm searchAlgorithm;

    SearchAlgorithm(ISearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    @Override
    public boolean checkPackage(Package packet) {
        return searchAlgorithm.checkPackage(packet);
    }
}
