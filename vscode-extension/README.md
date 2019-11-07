# Clj-kondo

Clj-kondo is a linter for Clojure and ClojureScript. This extension bundles a
language server and client for clj-kondo and requires no additional
installation.

After installation, the linter will run after opening and changing Clojure
related files.

The code for this extension is hosted [here](https://github.com/borkdude/clj-kondo.lsp).
For more information about clj-kondo itself, go [here](https://github.com/borkdude/clj-kondo).

## Changes

### 0.0.9

- bump clj-kondo lib to v2019.11.07.

### 0.0.8

- fix error when opening build.boot file (which is registered as a Clojure file by Calva)

### 0.0.7

- bump clj-kondo lib to v2019.11.03.

### 0.0.6

- bump clj-kondo lib to v2019.10.26.

### 0.0.5

- fix: language server would not shut down when editor closes

### 0.0.4

- add logo

## License

Copyright © 2019 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
