public class Application implements ISearchAlgorithm {
    @Override
    public boolean checkPackage(String stringOfContent) {
        return new RabinKarpSearchAlgorithm().search("exp!os:ve", stringOfContent, 101);
    }
}
