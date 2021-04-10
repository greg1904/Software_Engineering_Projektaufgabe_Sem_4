public class Application {
    public boolean checkPackage(String stringOfContent) {
        return new RabinKarpSearchAlgorithm().search("exp!os:ve", stringOfContent, 101);
    }
}
