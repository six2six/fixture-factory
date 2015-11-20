package br.com.six2six.fixturefactory.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassLoaderUtils {

	public static Set<Class<?>> getClassesForPackage(String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		JarInputStream jarInputStream = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
			while (resources.hasMoreElements()) {
				URL res = resources.nextElement();

				if (res.getProtocol().matches("(jar|zip)")) {
					res = new URL((res.getProtocol().equals("zip") || !res.getFile().contains("file:")  ? 
							"file:".concat(res.getFile().startsWith("/") ? "": "/") : "").concat(res.getFile().split("!")[0]));
				}
				if (res.getFile().contains(".jar")) {
					jarInputStream = new JarInputStream(res.openStream());
					JarEntry entry = jarInputStream.getNextJarEntry();


					while (entry != null) {
						if (entry.getName().startsWith(packageName.replace('.', '/')) && entry.getName().endsWith(".class") && !entry.getName().contains("$")) {
							classes.add(Class.forName(entry.getName().replace(".class","").replace("/",".")));
						}

						entry = jarInputStream.getNextJarEntry();
					}
				} else {
					classes.addAll(scanClassesFromDirectory(new File(URLDecoder.decode(res.getPath(), "UTF-8")), packageName));
				}   
			}
		} catch (Exception x) {
			throw new IllegalArgumentException("invalid package");
		} finally {
			if(jarInputStream != null){
				try {
					jarInputStream.close();
				} catch (IOException e) {
					throw new IllegalStateException(e.getMessage());
				}
			}
		}

		return classes;
	}

	private static Collection<Class<?>> scanClassesFromDirectory(File directory, String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		if (directory.exists() && directory.isDirectory()) {
			String[] files = directory.list();
			for (String fileItem : files) {
				if (fileItem.endsWith(".class")) {
					try {
						classes.add(Class.forName(packageName.concat(".").concat(fileItem.replace(".class",""))));
					} catch (Exception e) {
						throw new IllegalArgumentException("Invalid package " + packageName);
					}
				} else if (!fileItem.matches(".+\\..+")) {
					File dir = new File(directory,fileItem);
					if (dir.isDirectory()) {
						classes.addAll(scanClassesFromDirectory(dir, packageName.concat(".").concat(fileItem)));
					}
				}
			}
		}
		return classes;
	}
	
	public static boolean isPresent(String className) {
		try {
			Class.forName(className, true, Thread.currentThread().getContextClassLoader());
			return true;
		} catch (ClassNotFoundException ex) {
			// Class or one of its dependencies is not present... Not so pretty, but Spring uses same way
			return false;
		}
	}
	
	public static boolean isJava8Present() {
		return isPresent("java.time.LocalDate");
	}
}
