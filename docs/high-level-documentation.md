# High Level Documentation

Describes relevant information about the code structure of JabRef in a very precise and succinct way. 

## Package Structure

```
gui --> logic --> model
gui ------------> model
# all packages and classes which are not part of logic or model 
# are considered gui classes from a dependency stand of view
```

## Most Important Classes and their Relation

Both GUI and CLI are started via the `JabRefMain` which will in turn call `JabRef` which then decides whether the GUI (`JabRefFrame`) or the CLI (`JabRefCLI` and a lot of code in `JabRef`) will be started. The `JabRefFrame` represents the Window which contains a `SidePane` on the left used for the fetchers/groups and a `DragDropPopupPane` extending a `JTabbedPane` on the right. Each tab is a `BasePanel` which has a `SearchBar` at the top, a `MainTable` at the center and a `PreviewPanel` or an `EntryEditor` at the bottom. Any right click in the `MainTable` is handled by the `RightClickMenu`. Each `BasePanel` holds a `BibDatabaseContext` consisting of a `BibDatabase` and the `MetaData`, which are the only relevant data of the currently shown database. A `BibDatabase` has a list of `BibEntries`. Each `BibEntry` has a key, a bibtex key and a key/value store for the fields with their values. Interpreted data (such as the type or the file field) is stored in the `TypedBibentry` type. The user can change the `JabRefPreferences` through the `PreferencesDialog` which uses a `JTabbedPane` to structure the preferences.

![Class Diagram](http://yuml.me/20975ef4)

Visualization as a class diagram: http://yuml.me/edit/20975ef4