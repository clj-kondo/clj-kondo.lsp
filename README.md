# clj-kondo.lsp

This repo contains the code for:

- the [clj-kondo](https://github.com/borkdude/clj-kondo) language server (see [LSP](https://microsoft.github.io/language-server-protocol/)).
- the [VSCode extension](https://marketplace.visualstudio.com/items?itemName=borkdude.clj-kondo) which bundles the server and client.

## Prerequisites

To build the LSP server:

+ Java 8 JDK
  + On Ubuntu Linux: `sudo apt install openjdk-8-jdk`
+ [Leiningen](https://leiningen.org/)
+ [Babashka](https://github.com/babashka/babashka)

To use the LSP with Visual Studio Code:

+ [Visual Studo Code](https://code.visualstudio.com/)
  + On Ubuntu Linux: `sudo snap install code --classic`
+ [npm](https://www.npmjs.com/)
  + On Ubuntu Linux: `sudo apt install npm`

## Build

Project automation is done using [babashka tasks](https://book.babashka.org/#tasks).

### Server

To build the server, run this script from the root directory of this repo:

    bb vscode-server

This will copy the uberjar to the `vscode-extension` directory.

### VSCode extension

Enter the `vscode-extension` directory:

    cd vscode-extension

To intall the dependencies, run:

    npm install

While still in the `vscode-extension` directory, start VSCode with the
following command:

    code .

In the VSCode window that appears, press `F5` or choose menu item Run
-> Start Debugging.  This should cause _another_ VSCode window to
appear.  The first VSCode window that appears _does not_ have access
to the clj-kondo.lsp LSP server, but the second one does.

In the second VSCode window, open a Clojure file with suffix `.clj`
and you will get diagnostic feedback.

Near the bottom left of the window should appear two icons with
numbers to the right of each one.  The first icon looks like a circle
with an X inside of it, indicating errors, and the second icon looks
like a triangle with a ! inside of it, indicating warnings.  If you
click on one of these icons, it causes a separate window pane
containing details of the errors and warnings to appear (clicking the
icons when this window pane is visible causes the pane to disappear).
Inside of that pane, clicking on a line describing a warning or error
causes the main window pane containing your code to jump to the
relevant line of code.

## License

Copyright © 2019-2022 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
