1. Following different guides depending on your version of VMWare:
	1. If you are using VMWare Workstation 15.1 or lower see https://www.insanelymac.com/forum/topic/340876-macos-unlocker-3-vmware-workstation-155-amd-ryzen-5-3xxx/
	2. If you are using VMWare Workstation 15.5 or higher see https://dortania.github.io/OpenCore-Install-Guide/ and https://www.youtube.com/watch?v=jvb-BIMV1Mw
		1. Make sure to remove all booting entries prior to installation from USB because otherwise the wrong entry will get selected on bootup and make it seem as if the installation had crashed when it had not.
		2. Go into VMware BIOS.
		3. Enter setup
		4. Configure boot options
		5. Add boot option
		6. and edit boot order.
		7. Add new boot option.
		8. Choose EFI, OC, Bootstrap, Bootstrap.efi and finally confirm.
		9. Remove all other entries.
2. Create a bootable USB or ISO
	1. Bootable USB: https://www.insanelymac.com/forum/topic/329828-making-a-bootable-high-sierra-usb-installer-entirely-from-scratch-in-windows-or-linux-mint-without-access-to-mac-or-app-store-installerapp/
	2. ISO file: Download MacOS Catalina from App Store and run:

		```
		hdiutil attach /Applications/Install\ macOS\ Catalina.app/Contents/SharedSupport/InstallESD.dmg -noverify -nobrowse -mountpoint /Volumes/install_app
		hdiutil create -o /tmp/Catalina.cdr -size 7316m -layout SPUD -fs HFS+J
		hdiutil attach /tmp/Catalina.cdr.dmg -noverify -nobrowse -mountpoint /Volumes/install_build
		asr restore -source /Volumes/install_app/BaseSystem.dmg -target /Volumes/install_build -noprompt -noverify -erase
		rm /Volumes/OS\ X\ Base\ System/System/Installation/Packages
		cp -rp /Volumes/install_app/Packages /Volumes/OS\ X\ Base\ System/System/Installation/
		cp -rp /Volumes/install_app/BaseSystem.chunklist /Volumes/OS\ X\ Base\ System/BaseSystem.chunklist
		cp -rp /Volumes/install_app/BaseSystem.dmg /Volumes/OS\ X\ Base\ System/BaseSystem.dmg
		hdiutil detach /Volumes/install_app
		hdiutil detach /Volumes/OS\ X\ Base\ System/
		hdiutil convert /tmp/Catalina.cdr.dmg -format UDTO -o /tmp/Catalina.iso
		mv /tmp/Catalina.iso.cdr ~/Desktop/Catalina.iso
		```

3. If you're using an ISO file, copy Catalina.iso to a location that is accessible by VMWare
4. VMWare Workstation configuration
	1. Create a new virtual machine.
	2. Typical configuration.
	3. Install from ISO
	4. Store virtual disk as a single file
	5. Uncheck "Power on this virtual machine after creation".
	6. Finish
	7. Right click on the created VM, Settings
		1. Processors = 1
		2. Number of cores per processor = `<the number of cores your computer has>`
			1. If using AMD, value must match `<core count>` referenced by https://dortania.github.io/OpenCore-Install-Guide/AMD/zen.html#kernel
		4. Network adapter = Bridged, replicate physical network connection state.
		5. In the options tab, VMWare Tools, Check "Synchronize guest time with host" and "Update automatically"
		6. Okay
	8. Locate the VMX file on disk by hovering the mouse over the machine name
	9. Open the VMX file in a text editor.
	10. Add **smc.version = "0"** to avoid a core dump. Source: http://www.insanelymac.com/forum/files/file/339-unlocker/"0000:0000:0000:0001:0000:0110:1010:0101"
	11. Add **isolation.tools.bug328986.disable = "TRUE"** to suppress a warning message about using multiple cores. Source: http://daveparsons.net/blog/2013/12/30/suppress-vmware-multiple-vcpu-message/
	12. Add **bios.bootdelay = 5000** to introduce a delay every time the machine boots up.
	13. Save and close the file.
5. The following is based on http://www.insanelymac.com/forum/topic/290949-how-to-install-os-x-10x-snow-leopard-to-el-capitan-in-vmware-workstation-1011-workstation-proplayer-12-player-67-esxi-56/
6. Boot the virtual machine
7. Open Disk Utility
8. Select "VMWare Virtual SATA Hard Drive Media"
9. Select "Erase"
	1. Name = "MacOS"
	2. Format = "APFS"
	3. Select "Erase"
