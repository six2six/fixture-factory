Fixture Factory - generator to create fake objects from a template
==================================================================

[![Build Status](https://travis-ci.org/six2six/fixture-factory.png?branch=master)](https://travis-ci.org/six2six/fixture-factory)

## Installing

Use it like a maven dependency on your project

	<dependency>
		<groupId>br.com.six2six</groupId>
		<artifactId>fixture-factory</artifactId>
		<version>2.1.0</version>
	</dependency>

## Usage

Writing bean template rules

	Fixture.of(Client.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
		add("name", random("Anderson Parra", "Arthur Hirata"));
		add("nickname", random("nerd", "geek"));
		add("email", "${nickname}@gmail.com");
		add("birthday", instant("18 years ago"));
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

Creating a new template based on another existing template. Using this you can override the definition for a property

	Fixture.of(Address.class).addTemplate("valid-augusta").inherits("valid", new Rule(){{
		add("street", "Augusta Street");
	}});

Gimme one object from valid label

	Client client = Fixture.from(Client.class).gimme("valid");

Gimme N objects from valid label

	List<Client> clients = Fixture.from(Client.class).gimme(5, "valid");

More helpers functions for create generic template:

### Relationship (one-to-one and one-to-many)

	Fixture.of(Order.class).addTemplate("valid", new Rule(){{
		add("id", random(Long.class, range(1L, 200L)));
		add("items", has(3).of(Item.class, "valid"));
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

You can see more utilization on [tests](fixture-factory/tree/master/src/test/java/br/com/fixturefactory)!

## Contributing

Want to contribute with code, documentation or bug report?

Do this by [joining the mailing list](http://groups.google.com/group/fixture-factory) on Google Groups.

## Credits

Fixture-Factory was written by:

* [Anderson Parra](https://github.com/aparra)
* [Arthur Hirata](https://github.com/ahirata)
* [Douglas Rodrigo](https://github.com/douglasrodrigo)

with contributions from several authors, including:

* [Nykolas Lima](https://github.com/nykolaslima)

## License

Fixture-Factory is released under the Apache 2.0 license. See the LICENSE file included with the distribution for details.

