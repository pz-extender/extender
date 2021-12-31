# Project Zomboid Extender

_Project Zomboid Extender_ is an extension framework for Project Zomboid. It aims to expose more of the game than is
currently available for modding and introduce its own developer experience and quality of life features.

The work here is largely inspired by the work started at [PzStorm](https://github.com/pzstorm/storm).

## Features

### Configuring server vars from environment variables

All of Project Zomboid's dedicated server options can be configured via an environment variable instead of editing
server.ini. Where an environment variable is available, it takes precedence over any option set in the ini file.

Server options are prefixed with `ZOMBOID_` and converted to `UPPER_SNAKE_CASE`. For example, to set FireSpread=false
with an environment variable the syntax is:

```
ZOMBOID_FIRE_SPREAD=false
```

### Unified logging

All logs (except those from native code) are now routed to logback via SLF4J.

### Fine-grained metrics

Game Server metrics are exported via HTTP in a format consumable by [Prometheus](https://prometheus.io/).

### Game Server HTTP API

An HTTP server is launched alongside the game server to server requests to a simple HTTP API that responds with
information on the game. This is currently limited to basic information such as world date, players online, etc.

Example request:

```http request
GET /info
Content-Type: application/json

{
   "players": {
       "current": 19,
       "max": 48,
   },
   "world": {
       "daysElapsed": 3.5,
       "weather": "sunny"
   }
}
```

## Usage

**TODO**

## Contributing

**TODO**