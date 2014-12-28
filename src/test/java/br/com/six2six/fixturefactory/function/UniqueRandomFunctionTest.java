package br.com.six2six.fixturefactory.function;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.UniqueRandomFunction;

public class UniqueRandomFunctionTest {

	private enum EnumMock {
		A,
		B,
		C,
		D,
		E,
		F,
		G,
		H,
		I,
		J
	}
	
	@Test
	public void shouldGenerate1000UniqueIntegers() {
		Set<Integer> integers = new HashSet<Integer>();
		for(int i = 0; i <= 1000; i++) {
			integers.add(i);
		}
		UniqueRandomFunction function = new UniqueRandomFunction(0, 1000);
		for(int i = 0; i <= 1000; i++) {
			Object value = function.generateValue();
			integers.remove((Integer) value);
		}
		assertTrue("Not all generated integers were unique", integers.isEmpty());
	}
	
	@Test
	public void shouldGenerate10UniqueCountries() {
		String[] countriesArray = new String[]{
				"Brazil", 
				"Argentina", 
				"Chile", 
				"Uruguay", 
				"Germany", 
				"Colombia", 
				"Australia",
				"Canada",
				"United States",
				"France"};
		Set<String> countriesSet = new HashSet<String>(Arrays.asList(countriesArray));
		
		UniqueRandomFunction function = new UniqueRandomFunction(countriesArray);
		for(int i = 0; i < 10; i++) {
			Object value = function.generateValue();
			countriesSet.remove((String) value);
		}
		assertTrue("Not all generated values were unique.", countriesSet.isEmpty());
	}
	
	@Test
	public void shouldGenerateUniqueEnumValues() {
		UniqueRandomFunction function = new UniqueRandomFunction(EnumMock.class);
		EnumMock[] enumMockConstants = EnumMock.class.getEnumConstants();
		Set<EnumMock> enumValuesSet = new HashSet<EnumMock>(Arrays.asList(enumMockConstants)); 
		
		for(int i = 0; i < enumMockConstants.length; i++) {
			EnumMock enumMock = function.generateValue();
			enumValuesSet.remove(enumMock);
		}
		
		assertTrue("Not all generated enum values were unique.", enumValuesSet.isEmpty());
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenNoMoreIntegerValuesExist() {
		UniqueRandomFunction function = new UniqueRandomFunction(0, 10);
		for(int i = 0; i <= 10; i++) {
			function.generateValue();
		}
		
		function.generateValue();
			
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenNoMoreFixedValuesExist() {
		UniqueRandomFunction function = new UniqueRandomFunction(new String[]{
				"Brazil", 
				"Argentina", 
				"Chile", 
				"Uruguay", 
				"Germany", 
				"Colombia", 
				"Australia",
				"Canada",
				"United States",
				"France"});
		for(int i = 0; i <= 10; i++) {
			function.generateValue();
		}
		
		function.generateValue();
			
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenNoMoreEnumValuesExist() {
		UniqueRandomFunction function = new UniqueRandomFunction(EnumMock.class);
		for(int i = 0; i <= 10; i++) {
			function.generateValue();
		}
		
		function.generateValue();
			
	}
}
