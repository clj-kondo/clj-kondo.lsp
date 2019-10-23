# clj-kondo.lsp

## Build

### Server

To build the server, run this script:

    script/build-server

This will copy the uberjar to the vscode-extension directory.

### VSCode extension

Enter the `vscode-extension` directory:

     cd vscode-extension

To obtain the dependencies, run:

     npm install

In VSCode open the project directory and hit `F5 (Start Debugging)` to run the
extension. Edit a Clojure file (`.clj`) and you will get diagnostic feedback.

## License

Copyright © 2019 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
