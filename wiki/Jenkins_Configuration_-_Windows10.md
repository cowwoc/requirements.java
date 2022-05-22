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
7. Copy GPG private key (used for signing releases) into guest, and run: `gpg --import private.key`
8. Add SSH known hosts:

		REM *** Add github to trusted hosts: http://stackoverflow.com/a/29380765/14731
		mkdir %USERPROFILE%\.ssh
		ssh-keyscan -t rsa github.com >> %USERPROFILE%\.ssh\known_hosts
		
9. Install SSH server
	1. Start -> Optional features -> Add a feature -> "OpenSSH Server"
	2. Services -> OpenSSH SSH Server -> Automatic
	3. Create c:\users\builds\.ssh\authorized_keys and paste the contents of id_rsa.pub (public key) into it
	4. Grant local user full rights over C:\ProgramData\ssh\sshd_config
	5. Comment out the following two lines in C:\ProgramData\ssh\sshd_config
	```
	# Match Group administrators
	#       AuthorizedKeysFile __PROGRAMDATA__/ssh/administrators_authorized_keys
	```

10. Add the public key to Github, if you haven't already: https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/
11. Create and add a GitHub App to the Github project, if necessary: https://stackoverflow.com/a/70630952/14731
12. Add Jenkins credentials
	1. Add a "GitHub App" global credential with id "github-cowwoc". Set the owner to "cowwoc".
	2. Add a "SSH Username with private key" global credential id "jenkins".
13. Install git from https://git-scm.com/downloads into the default location
14. Install JDK11 from https://www.azul.com/downloads/?version=java-11-lts&os=windows&package=jdk#download-openjdk
15. Save the following text into jenkins.reg and run it:

		Windows Registry Editor Version 5.00

		; Disable Windows Defender: http://superuser.com/a/1007521/57662
		[HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Microsoft\Windows Defender]
		"DisableAntiSpyware"=dword:00000001
		"DisableRoutinelyTakingAction"=dword:00000001

		; Allow users without a password to run services: http://stackoverflow.com/a/1048691/14731
		[HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa]
		"LimitBlankPasswordUse"=dword:00000000

16. Open an Administrator Terminal (WindowsKey + X, A) and disable sleep mode by running:

		REM *** http://superuser.com/a/90418/57662
		powercfg -change -standby-timeout-ac 0

		REM *** http://superuser.com/a/347928/57662
		powercfg -h off

17. Download and install Visual Studio Community from https://www.visualstudio.com/downloads/. Make sure to choose Custom install since the default is unlikely to be a good fit.
18. Add the following to any Maven project you wish to deploy/release to Maven Central:

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

19. Windows has two home directories for the Local System account: C:\Windows\System32\config\systemprofile and C:\Windows\SysWOW64\config\systemprofile. For each one of them, follow the the instructions found at https://maven.apache.org/guides/mini/guide-encryption.html to create `~/.m2/settings-security.xml` and add server id "gpg.passphrase" to `~/.m2/settings.xml`

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

20. Windows Updates
	1. Advanced options → Give me updates for other Microsoft products when I update Windows
	2. Check For Updates (and install any you find)
21. Assign the VM a static IP (e.g. configure the router to assign its mac address to a static DHCP address)
22. Create a new Node in Jenkins
	1. Type = Permanent Agent
	2. Remote root directory = C:\jenkins
	3. Labels = windows amd64
	4. Launch method = Launch agents via SSH
	5. Host = `<The node's static ip>`
	6. Credentials = "jenkins" (the SSH credential added above)
	7. Host key verification strategy = Manually trusted key verification strategy
	8. Tool Locations:
		1. JAVA11_HOME=C:\Program Files\Java\jdk11.0.13
		2. JAVA_HOME=C:\Program Files\Java\jdk11.0.13
		3. GIT = C:\Program Files\Git\cmd\git.exe
23. Mark this node temporarily offline in Jenkins to prevent it from creating new files.
24. Clean up any temporary files using Disk Cleanup application, delete any sub-directories under C:\jenkins, then shut down and create a VM snapshot. This will shrink the snapshot size.
