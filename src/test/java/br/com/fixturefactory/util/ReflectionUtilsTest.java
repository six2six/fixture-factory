package br.com.fixturefactory.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.fixturefactory.model.Student;

public class ReflectionUtilsTest {
	@Test
	public void shouldSetPrivateFieldValueWithoutSetter() {
		HighSchoolStudent student = new HighSchoolStudent();
		ReflectionUtils.setFieldValue(student, "grade", "3A");
		assertEquals("3A", student.getGrade());
	}
	
	@Test
	public void shouldSetPrivateInheritedFieldValueWithoutSetter() {
		HighSchoolStudent student = new HighSchoolStudent();
		ReflectionUtils.setFieldValue(student, "firstName", "Nykolas");
		assertEquals("Nykolas", student.getFirstName());
	}
	
	private static class HighSchoolStudent extends Student {
		private String grade;
		
		public String getGrade() {
			return grade;
		}
	}
}
