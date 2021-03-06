1. Download and run Windows 10 "Media Creation Tool" from https://www.microsoft.com/en-ca/software-download/windows10
2. Create installation media for another PC
	1. Language = English
	2. Edition = Windows 10
	3. Architecture = 64-bit
3. Create an ISO file.
4. VMWare Workstation configuration
	1. Create a new virtual machine.
	2. Typical configuration.
	3. Install from ISO
	4. Store virtual disk as a single file
	5. Uncheck "Power on this virtual machine after creation".
	6. Finish
	7. Right click on the created VM, Settings
		1. Processors, Number of cores per processor = `<the number of cores your computer has, minus one>`
		2. Enable Virtualize Intel VT-x/EPT or AMD-V/RVI
		3. Enable Virtualize CPU performance counters
		4. Network adapter = Bridged, replicate physical network connection state.
		5. In the options tab, VMWare Tools, Check "Synchronize guest time with host" and "Update automatically"
		6. Okay
	8. Locate the VMX file on disk by hovering the mouse over the machine name
	9. Open the VMX file in a text editor.
	10. Add **bios.bootdelay = 5000** to introduce a delay every time the machine boots up.
	11. Save and close the file.
	12. Boot the virtual machine.
	13. "I don't have a key"
	14. "Windows 10 Pro"
	15. "Custom" installation type
	16. "Next"
	17. "Customize"
	18. Disable all features except for "Get updates from and send updates to other PCs on the Internet to speed up app and Windows update downloads."
	19. "Next"
	20. "I own it"
	21. "Skip this step"
	22. Set "Who is going to use this PC?" to "builds" and omit all password information.
	23. "Not now" for Cortana
	24. Wait for the installation to complete and click "I finished installing".
	25. Install VMWare Tools and reboot.
5. Place user configuration (e.g. Maven, Git) relative to C:\Users\Builds directory
6. Install GPG from https://www.gpg4win.org/download.html
7. Install JDK 1.8.0_121, both 32-bit and 64-bit versions, from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
8. Save the following text into jenkins.reg and run it:

		Windows Registry Editor Version 5.00

		; Disable Windows Defender: http://superuser.com/a/1007521/57662
		[HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Microsoft\Windows Defender]
		"DisableAntiSpyware"=dword:00000001
		"DisableRoutinelyTakingAction"=dword:00000001

		; Allow users without a password to run services: http://stackoverflow.com/a/1048691/14731
		[HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa]
		"LimitBlankPasswordUse"=dword:00000000

9. Copy GPG private key (used for signing releases) into guest, and run: `gpg --import private.key`
10. Download and install OpenSSH (client) from http://www.mls-software.com/opensshd.html
11. Add SSH known hosts:

		REM *** Add github to trusted hosts: http://stackoverflow.com/a/29380765/14731
		mkdir %USERPROFILE%\.ssh
		ssh-keyscan -t rsa github.com >> %USERPROFILE%\.ssh\known_hosts

12. Add a "Username with password" global credential in Jenkins with id "github". You can generate a Jenkins-specific password in Github using the "Personal access tokens" feature.
13. Install git from https://git-scm.com/downloads into the default location
14. Open an Administrator Terminal (WindowsKey + X, A) and disable sleep mode by running:

		REM *** http://superuser.com/a/90418/57662
		powercfg -change -standby-timeout-ac 0

		REM *** http://superuser.com/a/347928/57662
		powercfg -h off

15. Download and install Visual Studio Community from https://www.visualstudio.com/downloads/. Make sure to choose Custom install since the default is unlikely to be a good fit.
16. Add the following to any Maven project you wish to deploy/release to Maven Central:

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
						<!-- https://maven.apache.org/guides/mini/guide-encryption.html -->
						<passphraseServerId>gpg.passphrase</passphraseServerId>
					    </configuration>
					</execution>
				    </executions>
				</plugin>
			    </plugins>
			</build>
		    </profile>
		</profiles>

17. Windows has two home directories for the Local System account: C:\Windows\System\config\systemprofile and C:\Windows\SysWOW64\config\systemprofile. For each one of them, follow the the instructions found at https://maven.apache.org/guides/mini/guide-encryption.html to create `~/.m2/settings-security.xml` and add server id "gpg.passphrase" to `~/.m2/settings.xml`

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

18. Windows Updates
	1. Advanced options → Give me updates for other Microsoft products when I update Windows
	2. Check For Updates (and install any you find)
19. Assign the VM a static IP (e.g. configure the router to assign its mac address to a static DHCP address)
20. Create a new Node in Jenkins
	1. Type = Permanent Agent
	2. Remote root directory = C:\jenkins
	3. Leave "Labels" blank for now to prevent any jobs from running on it
	4. Launch method = Launch Agent via Java Web Start
	5. Host = `<The node's static ip>`
	6. Tool Locations:
		1. JAVA32_HOME=C:\Program Files (x86)\Java\jdk1.8.0_121
		2. JAVA64_HOME=C:\Program Files\Java\jdk1.8.0_121
		3. GIT = C:\Program Files\Git\cmd\git.exe
21. Install Jenkins slave
	1. Open the Node page (e.g. http://master.com/jenkins/computer/windows-slave/) in the guest browser.
	2. Click "Launch" and save the JNLP file to disk.
	3. Open an Administrator Terminal (WindowsKey + X, A)
	4. "cd C:\Users\Builds\Downloads"
	5. "slave-agent.jnlp"
	6. Wait for the dialog to read "Connected". A "File" pulldown menu will appear.
		1. See https://wiki.jenkins-ci.org/display/JENKINS/Installing+Jenkins+as+a+Windows+service#InstallingJenkinsasaWindowsservice-InstallSlaveasaWindowsservice%28require.NET2.0framework%29 if you run into any problems.
	7. Run "File" → "Install as a Service"
	8. If the dialog box closes with an error, the installation was successful.
	9, Open the properties dialog for the Jenkins service.
	10. In the Log On tab, configure the service to run under user "builds" with password "builds".
	11. Close the services dialog
22. Mark this node temporarily offline in Jenkins to prevent it from creating new files.
23. Clean up any temporary files using Disk Cleanup application, delete any sub-directories under C:\jenkins, then shut down and create a VM snapshot. This will shrink the snapshot size.
24. Go back into the Node configuration in Jenkins and set:
	1. Labels = windows i386 amd64