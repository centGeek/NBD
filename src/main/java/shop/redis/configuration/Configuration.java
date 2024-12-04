package shop.redis.configuration;

import java.util.Properties;

public class Configuration {
    Properties configFile;
    public Configuration()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("config.properties"));
        } catch (Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        return this.configFile.getProperty(key);
    }
    public String getPropertyHardcoded(){
        return "http://localhost:6379";
    }
}