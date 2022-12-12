package managers;

import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class AllDriverManager {

    private WebDriver webDriver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;

    public AllDriverManager() {
       driverType = FileReaderManager.getInstance().getConfigFileReader().getBrowser();
       environmentType = FileReaderManager.getInstance().getConfigFileReader().getEnvironment();
    }

    private WebDriver createLocalDriver(){
        switch (driverType){
            case CHROME:
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
            case EDGE:
                webDriver = new EdgeDriver();
                break;
            case SAFARI:
                webDriver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Browser name key value in configuration file is not matched: " + driverType);
        }
        webDriver.manage().window().maximize();
        long time = FileReaderManager.getInstance().getConfigFileReader().getTime();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(time));
        return webDriver;
    }

    WebDriver createRemoteDriver(){
        throw new RuntimeException("Remote we driver is not yet implemented");
    }

    private WebDriver createDriver(){
        switch (environmentType){
            case LOCAL:
                webDriver = createLocalDriver();
                break;
            case REMOTE:
                webDriver = createRemoteDriver();
                break;
            default:
                throw new RuntimeException("Environment name key value in configuration file is not matched: " + environmentType);
        }
        return webDriver;
    }

    public WebDriver getDriver(){
        if (webDriver == null) {
            webDriver = createDriver();
        }
        return webDriver;
    }

    public void closeDriver(){
        webDriver.quit();
        webDriver = null;
    }
}