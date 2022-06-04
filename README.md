# Yaml Resource Bundle

## Can be use with [Spring Framework](https://spring.io)

# Instructions

- Add dependency to Maven / Gradle project:

```groovy
implementation 'ru.maksimvoloshin.utility:yaml-resource-bundle:0.1'
```

```xml

<dependency>
    <groupId>ru.maksimvoloshin.utility</groupId>
    <artifactId>yaml-resource-bundle</artifactId>
    <version>0.1</version>
</dependency>
```

- Create a little configuration:

```java
@Bean
ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource bundle=new YamlResourceBundleMessageSource();
        bundle.setBasename("messages");
        bundle.setDefaultEncoding("utf-8");
        return bundle;
        }
```

- Just inject bean in a project and use as usually

# Features

```java
bundle.getString("top.sub.check");
// returns string value
```

```java
bundle.getObject("top.sub-another.may-be-array");
// returns list of objects
```

```java
bundle.getObject("top.sub-another.may-be-array.size");
// returns size of list
```

```java
bundle.getObject("top.sub-another.may-be-array[0].text");
// returns field value of array object
```

```java
bundle.getObject("top.sub-another.list-of-value[0]");
// returns array value by index
```

