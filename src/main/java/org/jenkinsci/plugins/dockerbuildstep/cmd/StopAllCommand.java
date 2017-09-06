package org.jenkinsci.plugins.dockerbuildstep.cmd;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;

import java.util.List;

import org.jenkinsci.plugins.dockerbuildstep.log.ConsoleLogger;
import org.kohsuke.stapler.DataBoundConstructor;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.Container;

/**
 * This command stop all Docker containers.
 * 
 * @author vjuranek
 * 
 */
public class StopAllCommand extends DockerCommand {

    @DataBoundConstructor
    public StopAllCommand() {
    }

    @Override
    public void execute(Launcher launcher, @SuppressWarnings("rawtypes") AbstractBuild build, ConsoleLogger console)
            throws DockerException {
        DockerClient client = getClient(build, null);
        List<Container> containers = client.listContainersCmd().exec();
        for (Container c : containers) {
            client.stopContainerCmd(c.getId()).exec();
            console.logInfo("stopped container id " + c.getId());
        }
    }

    @Extension
    public static class StopAllCommandDescriptor extends DockerCommandDescriptor {
        @Override
        public String getDisplayName() {
            return "Stop all containers";
        }
    }

}
