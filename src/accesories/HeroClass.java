package accesories;

public enum HeroClass {
    SOPHIE("src/Entity/hero/kunoichi/idlehero.png"),
    APOLLO("src/Entity/hero/apollo/apollo.png"),
    KUNOICHI("src/Entity/hero/Sophie/Sophie.png");
    private String urlClass;
    private HeroClass(String urlClass)
    {
        this.urlClass=urlClass;
    }
    public String getUrlClass()
    {
        return urlClass;
    }
}
