package br.com.six2six.fixturefactory;

public class JavaVersion {

	public static final JavaVersion JAVA_8 = new JavaVersion("1.8");
	public static final JavaVersion JAVA_7 = new JavaVersion("1.7");
	public static final JavaVersion JAVA_6 = new JavaVersion("1.6");
	
	private int major;
	private int minor;

	public JavaVersion(String version) {
	    String[] fields = version.split("\\.");
	    this.major = Integer.parseInt(fields[0]);
	    this.minor = Integer.parseInt(fields[1]);
	}
	
	public boolean gte(JavaVersion version) {
	    return this.major >= version.major && this.minor >= version.minor;
	}
	
	public static JavaVersion current(){
		return new JavaVersion(System.getProperty("java.version"));
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}
	
}