# DatodoMVC

A [TodoMVC](http://todomvc.com/) implementation using [Dato](https://github.com/sgrove/dato), the Clojure/Script Datomic/DataScipt app development framework.

This is an implementation of TodoMVC using Datomic on the backend to persist data that's then fed to DataScript in the frontend. The UI is driven entirely from the DataScript DB (even component local state). See the [Dato Rationale](https://github.com/sgrove/dato#rationale) for more. [Here's a video](https://www.youtube.com/watch?v=7bAdBXfZtZU) of what it looks like once it's running

## Running this demo

1. Install and start Datomic (TODO: friendlier instructions here)

2. Clone this repo

    ```
    git clone git@github.com:sgrove/datodomvc.git
    cd datodomvc
    git submodule update --init
    ```

3. Start the figwheel server. Figwheel compiles the frontend assets, recompiles them when the files change, and updates the browser with the changed code.

   `lein figwheel` or `rlwrap lein figwheel`

4. Start the web server. Export a few environment variables to get things working on your system. The DatodoMVC example uses environ, so you can also define env variables in a local profiles.clj.

    ```
    export DATOMIC_URI="datomic:free://localhost:4334/pc2" # This will depend on your datomic setup
    export NREPL_PORT=6005 # starts embedded nrepl server
    export DATO_PORT=8081 # 8080 is the default value, change it if there is a port conflict
    export PORT=10556 # 10555 is the default
    lein run
    ```

## Support

 * For questions on getting this demo running or modifying it, feel free to open issues on this repo.
 * For overall Dato design issues, please use the [Dato Repo's issue tracker](https://github.com/sgrove/dato/issues)
 * General questions, I'm on twitter at [@sgrove](https://twitter.com/sgrove)

## License

Copyright © 2015 Sean Grove

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
