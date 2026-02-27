# Chapter 4

**How to choose where to start, What test to right next and so forth.**
- choosing the next tests to write and direction are always a hard choice, especially for beginners in test driving
- There are heuristics we should follow when writing tests

**Strategies**
- Details vs. Big Picture
- Uncertain vs. Familiar
- High Value vs. Low Hanging Fruit
- Happy Path vs. Error Situations


- Don't think too much about which test is the "right" one to write next. Theres no right sequence in the first place.
and these test selection strategies will become second nature over time
- From what i gathered from these tests, its good to just get a test down and passing and that alone will help get you towards implementing more.
Its a good idea to not always try to build out all these edge cases when what good is an edge case when your basic functionality
doesn't work. Its acutally a good idea to do the easy obvious things first

**Different ways to make tests pass**

- faking it
- triangulation
- obvious implentation


**Faking it**

- no notes needed, self-explanatory
- hard code the result

**Triangulation**

- this is when you dont know what your solution exactly needs to look like.
Here we keep implementing more tests that force us towards a solution space so
we narrow down what really needs to happen

**Obvious Implementation**

- sometimes the obvious answer is the right one, if this is something were familiar with just take the bigger step and implement what we know.
Skip the faking and triangulation because it will just eventually get us to here


### Prime Guidelines for TDD
This is meant to be a short list we can always reflect on if we get stuck

- do not skip refactoring
- get to green fast
- slow down after a mistake

### Fixtures
**A Fixture is any precondition that your test silently depends on being true when it starts**
Whether that be in memory, reading from a file, reading a row from the database. Its does not matter.
These are all considered fixtures
Benefits
- help remove duplication from tests
- allow test to focus on the one thing they're testing

### Test Doubles (Mocks)
**What if the code you're testing depends on something complex or unreliable, like a database, a file, or another class that isn't built yet?**

You don't want your test to braek becuase the database is down. You want to test your code in isolation.
A test double is a simplified stand in swap in place of the real dependency, just for the test.

This is an umbrella term. Under it, there are a few types.

| Type  | What it does                          |
|-------|---------------------------------------|
| Stub  | Returns a hardcoded value when called |
| Mock  | Also checks how it was called         |
| Fake  | A working but simplified version      |
| Dummy | Passed in but never actually used     |
 

### State based Testing
Verifying the state of an object after you act on it.
So checking a return value, or check what ended up in some data structure. Both of those are state, the
data that exists after the operation ran.

Test doubles are used as stand ins when you dont care about the dependencys logic. You want to fake that logic
and only where about the logic of the object you are testing. You would do this for slow calculations, database access,
expensive calls. you want to fake the return for your outer object.

 