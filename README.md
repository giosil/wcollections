# Wrapped Collections

![Build](https://github.com/giosil/wcollections/workflows/Build/badge.svg)
![Release](https://img.shields.io/github/v/tag/giosil/wcollections)

A convenience library for manage collections.

## Examples

### Get typed values

```java
WMap wmap = new WMap(mapOfValues);
int iValue = wmap.getInt(key, 0);
double dValue = wmap.getDouble(key, 3.14d);
String sValue = wmap.getString(key, "");
Date dtValue = wmap.getDate(key, null);
```

## Build

- `git clone https://github.com/giosil/wcollections.git`
- `mvn clean install`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
