# clj-kondo.lsp

This repo contains the code for:

- the [clj-kondo](https://github.com/borkdude/clj-kondo) language server (see [LSP](https://microsoft.github.io/language-server-protocol/)).
- the [VSCode extension](https://marketplace.visualstudio.com/items?itemName=borkdude.clj-kondo) which bundles the server and client.

## Build

Project automation is done using [babashka tasks](https://book.babashka.org/#tasks).

### Server

To build the server, run this script:

    bb vscode-server

This will copy the uberjar to the vscode-extension directory.

### VSCode extension

Enter the `vscode-extension` directory:

     cd vscode-extension

To intall the dependencies, run:

     npm install

In VSCode open the project directory and hit `F5 (Start Debugging)` to run the
extension. Edit a Clojure file (`.clj`) and you will get diagnostic feedback.

## License

Copyright © 2019-2022 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