10. Quit Disk Utility
11. Select "Reinstall macOS"
12. Select "MacOS"
13. Click "Continue"
14. You can safely disable most features; however, you will need to provide an Apple ID in order to use the App Store.
15. Set "Full name, Account name, Password, Verify" fields to "builds"
16. Click "I finished installing".
17. Install VMWare Tools and reboot.
18. Log into the system.
19. Run:

		# Add "builds" to _developer group: http://stackoverflow.com/a/10594414/14731
		sudo dscl . append /Groups/_developer GroupMembership builds

		# Disable security checks for members of _developer group: http://stackoverflow.com/a/17286593/14731
		sudo /usr/sbin/DevToolsSecurity --enable

		# Install Homebrew: http://stackoverflow.com/a/25535532/14731
		ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" </dev/null

		# Install gpg
		brew install gnupg2

		# Disable screensaver
		defaults -currentHost write com.apple.screensaver idleTime 0

		# Disable sleep mode
		sudo systemsetup -setcomputersleep Never

		# Disable hibernation
		sudo pmset -a hibernatemode 0

		# Enable ssh server: https://apple.stackexchange.com/a/302606/21181
		sudo launchctl load -w /System/Library/LaunchDaemons/ssh.plist

		# Disable password authentication for ssh
		sudo tee -a /etc/ssh/sshd_config <<EOF
		PasswordAuthentication no
		EOF
		
		# Enable private-key authentication for ssh: https://askubuntu.com/a/306832/23678
		chmod 700 ~/.ssh
		sudo -tee -a ~/.ssh/authorized_keys
		<paste your RSA public key here>
		EOF
		chmod 600 ~/.ssh/authorized_keys
		chown $USER:$USER ~/.ssh -R

		# Install all software updates
		softwareupdate --install --all

		# Enable auto-login: https://github.com/xfreebird/kcpassword
		brew tap xfreebird/utils
		brew install kcpassword
		enable_autologin "builds" "builds"

		# Add github to trusted hosts: http://stackoverflow.com/a/29380765/14731
		mkdir -p ~/.ssh
		ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts

19. Copy GPG private key (used for signing releases) into guest, and run:
`gpg --import private.key`
20. Add a "Username with password" global credential in Jenkins with id "github". You can generate a Jenkins-specific password in Github using the "Personal access tokens" feature.
21. Add the public key to Github, if you haven't already: https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/
22. Install JDK8 update 121 from http://www.oracle.com/technetwork/java/javase/downloads/index.html
23. Add the following to any Maven project you wish to deploy/release to Maven Central:

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
				<id>deploy+gpg2</id>
				<activation>
				<os>
					<family>mac</family>
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
						<!-- https://maven.apache.org/guides/mini/guide-encryption.html -->
						<passphraseServerId>gpg.passphrase</passphraseServerId>
						<!-- See https://myshittycode.com/2017/08/07/maven-gpg-plugin-prevent-signing-prompt-or-gpg-signing-failed-no-such-file-or-directory-error/ -->
						<gpgArguments>
							<arg>--pinentry-mode</arg>
							<arg>loopback</arg>
						</gpgArguments>
					    </configuration>
					</execution>
				    </executions>
				</plugin>
			    </plugins>
			</build>
		    </profile>
		</profiles>

24. Follow the instructions found at https://maven.apache.org/guides/mini/guide-encryption.html to create ~/.m2/settings-security.xml and add server id "gpg.passphrase" to ~/.m2/settings.xml

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

25. Assign the VM a static IP (e.g. configure the router to assign its mac address to a static DHCP address)
26. Mark this node temporarily offline in Jenkins to prevent it from creating new files.
27. In VMWare settings, CDRom, uncheck "Connect at power on"
28. Clean up any temporary files (e.g. ~/Downloads, ~/.jenkins, Trash), reboot the OS once (to clear temporary files), then shut down and create a VM snapshot. This will shrink the snapshot size.
29. In Jenkins, Maven configuration → Global Tool Configuration → Git → Install automatically → shell command
	1. label = "mac"
	2. command:

			if ! /usr/local/bin/brew ls --versions git > /dev/null; then
				/usr/local/bin/brew install git
			fi

	3. Tool home = "/usr/bin/git"

30. Create a new Node in Jenkins
	1. Node should have the following Tool Locations:
		1. JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home. If this changes, you can invoke "/usr/libexec/java_home" to get the current value.
	2. Wait for user to login before allowing Jenkins to execute any jobs
		1. Install the [Slave Setup plugin](https://wiki.jenkins-ci.org/display/JENKINS/Slave+Setup+Plugin)
		2. Under "Configure System" subsection "Slave Setups" add a new entry
		3. Set "setup files directory" to "C:\Program Files (x86)\Jenkins\scripts\osx\scripts" (or whatever directory corresponds to ${JENKINS_HOME}/scripts/osx for you)
		4. Set "setup script after copy" to: 

				chmod u+x prepare-slave.sh
				./prepare-slave.sh

		5. Set "Label Expression" to "mac"
		6. Create prepare-slave.sh under ${JENKINS_HOME}/scripts/osx:

				#!/bin/bash
				/usr/bin/security unlock-keychain -p builds

				# Wait for the user to log in locally before allowing jobs to run, otherwise iOS simulator will not launch
				TIME_ELAPSED=0
				while true; do
					pgrep -u builds -f -l -q "/System/Library/CoreServices/SystemUIServer\.app/Contents/MacOS/SystemUIServer"
					[ $? -eq 0 ] && break
					((TIME_ELAPSED++))
					echo "Waiting for \"builds\" to log in locally [$TIME_ELAPSED / 60]..."
					if [ $TIME_ELAPSED -ge 60 ]; then
						exit 1
					fi
					sleep 1
				done
				echo
