
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
## Uniform Level of Abstraction

A method should either deal with *what* happens (high level) or *how* it happens (low level) — not both. When a method mixes the two, extract the low level detail into its own method.

In `evaluate()`, `parser.parse()` was high level ("go get me the segments") but the `StringBuilder` loop below it was low level detail. Extracting it into `concatenate()` gave `evaluate()` a uniform, high level focus.

Same applied to `append()` — the raw string checks and map lookups were buried in one messy method. Extracting `isVariable()` and `evaluateVariable()` made the intent immediately readable.

## Tell Don't Ask

Tell objects to do things rather than asking for their data and doing it yourself. If you're reaching into an object, pulling out data, and making decisions based on it — that logic probably belongs inside the object instead.