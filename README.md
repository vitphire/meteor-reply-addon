# Meteor Reply Addon

A Meteor addon that automatically replies to messages.

### How to use:  
- Clone this project
- Build it with gradle
- Put the .jar (from build/libs) into your mods folder

### Settings
The "File path" setting must be a valid file path, pointing to a .json file structured like this:
```json
{
    "regex": [
        {"trigger": "Trigger message 1 (as a regex)",
            "response": "Response message 1"},
        {"trigger": "Trigger message 2 (as a regex)",
            "response": "Response message 2"}
    ],
    "normal" : {
        "Trigger message 3 (not regex)": "Response message 3",
        "Trigger message 4 (not regex)": "Response message 4"
    }
}
```