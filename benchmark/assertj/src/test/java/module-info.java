module com.github.cowwoc.requirements.benchmark.assertj
{
	requires jmh.core;
	requires org.testng;
	requires org.assertj.core;

	exports com.github.cowwoc.requirements.benchmark.assertj to org.testng;
	exports com.github.cowwoc.requirements.benchmark.assertj.jmh_generated to jmh.core;
}