public class Application {
    public boolean checkPackage(String stringOfContents) {
        return new BoyerMooreSearchAlgorithm().search("exp!os:ve".toCharArray(), stringOfContents.toCharArray());
    }
}
