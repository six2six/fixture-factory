Fixture Factory - generator to create fake objects from a template
================================================================== 

### Instaling

	$ mvn clean install

Use it like a maven dependency on your project

	<dependency>
		<groupId>br.com.fixturefactory</groupId>
		<artifactId>fixture-factory</artifactId>
		<version>1.0-SNAPSHOT</version>
	</dependency>

### Usage

Writing bean template rules

	Fixture.of(Client.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
		add("name", random("Anderson Parra", "Arthur Hirata"));
		add("nickname", random("nerd", "geek"));
		add("email", "${nickname}@gmail.com");
		add("birthday", Calendar.getInstance());
		add("address", fixture(Address.class, "valid"));
	}});

	Fixture.of(Address.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 100L)));
		add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
		add("city", "São Paulo");
		add("state", "${city}");
		add("country", "Brazil");
		add("zipCode", random("06608000", "17720000"));
	}}); 

Gimme one object of valid label
	Client client = Fixture.of(Client.class).gimme("valid");

Gimme N objects of valid label
	List<Client> clients = Fixture.of(Client.class).gimme(5, "valid");

More helpers functions for create generic template:

#### Regex

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
	        add("id", regex("\\d{3,5}"));
	        add("phoneNumber", regex("(\\d{2})-(\\d{4})-(\\d{4})"));
	});

#### Date

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
		add("dueDate", beforeDate("2011-04-15", new SimpleDateFormat("yyyy-MM-dd")));
		add("payDate", afterDate("2011-04-15", new SimpleDateFormat("yyyy-MM-dd")));
		add("birthday", randomDate("2011-04-15", "2011-11-07", new SimpleDateFormat("yyyy-MM-dd")));
	});

#### Name

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
		add("firstName", firstName());
		add("lastName", lastName());
	});
	
You can see more utilization on [tests](fixture-factory/tree/master/src/test/java/br/com/fixturefactory)!
