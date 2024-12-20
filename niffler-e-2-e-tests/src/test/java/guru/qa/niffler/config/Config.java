package guru.qa.niffler.config;

public interface Config {

    public static Config getInstance() {
        return LocalConfig.instance;
    }



    String frontUrl();

    String spendUrl();
}
