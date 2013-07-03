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

## Copyright

First and last name lists provided by [Scrapmaker](http://scrapmaker.com/dir/names)

Library Copyright 2013, Spantree Technology Group, LLC.

## License

```
This software is licensed under the Apache 2 license, quoted below.

Copyright 2009-2013 Shay Banon and ElasticSearch <http://www.elasticsearch.org>

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
