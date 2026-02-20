
Template parser notes · MD
Copy

# Template Parsing - Session Notes

## Why We Extracted TemplateParser into Its Own Class

The parsing logic was complex enough that it deserved its own class rather than living inline. Extracting it keeps responsibilities separate. the original class doesn't need to know *how* parsing works, just that it does. This also makes the logic easier to test in isolation.

## The Regex

```
\$\{[^}]*}
```

Matches template variables like `${name}`. Breakdown:

- `\$\{` — matches the literal `${`
- `[^}]*` — matches any characters that are **not** `}`, i.e. the variable name. Stops as soon as it hits `}`, preventing it from greedily consuming too far
- `}` — matches the closing `}`


# Levels of Abstraction 
We had a method with 2 levels of abstraction. High level and low level. The goal is to have uniform level of abstraction.
This can be an easy refactoring effort sometimes. Heres an examples 
```java
public String evaluate() {
        TemplateParser parser = new TemplateParser();
        List<String> segments = parser.parse(stringTemplate);
        StringBuilder sb = new StringBuilder();
        for (String segment : segments) {
            append(segment, sb);
        }
        return sb.toString();
    }
```
This method above is mixing two levels of abstration. We have `parser.parse(stringTemplate)` which is going to split a string into segments and we say
"Here you go, we dont care how you do it. Just go get those segments for us". But then just below it we have a `StringBuilder` we are managing, looping over and calling append on it here.


```java
public String evaluate() {
        TemplateParser parser = new TemplateParser();
        List<String> segments = parser.parse(stringTemplate);
        return concatenate(segments);
    }
    
    private String concatenate(List<String> segments) {
        StringBuilder sb = new StringBuilder();
        for (String segment : segments) {
            append(segment, sb);
        }
        return sb.toString();
    }
```
This refactored version shows that looping manual logic pulled out. Now evaluate has a much tighter focus and what it needs to accomplish and how it goes about that.
It now has a uniform level of abstraction.


--- 
### Refactoring continued
We can go even further with this
```java
    private void append(String segment, StringBuilder sb) {
        if (segment.startsWith("${") && segment.endsWith("}")) {
            String variable = segment.substring(2, segment.length() - 1);
            
            if (!variableMap.containsKey(variable)) {
                throw new MissingValueException("No value found for " + segment);
            }
            sb.append(variableMap.get(variable));
            
        } else {
            sb.append(segment);
        }
    }
```
This method is disgusting and I woudlnt want another coworker to look at it. Its hard to read and if I came back to this at a later time
even as the author I would not be very happy. Lets pull more things out and make this readable


```java
  private void append(String segment, StringBuilder sb) {
        if (isVariable(segment)) {
            evaluateVariable(segment, sb);
        } else {
            sb.append(segment);
        }
    }
    
    private void evaluateVariable(String segment, StringBuilder sb) {
        String variable = segment.substring(2, segment.length() - 1);
        if (!variableMap.containsKey(variable)) {
            throw new MissingValueException("No value found for " + segment);
        }
        sb.append(variableMap.get(variable));
    }
    
    private boolean isVariable(String segment) {
        return segment.startsWith("${") && segment.endsWith("}");
    }
```


- note something short about "tell dont ask" principle