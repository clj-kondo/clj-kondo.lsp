/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

import * as path from 'path';
import { workspace, ExtensionContext, window } from 'vscode';
import { readFileSync } from 'fs';

import {
    LanguageClient,
    LanguageClientOptions,
    ServerOptions,
    TransportKind
} from 'vscode-languageclient';

let client: LanguageClient;

function readVersion(extensionDir: string) {
    return readFileSync(path.join(extensionDir,'CLJ_KONDO_VERSION')).toString().trim();
}

function welcome(version: string) { return `-- Welcome to clj-kondo v${version}.
--
-- To configure clj-kondo, create a .clj-kondo directory in the root of your workspace and place a config.edn file in it.
-- Check out the configuration documentation here: https://github.com/borkdude/clj-kondo/blob/master/doc/config.md.
--
-- If you have questions, join the #clj-kondo channel on Clojurians slack.
-- Report issues at: https://github.com/borkdude/clj-kondo.
--
-- Happy linting!
`};

export function activate(context: ExtensionContext) {

    const channel = window.createOutputChannel('clj-kondo');
    context.subscriptions.push(channel);

    // channel.show(true);

    // The debug options for the server
    // --inspect=6009: runs the server in Node's Inspector mode so VS Code can attach to the server for debugging
    let debugOptions = { execArgv: ['--nolazy', '--inspect=6009'] };
    let jarPath = path.join(context.extensionPath, 'clj-kondo.lsp-standalone.jar');
    let serverOptions: ServerOptions = {
        run: {command: 'java', args:['-jar', jarPath] },
        debug: {command: 'java', args:['-jar', jarPath]},
    }

    // If the extension is launched in debug mode then the debug server options are used
    // Otherwise the run options are used

    // Options to control the language client
    let clientOptions: LanguageClientOptions = {
        // Register the server for plain text documents
        documentSelector: [{ scheme: 'file', language: 'clojure' }],
        outputChannel: channel
    };

    // Create the language client and start the client.
    client = new LanguageClient(
        'clj-kondo',
        'clj-kondo',
        serverOptions,
        clientOptions
    );

    channel.appendLine(welcome(readVersion(context.extensionPath)));

    // Start the client. This will also launch the server
    context.subscriptions.push(client.start());
}

export function deactivate(): Thenable<void> | undefined {
    if (!client) {
        return undefined;
    }
    return client.stop();
}
