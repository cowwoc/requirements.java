module com.github.cowwoc.requirements.benchmark.guava
{
	requires jmh.core;
	requires com.google.common;
	requires org.testng;
	requires com.github.cowwoc.requirements.guava;

	exports com.github.cowwoc.requirements.benchmark.guava to org.testng;
	exports com.github.cowwoc.requirements.benchmark.guava.jmh_generated to jmh.core;
}