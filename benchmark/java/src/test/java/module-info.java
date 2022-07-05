module com.github.cowwoc.requirements.benchmark.java
{
	requires jmh.core;
	requires org.testng;
	requires com.github.cowwoc.requirements.java;

	exports com.github.cowwoc.requirements.benchmark.java to org.testng;
	exports com.github.cowwoc.requirements.benchmark.java.jmh_generated to jmh.core;
}