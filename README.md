# Fast Enum Maps

A modern Java library providing high-performance multi-dimensional EnumMap implementations.

## Overview

This library extends Java's `EnumMap` concept to support multiple enum keys (2, 3, or 4 dimensions). It provides better performance than using composite keys or nested maps, while offering a clean, type-safe API.

## Features

- **Multi-dimensional enum maps**: Support for 2, 3, and 4 enum key dimensions
- **Type-safe**: Full generic type support with compile-time type checking
- **High performance**: Built on EnumMap for optimal enum-based lookups
- **Modern Java**: Requires Java 17+, uses modern language features
- **Functional API**: Support for Optional, Stream API, and lambda expressions
- **Well-tested**: Comprehensive JUnit 5 test suite

## Requirements

- Java 17 or higher
- Gradle 7.0+ (or use included wrapper)

## Installation

### With Gradle

```gradle
dependencies {
    implementation 'org.thecloudforge:fastenummaps:0.2.0'
}
```

### Build from source

```bash
./gradlew build
```

## Usage Examples

### DoubleEnumMap (2 keys)

```java
enum Country { US, UK, FR }
enum Language { EN, FR, DE }

var map = new DoubleEnumMap<Country, Language, String>();
map.put(Country.US, Language.EN, "Hello");
map.put(Country.FR, Language.FR, "Bonjour");

String greeting = map.get(Country.US, Language.EN); // "Hello"

// Modern Optional API
Optional<String> optional = map.getOptional(Country.UK, Language.EN);
```

### TripleEnumMap (3 keys)

```java
enum Season { SPRING, SUMMER, FALL, WINTER }
enum Region { NORTH, SOUTH, EAST, WEST }
enum Activity { HIKING, SKIING, SWIMMING }

var map = new TripleEnumMap<Season, Region, Activity, Integer>();
map.put(Season.WINTER, Region.NORTH, Activity.SKIING, 42);

Integer count = map.get(Season.WINTER, Region.NORTH, Activity.SKIING); // 42
```

### QuadEnumMap (4 keys)

```java
var map = new QuadEnumMap<K1, K2, K3, K4, Value>();
map.put(key1, key2, key3, key4, value);
Value result = map.get(key1, key2, key3, key4);
```

## API Highlights

All map implementations provide:

- `get(K1 key1, K2 key2, ...)` - Get value by keys
- `getOptional(K1 key1, K2 key2, ...)` - Get Optional<V> by keys
- `put(K1 key1, K2 key2, ..., V value)` - Store value
- `remove(K1 key1, K2 key2, ...)` - Remove mapping
- `containsKeys(K1 key1, K2 key2, ...)` - Check if mapping exists
- `containsValue(Object value)` - Check if value exists
- `size()` - Number of mappings
- `isEmpty()` - Check if empty
- `clear()` - Remove all mappings

## Performance

EnumMap-based implementations provide O(1) lookup time and are significantly faster than using composite key objects with HashMap, as they avoid object creation and use array-based storage internally.

## Modernization (v0.2.0)

This version has been modernized with:

- **Java 17+**: Updated from Java 5 to Java 17
- **Gradle**: Migrated from Maven to Gradle with modern build practices
- **Modern APIs**: Optional support, Stream API, var declarations
- **Better validation**: Objects.requireNonNull for clear error messages
- **JUnit 5**: Updated test suite with modern assertions
- **Comprehensive Javadoc**: Full API documentation
- **Code improvements**: Lambda expressions, method references, enhanced clarity

## License

Apache License 2.0

## Contributing

Contributions are welcome! Please ensure all tests pass before submitting pull requests.

```bash
./gradlew test
```
