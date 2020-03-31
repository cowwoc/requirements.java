1. You will need to repeat these instructions twice: once for 32-bit, and once for 64-bit.
2. Download the ISOs from http://releases.ubuntu.com/16.04/ubuntu-16.04.2-desktop-i386.iso.torrent and http://releases.ubuntu.com/16.04/ubuntu-16.04.2-desktop-amd64.iso.torrent
3. VMWare Workstation configuration
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
4. Log into the system and run:

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

5. Export GPG private key (used for signing releases): `gpg --export-secret-key -a "User Name" > private.key`
6. Copy key into the guest (e.g. using `scp`), and run: `gpg --import private.key`
7. Delete the private key `rm private.key`
8. Add a "Username with password" global credential in Jenkins with id "github". You can generate a Jenkins-specific password in Github using the "Personal access tokens" feature.
9. Add the public key to Github, if you haven't already: https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/
10. Download JDK 8, 32-bit and 64-bit tar.gz packages from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
11. Copy into guest and run:

		# On 32-bit platform
		tar -xvf jdk-8u131-linux-i586.tar.gz

		# On 64-bit platform
		tar -xvf jdk-8u131-linux-x64.tar.gz

		# Source: http://askubuntu.com/questions/56104/how-can-i-install-sun-oracles-proprietary-java-jdk-6-7-8-or-jre/55960#55960
		sudo mkdir -p /usr/lib/jvm
		sudo mv ~/jdk1.8.0_131/ /usr/lib/jvm/jdk1.8.0_131

		# Use a priority of 2000, otherwise OpenJDK installation will replace our symbolic links.
		sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.8.0_131/bin/java" 2000
		sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.8.0_131/bin/javac" 2000
		sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.8.0_131/bin/javaws" 2000

		sudo chmod a+x /usr/bin/java
		sudo chmod a+x /usr/bin/javac
		sudo chmod a+x /usr/bin/javaws
		sudo chown -R root:root /usr/lib/jvm/jdk1.8.0_131

12. Add the following to any Maven project you wish to deploy/release to Maven Central:

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
				<version>1.6.7</version>
				<extensions>true</extensions>
				<configuration>
					<serverId>sonatype-nexus-staging</serverId>
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

13. Follow the instructions found at https://maven.apache.org/guides/mini/guide-encryption.html to create ~/.m2/settings-security.xml and add server id "gpg.passphrase" to ~/.m2/settings.xml

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
					<id>sonatype-nexus-staging</id>
					<username>sonatypeUsername</username>
					<password>{encryptedPasswordForSonatype}</password>
				</server>
			</servers>
		</settings>

14. Assign the VM a static IP (e.g. configure the router to assign its mac address to a static DHCP address)
15. Mark this node temporarily offline in Jenkins to prevent it from creating new files.
16. Clean up any temporary files (e.g. ~/Downloads, ~/.jenkins, Trash can), reboot the OS once (to clear the temporary directory), then shut down and create a VM snapshot. This will shrink the snapshot size.
17. In Jenkins, Maven configuration → Global Tool Configuration → Git → Install automatically → shell command → label = "linux", command = "sudo apt-get install git -y", Tool home = "/usr/bin/git"
18. Create a new Node in Jenkins
Type = Permanent Agent
	1. Remote root directory = /home/builds/.jenkins
	2. On 32-bit VM
		1. Labels = linux i386
	3. On 64-bit VM
		1. Labels = linux amd64
	4. Launch method = Launch slave agents on Unix machines via SSH
	5. Host = `<The node's static ip>`
	6. Tool Locations:
		1. JAVA_HOME=/usr/lib/jvm/jdk1.8.0_131