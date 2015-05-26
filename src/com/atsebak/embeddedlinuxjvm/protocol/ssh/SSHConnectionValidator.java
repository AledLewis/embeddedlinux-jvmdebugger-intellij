package com.atsebak.embeddedlinuxjvm.protocol.ssh;

import com.atsebak.embeddedlinuxjvm.localization.EmbeddedLinuxJVMBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import lombok.Builder;
import net.schmizz.sshj.SSHClient;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;

@Builder
public class SSHConnectionValidator {
    String ip;
    String username;
    String password;

    /**
     * Pings to see if it can contact hostname
     *
     * @return
     */
    public boolean canConnectToHostname(int timeout) {
        try {
            return InetAddress.getByName(ip).isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * See if user can connect to remote target
     *
     * @param client
     * @param project
     * @throws IOException
     */
    public void checkSSHConnection(SSHClient client, @NotNull Project project) throws IOException {
        try {
            client.connect(ip);
            client.authPassword(username, password);
            Messages.showInfoMessage(project, EmbeddedLinuxJVMBundle.getString("ssh.connection.success"), EmbeddedLinuxJVMBundle.getString("pi.connection.success.title"));
        } catch (Exception e) {
            Messages.showErrorDialog(project, EmbeddedLinuxJVMBundle.getString("pi.ssh.remote.error"),
                    EmbeddedLinuxJVMBundle.getString("pi.ssh.connection.error"));
        }
    }

}
