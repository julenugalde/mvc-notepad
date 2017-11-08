# mvcnotepad

Simple notepad application to test the MVC pattern

## UML class diagram

The following diagram shows the packet structure and dependencies. It has been made with [ObjectAid UML Explorer for Eclipse](http://www.objectaid.com/home).

![UML class diagram](https://github.com/julenugalde/mvcnotepad/blob/master/src/eus/julenugalde/mvcnotepad/UML_class_diagram.png)

## Prerequisites

The model implementation for database requires a MySQL server running in the local machine. Additionally, the MySQL connector must be included in the the project (mysql-connector-java-x.x.xx-bin.jar)

## Known issues

The network data model is still unimplemented.

There are problems with the line separator characters in Windows, which are not correctly read from text files. 

The password prompt for the  database connection should hide the characters.

## Icons

The icons used in the application are part of the [material design](https://github.com/google/material-design-icons/) project, available under the Apache License Version 2.0.

All icons are released under an [Attribution-ShareAlike 4.0 International](http://creativecommons.org/licenses/by-sa/4.0/) license.

## License

GNU General Public License v3.0. See [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.txt).
 