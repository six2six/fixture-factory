![Fixture Factory](http://s27.postimg.org/g2cbltgv7/fixture_factory.png)
==================================================================
[![Build Status](https://travis-ci.org/six2six/fixture-factory.png?branch=master)](https://travis-ci.org/six2six/fixture-factory)
[![codecov](https://codecov.io/gh/six2six/fixture-factory/branch/master/graph/badge.svg)](https://codecov.io/gh/six2six/fixture-factory)

Fixture Factory is a tool to help developers quickly build and organize fake objects for unit tests. The key idea is to create specification limits of the data (templates) instead of hardcoded data. Try using F-F, then you can focus on the behavior of your methods and we manage the data.

## Installing

Use it like a maven dependency on your project

	<dependency>
		<groupId>br.com.six2six</groupId>
		<artifactId>fixture-factory</artifactId>
		<version>3.1.0</version>
	</dependency>

## Usage

Writing template rules

	Fixture.of(Client.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
		add("name", random("Anderson Parra", "Arthur Hirata"));
		add("nickname", random("nerd", "geek"));
		add("email", "${nickname}@gmail.com");
		add("birthday", instant("18 years ago"));
		add("address", one(Address.class, "valid"));
	}});

	Fixture.of(Address.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 100L)));
		add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
		add("city", "São Paulo");
		add("state", "${city}");
		add("country", "Brazil");
		add("zipCode", random("06608000", "17720000"));
	}});

You can also create a new template based on another existing template. Using this you can override the definition for a property

	Fixture.of(Address.class).addTemplate("augustaStreet").inherits("valid", new Rule(){{
		add("street", "Augusta Street");
	}});

Using on your tests code:

Gimme one object from label "valid"

	Client client = Fixture.from(Client.class).gimme("valid");

Gimme N objects from label "valid"

	List<Client> clients = Fixture.from(Client.class).gimme(5, "valid");
	
Gimme N objects each one from one label

	List<Client> clients = Fixture.from(Client.class).gimme(2, "valid", "invalid");

Additional helper functions to create generic template:

### Managing Templates

Templates can be written within TemplateLoader interface

	public class ClientTemplateLoader implements TemplateLoader {
	    @Override
	    public void load() {
	        Fixture.of(Client.class).addTemplate("valid", new Rule(){{
	            add("id", random(Long.class, range(1L, 200L)));
	            add("name", random("Anderson Parra", "Arthur Hirata"));
	            add("nickname", random("nerd", "geek"));
	            add("email", "${nickname}@gmail.com");
	            add("birthday", instant("18 years ago"));
	            add("address", one(Address.class, "valid"));
	        }});

	        Fixture.of(Address.class).addTemplate("valid", new Rule(){{
	            add("id", random(Long.class, range(1L, 100L)));
	            add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
	            add("city", "São Paulo");
	            add("state", "${city}");
	            add("country", "Brazil");
	            add("zipCode", random("06608000", "17720000"));
	        }});
	    }
	}

All templates can be loaded using FixtureFactoryLoader telling what package that contains the templates

	FixtureFactoryLoader.loadTemplates("br.com.six2six.template");

Example of loading templates with JUnit tests

	@BeforeClass
	public static void setUp() {
	    FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

### Processors

Fixture-Factory comes with a simple mechanism to execute custom logic after the generation of each object.

To do so, implement the Processor interface:

 	public class MyCustomProcessor implements Processor {
   		public void execute(Object object) {
     		//do something with the created object
   		}
 	}

And use it:

	Fixture.from(SomeClass.class).uses(new MyCustomProcessor()).gimme("someTemplate");

The #execute method will be called for each object that Fixture-Factory generates. For instance, if a Client has an Address, the framework will generate the Address, call #execute with the generated Address as argument, set the Address into the Client, call #execute with the generated Client as argument and then return it.  

In case you want to persist the generated object in your database and you are using Hibernate, we already have a `HibernateProcessor` that persists all created objects using the provided session:

	Fixture.from(Client.class).uses(new HibernateProcessor(session)).gimme("valid");

### Relationship (one-to-one and one-to-many)

	Fixture.of(Order.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
		add("items", has(3).of(Item.class, "valid"));
		// add("items", has(3).of(Item.class, "valid", "invalid", "external")); this will generate three Item, each one from one of the given templates
		add("payment", one(Payment.class, "valid"));
	}});

	Fixture.of(Item.class).addTemplate("valid", new Rule(){{
		add("productId", random(Integer.class, range(1L, 200L)));
	}});

	Fixture.of(Payment.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
	}});

### Regex

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
		add("id", regex("\\d{3,5}"));
		add("phoneNumber", regex("(\\d{2})-(\\d{4})-(\\d{4})"));
	});

### Date

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
		add("dueDate", beforeDate("2011-04-15", new SimpleDateFormat("yyyy-MM-dd")));
		add("payDate", afterDate("2011-04-15", new SimpleDateFormat("yyyy-MM-dd")));
		add("birthday", randomDate("2011-04-15", "2011-11-07", new SimpleDateFormat("yyyy-MM-dd")));
		add("cutDate", instant("now"));
	});

### Name

	Fixture.of(Any.class).addTemplate("valid", new Rule(){{
		add("firstName", firstName());
		add("lastName", lastName());
	});

### Unique random
	
	Fixture.of(Any.class).addTemplate("valid", new Rule() {{
		add("country", "Brazil");
		add("state", uniqueRandom("São Paulo", "Rio de Janeiro", "Minas Gerais", "Bahia"));
	}});

The attribute state of this fixture will contain an unique value each time it is generated. 
Note that if this fixture is generated more times than there are available state values, the state values will start to repeat.

### CNPJ

	Fixture.of(User.class).addTemplate("valid", new Rule() {{
		add("cnpj", cnpj()); // this will generate an unformatted CNPJ e.g. 11111111111111
		add("cnpj", cnpj(true)); this will generate a formatted CNPJ e.g. 11.111.111/1111-11
	}});

You can see more utilization on [tests](https://github.com/six2six/fixture-factory/tree/master/src/test/java/br/com/six2six/fixturefactory)!

## Contributing

Want to contribute with code, documentation or bug report?

Do it by [joining the mailing list](http://groups.google.com/group/fixture-factory) on Google Groups.

## Credits

Fixture-Factory was written by:

* [Anderson Parra](https://github.com/aparra)
* [Arthur Hirata](https://github.com/ahirata)
* [Douglas Rodrigo](https://github.com/douglasrodrigo)

with contributions from several authors, including:

* [Nykolas Lima](https://github.com/nykolaslima)

## License

Fixture-Factory is released under the Apache 2.0 license. See the LICENSE file included with the distribution for details.
