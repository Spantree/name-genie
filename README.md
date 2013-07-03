# Name Genie

A Java/Groovy library that generates randomized, unique first and last names with gender.  Each generated name is
guaranteed unique for the lifecycle of a given NameGenie object.  It will regenerate if it detects a collision. If
generating for both genders, there should be over 16 million unique names possible.

## Basic

### Usage

```groovy
import net.spantree.NameGenie
import net.spantree.Person

NameGenie genie = new NameGenie()
def people = []
10.times {
    people << genie.generate()
}
people.each { Person person ->
    println "${person.firstName} ${person.lastName} (${person.gender})"
}
```

### Output

```
Leone Noseworthy (Female)
Elmer Kuruppillai (Male)
Annice Gaebel (Female)
Chauncey Ficken (Male)
Nicola Whiskin (Female)
Davis Wurtz (Male)
Otelia Raab (Female)
Hunter Callender (Male)
Scot Thibeault (Male)
Lise Tresrch (Female)
```

## Gender-Specific

### Usage

```groovy
import net.spantree.NameGenie
import net.spantree.Person

NameGenie genie = new NameGenie()

Person male = genie.generateMale()
println "${male.firstName} ${male.lastName} (${male.gender})"

Person female = genie.generateFemale()
println "${female.firstName} ${female.lastName} (${female.gender})"
```

### Output

```
Frank Hanzel (Male)
Marielle Fastfeat (Female)
```

## Copyright and License

First and last name lists provided by [Scrapmaker](http://scrapmaker.com/dir/names)

Copyright 2013, Spantree Technology Group, LLC.
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)