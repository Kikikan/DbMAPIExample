# DbMAPIExample
A very brief example of a plugin using the API of [Dead by Moonlight](https://www.spigotmc.org/resources/dead-by-moonlight.83310/).
# Tips
## Importing DeadByMoonlight.jar to the local Maven repository
Run the following command (replace <path-to-file> to the location of the .jar):
`mvn install:install-file -Dfile=<path-to-file> -DgroupId=org.kikikan -DartifactId=deadbymoonlight -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true`
## Regarding constructors
### Perks
2 parameters: The first one is a JavaPlugin, and the other is a PerkUser.

Example: `public YourPerk(JavaPlugin plugin, PerkUser perkUser) {...}`
### Items
2 parameters: The first one is a JavaPlugin, but the other is a Survivor.

Example: `public YourTime(JavaPlugin plugin, Survivor survivor) {...}`
### Game Components
2 parameters: The first one is a JavaPlugin, and the other is the Game it belongs to.

Example: `public YourComponent(JavaPlugin plugin, Game game) {...}`
### Events
As they get constructed by you, there are no restrictions regarding the constructors.
## Listening to events
Just create a `public void something()` method, with the parameter being the event you want to listen to.

Example: `public void onHealthChange(HealthStateChangedEvent event) {...}`
## Custom modifiable (through the appropriate .yml file) values
While using any class which derived from Configurable (Any Perk, Item, or GameComponent) you can easily work with values that can be modified by the server.
Simply use the `getValueFromConfig(id, defaultValue)` method. If the config file does not have the specified id, the method will write the default value to the file and will return it. The method uses Bukkit's built-in FileConfiguration class.
## Skill Checks
You can create a Skill Check using the static method `SkillCheck.createSkillCheck()` .

Example: `SkillCheck skillCheck = SkillCheck.createSkillCheck(plugin, survivor, SkillCheckReason.CUSTOM,
(survivor) -> { // Great Skill Check },
(survivor) -> { // Good Skill Check },
(survivor) -> { // Failed Skill Check }
);`

After creating one, you can modify some of its settings, and after you're done, use `skillCheck.show()` to show it to the Survivor.