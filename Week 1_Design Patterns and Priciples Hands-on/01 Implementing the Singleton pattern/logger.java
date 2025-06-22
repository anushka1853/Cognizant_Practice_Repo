public class Main {

    // Logger Singleton class inside the Main class
    static class Logger {
        // Step 1: Private static instance of Logger
        private static Logger instance;

        // Step 2: Private constructor
        private Logger() {
            System.out.println("Logger instance created");
        }

        // Step 3: Public static method to get the instance
        public static Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }

        // A logging method
        public void log(String message) {
            System.out.println("Log: " + message);
        }
    }

    // Main method to test the Singleton
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("This is the first log message.");
        logger2.log("This is the second log message.");

        if (logger1 == logger2) {
            System.out.println("Both logger1 and logger2 refer to the same instance.");
        } else {
            System.out.println("Different instances were created.");
        }
    }
}
