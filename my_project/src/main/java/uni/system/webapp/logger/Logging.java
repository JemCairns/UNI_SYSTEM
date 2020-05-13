package uni.system.webapp.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
public class Logging {

    public static Logging logging = new Logging();
    public static Logger getInstance(){
        return logger;
    }

    private static Logger logger;

    private Logging(){
        FileHandler handler = null;
        try {
            handler = new FileHandler("./default.log", true);
        } catch (IOException e) {
            System.out.println("handler not added");
            e.printStackTrace();
        }
        logger = Logger.getLogger("com.javacodegeeks.snippets.core");
        logger.addHandler(handler);
    }



}
