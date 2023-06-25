1. Download Ubuntu 20.04.3 LTS from https://ubuntu.com/download/desktop
2. VMWare Workstation configuration
    1. Create a new virtual machine.
    2. Typical configuration.
    3. Install from ISO
    4. Set "Full name, User name, Password, Confirm" fields to "builds"
    5. Store virtual disk as a single file
    6. Uncheck "Power on this virtual machine after creation".
    7. Finish
    8. Right click on the created VM, Settings
        1. Processors, Number of cores per processor = `<the number of cores your computer has, minus one>`
        2. Network adapter = Bridged, replicate physical network connection state.
        3. In the options tab, VMWare Tools, Check "Synchronize guest time with host" and "Update automatically"
        4. Okay
    9. Locate the VMX file on disk by hovering the mouse over the machine name
    10. Open the VMX file in a text editor.
    11. Add **cpuid.1.eax = "0000:0000:0000:0001:0000:0110:1010:0101"** to avoid a kernel panic ("Guest OS has disabled the CPU, Please power down or restart"). Source: http://askubuntu.com/a/841283/23678
    12. Add **bios.bootdelay = 5000** to introduce a delay every time the machine boots up.
    13. Save and close the file.
    14. Boot the virtual machine and let VMWare complete the installation.
3. Log into the system and run:

        sudo chmod u+w /etc/sudoers
        sudo tee -a /etc/sudoers <<EOF
        builds ALL=(root)NOPASSWD:/usr/bin/apt-get
        EOF

        sudo apt-get update
        sudo apt-get upgrade -y

        sudo apt-get install openssh-server git -y

        # https://wiki.archlinux.org/index.php/GnuPG#Unattended_passphrase
        mkdir -p ~/.gnupg
        sudo tee -a ~/.gnupg/gpg-agent.conf <<EOF
        allow-loopback-pinentry
        EOF

        # Add github to trusted hosts: http://stackoverflow.com/a/29380765/14731
        mkdir -p ~/.ssh
        ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts

4. Export GPG private key (used for signing releases): `gpg --export-secret-key -a "User Name" > private.key`
5. Copy key into the guest (e.g. using `scp`), and run: `gpg --import private.key`
6. Create and add a GitHub App to the Github project, if necessary: https://stackoverflow.com/a/70630952/14731
7. Add Jenkins credentials
    1. Add a "GitHub App" global credential with id "github-cowwoc". Set the owner to "cowwoc"
    2. Add a "SSH Username with private key" global credential id "jenkins".
8. Download JDK 11 from https://www.azul.com/downloads/?version=java-11-lts&os=linux&package=jdk
9. Install:

        # install the necessary dependencies
        sudo apt-get -q update
        sudo apt-get -yq install gnupg curl 

        # add Azul's public key
        sudo apt-key adv \
          --keyserver hkp://keyserver.ubuntu.com:80 \
          --recv-keys 0xB1998361219BD9C9

        # download and install the package that adds 
        # the Azul APT repository to the list of sources 
        curl -O https://cdn.azul.com/zulu/bin/zulu-repo_1.0.0-3_all.deb

        # install the package
        sudo apt-get install ./zulu-repo_1.0.0-3_all.deb

        # update the package sources
        sudo apt-get update
        sudo apt-get install -y zulu11-jdk

10. Add the following to any Maven project you wish to deploy/release to Maven Central:

        <parent>
            <groupId>org.sonatype.oss</groupId>
            <artifactId>oss-parent</artifactId>
            <version>9</version>
        </parent>
        [...]
        <pluginManagement>
            <plugin>
                <groupId>org.sonatype.plugins</groupId>
                <artifactId>nexus-staging-maven-plugin</artifactId>
                <version>1.6.13</version>
                <extensions>true</extensions>
                <configuration>
                    <serverId>maven-central-releases/serverId>
                    <nexusUrl>https://oss.sonatype.org/</nexusUrl>
                </configuration>
            </plugin>
        </pluginManagement>
        [...]
        <profiles>
            <profile>
                <id>deploy+gpg1</id>
                <activation>
                    <os>
                        <family>!mac</family>
                    </os>
                </activation>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-gpg-plugin</artifactId>
                            <version>1.6</version>
                            <executions>
                                <execution>
                                    <id>sign-artifacts</id>
                                        <phase>verify</phase>
                                    <goals>
                                        <goal>sign</goal>
                                    </goals>
                                    <configuration>
                                        <!-- See https://maven.apache.org/guides/mini/guide-encryption.html -->
                                        <passphraseServerId>gpg.passphrase</passphraseServerId>
                                    </configuration>
                                </execution>
                            </executions>
                        </plugin>
                    </plugins>
                </build>
            </profile>
        </profiles>

11. Follow the instructions found at https://maven.apache.org/guides/mini/guide-encryption.html to create ~/.m2/settings-security.xml and add server id "gpg.passphrase" to ~/.m2/settings.xml

        <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
            [...]
            <servers>
                [...]
                <server>
                    <id>gpg.passphrase</id>
                    <passphrase>{encryptedPasswordForSigning}</passphrase>
                </server>
                <server>
                    <id>sonatype-nexus-snapshots</id>
                    <username>sonatypeUsername</username>
                    <password>{encryptedPasswordForSonatype}</password>
                </server>
                <server>
                    <id>maven-central-releases</id>
                    <username>sonatypeUsername</username>
                    <password>{encryptedPasswordForSonatype}</password>
                </server>
            </servers>
        </settings>

12. Update vmware tools per https://github.com/vmware/open-vm-tools/issues/303#issuecomment-435916846
13. Assign the VM a static IP (e.g. configure the router to assign its mac address to a static DHCP address)
14. Mark this node temporarily offline in Jenkins to prevent it from creating new files.
15. Clean up any temporary files (e.g. ~/Downloads, ~/.jenkins, Trash can), reboot the OS once (to clear the temporary directory), then shut down and create a VM snapshot. This will shrink the snapshot size.
16. In Jenkins, Maven configuration → Global Tool Configuration → Git → Install automatically → shell command → label = "linux", command = "sudo apt-get install git -y", Tool home = "/usr/bin/git"
17. Create a new Node in Jenkins
    1. Type = Permanent Agent
    2. Remote root directory = /home/builds/.jenkins
    3. Labels = linux amd64
    4. Launch method = Launch slave agents on Unix machines via SSH
    5. Host = `<The node's static ip>`
    6. Tool Locations:
        1. JAVA_HOME=/usr/lib/jvm/zulu-11