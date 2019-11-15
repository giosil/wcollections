# Wrapped Collections

A simple convenience library for manage collections.

## Examples

### Get typed values

```java
WMap wmap = new WMap(mapOfValues);
int iValue = wmap.getInt(key, 0);
double dValue = wmap.getDouble(key, 3.14d);
String sValue = wmap.getString(key, "");
Date dtValue = wmap.getDate(key, null);
```

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
