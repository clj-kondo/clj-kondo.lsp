# clj-kondo.lsp

This repo contains the code for:

- the [clj-kondo](https://github.com/borkdude/clj-kondo) language server (see [LSP](https://microsoft.github.io/language-server-protocol/)).
- the [VSCode extension](https://marketplace.visualstudio.com/items?itemName=borkdude.clj-kondo) which bundles the server and client.

## Build

### Server

To build the server, run this script:

    script/build-server

This will copy the uberjar to the vscode-extension directory.

### VSCode extension

Enter the `vscode-extension` directory:

     cd vscode-extension

To intall the dependencies, run:

     npm install

In VSCode open the project directory and hit `F5 (Start Debugging)` to run the
extension. Edit a Clojure file (`.clj`) and you will get diagnostic feedback.

### Emacs
You can use [lsp-mode](https://github.com/emacs-lsp/lsp-mode), changing the lsp-mode server command configuration to point to `clj-kondo` lsp server.

First, download the lastest `clj-kondo-lsp-server-<version>-standalone.jar` [here](https://github.com/borkdude/clj-kondo/releases), then configure your `lsp-mode` pointing to the clj-kondo lsp server jar that you downloaded, like the example below:

```lisp
(use-package lsp-mode
  :hook ((clojure-mode . lsp))
  :commands lsp
  :custom
  ((lsp-clojure-server-command '("java" "-jar" "/home/user/clj-kondo/clj-kondo-lsp-server.jar")))

  :config
  (dolist (m '(clojure-mode
               clojurescript-mode))
    (add-to-list 'lsp-language-id-configuration `(,m . "clojure"))))
```

## License

Copyright © 2019 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
