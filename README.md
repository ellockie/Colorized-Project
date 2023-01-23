# Colorized-Project

🟨🟥🟩🟦

---

This is a fork of the [Project-Color by nowtilous of which the maintenance seems to be somewhat abandoned][original].

In general, some stale pull requests have been merged and some issues have been fixed.

The plugin has been renamed to Colorized-Project to namespace away and avoid confusion with the original plugin.

The plugin has been updated to work with the latest IntelliJ versions.

The README below is a WIP and may include references to the original plugin.

---

![Build](https://github.com/nowtilous/Project-Color/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19463.svg)](https://plugins.jetbrains.com/plugin/19463)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19463.svg)](https://plugins.jetbrains.com/plugin/19463)
![](https://img.shields.io/github/issues-closed/nowtilous/Project-Color)
![](https://img.shields.io/github/issues/nowtilous/Project-Color)
![](https://img.shields.io/jetbrains/plugin/r/rating/19463)

<!-- Plugin description -->
Remember your projects by color! Colorize the title bar to differentiate your open projects.

Working on multiple projects simultaneously with intellij products can be difficult,
since they all look the same on the taskbar, this plugin solves that by giving the user the option
to colorize each project's title bar, which is visible from the taskbar windows menu.
<!-- Plugin description end -->

## Usage & Screenshots 🖥️

![](/screenshots/usage.gif)
![](/screenshots/desktop_multiple_projects.png)
![](/screenshots/color_picker_menu.png)
![](/screenshots/taskbar_view.png)

## Compatibility ⚙️

Supports all Intellij IDEs from version 2021.1 (211) and above!
> ✅ Tested on all versions from 2021.1 to 2022.1

> ✅ Works on all Intellij products

> ✅ Supports Windows🪟, MacOS🍎 , and Linux🐧 distributions (that Jetbrains support)!

## Features 💪

  - Set menu frame color (as shown in the Screenshots section).
  > ❔ Next to the build icons, a colorful cube button will popup a menu which allows you to choose colors from.
  - Automatically select colors for all projects, based on every project name.
  > ❔ In the color chooser menu, select the 'Options' tab, and check the auto color set checkbox.

  - The foreground color is highlighted according to the darkness of the chosen color, in order to have a comfortable contrast from the background.

## Installation 🛠️

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Project-Color"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/nowtilous/Project-Color/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[original]: https://github.com/nowtilous/Project-Color/issues/
