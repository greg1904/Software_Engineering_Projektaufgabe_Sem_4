public class Application implements ISearchAlgorithm{
    @Override
    public boolean checkPackage(String stringOfContents) {
        return new BoyerMooreSearchAlgorithm().search("exp!os:ve".toCharArray(), stringOfContents.toCharArray());
    }
}
