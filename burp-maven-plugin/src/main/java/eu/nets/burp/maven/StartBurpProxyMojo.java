package eu.nets.burp.maven;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

/**
 * Maven plugin that allows you to run the Burp Proxy tool in headless mode.
 */
@Mojo(name = "start-proxy", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartBurpProxyMojo extends AbstractBurpMojo {

    /**
     * Burp proxy port.
     */
    @Parameter(defaultValue = "4646")
    private int proxyPort;

    /**
     * Burp proxy shutdown port.
     */
    @Parameter(defaultValue = "4444")
    private int shutdownPort;

    @Override
    protected String getBurpExtenderToRun() {
        return "headless-burp-proxy";
    }

    @Override
    protected boolean shouldWaitForProcessToComplete() {
        return false;
    }

    @Override
    protected List<String> createBurpExtensionCommandLineArguments() {
        List<String> command = Lists.newArrayList();

        command.add("--proxy-port");
        command.add(String.valueOf(proxyPort));

        command.add("--shutdown-port");
        command.add(String.valueOf(shutdownPort));

        return command;
    }

    @Override
    protected void execute(ProcessBuilder processBuilder) {
        getLog().info("Starting headless burp proxy...");
        getLog().info(StringUtils.join(processBuilder.command().iterator(), " "));

        executeAndRedirectOutput(processBuilder);
    }
}
