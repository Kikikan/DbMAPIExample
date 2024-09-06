# DbMAPIExample
A very brief example of a plugin using the API of [Dead by Moonlight](https://www.spigotmc.org/resources/dead-by-moonlight.83310/).
# Tutorial
## Including the API dependency
You can check the latest version at https://repo.gyongyosi.dev.
Maven examples are provided below.
```xml
<repositories>
  <repository>
    <id>gyongyosi-releases</id>
    <url>https://repo.gyongyosi.dev/releases</url>
  </repository>
</repositories>
```

```xml
<dependency>
  <groupId>dev.gyongyosi.dbm</groupId>
  <artifactId>api</artifactId>
  <version>2.0.0</version>
  <scope>provided</scope>
</dependency>
```

## Creating your own Components
### Different types of Components
First of all, you should know the different types of Components:
- Perk
- Item
- Game Component
- Status Effect
- World Object

Common properties:
- They all must implement the corresponding Core interface (PerkCore, ItemCore, GameComponentCore, StatusEffectCore, WorldObjectCore).
- Each Core implementation must not have any constructors (thus implicitly having the default public one, which will be used by Dead by Moonlight).

Differences:
- What fields must the configuration have, for example a GameComponent only needs the classpath, while a Perk also needs a list of the player types which can use the Perk. (Check the corresponding Config class for help.)
- What fields can be injected into the Core objects. (Check the Inject annotation for help.)

### Core and regular interfaces
If you take a look at the "regular" interface of any Component, you will see that it has a type parameter which must extend the Core interface. This is to reduce the boilerplate code extending a regular abstract class might lead to.
The structure is the following: every Component has an interface, which contains everything the specific Component needs to have. (For example, every Perk needs a PerkItem, which is used to represent the state of the Perk in the player's inventory.)
Each Component has a Core, which can handle events and do whatever it needs to do. The Core is what you modify (and thus, implement), while the "regular" interface has already been implemented by Dead by Moonlight.

### Listening to events
Just create a public void method (`public void something()`) inside your Core class, with the parameter being the interface of the event you want to listen to, and add `dev.gyongyosi.dbm.api.event.EventHandler` annotation to the method.
```java
@EventHandler
public void onHealthChange(HealthStateChangedEvent event) {
    // ...
}
```

## Extending Dead by Moonlight
### The Root of it All: Extension
Extending Dead by Moonlight requires you to create your own Extension instance. The Extension contains every addition (Core implementation) you made to DbM. You can create it in two ways:
1. Using ExtensionBuilder (more complicated, but does not require Spigot API)
2. Using Dead by Moonlight Spigot Helper dependency, which will automatically create the Extension instance based on your Spigot plugin (simple, but requires Spigot API)
### Why you would want to avoid Spigot API
Dead by Moonlight creates its own classes, meaning, you actually do not need the Spigot API in order to create an Extension. Of course, if you want your Java classes to be loaded, you have to make it a plugin (so the server loads and executes your code), but after that, no Spigot API calls are required.
If I or someone implements Dead by Moonlight's API in another platform (Fabric, or NeoForge, or something else) you will have an easier time making your Extension cross-platform.
Since this is all hypothetical, you are free to use the simpler solution, but in this example I will use ExtensionBuilder.
### ExtensionBuilder
ExtensionBuilder allows you to not care about the Extension class itself, and you can focus on what you actually want to add into Dead by Moonlight.
The only required field of the ExtensionBuilder is a Logger object. This logger is going to be used by Dead by Moonlight for communication, but you can also inject it into any Component you make using the @Inject annotation.
Once you have initialized an ExtensionBuilder, you can add your Components by the setConfig() method as seen in the example below. The Map<String, Object> you are creating must contain every Component of that type. In this example, we are only adding 1 new Perk.
Of course, if you fill the configuration map from an outer file, that means the server administrator can override the values, making your Component configurable.
```java
// Creating PerkConfig.
var perkConfig = new HashMap<String, Object>();
// Setting the required fields.
perkConfig.put("whoosh.classpath", "dev.gyongyosi.dbmapitest.Whoosh");
perkConfig.put("whoosh.playerTypes", List.of(DbmPlayerTypeEnum.KILLER.toString(), DbmPlayerTypeEnum.SURVIVOR.toString()));
// Creating a custom field. You could fill this value from a configuration file which the server administrators can edit.
// Custom fields like this are automatically inserted into the description of the Component.
perkConfig.put("whoosh.ticks", 100L);
// Setting the Extension's PerkConfig to our newly created config.
extensionBuilder.setConfig(PerkConfig.class, perkConfig);
```

### LanguageFile
By default, Dead by Moonlight does not know the name and description of your Components, which is why you should also add a LanguageFile using the ExtensionBuilder.
The API provides MapLanguageFile as a basic implementation of the LanguageFile interface. In the example below, a YAML Bukkit configuration file is loaded and added as a LanguageFile. Note that you can use this approach when adding Components, in fact, it is what Dead by Moonlight uses as well in order for the Components to be configurable.
```java
// Creating the Language map.
var textMap = new NestedLinkedHashMap();
for (Text value : Text.values()) {
    textMap.put(value.getPath(), value.getDefaultText());
}

// Adding the LanguageFile.
extensionBuilder.addLanguageFile(new MapLanguageFile(Locale.ENGLISH, textMap));
```

### Registering the Extension
Once you added everything you wanted, it is time to register them. It must happen before the initialization of the API, and in Bukkit servers, that happens in the onEnable() method call. This means, that you must register in in the onLoad() method.
Once the Extension was registered, the players will be able to use your Components as if they were included with Dead by Moonlight.
```java
@Override
public void onLoad() {
    // Creating the ExtensionBuilder.
    var extensionBuilder = new ExtensionBuilder(getLogger());
    // Setting up the ExtensionBuilder
    // ...
    // Registering the Extension.
    ApiHolder.getApi().registerExtension(extensionBuilder.build());
}
```