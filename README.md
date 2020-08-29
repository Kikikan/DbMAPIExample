# DbMAPIExample
A very brief example of a plugin using the API of Dead by Moonlight.
# Tips
## Regarding Constructors
### Perks
2 parameters: The first one is a JavaPlugin and the other is a PerkUser.
### Items
2 parameters: The first one is a JavaPlugin, but the other is a Survivor.
### Game Components:
2 parameters: The first one is a JavaPlugin and the other is the Game it belongs to.
### Events:
As they get constructed by you, there are no more restrictions regarding the constructors.
## Listening to events
Just create a `public void something()` method, with the parameter being the event you want to listen to. For example: `public void onHealthChange(HealthStateChangedEvent event)`
