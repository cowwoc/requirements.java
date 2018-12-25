module org.bitbucket.cowwoc.requirements.natives {
	requires org.bitbucket.cowwoc.pouch;

	exports org.bitbucket.cowwoc.requirements.natives.internal.util to org.bitbucket.cowwoc.requirements.java,
		org.bitbucket.cowwoc.requirements.maven.plugin;
}