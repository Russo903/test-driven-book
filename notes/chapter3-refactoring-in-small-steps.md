
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