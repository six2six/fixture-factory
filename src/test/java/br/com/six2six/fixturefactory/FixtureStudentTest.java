package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import br.com.six2six.fixturefactory.function.Function;
import br.com.six2six.fixturefactory.model.Address;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Student;

public class FixtureStudentTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureAnyStudent() {
		Student student = Fixture.from(Student.class).gimme("valid");
		assertNotNull("Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
		assertTrue("Tests taken should be greather than 0", student.getTestsTaken() > 0);
		assertTrue("Best score should be greather than 0", student.getTestsTaken() > 0);
		assertTrue("Addresses should be greather than 0", student.getAddresses().size() > 0);
	}

	@Test
	public void fixtureStudentWithChangedAddresses() throws Exception {
		Student student = Fixture.from(Student.class).gimme("valid", new Rule() {{
			add("addresses[0].id", 123456L);
			add("addresses[0].city.name", "BH");
			add("addresses[0].city.neighborhoods[0].name", "Santa Efigênia");
			add("addresses[1].id", 123457L);
			add("addresses[2].id", 123458L);
		}});

		Address[] addresses = student
				.getAddresses()
				.toArray(new Address[student.getAddresses().size()]);

		assertTrue(addresses[0].getId() == 123456L);
		assertTrue(addresses[0].getCity().getName().equals("BH"));
		assertTrue(addresses[0].getCity().getNeighborhoods().get(0).getName().equals("Santa Efigênia"));
		assertTrue(addresses[1].getId() == 123457L);
		assertTrue(addresses[2].getId() == 123458L);
	}

	@Test
	public void fixtureFemaleStudent() {
		Student student = Fixture.from(Student.class).gimme("validFemaleStudent");
		assertNotNull("Female Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
	}
	
	@Test
	public void fixtureSharedSequence() {
		Student oneStudent = Fixture.from(Student.class).gimme("sharedSequence");
		Student otherStudent = Fixture.from(Student.class).gimme("otherSharedSequence");
		assertTrue(oneStudent.getId() < otherStudent.getId());
	}
	
	 @Test
	public void fixtureDefaultNumberSequence() {
		 Student firstStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 Student secoundStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 assertTrue(firstStudent.getId() < secoundStudent.getId());
	}
	 
	 @Test
	 public void fixtureMaleStudent() {
		 Student student = Fixture.from(Student.class).gimme("validMaleStudent");
		 assertNotNull("Male Student should not be null", student);
		 assertNotNull("Students id should not be null", student.getId());
	 }
	 
	 @Test
	 public void shouldGimmeOneMaleAndOneFemaleStudent() {
		 List<Student> students = Fixture.from(Student.class).gimme(2, "validFemaleStudent", "validMaleStudent");
		 assertEquals(Gender.FEMALE, students.get(0).getGender());
		 assertEquals(Gender.MALE, students.get(1).getGender());
	 }
}
